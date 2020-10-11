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

public class DownloadingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DownLoadServlet");

        int photoId = Integer.parseInt(req.getParameter("photoId"));
        System.out.println(photoId + "" + "photoId");
        if (photoId == 0) {
            System.out.println("O Trying");
            try (FileInputStream in = new FileInputStream(new File("C:\\Tools\\apache-tomcat-9.0.37\\bin\\images\\Screenshot_1.png"))) {
                resp.getOutputStream().write(in.readAllBytes());
            }
        }
        Photo photo = PsqlStore.instOf().findPhotoById(photoId);
        String name = photo.getName();
        new ServletResponseOutfile().makeFileOut(name, resp);

    }
}

