package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FormServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("id", Integer.parseInt(req.getParameter("id")));  //     set candidate id by we going to find, and save photo in this candidate
        System.out.println("FS " + req.getAttribute("id"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/uploading.jsp");
        dispatcher.forward(req, resp);
    }
}
