package com.registration.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.registration.dto.Registration;

@WebServlet("/loginvalidate")
public class Logindetails extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("numetry");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select a from Registration a where a.email=?1 and a.password=?2");
        q.setParameter(1, email);
        q.setParameter(2, password);
        
        List<Registration> ru = q.getResultList();
        
        if(ru.size() > 0) {
            HttpSession hs = req.getSession();
            hs.setAttribute("registration", ru.get(0));
            
            PrintWriter pw = res.getWriter();
            pw.print("Login Successfully");
        } else {
            PrintWriter pw = res.getWriter();
            pw.print("Invalid Credentials");
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.include(req, res);
            res.setContentType("text/html");
        }
    }
}
