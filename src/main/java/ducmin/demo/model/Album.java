package ducmin.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String avatarAlbum;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "album_song",joinColumns = @JoinColumn(name = "album_id"),inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs = new ArrayList<>() ;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "album_singer",joinColumns = @JoinColumn(name = "album_id"),inverseJoinColumns = @JoinColumn(name = "singer_id"))
    private List<Singer> singers = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "album_band",joinColumns = @JoinColumn(name = "album_id"),inverseJoinColumns = @JoinColumn(name = "band_id"))
    private List<Band> bands =new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "album_category",joinColumns = @JoinColumn(name = "album_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();
    @ManyToOne
    User user;
    private int LikeAlbum;

    public Album() {
    }

    public Album(Long id, String name, String avatarAlbum, List<Song> songs, List<Singer> singers, List<Band> bands, List<Category> categories, User user, int likeAlbum) {
        this.id = id;
        this.name = name;
        this.avatarAlbum = avatarAlbum;
        this.songs = songs;
        this.singers = singers;
        this.bands = bands;
        this.categories = categories;
        this.user = user;
        LikeAlbum = likeAlbum;
    }

    public Album(Long id, String name, String avatarAlbum, List<Song> songs, List<Singer> singers, List<Band> bands, List<Category> categories, User user) {
        this.id = id;
        this.name = name;
        this.avatarAlbum = avatarAlbum;
        this.songs = songs;
        this.singers = singers;
        this.bands = bands;
        this.categories = categories;
        this.user = user;
    }

    public String getAvatarAlbum() {
        return avatarAlbum;
    }

    public void setAvatarAlbum(String avatarAlbum) {
        this.avatarAlbum = avatarAlbum;
    }

    public Album(Long id, String name, List<Song> songs, List<Singer> singers, List<Band> bands, List<Category> categories, User user) {
        this.id = id;
        this.name = name;
        this.songs = songs;
        this.singers = singers;
        this.bands = bands;
        this.categories = categories;
        this.user = user;
    }

    public int getLikeAlbum() {
        return LikeAlbum;
    }

    public void setLikeAlbum(int likeAlbum) {
        LikeAlbum = likeAlbum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        if (songs==null){
            songs =new ArrayList<>();
        }
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Singer> getSingers() {
        if (singers == null){
            singers =new ArrayList<>();
        }
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public List<Band> getBands() {
        if(bands==null){
            bands = new ArrayList<>();
        }
        return bands;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }

    public List<Category> getCategories() {
        if(categories==null){
            categories = new ArrayList<>();
        }
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
