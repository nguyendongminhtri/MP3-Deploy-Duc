package ducmin.demo.repository;

import ducmin.demo.model.LikeAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ILikeAlbumRepository extends JpaRepository<LikeAlbum,Long> {
    Optional<LikeAlbum> findByAlbumIdAndUserId(Long album_id, Long user_id);
}
