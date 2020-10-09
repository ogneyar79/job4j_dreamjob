package servlet;

import model.Candidate;
import model.Photo;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IStore store = PsqlStore.instOf();
        req.setAttribute("candidates", store.findAllCandidates());
 //       Collection<Candidate> candidates = (Collection<Candidate>) req.getAttribute("candidates");
      //  store.findAllCandidates().stream().forEach(System.out::println);
 //       candidates.stream().forEach(System.out::println);
        System.out.println(" DoGet  CandidateServlet");
//        System.out.println("Candidate name Mak" + " " + canMac.getName() + " "
//                + " CandiateId" + " " + canMac.getId() + " " + "CandidatePhotoId" + " " + canMac.getPhotoId());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println(" DoPost  CandidateServlet");
        PsqlStore.instOf().save(new Candidate(Integer.valueOf(req.getParameter("id")),
                req.getParameter("name"), Integer.valueOf(req.getParameter("photoId"))));

        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
