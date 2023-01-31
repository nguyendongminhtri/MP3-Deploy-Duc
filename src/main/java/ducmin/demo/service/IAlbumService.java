package ducmin.demo.service;

import ducmin.demo.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAlbumService {
    List<Album> findAll(Album album);
    Page<Album> findAll(Pageable pageable);
    void deleteById(Long id);
    Optional<Album> findById(Long id);
    Album save(Album album);
    List<Album> findAll();
}
