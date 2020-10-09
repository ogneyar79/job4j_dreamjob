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

public class DownloadingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DownLoadServlet");
        int photoId = Integer.parseInt(req.getParameter("photoId"));
        IStore store = PsqlStore.instOf();
        Photo photo = store.findPhotoById(photoId);
        String name = photo.getName();
        System.out.println("Photo NAME" + " " + name + " " + "PhotoId" + " " + photoId);
        resp.setContentType(name);
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        File file = new File("images" + File.separator + name);
        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        }
    }
}
