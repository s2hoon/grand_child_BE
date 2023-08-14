package grandchild.grandchild.service;

import org.springframework.stereotype.Service;
import grandchild.grandchild.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import grandchild.grandchild.dto.SignupRequest;


@Service
public class MemberService {

        private final MemberRepository memberRepository;

        @Autowired
        public MemberService(MemberRepository memberRepository) {
                this.memberRepository = memberRepository;
        }
        public boolean registerMember(SignupRequest signupRequest) {
                try {
                        return true;
                } catch (Exception e) {
                        return false;
                }
        }
}