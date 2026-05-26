 Projeto Vendas - Chain of Responsibility

Este projeto Java é um sistema simples de vendas baseado em JDBC, com entidades `Seller` e `Department`, implementações DAO e testes de execução via classes `program` e `Program2`.

A versão atual evolui o projeto original adicionando o padrão de projeto Chain of Responsibility, aplicado principalmente em dois pontos: validação de entidades e auditoria de operações.

 Versão original

Na versão original, o projeto possuía uma estrutura tradicional de CRUD com JDBC:

- Entidades de domínio:
  - `Seller`
  - `Department`
- Interfaces DAO:
  - `SellerDao`
  - `DepartmentDao`
- Implementações JDBC:
  - `SellerDaoJDBC`
  - `DepartmentDaoJDBC`
- Fábrica de DAOs:
  - `DaoFactory`
- Conexão com banco:
  - `DB`
  - `db.properties`

O foco da versão original era executar operações básicas no banco de dados, como:

 Buscar vendedor por ID.
 Buscar vendedores por departamento.
 Listar todos os vendedores.
 Inserir vendedor.
 Atualizar vendedor.
 Excluir vendedor.
 Fazer operações equivalentes com departamentos.

Limitação da versão original

A versão original não possuía uma estrutura clara para validar entidades antes das operações no banco.

Isso significa que regras como:

 Nome do vendedor não pode ser vazio.
 E-mail precisa ser válido.
 Salário precisa ser positivo.
 Vendedor precisa ter departamento.
 Nome do departamento não pode ser vazio.

ficariam misturadas diretamente dentro dos métodos dos DAOs, como `insert` e `update`.

Esse tipo de abordagem aumenta o acoplamento e dificulta a manutenção, porque cada nova regra exigiria alteração direta nos DAOs.

 Mudanças realizadas

 1. Criação da cadeia de validação

Foi criado o pacote:

```text
src/model/validation
```

Nele foram adicionadas classes e interfaces para representar uma cadeia de validadores.

Principais arquivos:

 `Validator<T>`
 `BaseValidator<T>`
 `ValidationException`
 `SellerNameValidator`
 `SellerEmailValidator`
 `SellerSalaryValidator`
 `SellerDepartmentValidator`
 `DepartmentNameValidator`
 `SellerValidatorFactory`
 `DepartmentValidatorFactory`

Cada validador é responsável por uma regra específica.

Exemplo da cadeia de validação de `Seller`:

```text
SellerNameValidator
        ↓
SellerEmailValidator
        ↓
SellerSalaryValidator
        ↓
SellerDepartmentValidator
```

Se uma validação falhar, uma `ValidationException` é lançada e a operação no banco é interrompida.

 2. Criação da cadeia de auditoria

Foi criado o pacote:

```text
src/model/audit
```

Nele foram adicionadas classes para registrar operações realizadas no sistema.

Principais arquivos:

 `AuditHandler`
 `BaseAuditHandler`
 `ConsoleAuditHandler`
 `FileAuditHandler`
 `AuditFactory`

A cadeia de auditoria atual funciona assim:

```text
ConsoleAuditHandler
        ↓
FileAuditHandler
```

Ou seja, uma operação pode ser registrada no console e também em arquivo.

Exemplo de saída:

```
[AUDIT] Operation: INSERT_SELLER | Entity: Seller [...]
```

 3. Integração com os DAOs

As classes `SellerDaoJDBC` e `DepartmentDaoJDBC` foram preparadas para usar as cadeias.

Antes de operações como `insert` e `update`, a entidade passa pela cadeia de validação.

Depois de operações como `insert`, `update` e `delete`, a operação passa pela cadeia de auditoria.

Com isso, os DAOs continuam responsáveis pelo acesso ao banco, mas deixam de concentrar diretamente todas as regras de validação e registro.

 4. Configuração para uso com H2

O projeto foi configurado para rodar também com banco H2 em memória.

Arquivos relacionados:

 `h2.jar`
 `init.sql`
 `db.properties`

O arquivo `init.sql` cria as tabelas e insere dados iniciais para teste.

Isso permite executar o projeto sem depender de um MySQL local configurado.


 Exemplo prático da validação

Na classe `program`, ao tentar inserir um vendedor com e-mail inválido, por exemplo:

```java
new Seller(null, "Guilherme", "guigmail.com", new Date(), 4000.0, department)
```

A cadeia identifica que o e-mail não contém `@` e lança:

```text
ValidationException: Email do vendedor inválido
```

Isso prova que a validação acontece antes da execução do SQL.

Como executar com H2

Compile o projeto e execute incluindo o `h2.jar` no classpath:

```powershell
java -cp "bin;h2.jar" application.program
```

O banco H2 será criado em memória e o script `init.sql` será executado automaticamente.

 Relação com o padrão Chain of Responsibility

O padrão Chain of Responsibility permite passar uma requisição por uma sequência de objetos, onde cada objeto pode processar uma parte da requisição ou repassá-la ao próximo.

Neste projeto:

 Cada validador é um elo da cadeia.
 Cada handler de auditoria é um elo da cadeia.
 As factories montam a ordem dos elos.
 Os DAOs apenas disparam a cadeia, sem conhecer todos os detalhes internos.

 Benefícios obtidos

 Código mais organizado.
 Menor acoplamento entre DAOs e regras de validação.
 Facilidade para adicionar novas validações.
 Facilidade para adicionar novos tipos de auditoria.
 Melhor demonstração de um padrão GoF em um projeto Java real.

 Próxima evolução possível

Para atender a etapa avançada da proposta acadêmica, o projeto ainda pode evoluir com:

 Uma anotação própria, como `@ValidationStep`.
 Reflexão para descobrir validadores anotados.
 Montagem automática da cadeia sem precisar declarar manualmente cada `setNext` nas factories.

Essa evolução reduziria ainda mais o acoplamento entre as factories e os validadores concretos.
