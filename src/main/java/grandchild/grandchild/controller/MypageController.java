package grandchild.grandchild.controller;

import grandchild.grandchild.domain.Member;
import grandchild.grandchild.dto.PasswordRequest;
import grandchild.grandchild.dto.base.BaseResponse;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.service.MemberService;
import grandchild.grandchild.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/mypage")
@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MypageController {

    private final MypageService mypageService;
    private final MemberService memberService;

    @GetMapping("/getinfo")
    public BaseResponse<String> memberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return new BaseResponse<>(BaseResponseStatus.INVALID_MEMBER_JWT);

        String username = authentication.getPrincipal().toString();
        Member member = mypageService.loadMemberByUsername(username);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS, member.getUsername());
    }

    @PutMapping("/password")
    public BaseResponse<String> changePassword(
            @RequestBody PasswordRequest passwordResponse
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.info("회원 정보 없음");
            return new BaseResponse<>(BaseResponseStatus.INVALID_MEMBER_JWT);
        }

        String username = authentication.getPrincipal().toString();
        String currentPassword = passwordResponse.getCurrentPassword();
        String newPassword = passwordResponse.getNewPassword();

        log.info(currentPassword);
        log.info(newPassword);

        boolean passwordChanged = memberService.changePassword(username, currentPassword, newPassword).getIsSuccess();
        if (passwordChanged) {
            String message = "비밀번호 변경 완료";
            return new BaseResponse<>(BaseResponseStatus.SUCCESS, message);
        } else {
            return new BaseResponse<>(BaseResponseStatus.WRONG_PASSWORD);
        }
    }
}
