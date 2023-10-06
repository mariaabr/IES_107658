# IES_107658

## Notebook IES lab02

### Server-side programming with servlets - lab02_1

O Java Servlet ou Jakarta Servlet é um componente de software de servidor Java, projetado para aprimorar os serviços do servidor atualizando os recursos para responder a quaisquer solicitações por meio de uma API web.

Um servlet é uma classe Java que roda no servidor, lida com solicitações (várias simultaneamente), processa estas solicitações e responde.
Um servlet pode ser implementado dentro de um servlet container.
O servlet é um genérico e o HttpServlet é uma extensão dessa interface (mais utilizado).
Um Java servlet define-se como uma tecnologia para projetar e implementar páginas web dinâmicas usando a liguagem de Java.

O primeiro código de teste foi:
### Embedded Jetty Server with ServletHandler

    import java.io.IOException;

    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    import org.eclipse.jetty.server.Connector;
    import org.eclipse.jetty.server.Server;
    import org.eclipse.jetty.server.ServerConnector;
    import org.eclipse.jetty.servlet.ServletHandler;
    
    public class EmbeddedJettyExample {
    
        public static void main(String[] args) throws Exception {
            
            Server server = new Server(8680);       
            
            ServletHandler servletHandler = new ServletHandler();
            server.setHandler(servletHandler);
                    
            servletHandler.addServletWithMapping(HelloServlet.class, "/");
            
            server.start();
            server.join();
    
        }
        
        public static class HelloServlet extends HttpServlet 
        {
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
            {
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<h1>New Hello Simple Servlet</h1>"); 
            } 
        }
    }

Para aceder aos resultados deve-se pesquisar no browser por
http://localhost:8680/, sendo 8680 o número da porta do server.

É, ainda, possível acrescentar parâmetros de entrada utilizando um método chamado getParameter(), sendo o request o pedido.

    String msg = request.getParameter("msg");
        if (msg != null){
            response.getWriter().println("<h1>" + msg + "</h1>");
        }

No browser deve-se pesquisar da seguinte forma:
http://127.0.0.1:8680/?msg=%22Hard%20workers%20welcome!%22.

### Server-side programming and application servers (Tomcat) - lab02_2

Neste exercício utilizamos o Apache Tomcat que foi corrido num Docker container.

Foi feito o download de um repositório git e depois de se fazer as alterações necessárias como imprimir uma mensagem usando parâmetros passados ao URL do servlet o resultado final foi assim:

    package ies.jakartawebstarter;

    import java.io.*;

    import jakarta.servlet.http.*;
    import jakarta.servlet.annotation.*;

    @WebServlet(name = "helloServlet", value = "/hello-servlet")
    public class HelloServlet extends HttpServlet {
        private String message;

        public void init() {
            message = "Refeição do dia!";
        }

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/html");
            
            String comida = request.getParameter("comida");
            String bebida = request.getParameter("bebida");

            // output
            PrintWriter out = response.getWriter();
            out.println("<html><head>");
            out.println("<title>Hello Servlet</title>");
            out.println("</head><body>");
            out.println("<h1>" + message + "</h1>");
            out.println();
            out.println("<h3>Jantar de hoje: " + comida + "</h3>");
            out.println("<h3> Bebida a acompanhar: " + bebida + "</h3>");
            out.println("</body></html>");
        }

        public void destroy() {
        }
    }

Foi necessário acrescentar algumas dependências ao pom.xml, nomeadamente:

```
    <dependency>
        <groupId>jakarta.platform</groupId>
        <artifactId>jakarta.jakartaee-web-api</artifactId>
        <version>10.0.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
```

Foi feito um docker-compose.yml com a aplicação .war para se poder testar o programa.

docker-compose.yml:
    ```
    version: '3'
    services:
    tomcat:
        image: tomcat:10-jre11-openjdk-slim
        ports:
        - "8080:8080"
        volumes:
        - ./target/jakartawebstarter-1.0-SNAPSHOT.war:/usr/local/tomcat/webapps/jakartawebstarter-1.0-SNAPSHOT.war
    ```

No browser pesquisa-se, agora, por:
http://127.0.0.1:8080/jakartawebstarter-1.0-SNAPSHOT/hello-servlet.

### Introduction to web development with a full-featured framework (Spring Boot) - lab02_3

Spring Boot é uma plataforma de desenvolvimento rápido de aplicações construída sobre o Spring Framework. Facilita a criação de aplicações independentes baseados em Spring based Applications, as quais se podem, simplesmente, executar.

