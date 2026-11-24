package hello.exception_review.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Component
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public @Nullable ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response,
            @Nullable Object handler,
            // 컨트롤러에서 던져진 Exception을 파라미터로 전달
            Exception ex
    ) {

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver : 500 to 400 converted");
                // SC_BAD_REQUEST : HTTP STATUS 400
                // response.sendError(...) : 컨트롤러에서 던져진 IllegalArgumentException 예외를
                // sendError(..)와 return new ModelAndVIew를 통해 더 이상의 예외 전파를 차단.
                // 만약 Exception이 IllegalArgumentExcpetion이 아니면 이 예외가 WAS까지 전파됨
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView();
            }
        } catch (Exception e) {
            log.error("resolver ex",e);
        }


        return null;

    }
}
