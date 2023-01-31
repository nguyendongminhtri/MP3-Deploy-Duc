package ducmin.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "like_song")
public class LikeSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    User user;
    @ManyToOne
    Song song;

    public LikeSong(Long id, User user, Song song) {
        this.id = id;
        this.user = user;
        this.song = song;
    }

    public LikeSong() {
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

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
