# Lab 3 - Unit tests with dependency mocking

## Upload da BD MySQL com Docker:

docker run --name mysql-test -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=car_service -p 3306:3306 -d mysql:latest

## Testes de API com MockMvc

Além dos testes básicos, é possível fazer testes mais complexos com o MockMvc, incluindo autenticação e autorização.

Exemplo (`CarControllerTest.java`):
```java
mvc.perform(post("/api/cars")
    .header("Authorization", "Bearer token")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonUtils.toJson(car)))
.andExpect(status().isCreated());
```

## JsonUtils

A classe JsonUtils é uma utilidade para converter objetos Java em JSON no contexto de testes. Utiliza o Jackson ObjectMapper para realizar a serialização, garantindo que apenas campos não nulos sejam incluídos no JSON.

```java
public class JsonUtils {
    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
``` 

#### Principais Pontos:
- O método `toJson()` converte um objeto Java num array de bytes JSON.
- `setSerializationInclusion(JsonInclude.Include.NON_NULL)` para omitir campos com valores nulos.
- Facilita Testes de API - Usado principalmente em testes com MockMvc para enviar objetos como conteúdo JSON nas requisições HTTP.

Esta abordagem torna os testes mais limpos e evita a repetição de código de serialização JSON em cada teste.


## Uso de @SpringBootTest com RANDOM_PORT

- **@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)**

A anotação @SpringBootTest com o parâmetro webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT é usada para iniciar o contexto completo da aplicação Spring Boot, incluindo o servidor web embutido (como o Tomcat), numa **porta aleatória**.

Pode-se injetar a porta com @LocalServerPort para uso nos testes.

É ideal para testar controladores e endpoints completos.


## Diferença entre @DataJpaTest e @SpringBootTest

**@DataJpaTest:**
- Focado exclusivamente em testes de repositórios (camada de persistência).
- Carrega apenas os componentes relacionados ao JPA, melhorando o desempenho.
- Usa uma BD embutida (H2) por padrão.
- Exclui componentes como controladores e serviços.

**@SpringBootTest:**
- Carrega o contexto completo da aplicação, incluindo controladores, serviços e repositórios.
- Ideal para testes de integração.
- Permite configurar um servidor embutido para testar endpoints.
- Permite utilizar uma BD real (como MySQL) em testes.

## TestRestTemplate
O @TestRestTemplate é usado para testes que simulam clientes reais, enquanto o MockMvc simula requisições diretamente no contexto da aplicação.

Exemplo (`E_EmployeeRestControllerTemplateIT.java`):
```java
class E_EmployeeRestControllerTemplateIT {
    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;
``` 

