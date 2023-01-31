package ducmin.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGeneratorService<S> {
    Page<S> findAll(Pageable pageable);
    Optional<S> findById(Long id);
    void deleteById(Long id);
    S save(S s);
}
