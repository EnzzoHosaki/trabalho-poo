# Sistema de Gerenciamento de Atividades e Projetos

Este projeto é um sistema de gerenciamento de atividades e projetos desenvolvido em Java, utilizando JavaFX para a interface gráfica e MySQL para o armazenamento de dados. O sistema permite o cadastro de usuários, projetos e atividades, com funcionalidades de acompanhamento de progresso, notificações e geração de relatórios.

## Funcionalidades

*   **Autenticação de Usuários:**
    *   Cadastro de usuários (Administradores e Usuários Comuns).
    *   Login seguro com hashing de senhas (jBCrypt).
*   **Gerenciamento de Projetos (CRUD):**
    *   Criação, edição e exclusão de projetos (apenas por administradores).
    *   Cada projeto possui:
        *   Nome
        *   Descrição
        *   Data de início e término
        *   Status (Dentro do Prazo, Atrasado, Concluído)
        *   Lista de responsáveis (implementação futura)
*   **Gerenciamento de Atividades (CRUD):**
    *   Criação, edição e exclusão de atividades associadas a projetos.
    *   Cada atividade possui:
        *   Nome
        *   Descrição
        *   Data de início e término
        *   Status (Dentro do Prazo, Atrasado, Concluído)
        *   Porcentagem de conclusão
        *   Justificativa (para atividades não concluídas)
        *   Responsáveis (implementação futura)
*   **Acompanhamento de Progresso:**
    *   Exibição do progresso geral do projeto (em desenvolvimento).
    *   Exibição do status das atividades (em desenvolvimento).
    *   Alertas visuais para prazos (em desenvolvimento).
*   **Dashboard:**
    *   Listagem de projetos com nome, data de término e status (em desenvolvimento).
    *   Botão "Criar Projeto" (visível apenas para administradores).
*   **Notificações:** (Planejado)
    *   Alertas por email ou na interface sobre prazos.
*   **Relatórios:** (Planejado)
    *   Geração de relatórios em PDF.
*   **Filtros e Buscas:** (Planejado)
    *   Filtros para projetos e atividades (por status, responsável, etc.).
*   **Integração com Calendário:** (Planejado)
    *   Integração com Google Calendar ou similar.

## Tecnologias Utilizadas

*   **Linguagem:** Java
*   **Interface Gráfica:** JavaFX
*   **Banco de Dados:** MySQL
*   **Gerenciador de Dependências:** Maven
*   **Hashing de Senhas:** jBCrypt
*   **Conexão com Banco de Dados:** JDBC
*   **Geração de PDF:** (Planejado - Apache PDFBox ou iText)
* **Integração com calendário:** (Planejado)

## Pré-requisitos

*   **JDK 11 (ou superior):**  É *altamente recomendável* usar o JDK 11, pois é uma versão LTS (Long-Term Support) e oferece melhor compatibilidade.  Você pode baixar o JDK 11 da Oracle, Amazon Corretto, AdoptOpenJDK/Temurin, ou outra distribuição.
*   **Maven:**  O Maven é usado para gerenciar as dependências e construir o projeto.
*   **MySQL:**  Você precisa de um servidor MySQL instalado e em execução.  Você pode usar o XAMPP, WampServer, MAMP, ou instalar o MySQL diretamente.
*   **IDE (Recomendado):**  Um ambiente de desenvolvimento integrado (IDE) como IntelliJ IDEA, Eclipse ou NetBeans facilita muito o desenvolvimento.

## Configuração

1.  **Banco de Dados:**
    *   Crie um banco de dados MySQL chamado `gestao_atividades`.
    *   Execute o script SQL fornecido (no arquivo `script.sql` ou na documentação) para criar as tabelas necessárias.
    *   Crie um usuário no MySQL com permissões de acesso ao banco de dados `gestao_atividades`.  *Evite usar o usuário `root` em produção.*

2.  **Configuração da Conexão:**
    *   Abra o arquivo `src/main/java/com/example/util/DBConnection.java`.
    *   Ajuste as constantes `URL`, `USER` e `PASSWORD` para corresponder às configurações do seu banco de dados MySQL.

3.  **Variáveis de Ambiente (Maven):**
     *  Certifique que você possui o Maven instalado
     *   Configure as variáveis de ambiente `M2_HOME` e `MAVEN_HOME` (opcional) apontando para a pasta de instalação do Maven.
     *   Adicione `%M2_HOME%\bin` à variável de ambiente `PATH`.

4.  **Configuração do Projeto no IDE (Recomendado):**

    *   Importe o projeto Maven no seu IDE (IntelliJ IDEA, Eclipse, NetBeans).  O IDE deve detectar automaticamente o `pom.xml` e configurar o projeto.
    *   Configure o JDK do projeto para o JDK 11 (ou superior).
    *   Configure o Maven do projeto para usar a sua instalação do Maven (em vez da versão embutida no IDE), caso tenha problemas.

## Executando o Projeto

1.  **Usando o Maven (Linha de Comando):**

    *   Abra um terminal/prompt de comando na pasta raiz do projeto (onde está o `pom.xml`).
    *   Execute o seguinte comando:

        ```bash
        mvn clean javafx:run
        ```

2.  **Usando o IDE:**

    *   Clique com o botão direito no arquivo `MainApp.java` e selecione "Run" (ou similar).  O IDE deve usar o Maven internamente para construir e executar o projeto.

## Próximos Passos (Desenvolvimento):

*   Implementar as funcionalidades restantes, conforme a documentação.
*   Adicionar testes unitários e de integração.
*   Refatorar o código para melhorar a organização e manutenibilidade.
*   Implementar recursos mais avançados (relatórios PDF, integração com calendário, etc.).
*   Melhorar a interface gráfica (CSS, layouts mais complexos, etc.).
* Tratar de forma mais robusta as exceptions

## Contribuições

Contribuições são bem-vindas! Se você quiser contribuir com o projeto, siga estes passos:

1.  Faça um fork do repositório.
2.  Crie uma branch para a sua feature (`git checkout -b feature/sua-feature`).
3.  Faça commit das suas alterações (`git commit -m 'Adiciona funcionalidade X'`).
4.  Faça push para a sua branch (`git push origin feature/sua-feature`).
5.  Abra um Pull Request.
