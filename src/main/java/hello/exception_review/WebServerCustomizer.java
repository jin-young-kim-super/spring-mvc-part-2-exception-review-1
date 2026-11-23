package hello.exception_review;

import org.springframework.boot.web.error.ErrorPage;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 과거에는 web.xml 설정을 통해 서블릿의 오류 페이지 기능을 사용했지만, 현재는 스프링 부트로
 * 오류 페이지 기능을 등록하는 것이 일반적이다.
 * WebServerFactoryCustomizer : 스프링이 제공하는 기능이며, 웹 서버 설정을 바꾸고 싶을 떄 사용
 */
@Component // 스프링 부트가 이걸 자동 빈 등록하도록 해줘야 한다
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

        // HttpStatus.NOT_FOUND : 404에러 발생 시
        // "/error-page/404" : WAS에서 이 주소의 컨트롤러를 재호출해라!
        @Override
        public void customize(ConfigurableWebServerFactory factory) {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
            ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
            factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
        }
}
