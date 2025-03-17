# Projeto de Vendas - VR Software

Este projeto é uma aplicação de vendas que inclui um **backend** em **Spring Boot** e um **frontend** em **Java Swing**. O sistema permite que o usuário registre vendas de produtos, visualize históricos de vendas e gerencie o catálogo de produtos e clientes.

## Requisitos para Rodar o Projeto

### Backend
O backend foi desenvolvido utilizando o framework **Spring Boot** com a persistência de dados feita através do **JPA (Hibernate)** e **PostgreSQL**. Para rodar o backend, você precisará de:

1. **JDK 17** (ou superior) - Para compilar e rodar o código Java.
2. **PostgreSQL** - Banco de dados relacional utilizado para persistência de dados.
3. **Maven** - Ferramenta de build para o backend.

### Frontend
O frontend foi desenvolvido com **Java Swing**, utilizando a biblioteca **JDBC** para comunicação com o backend. Para rodar o frontend, você precisará de:

1. **JDK 17** (ou superior) - Para compilar e rodar o código Java.
2. **Eclipse IDE** ou outra IDE que suporte projetos Java.

## Como Rodar o Backend

1. Clone o repositório do backend para sua máquina:

    ```bash
    git clone https://github.com/usuário/nome-do-repositório-backend.git
    ```

2. Navegue até o diretório do projeto:

    ```bash
    cd nome-do-repositório-backend
    ```

3. Abra o arquivo `application.properties` e configure as credenciais do banco de dados PostgreSQL:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/vrsoftware
    spring.datasource.username=postgres
    spring.datasource.password=000001
    spring.jpa.hibernate.ddl-auto=update
    ```

4. Compile e rode o backend utilizando o **Maven**:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

5. O backend estará disponível na URL: `http://localhost:8080`.

## Como Rodar o Frontend

1. O projeto FrontEnd esta junto com o projeto do Backend, portanto estão no mesmo repositório.

2. Abra o projeto no **Eclipse** ou outra IDE Java.

3. Certifique-se de que o **JDK 17** (ou superior) está configurado na sua IDE.

4. Execute a classe `MainFrame` para iniciar o frontend.

## Funcionalidades

- **Tela de Vendas**: Permite realizar vendas de produtos para clientes, com a opção de selecionar o produto, adicionar a quantidade e finalizar a compra.
- **Histórico de Vendas**: Exibe um histórico de todas as vendas realizadas, permitindo filtrar os dados por cliente ou produto.
- **Gerenciamento de Produtos e Clientes**: Possibilita cadastrar, editar e visualizar produtos e clientes no sistema.

## Como Utilizar o Sistema

### Tela de Vendas

1. Na tela de vendas, você pode **pesquisar** produtos e clientes digitando no campo de pesquisa.
2. Ao selecionar um produto, você pode definir a **quantidade** e adicionar o produto ao carrinho de vendas.
3. O valor total será automaticamente calculado com base nos produtos e quantidades selecionadas.
4. Ao finalizar a compra, você pode **salvar a venda** no banco de dados.

### Histórico de Vendas

1. Na tela de histórico de vendas, você pode visualizar todas as vendas realizadas.
2. Utilize o campo de **filtro** para pesquisar por nome do cliente, ID ou descrição do produto.
3. As vendas podem ser visualizadas em uma tabela com os seguintes campos: ID, Cliente, Produto, Data da Venda e Valor Total.

## Contribuição

Sinta-se à vontade para contribuir com o projeto, seja corrigindo bugs, adicionando novas funcionalidades ou aprimorando a documentação.

Para contribuir:

1. Faça um **fork** do repositório.
2. Crie uma nova **branch** para sua feature ou correção de bug.
3. Realize as alterações e faça **commit** das modificações.
4. Abra um **pull request** com uma descrição clara do que foi alterado.

## Licença

Este projeto é licenciado sob a **MIT License** - consulte o arquivo [LICENSE](LICENSE) para mais informações.
