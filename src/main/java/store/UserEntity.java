package store;

import model.Candidate;
import model.Photo;
import model.Post;
import model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserEntity implements IPsqlStoreBase<User> {

    private final BasicDataSource pool = PsqlStore.instOf().getPool();

    public User findByEmail(String email) {
        User user = new User(0, "NoName", "NoEmail", "NoPassword");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users where email = ?")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    user = new User(it.getInt("id"), it.getString("name"), it.getString("email"), it.getString("password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Collection<User> findAllEntity() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(new User(it.getInt("id"), it.getString("name"),
                            it.getString("email"), it.getString("password")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO users(name,email,password) VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
            System.out.println(ps);
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user = new User(id.getInt(1), user.getName(), user.getEmail(), user.getPassword());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(" UPDATE users set name = ?, email = ?, password = ? where id = ? ")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, String.valueOf(user.getId()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        return this.findAllEntity().stream().filter(p -> p.getId() == id)
                .findAny().orElse(new User(0, "NoUser", "email", "NO"));
    }

    public void deleteById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(" DELETE users  where id = ? ")
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Record is deleted!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String... args) {
        IPsqlStoreBase users = new UserEntity();
        User admin = new User(0, "Admin", "element@mail.ru", "234Alphabet");

        users.findAllEntity().stream().forEach(System.out::println);
        User show = (User) users.findAllEntity().stream().findAny().get();
    }
}
