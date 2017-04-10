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
                <h3 style="float: left;"><fmt:message key="checkout.label" /></h3>
                <h3 style="float: right;"><fmt:message key="currency.label" />&nbsp;${currency}</h3>
            </div>
            <c:choose>
                <c:when test="${not empty productDTOList}">
                    <form id="payForm" name="payForm" action="${pageContext.request.contextPath}/account/pay" method="post">
                        <table class="table table-striped table-hover ">
                            <thead>
                            <tr>
                                <th><fmt:message key="product.form.product.name.label" /></th>
                                <th><fmt:message key="product.form.product.price.label" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${productDTOList}" var="productDTO">
                                <tr>
                                    <td>${productDTO.name}</td>
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
                        <hr />
                        <h3><fmt:message key="card.info.label" /></h3>
                        <div class="form-checkout">
                            <div class="radio radio-primary">
                                <label>
                                    <input type="radio" name="cardType" id="visa" value="visa" checked="">
                                    Visa Credit
                                </label>
                            </div>
                            <div class="radio radio-primary">
                                <label>
                                    <input type="radio" name="cardType" id="mastercard" value="mastercard">
                                    MasterCard
                                </label>
                            </div>
                            <div class="form-group label-floating">
                                <label class="control-label" for="cardNumber"><fmt:message key="checkout.form.card.number.label" /></label>
                                <input type="number" id="cardNumber" name="cardNumber" class="form-control" maxlength="20" required autofocus />
                                <p class="help-block"><fmt:message key="checkout.form.card.number.help.label" /></p>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="expMonth"><fmt:message key="checkout.form.exp.month.label" /></label>
                                <select id="expMonth" name="expMonth" class="form-control" required>
                                    <option value="01" selected>01</option>
                                    <option value="02">02</option>
                                    <option value="03">03</option>
                                    <option value="04">04</option>
                                    <option value="05">05</option>
                                    <option value="06">06</option>
                                    <option value="07">07</option>
                                    <option value="08">08</option>
                                    <option value="09">09</option>
                                    <option value="10">10</option>
                                    <option value="11">11</option>
                                    <option value="12">12</option>
                                </select>
                                <p class="help-block"><fmt:message key="checkout.form.exp.month.help.label" /></p>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="expYear"><fmt:message key="checkout.form.exp.year.label" /></label>
                                <select id="expYear" name="expYear" class="form-control" required>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2020">2020</option>
                                    <option value="2021">2021</option>
                                    <option value="2022">2022</option>
                                    <option value="2023">2023</option>
                                    <option value="2024">2024</option>
                                    <option value="2025">2025</option>
                                    <option value="2026">2026</option>
                                    <option value="2027">2027</option>
                                    <option value="2028" selected>2028</option>
                                </select>
                                <p class="help-block"><fmt:message key="checkout.form.exp.year.help.label" /></p>
                            </div>
                            <div class="form-group label-floating">
                                <label class="control-label" for="cardCvv"><fmt:message key="checkout.form.cvv.label" /></label>
                                <input type="password" id="cardCvv" name="cardCvv" class="form-control" maxlength="4" required />
                                <p class="help-block"><fmt:message key="checkout.form.cvv.help.label" /></p>
                            </div>
                            <div class="form-group label-floating">
                                <label class="control-label" for="cardName"><fmt:message key="checkout.form.name.label" /></label>
                                <input type="text" id="cardName" name="cardName" class="form-control" maxlength="50" required />
                                <p class="help-block"><fmt:message key="checkout.form.name.help.label" /></p>
                            </div>
                        </div>
                        <hr />
                        <button id="payButton" class="btn btn-primary btn-lg btn-block btn-raised" type="submit" <c:if test="${empty shoppingCartBean.productEntities}">disabled</c:if>><fmt:message key="pay.button.label" />&nbsp;${currency.symbol}<fmt:formatNumber value="${shoppingCartBean.totalAmount}" type="number" /></button>
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
            <hr />
            <button id="returnBack" class="btn btn-default btn-lg btn-block btn-raised" type="button"><fmt:message key="return.back.button.label" /></button>
        </div>
    </div>

    <script>
        $(function () {
            var spinner =
                new Spinner({
                    lines: 13 // The number of lines to draw
                    , length: 28 // The length of each line
                    , width: 14 // The line thickness
                    , radius: 42 // The radius of the inner circle
                    , scale: 1 // Scales overall size of the spinner
                    , corners: 1 // Corner roundness (0..1)
                    , color: '#000' // #rgb or #rrggbb or array of colors
                    , opacity: 0.25 // Opacity of the lines
                    , rotate: 0 // The rotation offset
                    , direction: 1 // 1: clockwise, -1: counterclockwise
                    , speed: 1 // Rounds per second
                    , trail: 60 // Afterglow percentage
                    , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
                    , zIndex: 2e9 // The z-index (defaults to 2000000000)
                    , className: 'spinner' // The CSS class to assign to the spinner
                    , top: '50%' // Top position relative to parent
                    , left: '50%' // Left position relative to parent
                    , shadow: false // Whether to render a shadow
                    , hwaccel: false // Whether to use hardware acceleration
                    , position: 'absolute' // Element positioning
                });
            var paymentProcDialog =
                $('#payment-processing-dialog');
            var paymentProcessingInfoId =
                '#paymentProcessingInfo';

            paymentProcDialog.modal({
                backdrop: 'static',
                keyboard: false,
                show: false
            });

            paymentProcDialog.on('shown.bs.modal', function (e) {
                $(paymentProcessingInfoId).empty();
                $('#paymentProcessingDialogCloseButton').hide();
                spinner.spin(document.getElementById('paymentProcessingInfo'));
            });

            $('#returnBack').on('click', function(e) {
                window.location.replace("${pageContext.request.contextPath}/account/");
            });

            $('#payForm').on('submit', function(e) {
                $('#payment-processing-dialog').modal('show');

                $.ajax({
                    method: "POST",
                    url: "${pageContext.request.contextPath}/account/pay",
                    data: $("#payForm").serialize()
                }).always(function(json, textStatus, jqXHR) {
                    var color = '';

                    spinner.stop();
                    $(paymentProcessingInfoId).removeAttr('style');
                    $('#paymentProcessingDialogCloseButton').show();

                    if(textStatus === 'success') {
                        if(json.state === "approved") {
                            color = 'green';
                        } else {
                            color = 'red';
                        }

                        $(paymentProcessingInfoId).append('<div class="centered-text"><h1 style="padding-bottom: 20px; color: ' + color + ';">' + json.state.toUpperCase() + '</h1></div>');
                    } else {
                        $(paymentProcessingInfoId).append('<div class="centered-text"><h1 style="padding-bottom: 20px; color: red;"><fmt:message key="error.label" /></h1></div>');
                    }
                });

                e.preventDefault();
            });

            $('#paymentProcessingDialogCloseButton').on('click', function(e) {
                window.location.replace("${pageContext.request.contextPath}/account/");
            });
        });
    </script>

    <jsp:include page="../_footer.jsp" flush="true" />
    <jsp:include page="./_dialogs.jsp" flush="true" />
</body>
</html>