package servlet;

import model.User;
import store.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DoGET " + " " + "REGSERVLET");
        resp.sendRedirect(req.getContextPath() + "/reg.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DoPost " + " " + "REGSERVLET");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserEntity store = new UserEntity();
        store.save(new User(0, name, email, password));

        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }


}
