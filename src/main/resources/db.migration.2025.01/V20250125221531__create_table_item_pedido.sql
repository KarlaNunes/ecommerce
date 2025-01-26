CREATE TABLE item_pedido (
     id SERIAL PRIMARY KEY,
     quantidade INTEGER NOT NULL,
     valor_unitario DECIMAL(10, 2) NOT NULL,
     pedido_id BIGINT NOT NULL,
     produto_id BIGINT NOT NULL,
     CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
     CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
);
