package grandchild.grandchild.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import grandchild.grandchild.domain.Video;
import grandchild.grandchild.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final AmazonS3Client amazonS3Client;
    private final String bucket_url = "https://dubs3.s3.ap-northeast-2.amazonaws.com/";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;




    public void upload(String title, String category, MultipartFile image_file, String description, MultipartFile video_file, String content) throws IOException {
        // 사용자 인증 (익명)
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        ////// video 객체 생성 및 db 에 저장
        Video video = new Video();
        video.setTitle(title);
        video.setCategory(category);
        // image
        String imageName = randomUUIDString + "imageFile";
        uploadContentToS3(imageName, image_file);
        video.setImage(bucket_url + imageName);
        video.setDescription(description);
        //video
        String videoName = randomUUIDString + "videoFile";
        uploadContentToS3(videoName, video_file);
        video.setVideo(bucket_url + videoName);
        video.setContent(content);
        videoRepository.save(video);


    }

    private void uploadContentToS3(String contentName, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, contentName, file.getInputStream(), metadata);

    }

}
