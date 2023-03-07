package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 서블렛 설정 어노테이션, 서블렛 이름, URL 매핑 - 중복 불가!
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // /hello 경로 호출 시 서블렛 실행, 실행되면 service 메서드 실행
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");

        // 서블렛 요청에서 데이터를 얻을 수 있다.
        String userName = request.getParameter("userName");
        System.out.println("userName = " + userName);

        // 서블릿 응답 세팅 후 반환 데이터 세팅
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + userName);

    }
}
