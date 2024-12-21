package org.example.projet4dx.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.projet4dx.model.Player;
import org.example.projet4dx.service.PlayerService;
import org.example.projet4dx.util.PersistenceManager;

import java.io.IOException;

@WebServlet(name = "ProfilePageServlet", value = "/profile")
public class ProfilePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        EntityManager em = PersistenceManager.getEntityManager();
        PlayerService playerService = new PlayerService(em);
        Player player = (Player) session.getAttribute("loggedInUser");
        if ( player == null){
            request.getRequestDispatcher("/login").forward(request,response);
            return;
        }
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("pageTitle", "Profile");
        request.setAttribute("content","profilePage.jsp");
        request.setAttribute("meanScore",playerService.getPlayerMeanScore(em,player));
        request.setAttribute("maxScore",playerService.getMaximumPlayerScore(em,player));
        request.setAttribute("minScore",playerService.getMinimumPlayerScore(em,player));
        request.setAttribute("playerGameList",playerService.getPlayerGames(em,player));
        request.getRequestDispatcher("/views/layout.jsp").forward(request, response);



    }
}
