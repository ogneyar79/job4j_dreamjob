package store;

import model.Photo;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PhotoEntity implements IPsqlStoreBase<Photo> {

    final BasicDataSource pool = PsqlStore.instOf().getPool();

    @Override
    public Collection<Photo> findAllEntity() {
        List<Photo> photos = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM photo")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    photos.add(new Photo(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photos;
    }

    private Photo create(Photo photo) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO photo(name ) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, photo.getName());
            ps.execute();

            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    photo = new Photo(id.getInt(1), photo.getName());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return photo;
    }

    private void update(Photo photo) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(" UPDATE photo set name = ? where id = ? ")
        ) {
            ps.setString(1, photo.getName());
            ps.setString(2, String.valueOf(photo.getId()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Photo photo) {
        if (photo.getId() == 0) {
            create(photo);
        } else {
            update(photo);
        }
    }

    @Override
    public Photo findById(int id) {
        return this.findAllEntity().stream().filter(p -> p.getId() == id)
                .findAny().orElse(new Photo(0, "NOPhoto"));
    }

    public Photo findPhotoByName2(String name) {
        return this.findAllEntity().stream().filter(p -> p.getName().equalsIgnoreCase(name))
                .findAny().orElse(new Photo(0, "NOPhoto"));
    }
}
