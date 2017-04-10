<%--@elvariable id="userSettingsEntity" type="com.shimkiv.paypalm.dto.UserSettingsEntity"--%>
<%@ page errorPage="../error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="com.shimkiv.paypalm.dto.UserDTO" %>
<%@ page import="com.shimkiv.paypalm.dto.UserSettingsDTO" %>
<%@ page import="static com.shimkiv.paypalm.properties.PropertiesManager.getPropertyValue" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    UserSettingsDTO userSettingsDTO =
            ((UserDTO) request.
                    getAttribute(
                            getPropertyValue("user.dto.attr.name"))).
                    getUserSettingsDTO();

    if(userSettingsDTO == null) {
        userSettingsDTO =
                new UserSettingsDTO();
    }
%>

<div id="settings-dialog" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><fmt:message key="settings.label" /></h4>
            </div>
            <form name="settingsForm" action="${pageContext.request.contextPath}/account/update" method="post">
                <div class="modal-body">
                    <div class="form-group label-floating">
                        <label class="control-label" for="inputUserFirstName"><fmt:message key="settings.form.user.first.name.label" /></label>
                        <input type="text" id="inputUserFirstName" name="userFirstName" class="form-control" value="<%= userSettingsDTO.getFirstName() %>" autofocus />
                        <p class="help-block"><fmt:message key="settings.form.user.first.name.help.label" /></p>
                    </div>
                    <div class="form-group label-floating">
                        <label class="control-label" for="inputUserLastName"><fmt:message key="settings.form.user.last.name.label" /></label>
                        <input type="text" id="inputUserLastName" name="userLastName" class="form-control" value="<%= userSettingsDTO.getLastName() %>" />
                        <p class="help-block"><fmt:message key="settings.form.user.last.name.help.label" /></p>
                    </div>
                    <div class="form-group label-floating">
                        <label class="control-label" for="inputUserEmail"><fmt:message key="settings.form.user.email.label" /></label>
                        <input type="email" id="inputUserEmail" name="userEmail" class="form-control" value="<%= userSettingsDTO.getEmail() %>" />
                        <p class="help-block"><fmt:message key="settings.form.user.email.help.label" /></p>
                    </div>
                    <div class="form-group label-floating">
                        <label class="control-label" for="inputPayPalClientId"><fmt:message key="settings.form.pay.pal.client.id.label" /></label>
                        <input type="text" id="inputPayPalClientId" name="payPalClientId" class="form-control" value="<%= userSettingsDTO.getPayPalClientId() %>" />
                        <p class="help-block"><fmt:message key="settings.form.pay.pal.client.id.help.label" /></p>
                    </div>
                    <div class="form-group label-floating">
                        <label class="control-label" for="inputPayPalSecretPhrase"><fmt:message key="settings.form.pay.pal.secret.phrase.label" /></label>
                        <input type="text" id="inputPayPalSecretPhrase" name="payPalSecretPhrase" class="form-control" value="<%= userSettingsDTO.getPayPalSecretPhrase() %>" />
                        <p class="help-block"><fmt:message key="settings.form.pay.pal.secret.phrase.help.label" /></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary"><fmt:message key="save.button.label" /></button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="payment-processing-dialog" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><fmt:message key="payment.processing.label" /></h4>
            </div>
            <div class="modal-body">
                <div id="paymentProcessingInfo" style="height: 300px; width: 100%;"></div>
            </div>
            <div class="modal-footer">
                <button id="paymentProcessingDialogCloseButton" type="button" class="btn btn-primary"><fmt:message key="close.button.label" /></button>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        $('#settings-dialog').on('shown.bs.modal', function () {
            $('#inputUserFirstName').focus();
        })
    });
</script>