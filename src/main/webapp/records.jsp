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

    <title>Log in with your account</title>

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

        <form:form method="POST" action="/records" modelAttribute="recordForm" class="form-signin">
            <h2 class="form-signin-heading">Create new record</h2>
            <spring:bind path="name">
                <div>
                    <form:input type="text" path="name" class="form-control" placeholder="Name [Required]"></form:input>
                    <form:errors path="name"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="type">
                <div>
                    <form:input type="text" path="type" class="form-control" placeholder="Type [Required]"></form:input>
                    <form:errors path="type"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="content">
                <div>
                    <form:input type="text" path="content" class="form-control" placeholder="Content [Required]"></form:input>
                    <form:errors path="content"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="ttl">
                <div>
                    <form:input type="number" path="ttl" class="form-control" placeholder="TTL [Required]"></form:input>
                    <form:errors path="ttl"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="priority">
                <div>
                    <form:input type="number" path="priority" class="form-control" placeholder="Priority [Required]"></form:input>
                    <form:errors path="priority"></form:errors>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>

    </div>
    <!-- /container -->

    <table>
        <thead>
            <tr> <th> Id </th> <th> Name </th> <th> Type </th> <th> Content </th> <th> TTL </th> <th> Priority </th> </tr>
        </thead>
        <tbody>
        <c:forEach items="${recordsList}" var="record">
            <tr>
                <td> ${record.id} </td>
                <td> ${record.name} </td>
                <td> ${record.type} </td>
                <td> ${record.content} </td>
                <td> ${record.ttl} </td>
                <td> ${record.priority} </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
