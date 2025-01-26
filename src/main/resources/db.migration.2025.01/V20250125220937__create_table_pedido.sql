CREATE TABLE IF NOT EXISTS pedido (
    id SERIAL PRIMARY KEY,
    data_pedido TIMESTAMP NOT NULL,
    valor_total DECIMAL(15, 2) NOT NULL,
    status_pedido VARCHAR(255) NOT NULL,
    cliente_id BIGINT,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL
);

CREATE INDEX idx_pedido_cliente_id ON pedido(cliente_id);
