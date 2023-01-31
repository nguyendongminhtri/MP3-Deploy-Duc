package ducmin.demo.repository;

import ducmin.demo.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBandRepository extends JpaRepository<Band, Long> {
}
