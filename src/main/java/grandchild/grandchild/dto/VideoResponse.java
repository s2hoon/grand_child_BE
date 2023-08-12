package grandchild.grandchild.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VideoResponse {

    private Long id;
    private String title;
    private String category;
    private String image;
    private String description;

    private String video;

    private String content;




}
