package ducmin.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "like-album")
public class LikeAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    User user;
    @ManyToOne
    Album album;

    public LikeAlbum(Long id, User user, Album album) {
        this.id = id;
        this.user = user;
        this.album = album;
    }

    public LikeAlbum() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
