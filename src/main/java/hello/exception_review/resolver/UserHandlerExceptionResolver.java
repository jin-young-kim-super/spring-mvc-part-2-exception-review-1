package hello.exception_review.resolver;

import hello.exception_review.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public @Nullable ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {

        try{
            if(ex instanceof UserException) {
                log.info("UserException resolver 500 to 400 converted");
                // Http 상태 코드 500 -> 400
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                String accept = request.getHeader("accept");
                // 요청이 API 요청인 경우!
                if("application/json".equals(accept)) {
                    HashMap<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex",ex.getClass());
                    errorResult.put("message",ex.getMessage());

                    // 객체(errorResult)를 JSON(문자열)로 변환
                    String result = objectMapper.writeValueAsString(errorResult);

                    // 아래 3줄로 인해 WAS는 컨트롤러 재호출을 안 한다.
                    // ->왜냐하면 sendError가 아니고 정상 response로 여기서 바꿔치기 했기 때문이다.
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView();
                }
            } else {
                return new ModelAndView("error/500");
            }

        } catch (Exception e) {
            log.error("resolver ex",e);
        }
        return null;
    }
}
