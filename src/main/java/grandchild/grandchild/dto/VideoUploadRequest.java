package grandchild.grandchild.dto;


import jakarta.persistence.Lob;
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

    @Lob
    private String description;
    private MultipartFile video;
    @Lob
    private String content;




}
