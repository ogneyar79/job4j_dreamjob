package servlet;

import model.Photo;


import store.PsqlStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class DownloadServlet extends HttpServlet {
    private void showImage(String name, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        File file = new File("images" + File.separator + name);
        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(" Do Get DS");
        int photoId = Integer.parseInt(req.getParameter("photo"));
        Photo photo = PsqlStore.instOf().findPhotoById(photoId);
        String name = photo.getName();
        if (photoId == 0) {
            name = "Screenshot_1.png";
        }
        showImage(name, resp);
    }
}
