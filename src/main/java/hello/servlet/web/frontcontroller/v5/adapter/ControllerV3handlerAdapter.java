package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3handlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        // instanceof 연산자를 통해 파라미터로 넘어온 handler 객체가 ControllerV3 와 같은 타입일 경우 true 를 반환한다.
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        // supports 메서드를 통해 ControllerV3 형태의 핸들러인지 파악했기 때문에 파라미터의 핸들러를 ControllerV3 로 캐스팅
        ControllerV3 controller = (ControllerV3) handler;

        // ControllerV3 의 frontController 에서의 createParamMap 메소드를 가져와 동일한 방식으로 paramMap 객체 생성
        Map<String, String> paramMap = createParamMap(request);

        // ControllerV3 의 process 메소드 호출
        ModelView modelView = controller.process(paramMap);

        return modelView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
