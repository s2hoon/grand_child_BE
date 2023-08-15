package grandchild.grandchild.controller;


import grandchild.grandchild.dto.MemberLoginRequest;
import grandchild.grandchild.dto.SignupRequest;
import grandchild.grandchild.dto.base.BaseException;
import grandchild.grandchild.dto.base.BaseResponse;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public BaseResponse<String> registerMember(@RequestBody SignupRequest signupRequest) {
        try {

                memberService.registerMember(signupRequest);
                String result = "회원가입이 완료되었습니다.";
                return new BaseResponse<String>(BaseResponseStatus.SUCCESS, result);

            } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
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