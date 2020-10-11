package servlet.methodfordownloading;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IHtppResponce {

    void makeFileOut(String fileName, HttpServletResponse resp) throws FileNotFoundException, IOException;
}
