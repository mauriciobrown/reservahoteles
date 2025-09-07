package cl.ipchile.reservahotel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

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
        String passwordIngresada = request.getParameter("password");

        EntityManager em = emf.createEntityManager();

        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("buscar_usuario");
            spq.registerStoredProcedureParameter("correo", String.class, ParameterMode.IN);
            spq.setParameter("correo", email);

            List<Object[]> resultados = spq.getResultList();

            if (resultados.isEmpty()) {
                request.setAttribute("error", "Correo no registrado.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            Object[] fila = resultados.get(0);
            Usuario usuario = new Usuario();
            usuario.setId(((Number) fila[0]).longValue());
            usuario.setNombre((String) fila[1]);
            usuario.setEmail((String) fila[2]);
            usuario.setPassword((String) fila[3]);

            // 🔐 Verificar contraseña con BCrypt
            boolean passwordValida = BCrypt.checkpw(passwordIngresada, usuario.getPassword());

            if (!passwordValida) {
                request.setAttribute("error", "Contraseña incorrecta.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // ✅ Login exitoso
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al iniciar sesión.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
