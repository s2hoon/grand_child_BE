package grandchild.grandchild.controller;


import grandchild.grandchild.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import grandchild.grandchild.dto.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody SignupRequest signupRequest) {

        boolean isSuccess = memberService.registerMember(signupRequest);

        if (isSuccess) {
            String responseMessage = "회원가입이 완료되었습니다.";
            return ResponseEntity.ok(responseMessage);
        } else {
            String responseMessage = "회원가입에 실패하였습니다.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
}