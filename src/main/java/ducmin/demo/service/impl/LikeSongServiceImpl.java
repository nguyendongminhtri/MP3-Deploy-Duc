package ducmin.demo.service.impl;

import ducmin.demo.model.LikeSong;
import ducmin.demo.model.User;
import ducmin.demo.repository.ILikeSongRepository;
import ducmin.demo.security.userprincal.UserDetailService;
import ducmin.demo.service.ILikeSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LikeSongServiceImpl implements ILikeSongService {
    @Autowired
    ILikeSongRepository likeSongRepository;
    @Autowired
    UserDetailService userDetailService;
    @Override
    public Page<LikeSong> findAll(Pageable pageable) {
        return likeSongRepository.findAll(pageable);
    }

    @Override
    public Optional<LikeSong> findById(Long id) {
        return likeSongRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        likeSongRepository.deleteById(id);
    }

    @Override
    public LikeSong save(LikeSong likeSong) {
        User user = userDetailService.getCurrentUser();
        likeSong.setUser(user);
        return likeSongRepository.save(likeSong);
    }

    @Override
    public Optional<LikeSong> findBySongIdAndUserId(Long song_id, Long user_id) {
        return likeSongRepository.findBySongIdAndUserId(song_id,user_id);
    }
}
