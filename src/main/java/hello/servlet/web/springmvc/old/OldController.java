package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/springmvc/old-controller") // 빈의 이름 지정을 url 경로로 지정
public class OldController implements Controller {

    // 빈의 이름으로 핸들러를 찾을 수 있다: BeanNameUrlHandlerMapping
    // localhost:8080/springmvc/old-controller 경로 호출되면 다음과 같은 단계 지정

    /**
     * 0: @RequestMapping 에 잡히는 핸들러 있는지 확인 - RequestMappingHandlerMapping
     * 1: spring 빈의 이름으로 핸들러를 찾음 - BeanNameUrlHandlerMapping -> 여기서 걸리는 것
     */

    // Controller 인터페이스의 어댑터 처리
    // 스프링의 핸들러 어댑터 우선순위

    /**
     * 0: @RequestMapping 전용 - RequestMappingHandlerAdapter
     * 1: HttpRequestHandler 전용 - HttpRequestHandlerAdapter
     * 2: Controller 인터페이스 전용 - SimpleControllerHandlerAdapter -> 이거로 걸림!
     *    DispatcherServlet 에서 해당 클래스의 handle 메소드 호출됨
     */

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return null;
    }
}
