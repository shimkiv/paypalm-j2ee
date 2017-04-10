<%@ page errorPage="/error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <jsp:include page="/_header.jsp" flush="true" />
<body>

    <div class="container">
        <div class="well infobox form-signin">
            <%--@elvariable id="shiroLoginFailure" type=""--%>
            <c:if test="${shiroLoginFailure != null}">
                <div class="alert alert-dismissible alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <strong><fmt:message key="sign.in.form.error.label" /></strong>
                </div>
            </c:if>
            <form name="loginForm" class="form-signin" action="" method="post">
                <h2 class="form-signin-heading"><fmt:message key="sign.in.form.main.label" /></h2>
                <div class="form-group label-floating">
                    <label class="control-label" for="inputUserName"><fmt:message key="sign.in.form.user.name.label" /></label>
                    <input type="text" id="inputUserName" name="j_username" class="form-control" required autofocus />
                    <p class="help-block"><fmt:message key="sign.in.form.user.name.help.label" /></p>
                </div>
                <div class="form-group label-floating">
                    <label class="control-label" for="inputPassword"><fmt:message key="sign.in.form.user.password.label" /></label>
                    <input type="password" id="inputPassword" name="j_password" class="form-control" required />
                    <p class="help-block"><fmt:message key="sign.in.form.user.password.help.label" /></p>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="remember_me" value="true" /> <fmt:message key="sign.in.form.remember.me.label" />
                    </label>
                </div>
                <button class="btn btn-primary btn-lg btn-block btn-raised" type="submit"><fmt:message key="sign.in.form.button.label" /></button>
            </form>
        </div>
    </div>

    <jsp:include page="/_footer.jsp" flush="true" />
</body>
</html>
