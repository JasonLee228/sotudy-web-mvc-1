package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    // ControllerV1 을 상속받은 사용할 자식 컨트롤러 선언
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/V3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/V3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/V3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // paramMap(요청에서 넘어온 정보) 을 넘겨줘야 한다.
        Map<String, String> paramMap = createParamMap(request);
        // 서블릿을 사용하던 이전 버전과 달리 서블릿을 맵으로 대신하여 종속성을 제거했다.
        ModelView modelView = controller.process(paramMap);

        // 논리이름 조회, 실제 물리경로와 연동시켜줄 ViewResolver 필요
        String viewName = modelView.getViewName();
        MyView view = viewResolver(viewName);

        view.render(modelView.getModel(), request, response);
    }

    /**
     * view 의 논리이름을 가지고 실제 물리경로(이름)을 생성해 준다.
     * 중복되는 경로의 명시를 통합함으로서 만약 view 의 경로가 변경되더라도 여기서만 변경해주면 된다.
     * @param viewName
     * @return
     */
    private MyView viewResolver(String viewName) {
        MyView view = new MyView("/WEB-INF/views" + viewName + ".jsp");
        return view;
    }

    /**
     * HttpServletRequest 에서 parameter 들을 받아와 map 으로 넘겨준다.
     * @param request
     * @return
     */
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
