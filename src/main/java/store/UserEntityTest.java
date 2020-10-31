package store;

import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import servlet.RegServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MockWorkBaseData.class)
public class UserEntityTest {

    private IPsqlStoreBase base;
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);
    RegServlet regServlet;

    @Before
    public void install() {
        mockStatic(MockWorkBaseData.class);
        base = new MockWorkBaseData();
        regServlet = new RegServlet(base);
    }

    @Test
    public void regServletAddNewUserAndFindItById() throws ServletException, IOException {
        String name = "Irina";
        String email = "Email";
        String password = "Password";
        when(req.getParameter("name")).thenReturn(name);
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
        regServlet.doPost(req, res);
        User expected = new User(1, name, email, password);
        User modelReal = (User) base.findById(1);
        assertThat(expected, is(modelReal));
    }
    @Test
    public void redactingUser() throws IOException {
        String name = "Zlata";
        String email = "Email@";
        String password = "Password";
        when(req.getParameter("name")).thenReturn(name);
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
        regServlet.doPost(req, res);
    }

    @Test
    public void findById() {
    }


}