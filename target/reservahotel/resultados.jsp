<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<jsp:include page="includes/head.jsp" />

<body class="bg-light">

    <div class="container py-5">
        <h1 class="text-center mb-4">Hoteles encontrados</h1>

        <c:if test="${not empty hoteles}">
            <div class="row">
                <c:forEach var="hotel" items="${hoteles}">
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="hotel-card">
                            <h3>${hotel[1]}</h3> <!-- nombre -->
                            <p><strong>Ubicación:</strong> ${hotel[2]}</p>
                            <p><strong>Estrellas:</strong> ${hotel[3]}</p>
                            <p><strong>Precio mínimo por noche:</strong> $${hotel[4]}</p>
                            <a href="HabitacionesServlet?hotelId=${hotel[0]}" class="btn-reservar mt-3 d-block text-center">Ver habitaciones</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty hoteles}">
            <div class="alert alert-warning text-center">
                No se encontraron hoteles para su búsqueda.
            </div>
        </c:if>
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-auto">
        <p class="mb-0">© 2025 Sistema de Reservas Hoteleras</p>
    </footer>

</body>
</html>
