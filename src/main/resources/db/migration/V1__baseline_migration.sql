CREATE TABLE IF NOT EXISTS conta (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   valor DECIMAL(19, 2) NOT NULL,
   situacao VARCHAR(10) NOT NULL,
   descricao VARCHAR(255) NOT NULL,
   data_vencimento date NOT NULL,
   data_pagamento date,
   CONSTRAINT pk_conta PRIMARY KEY (id)
);