# Blinsights

## Fluxo de upload, ETL e dashboard

O fluxo do projeto é:

1. Vue lê a planilha, identifica linhas com erro e envia somente as válidas.
2. Spring valida os DTOs, cria um lote `PROCESSANDO` e encaminha os dados ao FastAPI.
3. FastAPI/pandas normaliza os dados, calcula estatísticas e grava clientes, contratos e JSON do dashboard no PostgreSQL.
4. Spring consulta os lotes e as estatísticas nas rotas `/api/dashboard` e `/api/dashboard/lotes`.
5. Vue mostra os dados persistidos em `/sidebar/dashboard` (e também aceita `/dashboard`).

Neon e Azure PostgreSQL usam o mesmo schema. O backend executa `BackEnd/src/main/resources/schema.sql`; o Python também cria/atualiza as tabelas necessárias ao iniciar o ETL.

## Configuração local

- Copie `BackEnd/.env.example` para `BackEnd/.env` e preencha as credenciais do PostgreSQL.
- Copie `CienciaD/analytics-python/.env.example` para `CienciaD/analytics-python/.env`; use o mesmo host, banco e usuário do backend.
- Configure o mesmo valor forte de `ETL_API_KEY` nos dois `.env`. O backend envia essa chave no cabeçalho `X-ETL-Key`.
- Configure `JWT_SECRET` no backend com pelo menos 32 caracteres aleatórios. O login devolve um JWT, e as rotas protegidas do Java exigem `Authorization: Bearer <token>`.
- `ETL_URL` é opcional e assume `http://localhost:8000`.
- Os arquivos `.env` não devem ser versionados.

## Executar no Windows

Em terminais separados:

```powershell
cd CienciaD\analytics-python
.\.venv\Scripts\Activate.ps1
uvicorn api:app --port 8000
```

```powershell
cd BackEnd
.\mvnw.cmd spring-boot:run
```

```powershell
cd FrontEnd
npm install
npm run dev
```

Abra o front-end, envie a planilha e acesse **Dashboard**. A API Python também disponibiliza `/saude`, `/testar` e `/docs`.

## Testes

```powershell
cd CienciaD\analytics-python
.\.venv\Scripts\python.exe -m unittest discover -s tests -v

cd ..\..\BackEnd
.\mvnw.cmd test

cd ..\FrontEnd
npm run build
```