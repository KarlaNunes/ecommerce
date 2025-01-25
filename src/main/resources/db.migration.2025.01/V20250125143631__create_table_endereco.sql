CREATE TABLE IF NOT EXISTS endereco (
    id SERIAL PRIMARY KEY,
    rua VARCHAR(255) NOT NULL,
    numero VARCHAR(50),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    cep VARCHAR(20),
    cliente_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);