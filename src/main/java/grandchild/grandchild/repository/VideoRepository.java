package grandchild.grandchild.repository;

import grandchild.grandchild.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
