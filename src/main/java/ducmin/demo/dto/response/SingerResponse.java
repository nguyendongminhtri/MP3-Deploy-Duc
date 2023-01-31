package ducmin.demo.dto.response;

import ducmin.demo.model.Singer;
import ducmin.demo.model.Song;
import org.springframework.data.domain.Page;

public class SingerResponse {
    private Singer singer;
    private Page<Song> songPage;

    public SingerResponse() {
    }

    public SingerResponse(Singer singer, Page<Song> songPage) {
        this.singer = singer;
        this.songPage = songPage;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public Page<Song> getSongPage() {
        return songPage;
    }

    public void setSongPage(Page<Song> songPage) {
        this.songPage = songPage;
    }
}
