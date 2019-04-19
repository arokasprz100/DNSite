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
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Supermasters</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">

    <form:form method="POST" action="/supermasters" modelAttribute="supermasterForm" class="form-signin">
            <h2 class="form-signin-heading">Create new supermaster</h2>
            <spring:bind path="supermasterId.ip">
                <div>
                    <form:input type="text" path="supermasterId.ip" class="form-control" placeholder="IP [Required]"></form:input>
                    <form:errors path="supermasterId.ip"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="supermasterId.nameserver">
                <div>
                    <form:input type="text" path="supermasterId.nameserver" class="form-control" placeholder="Nameserver [Required]"></form:input>
                    <form:errors path="supermasterId.nameserver"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="account">
                <div>
                    <form:input type="text" path="account" class="form-control" placeholder="Account [Required]"></form:input>
                    <form:errors path="account"></form:errors>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>

        <table>
            <thead>
                <tr> <th> IP </th> <th> Nameserver </th> <th> Account </th> <th> Delete </th> </tr>
            </thead>
            <tbody>
                <c:forEach items="${supermastersList}" var="supermaster">
                    <tr>
                        <td> ${supermaster.supermasterId.ip} </td>
                        <td> ${supermaster.supermasterId.nameserver} </td>
                        <td> ${supermaster.account} </td>
                        <td>
                            <a href="/supermasters/delete/${supermaster.supermasterId.ip}/${supermaster.supermasterId.nameserver}" onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
