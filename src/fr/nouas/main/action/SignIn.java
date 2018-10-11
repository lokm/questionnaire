package fr.nouas.main.action;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import fr.nouas.beans.User;
import fr.nouas.pojo.utils.Action;
import fr.nouas.pojo.utils.Md5;
import fr.nouas.utils.JpaUtil;

public class SignIn extends Action {


	@Override
    public boolean executeAction(HttpServletRequest request) {
        
        request.setAttribute("title", "page d'inscription");
        
        System.out.println("dans le sign");
        
        if(request.getMethod().equals("POST")) {
            
        
            
        // Recuperation des donnees utilisateur    
            
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        // hachage du mot de passe
        Md5 md = new Md5(password);
        password = md.codeGet();
     
        if(firstname != null && lastname != null && password != null
            && !firstname.isEmpty() && !lastname.isEmpty() 
            && !password.isEmpty()) {
            
            // creation de lutilisateur grace au information du formulaire
        

            User user = new User(lastname, firstname,email, password, role);
        

            EntityManager em = JpaUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            boolean redirect = false;
            
            try {
                transaction.begin();
                em.persist(user);
                transaction.commit();
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userid", user.getId());
                redirect = true;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
           
            em.close();
            return redirect;
          }
        }
        return true;
    }

}
