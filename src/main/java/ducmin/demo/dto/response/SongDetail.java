package ducmin.demo.dto.response;

import ducmin.demo.model.Song;

public class SongDetail {
    private Song song;
    private boolean checkLikeSong;

    public SongDetail() {
    }

    public SongDetail(Song song, boolean checkLikeSong) {
        this.song = song;
        this.checkLikeSong = checkLikeSong;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public boolean isCheckLikeSong() {
        return checkLikeSong;
    }

    public void setCheckLikeSong(boolean checkLikeSong) {
        this.checkLikeSong = checkLikeSong;
    }
}
