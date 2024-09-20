<div align="center">
  
![meu pet saúde](https://github.com/FabianaFSantana/meu_pet_saude/assets/161942930/0427791b-ec45-4545-a8a4-8494e771f216)

# meu_pet_saude
Api Rest para desenvolvimento de um app de caderneta de saúde Pet.

</div>

## Descrição do Projeto
O Meu Pet Saúde é uma API REST desenvolvida com Spring Boot para servir como backend de uma aplicação controle veterinário para cães e gatos de estimação. Ele oferece recursos para manipulação de tutores, pets, controle de vacinas, vermifugação, atiparasitários e consultas verterinárias, assim como envio por email de mensagem de aniversário para os tutores e de notificações de lembretes das doses de reforço através de um agendamento de tarefas, proporcionando uma interface para interação com o banco de dados MySQL. Fonerce segurança através da autenticação do usuário e seu cadastro por meio de login utilizando o Spring Security, assim como a autenticação de token através do JWT, para possibilitar a segurança do usuário.

## Configuração do Ambiente

### Requisitos
Certifique-se de ter as seguintes dependências instaladas em seu ambiente de desenvolvimento:

* [Java 11+](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [MySQL](https://dev.mysql.com/downloads/installer/)

### Requisitos Adicionais
Será necessário para o envio das notificações e para testá-las criar contas no:
* [Mailtrap](https://mailtrap.io)

### Instalação
1. Clone o repositório:
```
git clone https://github.com/FabianaFSantana/meu_pet_saude.git
```
2. No terminal, navegue até o diretório do projeto:
```
cd meu_pet_saude-app
```
3. Configure o banco de dados:
Certifique-se de que um servidor MySQL esteja em execução e crie um banco de dados chamado "meu_pet_saude".
```
CREATE DATABASE meu_pet_saude;
```
4. Configure as propriedades do banco de dados:
Se for o caso, edite o arquivo `src/main/resources/application.properties` e ajuste as configurações de conexão com o servidor MySQL:
```
spring.datasource.url=jdbc:mysql://localhost:3306/meu_pet_saude
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
```
Modifique, também, as configurações da conexão com o Mailtrap de acordo com as credenciais presentes na sua conta do Mailtrap:
```
spring.mail.port=PORT
spring.mail.username=USERNAME
spring.mail.password=PASSWORD
spring.mail.properties.mail.smtp.auth=AUTH
```
Acrescente sua chave e seu token para ativar e configurar o Spring Security.
```
spring.security.secret-key=SUA_CHAVE
spring.security.token-issuer=SEU_TOKEN-ISSUER
5. Execute a aplicação:
```
mvn spring-boot:run
```
O servidor estará acessível em `http://localhost:8080` por padrão.

## Estrutura do Projeto
O projeto é estruturado da seguinte forma:
* `com.meu_pet_saude.api.constant`: Constant para definir o gênero e a espécie do animal e o tipo de consulta (rotina ou emergência).
* `com.meu_pet_saude.api.controller`: Controladores para manipular as requisições HTTP.
* `com.meu_pet_saude.api.model`: Modelos de dados para representar Tutor, ViaCepEndereco, Animal, Vacina, CarrapatoPulga (carrapaticidas), Vermifugacao e Consulta.
* `com.meu_pet_saude.api.repository`: Repositórios para interação com o banco de dados.
* `com.meu_pet_saude.api.service`: Servicos de ViaCepEnderco (para acessar a API externa ViaCep), Animal, Vacina, CarrapatoPulga, Vermifugacao e Consulta para criar os métodos para relacionar Tutor a Animal, Enderço via cep a Tutor e vacinas, carrapaticidas, vermífugos e consultas a Animal.
* `com.meu_pet_saude.api.security`: Filtro e Configuração para permitir ou não o acesso à API.
* `com.meu_pet_saude.api.scheduler`: Agendador de tarefas para enviar mensagem de aniversário para o tutor, assim como envio de notificações para lembretes de doses de reforço das vacinas, vermifugos e carrapaticidas do pet.

## Uso da API
A API possui os seguintes endpoints:

### Autenticacao:
*`POST /login`: Permite o acesso do usuário à API por meio de email e senha.

### Tutor:
* `POST /tutor`: Cadastra um novo tutor.
* `GET /tutor`: Lista todos os tutores.
* `GET /tutor/{id}`: Obtém informações de um tutor específico.
* `GET /tutor/exibirListaDeAnimaisTutor/{idTutor}`: Exibe lista dos animais do tutor.
* `PUT /tutor/{id}`: Atualiza as informações de um tutor.
* `DELETE /tutor/{id}`: Exclui um tutor.

### Animal:
* `POST /animal/{tutor_id}`: Cria um novo animal e o adiciona à lista de animais do tutor.
* `GET /animal`: Lista todos os animais.
* `GET /animal/{animal_id}`: Obtém informações de um animal específico.
* `GET /animal/exibirListaDeVacinas/{animal_id}`: Exibe lista de vacinas de um animal específico.
* `GET /animal/exibirListaDeVermifugos/{animal_id}`: Exibe lista de vermífugos de um animal específico.
* `GET /animal/exibirListaDeCarrapaticidas/{animal_id}`: Exibe lista de carrapaticidas de um animal específico.
* `GET /animal/exibirListaDeConsultas/{idAnimal}`: Exibe lista de consultas de um animal específico.
* `PUT /animal/{animal_id}`: Atualiza as informações de um animal.
* `DELETE /animal/{tutor_id}/excluirAnimal/{animal_id}`: Exclui um animal e o remove da lista de animais do tutor.

### Carrapaticida:
* `POST /carrapaticida/{animal_id}`: Cadastra uma nova dosagem de carapaticida e adiciona à lista de carrapaticidas do animal.
* `GET /carrapaticida/{carrap_id}`: Obtém informações de um carrapaticida específico.
* `PUT /carrapaticida/{carrap_id}`: Atualiza as informações de um carrapaticida.
* `DELETE /carrapaticida/{animal_id}/ecluirCarrapaticida/{carrap_id}`: Exclui um carrapaticida e o remove da lista de carrapaticidas do animal.

### Vacina:
* `POST /vacina/{animal_id}`: Cadastra uma nova vacina e a adiciona na lista de vacinas do animal.
* `GET /vacna/{vacina_id}`: Obtém informações de uma vacina específica.
* `PUT /vacina/{vacina_id}`: Atualiza as informações de uma vacina.
* `DELETE /vacina/{animal_id}/excluirVacina/{vacina_id}`: Exclui uma vacina e a remove da lista de vacinas do animal.

### Vermifugacao:
* `POST /vermifugacao/{animal_id}`: Cadastra uma nova dosagem de vermífugo e a adiciona na lista de vermífugos do animal.
* `GET /vermifugacao/{verm_id}`: Obtém informações de um vermífugo específico.
* `PUT /vermifugacao/{verm_id}`: Atualiza as informações de um vermífugo.
* `DELETE /vermifugacao/{animal_id}/excluirvermifugo/{verm_id}`: Exclui uma dosagem de vermífugo e a remove da lista de vermífugos do animal.

### Consulta:
* `POST /consulta/{animal_id}`: Cadastra uma nova consulta e a adiciona na lista de consultas do animal.
* `GET /consulta/periodo"`: Lista todas as consultas realizadas em um determinado período.
* `GET /consulta/{consulta_id}`: Obtém informações de uma consulta específica.
* `GET /consulta/tipo/{animal_id}"`: Lista todas as consultas do animal de acordo com o tipo (rotina ou urgência).
* `PUT /consulta/{consulta_id}`: Atualiza as informações de uma consulta.
* `DELETE /consulta/{animal_id}/excluir/{consulta_id}`: Exclui uma consulta e a remove da lista de consultas do animal.

## Chamando os Endpoints via Postman
Após iniciar a aplicação, você pode acessar a documentação interativa da API por meio Postman. Lá, você encontrará uma interface fácil de usar para explorar e testar os endpoints da API.







