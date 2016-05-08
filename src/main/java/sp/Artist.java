package sp;

public class Artist {

    private final String name;

    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Artist oth) {
        return name.equals(oth.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
