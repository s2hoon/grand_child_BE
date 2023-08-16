package grandchild.grandchild.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import grandchild.grandchild.domain.Heart;
import grandchild.grandchild.domain.Member;
import grandchild.grandchild.domain.Video;
import grandchild.grandchild.dto.VideoGetAllResponse;
import grandchild.grandchild.dto.VideoResponse;
import grandchild.grandchild.dto.base.BaseException;
import grandchild.grandchild.dto.base.BaseResponseStatus;
import grandchild.grandchild.repository.HeartRepository;
import grandchild.grandchild.repository.MemberRepository;
import grandchild.grandchild.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final AmazonS3Client amazonS3Client;
    private final MemberRepository memberRepository;

    private final HeartRepository heartRepository;


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
    public List<VideoGetAllResponse> getAll() {
        // 모든 강의들 끌어옴
        List<Video> videos = videoRepository.findAll();
        // 응답 객체 생성 및 반환
        return videos.stream().map(this::convertToDTOS).collect(Collectors.toList());

    }


    public VideoResponse getOne(Long videoId) {

        // 한개의 강의 내용 끌어오기
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_THAT_ID_VIDEO));
        VideoResponse videoResponse = convertToDTO(video);
        return videoResponse;
    }



    public void bookmark(String username, Long videoId) {

        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_THAT_ID_VIDEO));
        Heart heart = new Heart();
        heart.setMember(member);
        heart.setVideo(video);
        heartRepository.save(heart);

        // 양방향 연관관계 세팅
        member.addHeart(heart);
        memberRepository.save(member);
        video.addHeart(heart);
        videoRepository.save(video);
        
    }

    private void uploadContentToS3(String contentName, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, contentName, file.getInputStream(), metadata);

    }


    private VideoGetAllResponse convertToDTOS(Video video) {
        VideoGetAllResponse videoGetAllResponse = new VideoGetAllResponse();
        videoGetAllResponse.setId(video.getId());
        videoGetAllResponse.setTitle(video.getTitle());
        videoGetAllResponse.setCategory(video.getCategory());
        videoGetAllResponse.setImage(video.getImage());
        return videoGetAllResponse;
    }
    private VideoResponse convertToDTO(Video video) {
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setId(video.getId());
        videoResponse.setTitle(video.getTitle());
        videoResponse.setCategory(video.getCategory());
        videoResponse.setImage(video.getImage());
        videoResponse.setDescription(video.getDescription());
        videoResponse.setVideo(video.getVideo());
        videoResponse.setContent(video.getContent());
        return videoResponse;
    }




}
