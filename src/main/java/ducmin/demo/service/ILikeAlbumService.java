package ducmin.demo.service;

import ducmin.demo.model.LikeAlbum;

import java.util.Optional;

public interface ILikeAlbumService extends IGeneratorService<LikeAlbum>{
    Optional<LikeAlbum> findByAlbumIdAndUserId(Long album_id, Long user_id);
}
