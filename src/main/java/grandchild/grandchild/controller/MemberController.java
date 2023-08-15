package grandchild.grandchild.controller;


import grandchild.grandchild.dto.MemberLoginRequest;
import grandchild.grandchild.dto.SignupRequest;
import grandchild.grandchild.dto.base.BaseException;
import grandchild.grandchild.dto.base.BaseResponse;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
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

    @GetMapping("/sign-in")
    public BaseResponse<String> login(@RequestBody MemberLoginRequest dto) {

        try {
            String token = memberService.login(dto.getUsername(), dto.getPassword());
            return new BaseResponse<String>(BaseResponseStatus.SUCCESS,"Bearer " + token);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }



}