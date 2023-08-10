package grandchild.grandchild.controller;


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

@RestController
@RequestMapping("/app/video")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoController {
    private final VideoService videoService;

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
    @GetMapping("/getAll")
    public ResponseEntity<String> video_getAll() {
        String result = "test 성공";
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getContent")
    public ResponseEntity<String> video_getContent() {
        String result = "test 성공";
        return ResponseEntity.ok(result);
    }
    @GetMapping("/bookmark")
    public ResponseEntity<String> video_bookmark() {
        String result = "test 성공";
        return ResponseEntity.ok(result);
    }


}
