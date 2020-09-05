package model;

import java.util.Objects;

public class Photo {
    private final int id;
    private final String name;

    public Photo(int id, String name) {
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
