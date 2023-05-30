package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller 어노테이션이 붙어 있다면 아래 2의 역할을 수행한다.
 * 1. 컴포넌트 스캔의 대상이 되어 빈으로 자동 등록
 * 2. RequestMappingHandlerMapping 에서 컨트롤러로 인식하여 매핑 정보를 수행한다.
 * -> 스프링 빈 중에서 @RequestMapping | @Controller 가 `클래스 레벨`에 붙어 있는 경우에 매핑정보로 인식
 * -> 스프링에서 어노테이션 기반 컨트롤러로 인식
 */
@Controller
public class SpringMemberFormControllerV1 {

    // 요청 정보 매핑
    @RequestMapping("/springmvc/v1/members/mew-form")
    public ModelAndView process(){

        // view 반환
        return new ModelAndView("new-form");

    }
}
