package servlet.methodfordownloading;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ServletResponseOutfile implements IHtppResponce {
    @Override
    public void makeFileOut(String fileName, HttpServletResponse resp) throws IOException {
        resp.setContentType("name=" + fileName);
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        File file = new File("images" + File.separator + fileName);
        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        }
    }
}
