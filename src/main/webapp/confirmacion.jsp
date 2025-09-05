<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body class="bg-light">

    <div class="container py-5">
        <div class="card p-4 shadow-sm">
            <h1 class="text-success text-center mb-4">¡Reserva confirmada!</h1>

            <p class="fs-5 text-center">Gracias por reservar con nosotros, <strong>${usuario.nombre}</strong>.</p>

            <h4 class="mt-4">Detalles de la reserva:</h4>
            <ul class="list-group list-group-flush mb-4">
                <li class="list-group-item"><strong>Hotel:</strong> ${reserva.habitacion.hotel.nombre}</li>
                <li class="list-group-item"><strong>Habitación:</strong> ${reserva.habitacion.numero} - ${reserva.habitacion.tipo}</li>
                <li class="list-group-item"><strong>Precio por noche:</strong> $${reserva.habitacion.precioPorNoche}</li>
                <li class="list-group-item"><strong>Fecha de inicio:</strong> ${reserva.fechaInicio}</li>
                <li class="list-group-item"><strong>Fecha de fin:</strong> ${reserva.fechaFin}</li>
            </ul>

            <p class="text-muted">Te hemos enviado una confirmación por correo electrónico.</p>

            <div class="d-flex justify-content-center gap-3">
                <a href="index.jsp" class="btn btn-secondary">Volver al inicio</a>
                <a href="search?destino=" class="btn btn-primary">Buscar otro hotel</a>
            </div>
        </div>
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p class="mb-0">© 2025 Sistema de Reservas Hoteleras</p>
    </footer>

</body>
</html>
