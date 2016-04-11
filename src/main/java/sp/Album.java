package sp;

import java.util.Arrays;
import java.util.List;

public class Album {

    private final String name;
    private final List<Track> tracks;
    private final Artist artist;

    public Album(Artist artist, String name, Track... tracks) {
        this.name = name;
        this.tracks = Arrays.asList(tracks);
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public Artist getArtist() {
        return artist;
    }

    public boolean equals(Album oth) {
        return name.equals(oth.name) && tracks.equals(oth.tracks) && artist.equals(oth.artist);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + tracks.hashCode() + artist.hashCode();
    }
}
