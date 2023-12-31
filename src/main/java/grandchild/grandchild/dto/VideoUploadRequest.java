package grandchild.grandchild.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoUploadRequest {
    private String title;

    private String category;

    private MultipartFile image;


    private String description;
    private MultipartFile video;

    private String content;




}
