<%@ page errorPage="/error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<shiro:authenticated>
    <c:redirect url="/account/" />
</shiro:authenticated>
<shiro:notAuthenticated>
    <c:redirect url="/login.jsp" />
</shiro:notAuthenticated>
