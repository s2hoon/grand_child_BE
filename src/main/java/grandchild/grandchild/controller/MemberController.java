package grandchild.grandchild.controller;


import grandchild.grandchild.dto.MemberLoginRequest;
import grandchild.grandchild.dto.SignupRequest;
import grandchild.grandchild.dto.base.BaseException;
import grandchild.grandchild.dto.base.BaseResponse;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.service.MemberService;
import grandchild.grandchild.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public BaseResponse<String> registerMember(@RequestBody SignupRequest signupRequest) {
        try {

            memberService.registerMember(signupRequest);
            String result = "회원가입이 완료되었습니다.";
            log.info(signupRequest.getUsername());
            log.info(signupRequest.getPassword());
            return new BaseResponse<String>(BaseResponseStatus.SUCCESS, result);

        } catch (BaseException e) {
            log.info("회원가입 실패");
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/sign-in")
    public BaseResponse<String> login(@RequestBody MemberLoginRequest dto) {
        try {
            String token = memberService.login(dto.getUsername(), dto.getPassword());
            log.info(dto.getUsername());
            log.info(dto.getPassword());
            return new BaseResponse<String>(BaseResponseStatus.SUCCESS, "Bearer " + token);
        } catch (BaseException e) {
            log.info("로그인 실패");
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 구현은 했는데 회원가입이랑 어떻게 결합하는지?
    @GetMapping("/username/{username}")
    public BaseResponse<String> checkUsernameDuplicate(@PathVariable String username) {
        boolean isUsernameAvailable = memberService.checkUsernameDuplicate(username);
        if (isUsernameAvailable) {
            String message = "아이디 사용 가능";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, message);
        } else
            return new BaseResponse<>(BaseResponseStatus.EMAIL_ALREADY_EXIST);
    }
}