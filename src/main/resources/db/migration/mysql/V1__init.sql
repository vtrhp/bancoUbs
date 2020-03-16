create table if not exists estoque(
	id integer auto_increment primary key,	
	produto varchar(255),
	quantidade integer,	
	preco double,
	tipo varchar(255),
	industria varchar(255),
	origem varchar(255),
	volume double
)engine = innodb;