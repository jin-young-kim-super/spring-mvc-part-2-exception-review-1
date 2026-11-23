package hello.exception_review.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * WAS에 Exception이 전파되거나 response.sendError(4xx,5xx)를 만나면 오류 페이지를 확인하고 컨트롤러를 재호출하는 것뿐 아니라
 * WAS는 어떠한 종류의 오류인지를 request 객체에 담아서 컨트롤러를 재호출한다.
 * -> WAS은 자바 표준 서블릿이 제공하는 RequestDispatcher 인터페이스에 미리 정의된 "ERROR_"로 시작하는 상수를 미리 정의해 놓았으며
 * 그 상수를 통해 에러 내용을 조회할 수가 있다. 아래 ERROR_는 그 일부이다.
 */

@Slf4j
@Controller
public class ErrorPageController {

    // RequestDispatcher에 정의돼 있는 상수 일부
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code";
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";


    @GetMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("error-page 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @GetMapping("/error-page/500")
    public String errorPageEx(HttpServletRequest request, HttpServletResponse response) {
        log.info("error-page 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    /**
     * 이런 오류 메시지를 로그로 반드시 출력해야 한다.
     * -> 그렇지 않으면 개발자가 일일이 코드를 뒤져가며 오류를 찾아 내야 한다.
     */
    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ex=", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE)); //ex의 경우 NestedServletException 스프링이 한번 감싸서 반환
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());

    }

}
