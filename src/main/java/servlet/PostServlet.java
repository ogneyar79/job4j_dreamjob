package servlet;

import model.Candidate;
import model.Post;
import model.User;
import org.apache.logging.log4j.Logger;
import store.IPsqlStoreBase;
import store.PostEntity;
import store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static org.apache.logging.log4j.LogManager.getLogger;


public class PostServlet extends HttpServlet {

    IPsqlStoreBase<Post> postEntity = new PostEntity();
    final static Logger logger = getLogger(PostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(" PostS doGet");
        req.setAttribute("posts", postEntity.findAllEntity());
      //  req.setAttribute("user", req.getSession().getAttribute("user"));
        User us = (User) req.getSession().getAttribute("user");
        System.out.println(req.getSession().getAttribute("user")  + " " +"30 String");
        req.setAttribute("user", us.getName());
        System.out.println(us.getName()  + " " +"33 String");
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("PostServlet doPost");
        req.setCharacterEncoding("UTF-8");
        postEntity.save(new Post(Integer.valueOf(req.getParameter("id")),
                        req.getParameter("name")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

}
