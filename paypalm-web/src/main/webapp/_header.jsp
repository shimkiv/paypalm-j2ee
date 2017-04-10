<%@ page errorPage="/error.jsp" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name=viewport content="width=device-width, initial-scale=1, user-scalable=no, maximum-scale=1">
    <meta name=description content="PayPalm">
    <meta name=author content="Serhii Shymkiv">

    <title><fmt:message key="application.title.label" /></title>

    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico" />

    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-material-design.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ripples.min.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/app.css" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ripples.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/material.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/spin.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.js"></script>

    <script>
        $(function () {
            $.material.init();

            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>
</head>
