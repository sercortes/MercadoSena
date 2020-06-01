<%-- 
    Document   : activarCuenta
    Created on : 31/05/2020, 06:45:00 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Activación de cuenta</h1>
        <c:if test="${activa}">
            <h3>Cuenta activada</h3>
        </c:if>
        <c:if test="${!activa}">
            <h3>Error al activar la cuenta</h3>
        </c:if>
    </body>
</html>
