package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4handlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        // instanceof 연산자를 통해 파라미터로 넘어온 handler 객체가 ControllerV4 와 같은 타입일 경우 true 를 반환한다.
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        // ControllerV4 casting
        ControllerV4 controller = (ControllerV4) handler;

        // paramMap, model setting
        Map<String, String> paramMap = createParamMap(request);
        HashMap<String, Object> model = new HashMap<>();

        // controllerV4 의 process 호출
        String viewName = controller.process(paramMap, model);

        // ControllerV4 는 viewName 을 반환한다.
        // ModelView 를 반환하기 위해 생성 및 model 세팅 (어댑터 변환)
        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);

        return modelView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
