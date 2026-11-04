CREATE TABLE IF NOT EXISTS clientes (
    codigo_cliente VARCHAR(50) PRIMARY KEY,
    nome_cliente VARCHAR(200) NOT NULL,
    consultor VARCHAR(120),
    segmento VARCHAR(100),
    nivel_cliente VARCHAR(20),
    faturamento_anual NUMERIC(18,2),
    cidade VARCHAR(120),
    uf VARCHAR(10),
    mes_contratacao VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS contratos (
    id SERIAL PRIMARY KEY,
    codigo_cliente VARCHAR(50) NOT NULL,
    servicos_contratados TEXT,
    data_contratacao DATE
);

CREATE TABLE IF NOT EXISTS resultado_dashboard (
    id SERIAL PRIMARY KEY,
    dados_json JSONB
);
