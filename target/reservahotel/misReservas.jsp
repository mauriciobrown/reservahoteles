<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body class="bg-light">

    <div class="container py-5">
        <h1 class="text-center mb-4">Reservas de <span class="text-primary">${sessionScope.usuario.nombre}</span></h1>

        <c:if test="${not empty reservas}">
            <div class="table-responsive">
                <table class="table table-bordered table-striped align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>Hotel</th>
                            <th>Habitación</th>
                            <th>Tipo</th>
                            <th>Precio por noche</th>
                            <th>Fecha inicio</th>
                            <th>Fecha fin</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reserva" items="${reservas}">
                            <tr>
                                <td>${reserva.habitacion.hotel.nombre}</td>
                                <td>${reserva.habitacion.numero}</td>
                                <td>${reserva.habitacion.tipo}</td>
                                <td>$${reserva.habitacion.precioPorNoche}</td>
                                <td>${reserva.fechaInicio}</td>
                                <td>${reserva.fechaFin}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${empty reservas}">
            <div class="alert alert-info text-center">
                No tienes reservas registradas.
            </div>
        </c:if>

        <div class="text-center mt-4">
            <a href="index.jsp" class="btn btn-secondary">Volver al inicio</a>
        </div>
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p class="mb-0">© 2025 Sistema de Reservas Hoteleras</p>
    </footer>

</body>
</html>
