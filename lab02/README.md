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

``` import java.io.IOException;

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
    }  ```

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

### Introduction to web development with a full-featured framework (Spring 
Boot) - lab02_3

### Wrapping-up & integrating concepts - lab02_4

O objetivo é criar um serviço web (Rest API) para oferecer quotes aleatórias de filmes/séries, tendo em conta que todas as respostas devem estar formatadas em JSON.

Method | Path | Description
GET | api/quote | Returns a random quote (show not specified).
GET | api/shows | List of all available shows (for which some quote exists). For 
convenience, a show should have some identifier/code.
GET | api/quotes?show=<show_id> | Returns the existing quotes for the specified show/film
