package grandchild.grandchild.controller;


import grandchild.grandchild.dto.VideoGetAllResponse;
import grandchild.grandchild.dto.VideoResponse;
import grandchild.grandchild.dto.VideoUploadRequest;
import grandchild.grandchild.dto.base.BaseException;
import grandchild.grandchild.dto.base.BaseResponse;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/app/video")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoController {
    private final VideoService videoService;

    /**
     * 강의 업로드
     * @param videoUploadRequest
     * @return
     */
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<String> video_upload(@ModelAttribute VideoUploadRequest videoUploadRequest) {
        String title = videoUploadRequest.getTitle();
        String category = videoUploadRequest.getCategory();
        MultipartFile image = videoUploadRequest.getImage();
        String description = videoUploadRequest.getDescription();
        MultipartFile video = videoUploadRequest.getVideo();
        String content = videoUploadRequest.getContent();
        try {
            videoService.upload(title, category, image, description, video, content);
        } catch (IOException e) {
            return new BaseResponse<>(BaseResponseStatus.FILE_SAVE_ERROR);
        }
        String result = "강의 등록 성공";
        return new BaseResponse<String>(BaseResponseStatus.SUCCESS,result);
    }

    /**
     * 강의 전체 조회
     * @return
     */
    @GetMapping("/getAll")
    public BaseResponse<List<VideoGetAllResponse>> video_getAll() {
        List<VideoGetAllResponse> videoGetAllResponses =videoService.getAll();
        return new BaseResponse<List<VideoGetAllResponse>>(BaseResponseStatus.SUCCESS, videoGetAllResponses );
    }

    /**
     * 특정 강의 내용 조회
     * @param videoId
     * @return
     */
    @GetMapping("/getContent/{videoId}")
    public BaseResponse<VideoResponse> video_getContent(@PathVariable("videoId") Long videoId) {
        try {
            VideoResponse videoResponse = videoService.getOne(videoId);
            return new BaseResponse<VideoResponse>(BaseResponseStatus.SUCCESS, videoResponse);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }


    @PostMapping("/bookmark/{videoId}")
    public BaseResponse<String> video_bookmark(@PathVariable("videoId")  Long videoId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.info("회원 정보 없음");
            return new BaseResponse<>(BaseResponseStatus.JWT_TOKEN_ERROR);
        }

        try {
            String username = authentication.getPrincipal().toString();
            videoService.bookmark(username, videoId);
            String result = "강의 즐겨찾기 완료.";
            return new BaseResponse<String>(BaseResponseStatus.SUCCESS, result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
