package store;

import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import servlet.CandidateServlet;
import servlet.RegServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest(UserEntity.class)
public class UserEntityTest {

    private final UserEntity store = new UserEntity();
    private final IPsqlStoreBase base = new MockWorkBaseData();
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    RegServlet regServlet;
    @Before
    public void install() {
        PowerMockito.mockStatic(MockWorkBaseData.class);
        when(store.findAllEntity()).thenReturn(base.findAllEntity());
        regServlet= new RegServlet();
        CandidateServlet candidateServlet = new CandidateServlet();
    }
    @Test
    public void regServlet() throws ServletException, IOException {
    //    RegServlet regServlet = new RegServlet();
        CandidateServlet candidateServlet = new CandidateServlet();
        String name = "Irina";
        String email = "Email";
        String password = "Password";
        when(req.getParameter("name")).thenReturn(name);
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
     //   regServlet.;
        new RegServlet().service(req, res);
        candidateServlet.doServlet;
    }


    @Test
    public void save() {
    }

    @Test
    public void findById() {
    }


}