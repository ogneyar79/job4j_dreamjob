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
        String candidateName = req.getParameter("name");
        IStore store = PsqlStore.instOf();
        Photo photo = store.findPhotoById(photoId);
        String name = photo.getName();
        if (photo.getName().equals("NOPhoto")) {
            // No record found, redirect to default image.
            System.out.println("Photo Name" + name + " " + "NULL");
            File file = new File("C:\\Users\\Администратор\\IdeaProjects\\job4j_dreamjob\\src\\main\\webapp\\image\\Screenshot_1.png");
            try (FileInputStream in = new FileInputStream(file)) {
                resp.getOutputStream().write(in.readAllBytes());
            }
        }
        if (!photo.getName().equals("NOPhoto")) {
            System.out.println("Photo Name" + name);
            resp.setContentType("name=" + name);
            resp.setContentType("image/png");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
            File file = new File("images" + File.separator + name);
            try (FileInputStream in = new FileInputStream(file)) {
                resp.getOutputStream().write(in.readAllBytes());
            }
        }
    }
}
