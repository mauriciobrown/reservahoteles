package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "ReservaFormServlet", urlPatterns = {"/ReservaFormServlet"})
public class ReservaFormServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("reservaHotelPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el parámetro de la habitación
        String idParam = request.getParameter("habitacionId");
        if (idParam == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Long habitacionId = Long.parseLong(idParam);

        EntityManager em = emf.createEntityManager();
        try {
            Habitacion habitacion = em.find(Habitacion.class, habitacionId);
            if (habitacion == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            request.setAttribute("habitacion", habitacion);
            request.getRequestDispatcher("reserva.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
