package cl.ipchile.reservahotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "ReservaServlet", urlPatterns = {"/ReservaServlet"})
public class ReservaServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("reservaHotelPU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Long habitacionId = Long.parseLong(request.getParameter("habitacionId"));
            LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
            LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));

            EntityManager em = emf.createEntityManager();

            try {
                Habitacion habitacion = em.find(Habitacion.class, habitacionId);
                if (habitacion == null) {
                    response.sendRedirect("index.jsp");
                    return;
                }

                // 🔍 Llamar al procedimiento almacenado para verificar disponibilidad
                StoredProcedureQuery sp = em.createStoredProcedureQuery("verificar_disponibilidad");
                sp.registerStoredProcedureParameter("p_habitacion_id", Integer.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("p_fecha_inicio", Date.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("p_fecha_fin", Date.class, ParameterMode.IN);

                sp.setParameter("p_habitacion_id", habitacionId.intValue());
                sp.setParameter("p_fecha_inicio", Date.valueOf(fechaInicio));
                sp.setParameter("p_fecha_fin", Date.valueOf(fechaFin));

                List<Object[]> resultado = sp.getResultList();
                int total = Integer.parseInt(resultado.get(0)[0].toString());
                boolean disponible = (total == 0);

                if (!disponible) {
                    request.setAttribute("error", "La habitación no está disponible en las fechas seleccionadas.");
                    request.setAttribute("habitacion", habitacion);
                    request.getRequestDispatcher("reserva.jsp").forward(request, response);
                    return;
                }

                // Crear reserva
                Reserva reserva = new Reserva();
                reserva.setUsuario(usuario);
                reserva.setHabitacion(habitacion);
                reserva.setFechaInicio(fechaInicio);
                reserva.setFechaFin(fechaFin);

                em.getTransaction().begin();
                em.persist(reserva);
                em.getTransaction().commit();

                request.setAttribute("reserva", reserva);
                request.getRequestDispatcher("confirmacion.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Ocurrió un error al procesar la reserva. Intenta nuevamente.");
                request.setAttribute("habitacionId", habitacionId);
                request.getRequestDispatcher("reserva.jsp").forward(request, response);
            } finally {
                em.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error inesperado. Verifica los datos ingresados.");
            request.getRequestDispatcher("reserva.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
