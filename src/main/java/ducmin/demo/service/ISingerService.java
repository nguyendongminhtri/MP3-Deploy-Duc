package ducmin.demo.service;

import ducmin.demo.model.Singer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISingerService {
    Page<Singer> findAll(Pageable pageable);
    List<Singer> findAll();
    void deleteById(Long id);
    Singer save(Singer singer);
    Optional<Singer> findById(Long id);
}
