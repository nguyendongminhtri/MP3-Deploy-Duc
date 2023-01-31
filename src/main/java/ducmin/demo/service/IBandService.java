package ducmin.demo.service;

import ducmin.demo.model.Band;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBandService {
    Page<Band> findAll(Pageable pageable);
    void deleteById(Long id);
    Optional<Band> findById(Long id);
    Band save(Band band);
    List<Band> findAll();
}
