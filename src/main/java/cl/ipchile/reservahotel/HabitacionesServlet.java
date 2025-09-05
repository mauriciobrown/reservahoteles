package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.List;

public class HabitacionesServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("reservaHotelPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long hotelId = Long.parseLong(request.getParameter("hotelId"));

        EntityManager em = emf.createEntityManager();

        try {
            Hotel hotel = em.find(Hotel.class, hotelId);

            TypedQuery<Habitacion> query = em.createQuery(
                "SELECT h FROM Habitacion h WHERE h.hotel = :hotel", Habitacion.class);
            query.setParameter("hotel", hotel);

            List<Habitacion> habitaciones = query.getResultList();

            request.setAttribute("hotel", hotel);
            request.setAttribute("habitaciones", habitaciones);
            request.getRequestDispatcher("habitaciones.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
