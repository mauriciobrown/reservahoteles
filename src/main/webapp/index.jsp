<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Título dinámico para el head.jsp --%>
<c:set var="pageTitle" value="Inicio - ReservaHotel" />

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body class="bg-light">

    <!-- Hero con fondo -->
 <div class="bg-dorado text-white text-center py-5 mb-4 shadow-sm position-relative">
    <h1 class="display-5">
        <i class="bi bi-building icono-hotel"></i> Bienvenido a <strong>Reserva de Hoteles</strong>
    </h1>
    <p class="lead">Encuentra tu próxima estadía con estilo y comodidad</p>

    <div class="logo-derecha">
        <img src="images/LogoHotel.png" alt="Logo ReservaHotel">
    </div>
</div>


    <!-- Navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm mb-4">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">Reserva Hotel</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarContent">
                <ul class="navbar-nav ms-auto">
                    <c:choose>
                        <c:when test="${not empty sessionScope.usuario}">
                            <li class="nav-item"><span class="nav-link">👤 ${sessionScope.usuario.nombre}</span></li>
                            <li class="nav-item"><a class="nav-link" href="MisReservasServlet">Mi reserva</a></li>
                            <li class="nav-item"><a class="nav-link" href="logout.jsp">Cerrar sesión</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item"><a class="nav-link" href="login.jsp">Iniciar sesión</a></li>
                            <li class="nav-item"><a class="nav-link" href="registro.jsp">Registrarse</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Buscador -->
    <div class="container">
        <div class="card p-4 shadow-sm mb-4 animate__animated animate__fadeIn">
            <h2 class="mb-3"><i class="bi bi-search"></i> Buscar hoteles por destino</h2>
            <form action="SearchServlet" method="get" class="row g-3">
                <div class="col-md-8">
                    <input type="text" name="destino" class="form-control" placeholder="Ej. Santiago" required>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Buscar Hoteles</button>
                </div>
            </form>
        </div>

        <c:if test="${not empty sessionScope.usuario}">
            <div class="alert alert-info text-center">
                <i class="bi bi-calendar-check"></i> Busca un hotel, selecciona una habitación y completa tu reserva.
            </div>
        </c:if>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p class="mb-0">© 2025 Sistema de Reservas Hoteleras</p>
    </footer>

    <!-- Bootstrap JS (opcional si usas navbar responsive) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
