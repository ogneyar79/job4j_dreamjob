package store;

import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockWorkBaseData implements IPsqlStoreBase<User> {

    private final Map<Integer, User> store = new HashMap();
    private int ids = 1;

    private User add(User user) {
        user.setId(this.ids++);
        this.store.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> findAllEntity() {
        return new ArrayList(this.store.values());

    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            add(user);
        } else {
            this.store.put(user.getId(), user);
        }
    }

    @Override
    public User findById(int id) {
        return store.get(id) != null ? store.get(id) : new User(0, "Zero", "null@null.com", "ZERO");
    }

    public void deleteById(int id) {
        this.store.remove(id);
        this.ids--;
    }

    public User findByEmail(String email) {
        return findAllEntity().stream().filter(user -> user.getEmail().equals(email)).
                findAny().orElse(new User(0, "Zero", "null@null.com", "ZERO"));
    }
}
