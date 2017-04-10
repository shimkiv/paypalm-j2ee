<%@ page errorPage="../error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="paypalm" uri="/paypalm" %>

<!DOCTYPE html>
<html>
<jsp:include page="../_header.jsp" flush="true" />
<body>

<div class="container">
    <jsp:include page="./_navigation.jsp" flush="true" />

    <%--@elvariable id="orderDTOList" type="java.util.List<com.shimkiv.paypalm.dto.OrderDTO>"--%>
    <%--@elvariable id="productDTO" type="com.shimkiv.paypalm.dto.ProductDTO"--%>
    <%--@elvariable id="currency" type="java.util.Currency"--%>
    <div class="well infobox content-container table-responsive">
        <div>
            <h3 style="float: left;"><fmt:message key="statistics.label" /></h3>
            <h3 style="float: right;"><fmt:message key="currency.label" />&nbsp;${currency}</h3>
        </div>
        <c:choose>
            <c:when test="${not empty productDTO && not empty orderDTOList}">
                <br />
                <br />
                <br />
                <h3><i>&ldquo;${productDTO.name}&rdquo;</i>&nbsp;<fmt:message key="related.orders.label" /></h3>
                <table class="table table-striped table-hover ">
                    <thead>
                    <tr>
                        <th><fmt:message key="statistics.form.id.label" /></th>
                        <th><fmt:message key="statistics.form.products.label" /></th>
                        <th><fmt:message key="statistics.form.date.time.label" /></th>
                        <th><fmt:message key="statistics.form.pay.pal.id.label" /></th>
                        <th><fmt:message key="statistics.form.card.type.label" /></th>
                        <th><fmt:message key="statistics.form.card.number.label" /></th>
                        <th><fmt:message key="statistics.form.amount.label" /></th>
                        <th><fmt:message key="statistics.form.status.label" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="totalAmount" value="${0}"/>

                    <c:forEach items="${orderDTOList}" var="orderDTO">
                        <tr class="<c:choose><c:when test="${orderDTO.status == 'approved'}">success</c:when><c:otherwise>danger</c:otherwise></c:choose>">
                            <td><paypalm:generateUniqueRef id="${orderDTO.id}" /></td>
                            <td>
                                <c:forEach items="${orderDTO.productDTOList}" var="productDTOItem">
                                    ${productDTOItem.name}
                                    <br />
                                </c:forEach>
                            </td>
                            <td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${orderDTO.creationDate.time}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty orderDTO.link}">
                                        <a href="${orderDTO.link}" title="<fmt:message key="statistics.form.navigate.to.pay.pal.label" />" target="_blank">${orderDTO.paypalId}</a>
                                    </c:when>
                                    <c:otherwise>
                                        ${orderDTO.paypalId}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${fn:toUpperCase(orderDTO.cardType)}</td>
                            <td><paypalm:decryptAndMask toMask="${orderDTO.cardNumber}" lastShown="4" mask="*" /></td>
                            <td>${currency.symbol}<fmt:formatNumber value="${orderDTO.amount}" type="number" /></td>
                            <td><b>${fn:toUpperCase(orderDTO.status)}</b></td>
                        </tr>

                        <c:set var="totalAmount" value="${totalAmount + orderDTO.amount}" />
                    </c:forEach>
                    </tbody>
                </table>
                <div>
                    <p style="float: left;"><fmt:message key="total.orders.label" />&nbsp;${orderDTOList.size()}</p>
                    <p style="float: right;"><fmt:message key="total.amount.label" />&nbsp;${currency.symbol}<fmt:formatNumber value="${totalAmount}" type="number" /></p>
                </div>
                <br />
            </c:when>
            <c:otherwise>
                <br />
                <br />
                <br />
                <div class="centered-text">
                    <h4 style="padding-bottom: 20px;"><fmt:message key="statistics.form.no.data.label" /></h4>
                </div>
            </c:otherwise>
        </c:choose>
        <hr />
        <button id="returnBack" class="btn btn-default btn-lg btn-block btn-raised" type="button"><fmt:message key="return.back.button.label" /></button>
    </div>
</div>

<script>
    $(function () {
        $('#returnBack').on('click', function(e) {
            window.location.replace("${pageContext.request.contextPath}/account/");
        });
    });
</script>

<jsp:include page="../_footer.jsp" flush="true" />
<jsp:include page="./_dialogs.jsp" flush="true" />
</body>
</html>