package grandchild.grandchild.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "video")
    private List<Heart> hearts = new ArrayList<>();
    @Column
    private String title;

    @Column
    private String category;

    @Column
    private String image;
    @Column
    @Lob
    private String description;
    @Column
    private String video;
    @Column
    @Lob
    private String content;


    public void addHeart(Heart heart) {
        if (heart != null) {
            hearts.add(heart);
        }
    }


}
