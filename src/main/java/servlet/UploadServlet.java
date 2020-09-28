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
        for (File name : new File("images").listFiles()) {
            images.add(name.getName());
        }
        req.setAttribute("images", images);
        System.out.println(" DoGet" + req.getParameter("id"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/candidates.do");
        dispatcher.forward(req, resp);
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
        resp.setContentType("text/html");
        PrintWriter outt = resp.getWriter();
        if (!isMultipart) {
            outt.println("<html>");
            outt.println("<head>");
            outt.println("<title>Servlet upload</title>");
            outt.println("</head>");
            outt.println("<body>");
            outt.println("ID" + req.getParameter("id"));
            outt.println("<p>No file uploaded</p>");
            outt.println("</body>");
            outt.println("</html>");
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
                    outt.println("<html>");
                    outt.println("<head>");
                    outt.println("<title>Servlet upload</title>");
                    outt.println("</head>");
                    outt.println("<body>");
                    File file = new File(folder + File.separator + item.getName());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                        IStore store = PsqlStore.instOf();
                        store.save(new Photo(0, file.getName()));
                        Photo photo = store.findPhotoByName2(file.getName());

                        Candidate temp = store.findCandidate(Integer.parseInt(req.getParameter("id")));
                        store.save(new Candidate(temp.getId(), temp.getName(), photo.getId()));
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }
}
