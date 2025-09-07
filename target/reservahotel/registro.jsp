<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body class="bg-light">

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card p-4 shadow-sm animate-fade">
                    <h2 class="text-center mb-4"><i class="bi bi-person-plus icono-hotel"></i> Crear cuenta</h2>

                    <form action="RegistroServlet" method="post" id="registroForm">
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre:</label>
                            <input type="text" name="nombre" id="nombre" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Correo electrónico:</label>
                            <input type="email" name="email" id="email" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Contraseña:</label>
                            <input type="password" name="password" id="password" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirmar contraseña:</label>
                            <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required>
                        </div>

                        <button type="submit" class="btn-reservar w-100">Registrarse</button>
                    </form>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger mt-3 text-center">
                            ${error}
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p class="mb-0">© 2025 Sistema de Reservas Hoteleras</p>
    </footer>

    <script>
        document.getElementById("registroForm").addEventListener("submit", function(e) {
            const password = document.getElementById("password").value;
            const confirm = document.getElementById("confirmPassword").value;
            if (password !== confirm) {
                e.preventDefault();
                alert("Las contraseñas no coinciden.");
            }
        });
    </script>

</body>
</html>
