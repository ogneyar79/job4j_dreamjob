package servlet;

import model.Candidate;
import store.CandidateEntity;
import store.CityEntity;
import store.IPsqlStoreBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    IPsqlStoreBase<Candidate> candidateEntity;

    public CandidateServlet(IPsqlStoreBase<Candidate> candidateEntity) {
        this.candidateEntity = candidateEntity;
    }

    public CandidateServlet() {
        candidateEntity = new CandidateEntity();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", candidateEntity.findAllEntity());
        System.out.println(" DoGet  CandidateServlet");

        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println(" DoPost  CandidateServlet");
        String nameCity = req.getParameter("city");
        int cityId = new CityEntity().getCityId(nameCity).getId();
        candidateEntity.save(new Candidate(Integer.valueOf(req.getParameter("id")),
                req.getParameter("name"), Integer.valueOf(req.getParameter("photoId")), cityId));
        System.out.println("City name is:" + nameCity + ", cityId:" + cityId);
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
