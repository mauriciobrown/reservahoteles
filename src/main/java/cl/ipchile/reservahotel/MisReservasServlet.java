package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        EntityManager em = emf.createEntityManager();

        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("ver_reservas");
            spq.registerStoredProcedureParameter("usuarioId", Integer.class, ParameterMode.IN);
            spq.setParameter("usuarioId", usuario.getId().intValue());

            List<Object[]> resultados = spq.getResultList();
            List<Reserva> reservas = new ArrayList<>();

            for (Object[] fila : resultados) {
                Reserva reserva = new Reserva();
                reserva.setId(((Number) fila[0]).longValue());
                reserva.setFechaInicio(((java.sql.Date) fila[1]).toLocalDate());
                reserva.setFechaFin(((java.sql.Date) fila[2]).toLocalDate());

                Habitacion habitacion = new Habitacion();
                habitacion.setId(((Number) fila[3]).longValue());
                habitacion.setNumero((String) fila[4]);
                habitacion.setTipo((String) fila[5]);
                habitacion.setPrecioPorNoche((BigDecimal) fila[6]);

                Hotel hotel = new Hotel();
                hotel.setId(((Number) fila[7]).longValue());
                hotel.setNombre((String) fila[8]);
                hotel.setUbicacion((String) fila[9]);
                hotel.setEstrellas(((Number) fila[10]).intValue());

                habitacion.setHotel(hotel);
                reserva.setHabitacion(habitacion);
                reserva.setUsuario(usuario);

                reservas.add(reserva);
            }

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
