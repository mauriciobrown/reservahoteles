package cl.ipchile.reservahotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("reservaHotelPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String destino = request.getParameter("destino");
        EntityManager em = emf.createEntityManager();
        List<Object[]> hotelesEncontrados;

        try {
            Query query;

            if (destino != null && !destino.isEmpty()) {
                query = em.createNativeQuery("""
                    SELECT id, nombre, ubicacion, estrellas, precio_minimo
                    FROM vista_hoteles_por_destino
                    WHERE LOWER(ubicacion) LIKE LOWER(?)
                """);
                query.setParameter(1, "%" + destino + "%");
            } else {
                query = em.createNativeQuery("""
                    SELECT id, nombre, ubicacion, estrellas, precio_minimo
                    FROM vista_hoteles_por_destino
                """);
            }

            hotelesEncontrados = query.getResultList();

        } finally {
            em.close();
        }

        request.setAttribute("hoteles", hotelesEncontrados);
        request.getRequestDispatcher("resultados.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
