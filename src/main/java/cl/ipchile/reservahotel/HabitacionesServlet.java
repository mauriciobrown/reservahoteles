package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

            StoredProcedureQuery spq = em.createStoredProcedureQuery("buscar_habitaciones");
            spq.registerStoredProcedureParameter("hotel", Integer.class, ParameterMode.IN);
            spq.setParameter("hotel", hotelId.intValue());

            List<Object[]> resultados = spq.getResultList();
            List<Habitacion> habitaciones = new ArrayList<>();

            for (Object[] fila : resultados) {
                Habitacion h = new Habitacion();
                h.setId(((Number) fila[0]).longValue());
                h.setNumero((String) fila[1]);
                h.setTipo((String) fila[2]);
                h.setPrecioPorNoche((BigDecimal) fila[3]);
                h.setHotel(hotel); // Asignamos el objeto hotel ya cargado
                habitaciones.add(h);
            }

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
