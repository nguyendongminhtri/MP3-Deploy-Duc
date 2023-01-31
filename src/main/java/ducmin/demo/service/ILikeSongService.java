package ducmin.demo.service;

import ducmin.demo.model.LikeSong;

import java.util.Optional;

public interface ILikeSongService extends IGeneratorService<LikeSong>{
    Optional<LikeSong> findBySongIdAndUserId(Long song_id, Long user_id);
}
