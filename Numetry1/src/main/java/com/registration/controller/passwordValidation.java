package com.registration.controller;

import java.io.*;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.registration.dto.Registration;

@WebServlet("/savedata")
public class passwordValidation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String cpassword = req.getParameter("cpassword");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("numetry");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        if(password.equals(cpassword)) {
            Registration r1 = new Registration();
            r1.setName(name);
            r1.setEmail(email);
            r1.setPassword(password);
            r1.setCpassword(cpassword);
            
            et.begin();
            em.persist(r1);
            et.commit();
            
            PrintWriter pout = res.getWriter();
            pout.print("Registration Successfully.");
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.include(req, res);
            res.setContentType("text/html");
        } else {
            PrintWriter pw = res.getWriter();
            pw.print("Passwords do not match");
            RequestDispatcher rd = req.getRequestDispatcher("registration.html");
            rd.include(req, res);
            res.setContentType("text/html");
        }
    }
}
