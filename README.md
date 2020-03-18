### Título 
Teste Banco UBS

### Pré-requisitos
Banco de Dados MySQL, JDK 8 e Eclipse IDE, Maven

### Guia
Crie um banco de dados
com usuario root e senha t5d34e

Utilize os comandos abaixo para criar o schema.

	create database ubs;
	use ubs;
	
Faça o clone do projeto https://github.com/vtrhp/bancoUbs
Import o projeto no eclipse.

No arquivo Application.Properties na propriedade prop.dir informe o diretório onde os arquivos estão.

Configure o Maven nas variáveis de ambiente.

Execute o projeto como Spring Boot App.

Execute a chamada do endPoint conforme exemplo abaixo, informando o produto e a quantidade na URL.

	http://localhost:8080/api/loja/calculo/RTIX/3
	
execute o shell script verifica_servidor.sh

