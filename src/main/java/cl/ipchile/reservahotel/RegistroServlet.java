package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.List;

public class RegistroServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("reservaHotelPU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EntityManager em = emf.createEntityManager();

        try {
            // 🔍 Llamar al procedimiento almacenado para verificar si el correo ya existe
            StoredProcedureQuery sp = em.createStoredProcedureQuery("verificar_usuario_por_email");
            sp.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
            sp.setParameter("p_email", email);

            List<Object[]> resultado = sp.getResultList();
            int total = Integer.parseInt(resultado.get(0)[0].toString());
            boolean existe = (total > 0);

            if (existe) {
                request.setAttribute("error", "El correo ya está registrado.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setPassword(password);

            em.getTransaction().begin();
            em.persist(nuevoUsuario);
            em.getTransaction().commit();

            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al registrar el usuario.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
