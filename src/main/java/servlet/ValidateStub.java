package servlet;

import model.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateStub implements IValidate {

    private final Map<Integer, User> store = new HashMap<>();
    private int ids = 0;

    public static IValidate getValidateObject() {
        return ValidateService.getInstance();
    }

    @Override
    public User add(User user) {
        user.setId(this.ids++);
        this.store.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<User>(this.store.values());
    }

    @Override
    public void deleteById(int id) {
        ArrayList<User> users = this.store.remove(id);

    }
}
