package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("reservaHotelPU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password", Usuario.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            Usuario usuario = query.getResultStream().findFirst().orElse(null);

            if (usuario != null) {
                request.getSession().setAttribute("usuario", usuario);
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("error", "Credenciales incorrectas");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
