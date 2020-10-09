package servlet;

import model.Candidate;
import model.Photo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import store.IStore;
import store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = "/upload")
public class UploadServlet extends HttpServlet {
    private boolean isMultipart;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> images = new ArrayList<>();
        List<File> photoFile = new ArrayList<>();
        for (File file : new File("images").listFiles()) {
            photoFile.add(file);
        }
        for (File name : new File("images").listFiles()) {
            images.add(name.getName());
        }
        req.getServletContext().setAttribute("photo", photoFile);
        req.getServletContext().setAttribute("images", images);
        System.out.println(" DoGet UploadServlet" + " " + req.getParameter("id"));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DoPost");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        isMultipart = ServletFileUpload.isMultipartContent(req);
        //  resp.setContentType("text/html");
        if (!isMultipart) {
            return;
        }
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator + item.getName()); // Create file with name ?
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                        IStore store = PsqlStore.instOf();
                        Candidate temp = store.findCandidate(Integer.parseInt(req.getParameter("id")));   //get  id Candidate Where we are working with
                        System.out.println(" Id Candidate that we get from " + req.getParameter("id"));
                        System.out.println(" File NAME" + " " + file.getName());
                        if (temp.getPhotoId() == 0) {
                            System.out.println("UploadServlet Photo no upload new Photo");
                            store.save(new Photo(0, file.getName()));

                        } else {
                            System.out.println("UploadServlet We have Photo we change it and for new Photo");
                            store.save(new Photo(store.findPhotoCandidates(temp).getId(), file.getName()));
                        }
                        // check we have photo or not
                        System.out.println("We save photo");
                        Photo photo = store.findPhotoByName2(file.getName()); // we get photo that was saved and add to our Candidate
                        System.out.println("We try to get Photo that's saved" + " " + photo +" "+ "Id " + photo.getId() +" "+ "name " + photo.getName());
                        store.save(new Candidate(temp.getId(), temp.getName(), photo.getId()));
                        Candidate show = store.findCandidate(temp.getId());
                        System.out.println(" Work with candidate" + "Name" + show.getName() + "Id" + show.getId() + "PhotoId" + show.getPhotoId());
                        Photo photo1 = store.findPhotoById(show.getPhotoId());
                        System.out.println(photo1.getName() + "Photo Name" + "fileName" + file.getName());
                        System.out.println(" We save candidate with" + " Id" + temp.getId() + " Name" + temp.getName() + "PhotoId" + photo.getId());
               //         store.findAllCandidates().stream().forEach(System.out::println);
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }
}
