<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body class="bg-light">

    <div class="container py-5">
        <h1 class="text-center mb-4"><i class="bi bi-door-open icono-hotel"></i> Habitaciones en <span class="text-primary">${hotel.nombre}</span></h1>

        <div class="row">
            <c:forEach var="habitacion" items="${habitaciones}">
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="hotel-card h-100 animate__animated animate__fadeInUp">
                        <h3><i class="bi bi-bed"></i> Habitación ${habitacion.numero}</h3>
                        <p><strong>Tipo:</strong> ${habitacion.tipo}</p>
                        <p><strong>Precio por noche:</strong> $${habitacion.precioPorNoche}</p>

                        <form action="ReservaFormServlet" method="get">
                            <input type="hidden" name="habitacionId" value="${habitacion.id}" />
                            <button type="submit" class="btn-reservar mt-3 w-100">Reservar</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-5">
        <p class="mb-0">© 2025 Sistema de Reservas Hoteleras</p>
    </footer>

</body>
</html>
