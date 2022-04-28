create table bilheteria(
	esta_zoo boolean, 
	num_pulseira int primary key, 
	cod_visitante varchar, 
	foreign key (cod_visitante) references visitantes
); 

# ADICIONANDO INFORMAÇÕES A TABELA COM IMPORTAÇÕES:

\copy bilheteria from ‘C:\Users\emanuelle_tomczyk\Documents\bilheteria1.csv’ delimiter ';' csv header;

-- INFORMAÇÕES: 
esta_zoo;num_pulseira;cod_visitante
false;40082767;255.655.525-52
false;40082768;555.666.522-97
false;35679004;255.655.582-45
false;35679005;638.885.319-53
