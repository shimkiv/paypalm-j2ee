<%@ page errorPage="/error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="now" value="<%= new java.util.Date() %>" />

<footer class="centered-text footer">
    <hr />
    <p>Serhii Shymkiv &copy; <fmt:formatDate pattern="yyyy" value="${now}" /></p>
</footer>
