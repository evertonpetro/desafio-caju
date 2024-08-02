# Desafio Técnico Caju

## Descrição
API para autorização de transações com cartão de crédito.
A solução encontrada foi criar quatro entidades **balances**, **transactions**, **mcc_category** e **merchants**.

**balances** guarda o saldo de cada categoria
**transactions** registra todas as chamadas realizadas
**mcc_category** guarda as categorias organizadas em intervalos de código, exemplo: mccs entre 5400 até 5499 estão na categoria *FOOD* 
**merchants** registra novos merchants

## Tabela de Conteúdos
1. [Instalação](#instalação)
2. [Uso](#uso)
3. [Características](#características)
4. [Testes](#testes)
5. [Questão aberta](#questão)

## Instalação
Instruções para execução
```bash
./gradlew bootRun
```

## Uso
A autorização é realizada através da chamada abaixo
```bash
curl --location --request GET 'http://localhost:8080/transactions' \
--header 'Content-Type: application/json' \
--data '{
    "account": "123",
    "totalAmount": 100.00,
    "mcc": "5811",
    "merchant": "PADARIA DO ZE               SAO PAULO BR"
}'
```

Após conclusão do processo é possível realizar chamadas à API para consultar dados, exemplo:

Todas as transações realizadas:
```bash
curl --location 'http://localhost:8080/transactions'
```
Carteira:
```bash
curl --location 'http://localhost:8080/balances'
```
Lista de Merchants
```bash
curl --location 'http://localhost:8080/merchants'
```

## Características
Nste projeto foi utilizado o framework reativo *WebFlux* baseado no projeto *Reactor*, biblioteca de programação reativa.

Pensando na simplicidade, o armazenamento dos dados foi feito com ***H2***, um banco de dados escrito em Java e o conector ***R2DBC***, que fornece uma API não-bloqueante totalmente reativa para bancos relacionais.

O driver ***R2DBC*** possui algumas limitações, e a solução foi implementar um componente que funciona como a classe *repository* padrão. A execução das consultas mais complexas ficou por conta de ***DatabaseClient***, uma das classes centrais do *core package* que pertence ao ***R2DBC***.

Para fins de teste, ao executar a aplicação, um conjunto de dados é inserido no banco de dados (Vide arquivo **data.sql** nos *resources*)

## Testes
Instruções para execução dos testes
```bash
./gradlew test
```

## Questão aberta

É necessário garantir que as transações sejam processadas de forma segura e eficiente, sem permitir que duas transações concorrentes causem inconsistências ou falhas.
Abaixo, algumas técnicas que podem ser utilizadas para resolver o problema:

1. **Bloqueios Otimistas (Optimistic Locking)**:

Vantagens: Evita bloqueios pesados. Pode ser mais eficiente em cenários de baixa concorrência.

Desvantagens: Em cenários de alta concorrência, muitas transações podem falhar e degradar a performance.

2. **Bloqueios Pessimistas (Pessimistic Locking)**:

Vantagens: Garante que apenas uma transação possa modificar o saldo por vez, eliminando a necessidade de reexecução.

Desvantagens: Pode causar problemas de performance em sistemas de alta concorrência, pois outras transações ficam aguardando a liberação do bloqueio.

3. **Uso de Filas Distribuídas (Distributed Queues)**:

Vantagens: Simplifica a concorrência, as transações seguem uma fila.

Desvantagens: Pode aumentar a latência, não sendo ideal para sistemas que exigem baixa latência.

4. **Bancos de Dados com Consistência Eventual (Eventual Consistency)**:

Vantagens: Alta disponibilidade e tolerância a falhas. Escalabilidade horizontal.

Desvantagens: Difícil implementação. Requer mecanismos adicionais para garantir a consistência.

A solução mais adequada depende do contexto e necessidades específicas.
Para o desafio em questão, combinar Optimistic Locking, para garantir a integridade dos dados, com Pessimistic Locking em casos de alta concorrência, confere uma abordagem prática. Também o uso de filas distribuídas para gerenciar a concorrência em ambientes que necessitam de alta escalabilidade.

Neste desafio, pode-se utilizar da seguinte forma:
Usar JPA/Hibernate com versões de entidade para implementar o Optimistic Locking.
Para o Pessimistic Locking, a API JPA/Hibernate também oferece suporte a bloqueios exclusivos (LockModeType.PESSIMISTIC_WRITE).
Filas distribuídas podem ser implementadas usando Apache Kafka ou RabbitMQ, por exemplo.
Essa combinação oferece uma boa flexibilidade e desempenho, ao mesmo tempo em que garante a integridade dos dados e a segurança das transações em um ambiente de alta concorrência.
