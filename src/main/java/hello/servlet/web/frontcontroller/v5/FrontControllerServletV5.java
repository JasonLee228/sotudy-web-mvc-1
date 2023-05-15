package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3handlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4handlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();

        initHandlerAdapters();
    }

    // handlerMap 을 선언하고, 모든 Controller 형태를 다 수용할 수 있도록 Object 형태의 Map 에 put
    private void initHandlerMappingMap() {

        // v3
        handlerMappingMap.put("/front-controller/v5/V3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/V3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/V3/members", new MemberListControllerV3());

        // v4
        handlerMappingMap.put("/front-controller/v5/V4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/V4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/V4/members", new MemberListControllerV4());
    }

    // 각 ControllerAdapter 생성 후 추가
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3handlerAdapter());
        handlerAdapters.add(new ControllerV4handlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 요청정보로 handlerMap 에서 Handler 조회
        Object handler = getHandler(request);

        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 핸들러를 처리할 수 있는 핸들러 어뎁터 조회
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 핸들러 어댑터를 통해 process 등 핸들 호출하여 작업, modelView 반환
        ModelView modelView = adapter.handle(request, response, handler);

        // viewName 조회
        String viewName = modelView.getViewName();

        // viewResolver 를 통해 실제 view 반환
        MyView view = viewResolver(viewName);

        // 랜더링
        view.render(modelView.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {

        // 추가되어있는 어뎁터들 순회
        for (MyHandlerAdapter adapter : handlerAdapters) {
            // 어뎁터가 특정 핸들러를 지원한다면 사용할 핸들러 지정
            if(adapter.supports(handler)) {
                return adapter;
            }
        }

        // 전체 순회 후에도 찾을 수 없다면 오류
        throw new IllegalArgumentException("handler adapter 를 찾을 수 없습니다. handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        MyView view = new MyView("/WEB-INF/views" + viewName + ".jsp");
        return view;
    }

}
