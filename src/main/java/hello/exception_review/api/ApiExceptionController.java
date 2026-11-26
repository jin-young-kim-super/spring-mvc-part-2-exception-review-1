package hello.exception_review.api;


import hello.exception_review.exception.BadRequestException;
import hello.exception_review.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController // @Controller + @ResponseBody
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable String id) {

        if(id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if(id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }

        if(id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id,"hello" + id);

    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    /**
     * ResponseStatusException의 활용
     * -> @ResponseStatus는 개발자가 코드 변경 불가능한 외부 라이브러리에는 적용이 불가능하고,
     * 에노테이션의 만성적 문제인 동적인 코드 처리가 불가능하다는 것이다.
     * 이럴 때에는 ResponseStatusException 예외를 사용해주면 해결된다.
     */
    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        // 만약 동적으로 HTTP status, 오류 메시지, 터트릴 예외가 있으면 자바 코드등을 이용해서
        // ResponseStatusException 인자를 동적으로 설정하면 된다.
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error.bad",new IllegalArgumentException());
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}
