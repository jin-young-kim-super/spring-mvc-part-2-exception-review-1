package hello.exception_review;

import hello.exception_review.filter.LogFilter;
import hello.exception_review.interceptor.LogInterceptor;
import hello.exception_review.resolver.MyHandlerExceptionResolver;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration -> 이제는 스프링 부트가 기본 제공하는 오류 페이지 기능을 사용하기위해 주석 처리!
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","*.ico",
                        // 인터셉터 등록은 필터 등록처럼 setDispatcherTYpe()를 제공하지 x
                        // -> 그 대신 excludePathPatterns에서 WAS에서 오류 발생 시 재요청하는 오류 컨트롤러의 URL을 넣음으로써
                        // 인터셉터의 중복 호출을 막는 것이 서블릿 필터와의 큰 차이이다.
                        "error", "/error-page/**"
                        );
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
    }

    //@Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        // DispatcerTYpe이 REQUEST나 ERROR일떄에만 필터 적용
        // setDispatcherTyps()를 설정하지 않으면 디폴트로 DispatcherType.REQUEST만 설정된다.
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        return filterRegistrationBean;
    }
}
