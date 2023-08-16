package grandchild.grandchild.dto.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : Successed
     */
    SUCCESS(true, 1000, "요청에 성공했습니다."),

    /**
     * 2xxx : Member
     */

    EMAIL_ALREADY_EXIST(false, 2000, "이미 가입된 유저이름입니다."),
    WRONG_USER_NAME(false, 2001, "해당하는 유저이름이 없습니다."),
    WRONG_PASSWORD(false, 2002, "잘못된 비밀번호입니다."),



    // mypage
    INVALID_MEMBER_JWT(false,2300,"권한이 없는 회원의 접근입니다."),

    NO_SUCH_MEMBER_EXIST(false, 3203, "존재하지 않는 회원입니다."),

    JWT_TOKEN_ERROR(false, 2302, "jwt 토큰을 확인해주세요."),

    /**
     * 3xxx: Video
     */
    FILE_SAVE_ERROR(false, 3001, "파일 저장에 실패하였습니다."),
    NO_THAT_ID_VIDEO(false, 3002, "id에 해당하는 강의가없습니다.");



    private final boolean isSuccess;
    private final int code;
    private final String message;


    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

