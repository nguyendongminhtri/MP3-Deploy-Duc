package ducmin.demo.controller;

import ducmin.demo.dto.response.ResponMessage;
import ducmin.demo.model.Band;
import ducmin.demo.service.impl.BandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("band")
@RestController
public class BandController {
    @Autowired
    BandServiceImpl bandService;
    @GetMapping
    public ResponseEntity<?> pageBands(@PageableDefault(sort = "nameBand", direction = Sort.Direction.ASC)Pageable pageable){
        Page<Band> bandPage = bandService.findAll(pageable);
        if(bandPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bandPage, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createBand(@RequestBody Band band){
        if(band.getAvatarBand()==null){
            return new ResponseEntity<>(new ResponMessage("no_avatar_band"), HttpStatus.OK);
        }
        bandService.save(band);
        return new ResponseEntity<>(new ResponMessage("create_success"), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editBand(@PathVariable Long id, @RequestBody Band band){
        Optional<Band> band1 = bandService.findById(id);
        if(!band1.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        band1.get().setNameBand(band.getNameBand());
        band1.get().setAvatarBand(band.getAvatarBand());
        band1.get().setDescription(band.getDescription());
        bandService.save(band1.get());
        return new ResponseEntity<>(new ResponMessage("update_success"), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detailBand(@PathVariable Long id){
        Optional<Band> band = bandService.findById(id);
        if(!band.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(band, HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getListBand(){
        List<Band> bandList = bandService.findAll();
        if(bandList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bandList, HttpStatus.OK);
    }
}
