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
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
            query.setParameter("email", email);

            List<Usuario> usuarios = query.getResultList();

            if (usuarios.isEmpty()) {
                request.setAttribute("error", "Correo no registrado.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            Usuario usuario = usuarios.get(0);

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
