package ducmin.demo.service.impl;

import ducmin.demo.model.LikeAlbum;
import ducmin.demo.model.User;
import ducmin.demo.repository.IAlbumRepository;
import ducmin.demo.repository.ILikeAlbumRepository;
import ducmin.demo.security.userprincal.UserDetailService;
import ducmin.demo.service.ILikeAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LikeAlbumService implements ILikeAlbumService {
    @Autowired
    ILikeAlbumRepository likeAlbumRepository;
    @Autowired
    private IAlbumRepository iAlbumRepository;
    @Autowired
    UserDetailService userDetailService;

    @Override
    public Page<LikeAlbum> findAll(Pageable pageable) {
        return likeAlbumRepository.findAll(pageable);
    }

    @Override
    public Optional<LikeAlbum> findById(Long id) {
        return likeAlbumRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        likeAlbumRepository.deleteById(id);
    }

    @Override
    public LikeAlbum save(LikeAlbum likeAlbum) {
        User user = userDetailService.getCurrentUser();
        likeAlbum.setUser(user);
        return likeAlbumRepository.save(likeAlbum);
    }

    @Override
    public Optional<LikeAlbum> findByAlbumIdAndUserId(Long album_id, Long user_id) {
        return likeAlbumRepository.findByAlbumIdAndUserId(album_id,user_id);
    }
}
