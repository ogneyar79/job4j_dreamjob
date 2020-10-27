package servlet;

import model.User;

import java.util.List;

interface IValidate {
    User add(User user);

    List<User> getAll();
}
