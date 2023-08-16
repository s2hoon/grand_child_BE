package grandchild.grandchild.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoGetAllResponse {

    private Long id;
    private String title;
    private String category;
    private String image;

}
