package hello.exception_review.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResponseStatusExceptionResovler는 다음 2가지 경우일 때 호출되는 ExceptionResolver
 * 1. @ResponseStatus 에노테이션이 붙어 예외
 * 2. ResponseStatusException 예외
 * -> 특징 : 내부적으로 sendError()를 설정하기에 WAS에서 BasicErrorController를 재호출한다.
 */

// ・HTTP STATUS를 500에서 400(BAD_REQUEST)로 전환
// ・오류 메시지 설정
//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류")
//public class BadRequestException extends RuntimeException{
//}

// 오류 메시지를 코드화할 수도 있다.
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException{
}
