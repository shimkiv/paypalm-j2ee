<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <jsp:include page="/_header.jsp" flush="true" />
<body>

    <div class="container">
        <div class="well infobox content-container table-responsive">
            <h2><fmt:message key="error.form.main.label" /></h2>
            <%
                String closingTableTag = "</table>";

                try {
            %>
            <table width="100%" class="table table-striped table-hover">
                <c:if test="${pageContext.exception != null}">
                    <tr valign="top">
                        <td width="40%"><b><fmt:message key="error.form.error.label" /></b></td>
                        <td>${pageContext.exception}</td>
                    </tr>
                </c:if>
                <c:if test="${pageContext.errorData != null}">
                    <tr valign="top">
                        <td><b><fmt:message key="error.form.uri.label" /></b></td>
                        <td>${pageContext.errorData.requestURI}</td>
                    </tr>
                    <tr valign="top">
                        <td><b><fmt:message key="error.form.status.code.label" /></b></td>
                        <td>${pageContext.errorData.statusCode}</td>
                    </tr>
                </c:if>
                <c:if test="${pageContext.exception != null}">
                    <tr valign="top">
                        <td><b><fmt:message key="error.form.stack.trace.label" /></b></td>
                        <td>
                            <code>
                                <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
                                    ${trace}<br />
                                </c:forEach>
                            </code>
                        </td>
                    </tr>
                </c:if>
            </table>
            <%
                } catch (Exception e) {
                    out.println(closingTableTag);
                }
            %>
            <br />
            <h4><a class="btn btn-primary btn-lg btn-block btn-raised" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="return.back.button.label" /></a></h4>
        </div>
    </div>

    <jsp:include page="/_footer.jsp" flush="true" />
</body>
</html>
