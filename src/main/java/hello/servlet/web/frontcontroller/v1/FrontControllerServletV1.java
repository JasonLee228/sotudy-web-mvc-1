package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*") // * 를 붙임으로서 해당 경로 하위의 모든 경로가 호출되면 이 서블렛에서 받아들인다.
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // ControllerV1 을 상속받은 사용할 자식 컨트롤러 선언
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletV1.service");

        // request 에서 요청 URI 획득
        String requestURI = request.getRequestURI();

        // requestURI 와 동일한 경로의 컨트롤러 호출
        ControllerV1 controller = controllerMap.get(requestURI);
        if(controller == null) {
            // map 에 존재하지 않는 경로로 요청 시 - 404 NOT_FOUND
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 해당하는 URI controller 호출, 다형성에 의해서 필요한 controller 로 전달(override 된 메소드로 전달)
        controller.process(request, response);
    }
}
