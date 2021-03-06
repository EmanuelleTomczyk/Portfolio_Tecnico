create table estoque(
	cod serial primary key, 
	produto varchar, 
	marca varchar,
	preco decimal(7,2), 
	validade date, 
	qtd int, 
	data_compra date, 
	cod_fornecedor varchar, 
	foreign key (cod_fornecedor) references fornecedor(cnpj)
);

insert into estoque
(produto, marca, preco, validade, qtd, data_compra, cod_fornecedor)
values 
('Ração de elefante', 'Elephant', 520.60, '2027-09-04', 8, '2022-02-25', '22.333.333/0001-22'),
('Ração para equinos', 'Horse', 100.00, '2030-07-02', 12, '2021-01-29', '11.222.343/0001-12'); 

select * from estoque;
