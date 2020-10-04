package servlet;

import model.Photo;
import store.IStore;
import store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(" Do Get DS");
        int photoId = Integer.parseInt(req.getParameter("photo"));  // get Param = candidate.photoId from candidate.jsp that equals photoId
        System.out.println(photoId + "   " + "PhotoID");

        if (photoId == 0) {
            String path = req.getContextPath() + "/notfound";
            resp.sendRedirect(path);
            System.out.println(" No Found RETURN ");
            return;
        }
        IStore store = PsqlStore.instOf();
        Photo photo = store.findPhotoById(photoId);
        if (photo != null) {
            String name = photo.getName();
            System.out.println("Photo Name" + name);
            resp.setContentType("name=" + name);
            resp.setContentType("image/png");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
            File file = new File("images" + File.separator + name);
            try (FileInputStream in = new FileInputStream(file)) {
                resp.getOutputStream().write(in.readAllBytes());
                resp.sendRedirect("/candidates.do");
            }
        }

    }
}
