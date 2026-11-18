# Deploy do Blinsights

Arquitetura em produção:

| Parte | Onde | Pasta |
|---|---|---|
| Banco PostgreSQL | Azure Database for PostgreSQL (Flexible Server) — ou Neon | — |
| API Spring Boot | Render (Docker) | `SYNAPSE/BackEnd` |
| ETL Python (FastAPI) | Render (Python) | `SYNAPSE/CienciaD/analytics-python` |
| Front-end Vue | Vercel | `SYNAPSE/FrontEnd` |

A ordem importa: **banco → Python → backend → front**, porque cada parte precisa do endereço da anterior. No Azure há um passo a mais: liberar os IPs do Render no firewall (seção 1).

---

## 1. Banco (Azure Database for PostgreSQL)

Os dados de conexão são os mesmos que você usa no DBeaver (botão direito na conexão → *Edit Connection*):

| DBeaver | Variável |
|---|---|
| Host (`<servidor>.postgres.database.azure.com`) | `DB_HOST` (Python) e parte do `DB_URL` (backend) |
| Database | `DB_NAME` (Python) e parte do `DB_URL` |
| Username | `DB_USER` (Flexible Server: só o nome, **sem** `@servidor`) |
| Password | `DB_PASSWORD` |

Formato JDBC usado pelo backend:

```
jdbc:postgresql://<servidor>.postgres.database.azure.com:5432/<banco>?sslmode=require
```

### Firewall: liberar o acesso do Render

O Azure bloqueia todo IP que não estiver liberado. O DBeaver funciona porque o IP da sua máquina já está na lista, mas o Render tem IPs próprios.

1. Portal Azure → seu servidor PostgreSQL → **Networking** (Rede).
2. Em *Firewall rules*, adicione os IPs de saída do Render: no painel do Render, abra o serviço → **Connect → Outbound** e copie a lista. Crie uma regra para cada IP (início = fim = o IP).
3. Salve. A opção *"Allow public access from any Azure service"* **não** cobre o Render, porque ele não roda no Azure.

> Atalho para testes (menos seguro): uma regra `0.0.0.0` – `255.255.255.255` libera qualquer IP. Nesse caso a proteção fica só na senha, então use uma senha forte e remova a regra depois da apresentação.

### Banco que já tem dados

Não é preciso criar nem apagar tabelas. O `schema.sql` só faz `CREATE TABLE IF NOT EXISTS` e `ADD COLUMN IF NOT EXISTS`, nunca apaga nada, e o Python garante as tabelas que usa. Mesmo assim, antes da primeira subida:

- No DBeaver, faça um backup: botão direito no banco → *Tools → Backup*.
- Depois que a API subir, confira no DBeaver se existem `usuario`, `upload_lote`, `cliente`, `contrato` e `estatistica_lote`.

> **Usando Neon em vez de Azure:** `DB_URL=jdbc:postgresql://<host>.neon.tech/<banco>?sslmode=require`. O Neon não tem firewall de IP, então pule essa etapa.

## 2. Gerar os segredos

Gere dois valores aleatórios diferentes (Git Bash ou WSL):

```bash
openssl rand -base64 48   # JWT_SECRET
openssl rand -hex 32      # ETL_API_KEY (o mesmo valor vai no backend e no Python)
```

## 3. Render: API e ETL

O arquivo `render.yaml` na raiz do repositório cria os dois serviços de uma vez.

1. Render → **New → Blueprint** → escolha este repositório.
2. Preencha as variáveis pedidas:

**blinsights-etl (Python)**

| Variável | Valor |
|---|---|
| `DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` | dados do Azure (os mesmos do DBeaver) |
| `ETL_API_KEY` | o valor gerado no passo 2 |

**blinsights-api (Spring)**

| Variável | Valor |
|---|---|
| `DB_URL` | `jdbc:postgresql://<servidor>.postgres.database.azure.com:5432/<banco>?sslmode=require` |
| `DB_USER`, `DB_PASSWORD` | dados do Azure (os mesmos do DBeaver) |
| `JWT_SECRET` | gerado automaticamente pelo Render |
| `ETL_URL` | URL do serviço Python, **sem barra no final** (ex.: `https://blinsights-etl.onrender.com`) |
| `ETL_API_KEY` | o mesmo valor do Python |
| `CORS_ALLOWED_ORIGINS` | URL do front na Vercel (preencha depois do passo 4) |
| `ADMIN_EMAIL`, `ADMIN_PASSWORD` | conta de administrador criada na primeira inicialização. **Não use a senha padrão.** |

3. Confira se subiu:
   - `https://<api>.onrender.com/api/health` → `{"status":"ok"}`
   - `https://<etl>.onrender.com/saude` → `{"status":"ok"}`

> **Plano gratuito:** os serviços "dormem" após ~15 min sem uso. A primeira requisição depois disso pode levar de 30 a 60 s. O front espera até 60 s (`timeout` do axios).

### Sem Blueprint (manual)

- **API:** New → Web Service → Docker, *Root Directory* `SYNAPSE/BackEnd`, *Health Check Path* `/api/health`.
- **ETL:** New → Web Service → Python, *Root Directory* `SYNAPSE/CienciaD/analytics-python`,
  build `pip install -r requirements.txt`, start `uvicorn api:app --host 0.0.0.0 --port $PORT`.

## 4. Vercel: front-end

1. Vercel → **Add New → Project** → escolha o repositório.
2. **Root Directory:** `SYNAPSE/FrontEnd` (o framework Vite é detectado; o `vercel.json` já configura o build e o rewrite de SPA).
3. **Environment Variables:** `VITE_API_URL` = `https://<api>.onrender.com/api`
4. Deploy. Copie a URL gerada (ex.: `https://blinsights.vercel.app`).
5. Volte ao Render e coloque essa URL em `CORS_ALLOWED_ORIGINS` da API (pode haver várias, separadas por vírgula). Salve para reiniciar.

## 5. Teste final

1. Abra o site e entre com `ADMIN_EMAIL` / `ADMIN_PASSWORD`.
2. Envie uma planilha `.xlsx`, confira o relatório de validação e libere as linhas válidas.
3. Abra o Dashboard e confira os indicadores.
4. Cadastre uma conta nova, aprove-a em **Administração** e entre com ela.

## Problemas comuns

| Sintoma | Causa provável |
|---|---|
| API ou ETL não iniciam: `Connection timed out` / `no pg_hba.conf entry` | firewall do Azure sem os IPs de saída do Render |
| `password authentication failed` | usuário com `@servidor` no nome (Flexible Server não usa) ou senha errada |
| `SSL off` / `SSL required` | faltou `?sslmode=require` no `DB_URL`, ou `DB_SSLMODE` diferente de `require` no Python |
| Erro de CORS no console do navegador | `CORS_ALLOWED_ORIGINS` não tem a URL exata do front (com `https://`, sem barra final) |
| API não inicia: `JWT_SECRET deve possuir ao menos 32 caracteres` | segredo ausente ou curto |
| Upload termina com erro de ETL | `ETL_URL` errada, com `/` no final, ou `ETL_API_KEY` diferente nos dois serviços |
| `401` no ETL | `ETL_API_KEY` diferente entre a API e o Python |
| Recarregar `/sidebar/dashboard` dá 404 na Vercel | *Root Directory* errado, então o `vercel.json` não foi lido |
| Primeira requisição muito lenta | serviço do Render acordando (plano gratuito) |
