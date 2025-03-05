# Lab 2 - Unit tests with dependency mocking

## Solving the exercise guide

### 2.2) 

#### b)

O mocking é usado nesse cenário para:

- Como a equipa ainda não escolheu qual cliente HTTP usar, o mocking permite testar o método findProductDetails sem precisar de uma implementação real do cliente HTTP.

- Chamadas reais à API podem ter custos associados. O mocking permite simular respostas da API sem fazer chamadas reais, economizando recursos durante o desenvolvimento.

- O foco do teste é verificar se o método findProductDetails consegue converter corretamente o JSON em objetos Java (POJOs). O mocking permite isolar essa lógica, simulando respostas JSON controladas para garantir que o parsing funciona como esperado.



### 2.3

#### e)

Apontamentos:

- Teste de integração com a API real:

        O teste de integração (ProductFinderServiceIT) está configurado para usar a implementação real do cliente HTTP (TqsBasicHttpClient), que faz chamadas reais à API remota (https://fakestoreapi.com).

        Quando desligámos a internet, o cliente HTTP não conseguiu resolver o nome de domínio fakestoreapi.com (erro UnknownHost).
        Isso ocorre porque o teste depende de uma conexão com a internet para acessar a API remota.

        Como ambos os testes falharam com o erro UnknownHost, isto confirma que o teste está realmente a tentar aceder à API remota, em vez de usar mocks ou dados fictícios.

#### f)

Diferenças entre os comandos Maven:

##### mvn test:

- Executa apenas os testes unitários (usando o plugin Surefire).
- Ignora testes de integração (classes com sufixo IT).

##### mvn package:

- Compila o código, executa os testes unitários e empacota o projeto (gerando um JAR, WAR, etc.).
- Se os testes unitários falharem, o pacote não será gerado.

##### mvn package -DskipTests=true:

- Compila o código e empacota o projeto, mas ignora todos os testes (unitários e de integração).

##### mvn failsafe:integration-test:

- Executa apenas os testes de integração (usando o plugin Failsafe).
- Ideal para testar a integração com sistemas externos, como APIs remotas.

##### mvn install:

- Compila o código, executa todos os testes (unitários e de integração) e instala o pacote no repositório local do Maven.
- Se algum teste falhar, a instalação não será concluída.


## Apontamentos gerais

### 1. Introdução ao Mocking e Testes de Unidade

#### O que é Mocking?

Mocking é uma técnica usada para simular o comportamento de dependências (como APIs, serviços externos, BD, etc.) durante testes de unidade.

Permite testar uma classe em isolamento, sem depender de implementações reais de suas dependências.

Útil quando:
  - A dependência não está disponível.
  - A dependência tem comportamento imprevisível ou variável.
  - A dependência é lenta ou tem custos associados (ex.: APIs pagas).

### 2. Como usar Mockito

#### Anotações do Mockito:
- `@Mock`: Cria um objeto mock de uma classe ou interface.
- `@InjectMocks`: Injeta os mocks na classe que está sendo testada.
- `@ExtendWith(MockitoExtension.class)`: Integra o Mockito com o JUnit 5.

#### Exemplo de uso:
```java
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StocksPortfolioTest {

    @InjectMocks
    StocksPortfolio portfolio;

    @Mock
    IStockmarketService market;

    @Test
    void getTotalValue(){
        // lógica do teste
    }
```

#### Verificações com Mockito:
verify(mock, times(n)): Verifica se um método foi chamado um número específico de vezes.

Exemplo:
- verify(market, times(2)).lookUpPrice(anyString());

### 3. Testes de Integração
#### O que são testes de integração?

Testes que verificam a interação entre diferentes módulos ou sistemas (ex.: chamadas a APIs reais, BD, etc.).

Diferente dos testes de unidade, que usam mocks, os testes de integração usam implementações reais.

#### Como criar testes de integração:

1) Usar o plugin Failsafe do Maven.
2) Criar classes de teste com o sufixo IT (ex.: ProductFinderServiceIT).
3) Remover o uso de mocks e use implementações reais (ex.: TqsBasicHttpClient).

#### Exemplo:
```java
@Test
    public void testFindProductDetails_ValidProduct() throws IOException {
        TqsBasicHttpClient httpClient = new TqsBasicHttpClient();
        ProductFinderService productFinderService = new ProductFinderService(httpClient);

        Optional<Product> result = productFinderService.findProductDetails(3);

        assertTrue(result.isPresent());
        assertEquals(3, result.get().getId());
        assertEquals("Mens Cotton Jacket", result.get().getTitle());
    }
``` 

#### Executar testes de integração:
    mvn failsafe:integration-test

### 4. Testes de Unidade vs. Testes de Integração

#### Testes de Unidade:
- Focam em testar uma única classe ou método em isolamento.
- Usam mocks para simular dependências.
- Executados com o plugin Surefire (`mvn test`).

#### Testes de Integração:
- Focam em testar a interação entre diferentes componentes ou sistemas.
- Usam implementações reais de dependências.
- Executados com o plugin Failsafe (`mvn failsafe:integration-test`).

