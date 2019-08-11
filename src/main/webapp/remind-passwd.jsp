<%--suppress ALL --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta content="text/html; charset=UTF-8; X-Content-Type-Options=nosniff" http-equiv="Content-Type" />
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Reimnd password</title>

    <link rel="shortcut icon" href="../src/resources/images/icon.png" />
    <link href="${contextPath}/src/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/src/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/src/resources/styles/remind-password.css" rel="stylesheet">
    <link
            href="https://fonts.googleapis.com/css?family=Montserrat:400,600,700,900|Varela"
            rel="stylesheet"
    />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
    <%--TODO informacja o wysłaniu maila z hasłem tymczasowym--%>
    <form:form method="POST" modelAttribute="userForm" class="form-signin RemindPasswordForm">
        <h3 class="form-signin-heading RemindPasswordHeading">Remind Password</h3>

        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <div class="remind-textbox">
                    <i class="fas fa-envelope"></i>
                    <form:input type="text" path="username" class="form-control RemindPasswordControl" placeholder="Username"></form:input>
                </div>
                <form:errors path="username"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block remind-button" type="submit">Submit</button>
    </form:form>


</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="../src/resources/js/bootstrap.min.js"></script>
</body>
</html>