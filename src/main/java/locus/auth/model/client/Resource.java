package locus.auth.model.client;

public class Resource {
    private final int id;
    private final String name;

    public Resource(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
