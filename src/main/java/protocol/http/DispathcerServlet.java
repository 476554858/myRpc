package protocol.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispathcerServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            new HttpServerHandler().handler(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
