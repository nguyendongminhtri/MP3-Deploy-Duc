package ducmin.demo.controller;

import ducmin.demo.dto.response.ResponMessage;
import ducmin.demo.dto.response.SongDetail;
import ducmin.demo.model.*;
import ducmin.demo.security.userprincal.UserDetailService;
import ducmin.demo.service.impl.CategoryServiceImpl;
import ducmin.demo.service.impl.LikeSongServiceImpl;
import ducmin.demo.service.impl.SongServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("song")
@RestController
public class SongController {
    @Autowired
    SongServiceImpl songService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    LikeSongServiceImpl likeSongService;
    @Autowired
    UserDetailService userDetailService;
    @GetMapping
    public ResponseEntity<?> pageSong(@PageableDefault(sort = "nameSong", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Song> page = songService.findAll(pageable);
        List<Band> bandList = new ArrayList<>();
        if (page.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSong(@RequestBody Song song) {
//        song.setCategoryList(song.getCategoryList());
        if (song.getAvatarSong() == null) {
            return new ResponseEntity<>(new ResponMessage("no_avatar"), HttpStatus.OK);
        }
        if (song.getMp3Url() == null) {
            return new ResponseEntity<>(new ResponMessage("no_mp3Url"), HttpStatus.OK);
        }
        if (song.getCategory() == null) {
            return new ResponseEntity<>(new ResponMessage("no_category"), HttpStatus.OK);
        }
        if (song.getSingerList().size() == 0) {
            if (song.getBandList().size() == 0) {
                return new ResponseEntity<>(new ResponMessage("no_singer_band"), HttpStatus.OK);
            } else {
                songService.save(song);
                return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
            }
        } else {
            songService.save(song);
            return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
        }
    }

    @GetMapping("category/{id}")
    public ResponseEntity<?> findCategoryBySong(@PathVariable Long id, Pageable pageable) {
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Page<Song> songPage = songService.findAllByCategoryId(category.get().getId(), pageable);
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailSong(@PathVariable Long id) {
        Optional<Song> song = songService.findById(id);
        if (!song.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SongDetail songDetail = new SongDetail();
        songDetail.setSong(song.get());
        User user = userDetailService.getCurrentUser();
        System.out.println("user.getId ==> "+user.getId());
        if(user.getId()!=null){
            Optional<LikeSong> likeSong = likeSongService.findBySongIdAndUserId(song.get().getId(),user.getId());
            if(!likeSong.isPresent()){
                songDetail.setCheckLikeSong(false);
            } else {
                songDetail.setCheckLikeSong(true);
            }
        } else {
            songDetail.setCheckLikeSong(false);
        }
        return new ResponseEntity<>(songDetail, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        Optional<Song> song = songService.findById(id);
        if (!song.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        songService.deleteById(id);
        return new ResponseEntity<>(new ResponMessage("delete_success"), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listSong() {
        List<Song> songList = songService.findAll();
        if (songList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/by-singer/{id}")
    public ResponseEntity<?> getSingerBySongId(@PathVariable Long id) {
        Optional<Song> song = songService.findById(id);
        if (!song.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Singer> singerList = songService.listSingerBySongId(song.get().getId());
        return new ResponseEntity<>(singerList, HttpStatus.OK);
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<?> likeSong(@PathVariable Long id) {
            User user = userDetailService.getCurrentUser();
            if(user.getId()==null){
                return new ResponseEntity<>(new ResponMessage("no_user"), HttpStatus.OK);
            }
           Optional<Song> song = songService.findById(id);
            if(!song.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        Optional<LikeSong> likeSong = likeSongService.findBySongIdAndUserId(song.get().getId(),user.getId());
        if(!likeSong.isPresent()){
            LikeSong likeSong1 = new LikeSong();
            likeSong1.setSong(song.get());
            likeSongService.save(likeSong1);
            song.get().setLikeSong(song.get().getLikeSong()+1);
            songService.save(song.get());
            return new ResponseEntity<>(new ResponMessage("like"), HttpStatus.OK);
        } else {
            likeSongService.deleteById(likeSong.get().getId());
            song.get().setLikeSong(song.get().getLikeSong()-1);
            songService.save(song.get());
            return new ResponseEntity<>(new ResponMessage("un_like"), HttpStatus.OK);
        }
    }
}
