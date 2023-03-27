package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /servlet-mvc/members/new-form 경로로 호출하면 servlet 을 먼저 타고,
 * servlet 에서는 viewPath 를 호출한다. (서버 내부 호출)
 * WEB-INF 하위에 있는 JSP 는 외부에서 직접 호출 할 수 없다. -> forward 를 거쳐야 함
 */
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp"; // jsp 경로
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        // 선언한 경로 호출 - 다른 서블릿이나 JSP 로 이동할 수 있는 기능
        // * 서버 내부 호출, redirect 가 아니기 때문에 client 는 알 수 없음.
        dispatcher.forward(req, res);
    }
}
