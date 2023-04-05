package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {
    // MyView 를 반환하여 view 를 각각의 컨트롤러에서 호출하지 않도록 코드 간소화(공통 코드 제거)
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
