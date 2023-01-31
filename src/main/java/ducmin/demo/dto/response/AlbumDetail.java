package ducmin.demo.dto.response;

import ducmin.demo.model.Album;

public class AlbumDetail {
    private Album album;
    private boolean checkLikeAlbum;

    public AlbumDetail(Album album, boolean checkLikeAlbum) {
        this.album = album;
        this.checkLikeAlbum = checkLikeAlbum;
    }

    public boolean isCheckLikeAlbum() {
        return checkLikeAlbum;
    }

    public void setCheckLikeAlbum(boolean checkLikeAlbum) {
        this.checkLikeAlbum = checkLikeAlbum;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public AlbumDetail(Album album) {
        this.album = album;
    }

    public AlbumDetail() {
    }
}
