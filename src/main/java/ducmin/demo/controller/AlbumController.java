package ducmin.demo.controller;

import ducmin.demo.dto.response.AlbumDetail;
import ducmin.demo.dto.response.ResponMessage;
import ducmin.demo.model.*;
import ducmin.demo.security.userprincal.UserDetailService;
import ducmin.demo.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("album")
@RestController
public class AlbumController {
    @Autowired
    AlbumServiceImpl albumService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    LikeAlbumService likeAlbumService;
    @GetMapping
    public ResponseEntity<?> pageAlbum(@PageableDefault(sort = "nameAlbum",direction = Sort.Direction.ASC)Pageable pageable){
        Page<Album> albumPage = albumService.findAll(PageRequest.of(0,5));
        if (albumPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(albumPage,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createAlbum(@RequestBody Album album){
        User user = userDetailService.getCurrentUser();
        System.out.println("user.getID ==>"+user.getId());
        if (user.getId()==null){
            return new ResponseEntity<>(new ResponMessage("login please"),HttpStatus.NOT_FOUND);
        }
        albumService.save(album);
        return new  ResponseEntity<>( new ResponMessage("yes"),HttpStatus.OK);
    }
    @GetMapping("/album")
    public ResponseEntity<?> findAll(){
        List<Album> albums= albumService.findAll();
        return new ResponseEntity<>(albums,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id){
        Optional<Album> album = albumService.findById(id);
        if (!album.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        albumService.deleteById(id);
        return new ResponseEntity<>(new ResponMessage("delete success"),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detailALbum(@PathVariable Long id){
        Optional<Album> album = albumService.findById(id);
        if (!album.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AlbumDetail albumDetail = new AlbumDetail();
        albumDetail.setAlbum(album.get());
        User user = userDetailService.getCurrentUser();
        if (user.getId()!=null){
            Optional<LikeAlbum> likeAlbum = likeAlbumService.findByAlbumIdAndUserId(album.get().getId(),user.getId());
            if (!likeAlbum.isPresent()){
                albumDetail.setCheckLikeAlbum(false);
            }else {
                albumDetail.setCheckLikeAlbum(true);
            }
        }else {
            albumDetail.setCheckLikeAlbum(false);
        }
        return new ResponseEntity<>(albumDetail,HttpStatus.OK);
    }
    @GetMapping("/like/{id}")
    public ResponseEntity<?> likeSong(@PathVariable Long id) {
        User user = userDetailService.getCurrentUser();
        if(user.getId()==null){
            return new ResponseEntity<>(new ResponMessage("no_user"), HttpStatus.OK);
        }
        Optional<Album> album = albumService.findById(id);
        if(!album.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<LikeAlbum> likeAlbum = likeAlbumService.findByAlbumIdAndUserId(album.get().getId(),user.getId());
        if(!likeAlbum.isPresent()){
            LikeAlbum likeAlbum1 = new LikeAlbum();
            likeAlbum1.setAlbum(album.get());
            likeAlbumService.save(likeAlbum1);
            album.get().setLikeAlbum(album.get().getLikeAlbum()+1);
            albumService.save(album.get());
            return new ResponseEntity<>(new ResponMessage("like"), HttpStatus.OK);
        } else {
            likeAlbumService.deleteById(likeAlbum.get().getId());
            album.get().setLikeAlbum(album.get().getLikeAlbum()-1);
            albumService.save(album.get());
            return new ResponseEntity<>(new ResponMessage("un_like"), HttpStatus.OK);
        }
    }
}
