package grandchild.grandchild.service;

import grandchild.grandchild.config.JwtTokenUtil;
import grandchild.grandchild.domain.Member;
import grandchild.grandchild.dto.SignupRequest;
import grandchild.grandchild.dto.base.BaseException;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MemberService {
        private final MemberRepository memberRepository;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        @Value("${jwt.token.secret}")
        private String key;
        private Long expireTimeMs = 1000 * 60 * 60L; //1시간

        public boolean registerMember(SignupRequest signupRequest) {
                try {
                        Member newMember = convertSignupRequestToMember(signupRequest);
                        memberRepository.save(newMember);
                        return true;
                } catch (Exception e) {
                        return false;
                }
        }

        private Member convertSignupRequestToMember(SignupRequest signupRequest) {
                Member member = new Member();
                member.setUsername(signupRequest.getUsername());
                member.setAge(signupRequest.getAge());
                String hashedPassword = bCryptPasswordEncoder.encode(signupRequest.getPassword());
                member.setPassword(hashedPassword);
                return member;
        }

        public String login(String username, String password) {

                // 해당 username 이 있는지 확인
                Member selectedUser = memberRepository.findByUsername(username)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_USER_NAME));

                // 비밀번호 틀림
                if (!bCryptPasswordEncoder.matches(password, selectedUser.getPassword())) {
                        throw new BaseException(BaseResponseStatus.WRONG_PASSWORD);
                }

                // username 으로 토큰 생성
                String token = JwtTokenUtil.createToken(selectedUser.getUsername(), key, expireTimeMs);

                return token;
        }

}
