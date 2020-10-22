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

    UserEntity store = new UserEntity();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("AuthS doPost ");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (store.findByEmail(email).getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            User user = store.findByEmail(email);
            //       admin.setName("Admin");
            //       admin.setEmail(email);
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            resp.sendRedirect(req.getContextPath() + "/reg.do");
          //  req.getRequestDispatcher("/reg.do").forward(req, resp);
        }
    }
}
