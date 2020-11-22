package servlet;

import model.User;
import store.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AuthS doPost ");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = new UserEntity().findByEmail(email);
        if (user.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            sc.setAttribute("name",user.getName());
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            resp.sendRedirect(req.getContextPath() + "/reg.do");
        }
    }
}
