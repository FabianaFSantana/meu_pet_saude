<div align="center">
  
![meu pet saúde](https://github.com/FabianaFSantana/meu_pet_saude/assets/161942930/0427791b-ec45-4545-a8a4-8494e771f216)

# meu_pet_saude
Api Rest para desenvolvimento de um app de caderneta de saúde Pet.

</div>

## Descrição do Projeto
O Meu Pet Saúde é uma API REST desenvolvida com Spring Boot para servir como backend de uma aplicação controle veterinário para cães e gatos de estimação. Ele oferece recursos para manipulação de tutores, pets, controle de vacinas, vermifugação, atiparasitários e consultas verterinárias, assim como envio por email de lembretes para doses de reforço para os tutores, proporcionando uma interface para interação com o banco de dados MySQL.

## Configuração do Ambiente

### Requisitos
Certifique-se de ter as seguintes dependências instaladas em seu ambiente de desenvolvimento:

* [Java 11+](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [MySQL](https://dev.mysql.com/downloads/installer/)

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

## Uso da API
A API possui os seguintes endpoints:

### Tutor:
* `POST /tutor`: Cria um novo tutor.
* `POST /tutor/{idTutor}/adicionarAnimalNaLista/{idAnimal}`: Adciona um animal cadastrado a uma lista de animais do tutor.
* `GET /tutor`: Lista todos os tutores.
* `GET /tutor/{id}`: Obtém informações de um tutor específico.
* `GET /tutor/exibirListaDeAnimaisTutor/{idTutor}`: Exibe lista dos animais do tutor.
* `PUT /tutor/{id}`: Atualiza as informações de um tutor.
* `DELETE /tutor/{id}`: Exclui um tutor.
* `DELETE /tutor/{idTutor}/removerAnimalDaLista/{idAnimal}`: Remove um animal da lista de animais do tutor.

### Animais:
* `POST /animal`: Cria um novo animal.
* `POST /animal/{idAnimal}/adicionarVacinaListaAnimal/{idVacina}`: Relaciona vacina ao animal.
* `POST /animal/{idAnimal}/adicionarVermifugoListaAnimal/{idVerm}`: Relaciona vermífugo ao animal.
* `POST /animal/{idAnimal}/adicionarCarrapListaAnimal/{idCarrap}`: Relaciona carrapticida ao animal.
* `POST /animal/{idAnimal}/adicionarConsultaListaAnimal/{idConsulta}`: Relaciona consulta ao animal.
* `GET /animal`: Lista todos os animais.
* `GET /animal/{idAnimal}`: Obtém informações de um animal específico.
* `GET /animal/exibirListaDeVacinas/{idAnimal}`: Exibe lista de vacinas de um animal específico.
* `GET /animal//exibirListaDeVermifugos/{idAnimal}`: Exibe lista de vermífugos de um animal específico.
* `GET /animal/exibirListaDeCarrapaticidas/{idAnimal}`: Exibe lista de carrapaticidas de um animal específico.
* `GET /animal/exibirListaDeConsultas/{idAnimal}`: Exibe lista de consultas de um animal específico.
* `PUT /animal/{idAnimal}`: Atualiza as informações de um animal.
* `DELETE /animal/{idAnimal}`: Exclui um animal.
* `DELETE /animal/{idAnimal}/removerVacinaDaLista/{idVacina}`: Remove uma vacina da lista de vacinas de um animal.
* `DELETE /animal/{idAnimal}/removerVermifugoDaLista/{idVerm}`: Remove um vermífugo da lista de vermífugos de um animal.
* `DELETE /animal/{idAnimal}/removerCarrapDaLista/{idCarrap}`: Remove um carrapaticida da lista de carrapaticidas de um animal.
* `DELETE /animal/{idAnimal}/removerConsultaListaAnimal/{idConsulta}`: Remove uma consulta da lista de consultas de um animal.

### CarrapatoPulga:
* `POST /carrapatoPulga`: Cria um novo carapaticida.
* `GET /carrapatoPulga`: Lista todos os carrapaticidas.
* `GET /carrapatoPulga/{idCarrap}`: Obtém informações de um carrapaticida específico.
* `GET /carrapatoPulga/data/{data`: Busca um carrapaticida pela data da aplicação.
* `PUT /carrapatoPulga/{idCarrap}`: Atualiza as informações de um carrapaticida.
* `DELETE /carrapatoPulga/{idCarrap}`: Exclui um carrapaticida.

### Vacina:
* `POST /vacina`: Cadastra uma nova vacina.
* `GET /vacina`: Lista todos os carrapaticidas.
* `GET /vacna/{idVacina}`: Obtém informações de uma vacina específica.
* `PUT /vacina/{idVacina}`: Atualiza as informações de uma vacina.
* `DELETE /vacina/{idVacina}`: Exclui uma vacina.

### Vermifugacao:
* `POST /vermifugacao`: Cadastra um novo vermífugo.
* `GET /vermifugacao`: Lista todos os vermífugos.
* `GET /vermifugacao/{idVerm}`: Obtém informações de um vermífugo específico.
* `PUT /vermifugacao/{idVerm}`: Atualiza as informações de um vermífugo.
* `DELETE /vermifugacao/{idVerm}`: Exclui um vermífugo.

### Consulta:
* `POST /consulta`: Cadastra uma nova consulta.
* `GET /consulta`: Lista todas as consultas.
* `GET /consulta/{idConsulta}`: Obtém informações de uma consulta específica.
* `PUT /consulta/{idConsulta}`: Atualiza as informações de uma consulta.
* `DELETE /consulta/{idConsulta}`: Exclui uma consulta.

## Chamando os Endpoints via Postman
Após iniciar a aplicação, você pode acessar a documentação interativa da API por meio Postman. Lá, você encontrará uma interface fácil de usar para explorar e testar os endpoints da API.







