package grandchild.grandchild.service;

import grandchild.grandchild.domain.Member;
import grandchild.grandchild.dto.base.BaseException;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final MemberRepository memberRepository;

    public Member loadMemberByUsername(String username) throws BaseException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_USER_NAME));

        return member;
    }
}
