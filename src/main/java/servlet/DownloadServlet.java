package servlet;

import model.Photo;
import servlet.methodfordownloading.ServletResponseOutfile;
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
        int photoId = Integer.parseInt(req.getParameter("photo"));
        IStore store = PsqlStore.instOf();
        Photo photo = store.findPhotoById(photoId);
        String name = photo.getName();
        if (!photo.getName().equals("NOPhoto")) {
            new ServletResponseOutfile().makeFileOut(name, resp);
        } else {
            try (FileInputStream in = new FileInputStream(new File("C:\\Tools\\apache-tomcat-9.0.37\\bin\\images\\Screenshot_1.png"))) {
                resp.getOutputStream().write(in.readAllBytes());
            }
        }
    }
}
