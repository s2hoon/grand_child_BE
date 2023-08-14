package grandchild.grandchild.service;

import grandchild.grandchild.domain.Member;
import org.springframework.stereotype.Service;
import grandchild.grandchild.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import grandchild.grandchild.dto.SignupRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;



@Service
public class MemberService {

        private final MemberRepository memberRepository;

        @Autowired
        public MemberService(MemberRepository memberRepository) {
                this.memberRepository = memberRepository;
        }

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
                member.setUsername(signupRequest.getName());
                member.setAge(signupRequest.getAge());
                String hashedPassword = BCrypt.hashpw(signupRequest.getPassword(), BCrypt.gensalt());
                member.setPassword(hashedPassword);
                return member;
        }
}