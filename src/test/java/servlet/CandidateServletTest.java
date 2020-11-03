package servlet;

import model.Candidate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import store.CandidateImitation;
import store.IPsqlStoreBase;
import store.MockWorkBaseData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest(CandidateImitation.class)
public class CandidateServletTest {

    private IPsqlStoreBase base;
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);
    CandidateServlet servlet;

    @Before
    public void install() {
        base = new CandidateImitation();
        servlet = new CandidateServlet(base);
    }

    @Test
    public void doGetAllCandidates() throws ServletException, IOException {
        this.base.save(new Candidate(1, "Zlata", 365));
        doNothing().when(req).setAttribute(String.valueOf((String.class)), this.base.findAllEntity());
        RequestDispatcher requestDispatcher = new RequestDispatcher() {
            @Override
            public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
                System.out.println("EVERYTHING OK");
            }

            @Override
            public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            }
        };
        when(req.getRequestDispatcher("candidates.jsp")).thenReturn(requestDispatcher);
        servlet.doGet(req, res);
        int real = this.base.findAllEntity().size();
        int expected = 1;
        assertThat(expected, is(real));
    }

    @Test
    public void doPostSaveCandidateAndRedactingCandidate() throws IOException {
        String id = "0";
        String name = "Irina";
        String photoId = "365";
        when(req.getParameter("id")).thenReturn(id);
        when(req.getParameter("name")).thenReturn(name);
        when(req.getParameter("photoId")).thenReturn(photoId);
        servlet.doPost(req, res);
        Candidate expected = new Candidate(1, name, Integer.valueOf(photoId));
        Candidate modelReal = (Candidate) this.base.findById(1);
        assertThat(expected, is(modelReal));
        this.base.save(new Candidate(1, "Zlata", Integer.valueOf(photoId)));
        Candidate expectedM = new Candidate(1, "Zlata", Integer.valueOf(photoId));
        Candidate modelRealM = (Candidate) this.base.findById(1);
        assertThat(expectedM, is(modelRealM));
    }
}