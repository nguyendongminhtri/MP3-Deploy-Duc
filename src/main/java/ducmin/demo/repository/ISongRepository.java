package ducmin.demo.repository;
import ducmin.demo.model.Singer;
import ducmin.demo.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISongRepository extends JpaRepository<Song, Long> {
   Page<Song> findAllByCategoryId(Long id, Pageable pageable);
   @Query("SELECT s.id FROM Song s INNER JOIN s.singerList t WHERE t.id = :id")
   Page<Song> pageSongBySingerId(Long id,Pageable pageable);
   @Query("SELECT s.singerList FROM  Song s  WHERE  s.id = :id")
   List<Singer> listSingerBySongId(Long id);
}
