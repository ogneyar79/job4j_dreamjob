package model;

import java.util.Objects;

public class Candidate {

    private final int id;
    private final String name;

    private final int photoId;
     private final int cityId;
    public Candidate(int id, String name, int photoId, int cityId) {
        this.id = id;
        this.name = name;
        this.photoId = photoId;
        this.cityId = cityId;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPhotoId() {
        return photoId;
    }

    public int getCityId() {
        return cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id == candidate.id &&
                photoId == candidate.photoId &&
                Objects.equals(name, candidate.name);
    }

    @Override
    public String toString() {
        return "Name" + this.getName() +
                "PhotoId" + this.getPhotoId() +
                "Id" + this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photoId);
    }
}
