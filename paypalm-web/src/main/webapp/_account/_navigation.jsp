<%--suppress XmlPathReference --%>

<%@ page errorPage="../error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="list-group">
    <div class="list-group-item" style="overflow: visible;">
        <div class="row-action-primary">
            <i class="material-icons"><img src="${pageContext.request.contextPath}/img/facepalm1.png" style="height: 50px; width: 50px; background: none;" /></i>
        </div>
        <div class="row-content">
            <div class="least-content">
                <div class="btn-group" style="overflow: visible;">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="account.label" /> <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="#" data-toggle="modal" data-target="#settings-dialog"><fmt:message key="settings.label" /></a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="<c:url value="/logout" />"><fmt:message key="log.out.label" /></a></li>
                    </ul>
                </div>
            </div>
            <h4 class="list-group-item-heading"><fmt:message key="welcome.back.label" /></h4>
            <p class="list-group-item-text">
                <i>
                    <c:choose>
                        <%--@elvariable id="userDTO" type="com.shimkiv.paypalm.dto.UserDTO"--%>
                        <c:when test="${userDTO != null && userDTO.userSettingsDTO != null}">
                            <c:choose>
                                <c:when test="${not empty fn:trim(userDTO.userSettingsDTO.firstName) && not empty fn:trim(userDTO.userSettingsDTO.lastName)}" >
                                    <c:out value="${userDTO.userSettingsDTO.firstName}" escapeXml="true" />&nbsp;
                                    <c:out value="${userDTO.userSettingsDTO.lastName}" escapeXml="true" />
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${userDTO.name}" escapeXml="true" />
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <shiro:principal />
                        </c:otherwise>
                    </c:choose>
                </i>
            </p>
        </div>
    </div>
</div>