create table tb_accounts (
 id serial PRIMARY KEY,
 account_number VARCHAR (20) NOT NULL,
 account_type VARCHAR (20) NOT NULL,
 account_status VARCHAR (20) NOT NULL,
 account_balance DECIMAL(10,2),
 client_document VARCHAR (20) NOT NULL,
 client_id serial NOT NULL,
 created_at TIMESTAMP NOT NULL
);
