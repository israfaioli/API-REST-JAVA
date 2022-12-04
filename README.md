### Projeto de automação API ###
URL de estudos para o portifólio: https://fakerestapi.azurewebsites.net/index.html

### Pré-requesitos ###

* Java 8.

* Maven

### Tecnologias Utilizadas ###

* RestAssured
* Junit
* Lombok
* Maven
* Java

### Estrutura do projeto ###

* ci - arquivo que contem arquivo de criação imagem do projeto utilizando maven para pipeline futura CI/CD
* model - repositorio onde criamos nossas instancias de objetos atraves da dependencia lombok
* service - classe de serviço onde iremos realizar a criação das chamadas rest
* steps_definitions - classe destinada aos steps do gherkin em conjunto com classe service
* utils - local de armazenamento das classes se suporte como metodos base rest, enums de headers, leitura de arquivo properties, métodos de criação de dados fake...
* features - local de armazenamento das nossas features cucumber onde demonstramos a idéia nivel de negócio
* payloads - local de armazenamento de nossos arquivos json que utilizaremos nas requisicoes rest de envio de dados
* application.properties - arquivo de mapeamento de todos endpoints

### Rodando o projeto ###

Em alguns casos, a depender de suas configurações locais, pode ser necessário executar o projeto com permissões de administrador adicionando "sudo" antes dos comandos abaixo.

* Acessa a pasta do projeto
* Rode o seguinte comando do maven:

## Execução dos Testes de API ##

```
mvn verify

mvn clean test -Dtest=TestRunner -Dcucumber.options="-t @nome_da_tag"
```