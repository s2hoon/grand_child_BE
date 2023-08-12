package grandchild.grandchild.controller;


import com.amazonaws.services.kms.model.NotFoundException;
import grandchild.grandchild.dto.VideoResponse;
import grandchild.grandchild.dto.VideoUploadRequest;
import grandchild.grandchild.dto.base.BaseResponse;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/app/video")
@RequiredArgsConstructor
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
    public BaseResponse<List<VideoResponse>> video_getAll() {
        List<VideoResponse> videoResponses =videoService.getAll();
        return new BaseResponse<List<VideoResponse>>(BaseResponseStatus.SUCCESS, videoResponses );

    }

    /**
     * 특정 강의 내용 조회
     * @param videoId
     * @return
     */
    @GetMapping("/getContent")
    public BaseResponse<VideoResponse> video_getContent(@RequestParam Long videoId) {
        try {
            VideoResponse videoResponse = videoService.getOne(videoId);
            return new BaseResponse<VideoResponse>(BaseResponseStatus.SUCCESS, videoResponse);
        } catch (NotFoundException e) {
            return new BaseResponse<>(BaseResponseStatus.NO_THAT_ID_VIDEO);
        }
    }



    @GetMapping("/bookmark")
    public ResponseEntity<String> video_bookmark() {
        String result = "test 성공";
        return ResponseEntity.ok(result);
    }


}
