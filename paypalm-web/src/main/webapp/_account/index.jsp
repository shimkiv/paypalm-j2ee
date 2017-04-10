<%@ page errorPage="../error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paypalm" uri="/paypalm" %>

<!DOCTYPE html>
<html>
    <jsp:include page="../_header.jsp" flush="true" />
<body>

    <div class="container">
        <jsp:include page="./_navigation.jsp" flush="true" />

        <%--@elvariable id="productDTOList" type="java.util.List<com.shimkiv.paypalm.dto.ProductDTO>"--%>
        <%--@elvariable id="shoppingCartBean" type="com.shimkiv.paypalm.ejb.ShoppingCartBean"--%>
        <%--@elvariable id="currency" type="java.util.Currency"--%>
        <div class="well infobox content-container table-responsive">
            <div>
                <h3 style="float: left;"><fmt:message key="products.label" /></h3>
                <h3 style="float: right;"><fmt:message key="currency.label" />&nbsp;${currency}</h3>
            </div>
            <c:choose>
                <c:when test="${not empty productDTOList}">
                    <form id="productEntityListForm" name="productEntityListForm" action="${pageContext.request.contextPath}/account/checkout" method="post">
                        <table class="table table-striped table-hover ">
                            <thead>
                            <tr>
                                <th></th>
                                <th><fmt:message key="product.form.product.name.label" /></th>
                                <th><fmt:message key="product.form.product.price.label" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${productDTOList}" var="productDTO">
                                <tr>
                                    <td>
                                        <div class="checkbox" title="<fmt:message key="product.form.checkbox.action.label" />">
                                            <label>
                                                <input title="<fmt:message key="product.form.checkbox.action.label" />" type="checkbox" name="productId_<paypalm:generateUniqueRef id="${productDTO.id}" />" value="<paypalm:generateUniqueRef id="${productDTO.id}" />" <c:if test="${shoppingCartBean.contains(productDTO.id)}">checked="checked"</c:if>/>
                                            </label>
                                        </div>
                                    </td>
                                    <td><a href="${pageContext.request.contextPath}/account/statistics/?product=<paypalm:generateUniqueRef id="${productDTO.id}" />" title="<fmt:message key="product.form.statistics.link.label" />" target="_self">${productDTO.name}</a></td>
                                    <td>
                                        ${currency.symbol}<fmt:formatNumber value="${productDTO.price}" type="number" />
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div>
                            <p style="float: left;"><fmt:message key="total.selected.products.label" />&nbsp;${shoppingCartBean.size()}</p>
                            <p style="float: right;"><fmt:message key="total.amount.label" />&nbsp;${currency.symbol}<fmt:formatNumber value="${shoppingCartBean.totalAmount}" type="number" /></p>
                        </div>
                        <br />
                        <br />
                        <button class="btn btn-primary btn-lg btn-block btn-raised" type="submit" <c:if test="${empty shoppingCartBean.productEntities}">disabled</c:if>><fmt:message key="proceed.to.checkout.button.label" /></button>
                        <hr />
                        <button id="clearShoppingCartButton" class="btn btn-default btn-lg btn-block btn-raised" type="button" <c:if test="${empty shoppingCartBean.productEntities}">disabled</c:if>><fmt:message key="clear.shopping.cart.button.label" /></button>
                    </form>
                </c:when>
                <c:otherwise>
                    <br />
                    <br />
                    <br />
                    <div class="centered-text">
                        <h4 style="padding-bottom: 20px;"><fmt:message key="product.form.no.products.label" /></h4>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        $(function () {
            $('#clearShoppingCartButton').on('click', function(e) {
                if($(this).not(':disabled')) {
                    $.ajax({
                        method: "POST",
                        url: "${pageContext.request.contextPath}/account/clear-shopping-cart"
                    }).always(function() {
                        window.location.replace("${pageContext.request.contextPath}/account/");
                    });
                }
            });

            $("[type='checkbox']").on('click', function(e) {
                $.ajax({
                    method: "POST",
                    url: "${pageContext.request.contextPath}/account/update-shopping-cart",
                    data: "product=" + $(this).attr('value') + "&status=" + $(this).is(':checked')
                }).always(function() {
                    window.location.replace("${pageContext.request.contextPath}/account/");
                });
            });
        });
    </script>

    <jsp:include page="../_footer.jsp" flush="true" />
    <jsp:include page="./_dialogs.jsp" flush="true" />
</body>
</html>
