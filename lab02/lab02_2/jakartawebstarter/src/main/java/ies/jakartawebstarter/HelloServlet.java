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