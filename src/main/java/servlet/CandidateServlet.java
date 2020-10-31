package servlet;

import model.Candidate;
import model.Photo;
import store.CandidateEntity;
import store.IPsqlStoreBase;
import store.IStore;
import store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class CandidateServlet extends HttpServlet {

    IPsqlStoreBase<Candidate> candidateEntity;

    public CandidateServlet(IPsqlStoreBase<Candidate> candidateEntity) {
        this.candidateEntity = candidateEntity;
    }

    public CandidateServlet() {
        candidateEntity  = new CandidateEntity();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // IStore store = PsqlStore.instOf();
        req.setAttribute("candidates", candidateEntity.findAllEntity());
        System.out.println(" DoGet  CandidateServlet");

        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println(" DoPost  CandidateServlet");
        candidateEntity.save(new Candidate(Integer.valueOf(req.getParameter("id")),
                req.getParameter("name"), Integer.valueOf(req.getParameter("photoId"))));

        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
