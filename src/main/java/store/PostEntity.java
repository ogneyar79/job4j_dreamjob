package store;

import model.Post;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostEntity  implements IPsqlStoreBase<Post>{

    final BasicDataSource pool = PsqlStore.instOf().getPool();

    @Override
    public Collection<Post> findAllEntity() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                posts.add(new Post(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }
    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post = new Post(id.getInt(1), post.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }


    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(" UPDATE post set name = ? where id = ? ")
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, String.valueOf(post.getId()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Post findById(int id) {
        return null;
    }
}
