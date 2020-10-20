package servlet;

import model.Candidate;
import model.Post;
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
        req.setAttribute("posts", postEntity.findAllEntity());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        postEntity.save(new Post(Integer.valueOf(req.getParameter("id")),
                        req.getParameter("name")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

}
