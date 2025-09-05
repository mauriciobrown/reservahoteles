<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body class="bg-light">

    <div class="container py-5">
        <h1 class="text-center mb-4">Reservar habitación</h1>

        <c:if test="${not empty habitacion}">
            <div class="hotel-card mb-4">
                <h3>${habitacion.hotel.nombre}</h3>
                <p><strong>Ubicación:</strong> ${habitacion.hotel.ubicacion}</p>
                <p><strong>Habitación:</strong> ${habitacion.numero} - ${habitacion.tipo}</p>
                <p><strong>Precio por noche:</strong> $${habitacion.precioPorNoche}</p>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger text-center mb-4">
                    ${error}
                </div>
            </c:if>

            <div class="card p-4 shadow-sm">
                <form action="ReservaServlet" method="post">
                    <input type="hidden" name="habitacionId" value="${habitacion.id}" />

                    <div class="mb-3">
                        <label for="fechaInicio" class="form-label">Fecha de inicio:</label>
                        <input type="date" name="fechaInicio" id="fechaInicio" class="form-control"
                               value="${param.fechaInicio}" required />
                    </div>

                    <div class="mb-3">
                        <label for="fechaFin" class="form-label">Fecha de fin:</label>
                        <input type="date" name="fechaFin" id="fechaFin" class="form-control"
                               value="${param.fechaFin}" required />
                    </div>

                    <button type="submit" class="btn-reservar w-100">Confirmar reserva</button>
                </form>

                <div class="text-center mt-4">
                    <a href="search" class="btn btn-outline-primary">Buscar otro hotel</a>
                </div>
            </div>
        </c:if>

        <c:if test="${empty habitacion}">
            <div class="alert alert-danger text-center">
                Error: No se pudo cargar la información de la habitación.
            </div>
            <div class="text-center mt-3">
                <a href="index.jsp" class="btn btn-secondary">Volver al inicio</a>
            </div>
        </c:if>
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-auto">
        <p class="mb-0">© 2025 Sistema de Reservas Hoteleras</p>
    </footer>

</body>
</html>
