package ducmin.demo.service.impl;

import ducmin.demo.model.Singer;
import ducmin.demo.model.User;
import ducmin.demo.repository.ISingerRepository;
import ducmin.demo.security.userprincal.UserDetailService;
import ducmin.demo.service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SingerServiceImpl implements ISingerService {
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    ISingerRepository singerRepository;
    @Override
    public Page<Singer> findAll(Pageable pageable) {
        return singerRepository.findAll(pageable);
    }

    @Override
    public List<Singer> findAll() {
        return singerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        singerRepository.deleteById(id);
    }

    @Override
    public Singer save(Singer singer) {
        User user = userDetailService.getCurrentUser();
        singer.setUser(user);
        return singerRepository.save(singer);
    }

    @Override
    public Optional<Singer> findById(Long id) {
        return singerRepository.findById(id);
    }
}
