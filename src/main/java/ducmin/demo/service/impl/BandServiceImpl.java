package ducmin.demo.service.impl;

import ducmin.demo.model.Band;
import ducmin.demo.model.User;
import ducmin.demo.repository.IBandRepository;
import ducmin.demo.security.userprincal.UserDetailService;
import ducmin.demo.service.IBandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandServiceImpl implements IBandService {
    @Autowired
    IBandRepository bandRepository;
    @Autowired
    UserDetailService userDetailService;
    @Override
    public Page<Band> findAll(Pageable pageable) {
        return bandRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        bandRepository.deleteById(id);
    }

    @Override
    public Optional<Band> findById(Long id) {
        return bandRepository.findById(id);
    }

    @Override
    public Band save(Band band) {
        User user = userDetailService.getCurrentUser();
        band.setUser(user);
        return bandRepository.save(band);
    }

    @Override
    public List<Band> findAll() {
        return bandRepository.findAll();
    }
}
