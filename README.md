# People-API

Este projeto consiste em uma api para gerenciamento de pessoas que podem ter diversos endereços.

## Estrutura do Projeto

Este projeto foi estruturado realizando a separação em 6 packages , sendo elas:

### Controllers

Esta package tem por objetivo armazenar o controlador das requisições HTTP realizadas pelo usuário. Fazendo uma ligação direta com a camada de serviços, o controlador será responsável também por fornecer respostas ao usuário de acordo com as requisições e as regras de negócio implementadas na camada de serviço.

### Services

Esta package tem por objetivo armazenar a camada de serviço da aplicação. Nesta camada, todas as regras de negócio serão implementadas para que todas as requisições feitas pelo usuário possam ser respondidas de acordo com as regras impostas na ideação do projeto. Nesta camada os dados enviados são validados, tratados e a intermediação entre o controlador e o repositório é feita para que a requisição seja respondida.

### Repositories

Esta package tem por objetivo armazenar a camada de repositório da aplicação. Esta camada irá implementar uma interface que acessará e manipulará os dados do banco de dados da aplicação. Estando em contato direto com o banco de dados, esta interface
tornará o acesso mais simples e rápido.

### Entities

Esta package tem por objetivo armazenar a camada de entidades da aplicação. Esta camada será responsável por definir as estruturas de dados da aplicação e do banco de dados, e terá contato direto com a camada de repositório.

### DTO

Esta package tem por objetivo armazenar a camada de Data Transfer Objects da aplicação. A camada de DTO é responsável por definir as estruturas de dados usadas para transferir informações entre as diferentes camadas da aplicação ou entre a aplicação e outros sistemas externos. DTOs são projetados para serem simples e leves, contendo apenas os dados necessários para a transferência de informações. A camada de DTO ajuda a separar a lógica de negócios da lógica de transferência de dados e a melhorar o desempenho e a escalabilidade da aplicação.

### Exceptions

Esta package tem por objetivo armazenar as classes de implementação das exceções próprias do projeto. Estas exceções contextualizarão os possíveis erros de uma forma melhor e aprimorarão a experiência do usuário.

## Testes Unitários

Neste projeto foram implementados testes unitários para as camadas Controllers, Services e Repositories. Para executá-los todos juntos, após clonar o projeto na IDE Intellij, selecione a pasta de nome "java" localizada dentro da pasta de nome "test" da aplicação, clique com o botão direito e selecione "Run 'All Tests'". Os testes estão nomeados de acordo com a função de cada teste.

## Instruções para Execução do Projeto

Para executar a aplicação, pode ser usado o botão de Run de cada IDE. Mas caso seja necessário outro método, deve-se localizar a classe "Application", seleciona-lá com o botão direito do mouse e selecionar a opção "Run Application".

### Clonando via Github

Requisitos:
Git : https://git-scm.com  
Na pasta onde se deseja salvar o clone do projeto, clique com o botão direito na área da pasta e selecione a opção "Git Bash Here". Quando o terminal for aberto copie, cole e execute o seguinte comando :

git clone https://github.com/marcosbilobram/PeopleAPi.git

### Importando postman collection

Requisitos : 
Postman: https://www.postman.com

Ao ser realizado o clone ou download do projeto, caso deseje utilizar a ferramenta postman para os testes de métodos HTTP, abra o programa postman, entre ou crie uma workspace, clique no botão "import" localizado no canto superior esquerdo e insira o arquivo ".postman_collection" na drop box ou apenas recupere o mesmo da pasta clonada do projeto.
