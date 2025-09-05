package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.List;

public class MisReservasServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("reservaHotelPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el usuario está logueado
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Reserva> query = em.createQuery(
                "SELECT r FROM Reserva r WHERE r.usuario = :usuario", Reserva.class);
            query.setParameter("usuario", usuario);

            List<Reserva> reservas = query.getResultList();

            request.setAttribute("reservas", reservas);
            request.getRequestDispatcher("misReservas.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
