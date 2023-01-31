package ducmin.demo.service.impl;

import ducmin.demo.model.Album;
import ducmin.demo.repository.IAlbumRepository;
import ducmin.demo.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AlbumServiceImpl implements IAlbumService {
    @Autowired
    IAlbumRepository albumRepository;

    @Override
    public List<Album> findAll(Album album) {
        return albumRepository.findAll();
    }

    @Override
    public Page<Album> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        albumRepository.deleteById(id);

    }

    @Override
    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Album save(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }
}