Principais características:
- cria aplicações Spring independentes
- incorpora Tomcat, Jetty ou Undertow diretamente ( sem necessidade de implementar arquivos war)
- fornece dependências iniciais opinativas para simplificar a configuração de compilação
- configura automaticamente bibliotecas Spring e de terceiros sempre que possível
- fornece recursos prontos para produção como métricas, verificações de integridade e configuração externalizada
- não tem absolutamente nenhuma geração de código e nenhum requisito para configuração xml

Foi usado o Spring Initializr para criar um novo projeto web app que seja maven-supported e que faça uso do Spring Boot.
Os templates do Spring Initializr contêm uma coleção de todas as dependências transitivas relevantes que são necessárias para iniciar uma funcionalidade específica e simplificar a configuração do pom.
O projeto gerado inclui, também, um script weapper Maven (mvnw).
O Maven Wrapper é uma opção útil para projetos que precisam de uma versão específica do Maven. Em substituição a instalar muitas versões no sistema operativo, pode-se optar por usar, apenas, o script wrapper específico do projeto.

Para correr uma aplicação Spring Boot os comandos a utilizar são:
    $ mvn install -DskipTests && java -jar target\webapp1-0.0.1-SNAPSHOT.ja
    $ ./mvnw spring-boot:run

Ao inicia a aplicação é normal, ao aceder ao browser, recebermos uma página produzida pelo Spring Boot com um Whitelabel error. Este erro pode indicar que a porta que estamos a tentar usar já está em uso.
Para alterar a porta devemos adicionar no ficheiro application.properties (que se encontra dentro da pasta resources):
    server.port=8780

#### Create a Web Controller

Na abordagem do Spring para construção de sites, as solicitações HTTP são tratadas por um controlador identificado pela anotação @Controller.
A classe GreetingController do exemplo trata solicitações GET (@GetMapping) para a página /greeting retornando o nome de uma view. Uma view é responsável por renderizar o conteúdo HTML.

    @Controller
    public class GreetingController {

        @GetMapping("/greeting")
        public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
            model.addAttribute("name", name);
            return "greeting";
        }

    }

Para este exercício foi necessário incluir uma dependência para se usar o Thymeleaf, uma biblioteca Java de código aberto. É uma mecanismo do lado do servidor para ambientes web (baseado em servlet) e não web (offline). Aplica um conjunto de transformações aos arquivos de modelo para exibir dados ou texto produzido pela aplicação.
O Thymeleaf é baseado em tags e atributos xml que definem a execução da lógica predefinida no DOM em vez de escrever explicitamente essa lógica como código.
A sua arquitetura permite o processamento rápido de templates que depende do cache dos arquivos analisados e usa a menor quantidade possível de operações I/O durante a execução.
Analisando o template index.html, o Thymeleaf avalia a expressão **th:text**.

Para concluir o exercício foi necessário criar um endpoint REST que escuta  a solicitação HTTP e responde com um resultado JSON.
A solicitação GET deve retornar uma resposta 200 OK com JSON no corpo que representa a greeting, neste caso.
Para acompanhar esta alteração convém criar uma Resource Representation Class, no nosso caso é a classe Greeting.

    public class Greeting{

        private final long id;
        private final String name;

        public Greeting(long id, String name){

            this.id = id;
            this.name = name;
        }

        public long getId() {
            return this.id;
        }


        public String getName() {
            return this.name;
        }
    }

A variável **id** é um identificador da greeting e a variável **name** é a representação textual da mesma. A classe de representação de recursos é criada para modelar a representação do objeto.

Em vez de se usar a anotação @Controller passa-se a usar a @RestController. É retornado como resultado final uma nova instância de Greeting.

    import java.util.concurrent.atomic.AtomicLong;

    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class GreetingRestController {

        private static final String template = "Hello, %s!";
        private final AtomicLong counter = new AtomicLong();

        @GetMapping("/greeting")
        public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
            return new Greeting(counter.incrementAndGet(), String.format(template, name));
        }
    }

### Wrapping-up & integrating concepts - lab02_4

O objetivo é criar um serviço web (Rest API) para oferecer quotes aleatórias de filmes/séries, tendo em conta que todas as respostas devem estar formatadas em JSON.


| Method | Path                      | Description |
| GET    | api/quote                 | Returns a random quote (show not specified). |
| GET    | api/shows                 | List of all available shows (for which some quote exists). For 
convenience, a show should have some identifier/code. |
| GET    | api/quotes?show=<show_id> | Returns the existing quotes for the specified show/film. |