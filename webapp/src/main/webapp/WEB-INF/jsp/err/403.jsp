<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<%@include file="../common/cssHead.jsp" %>
</head>
<body>
	<c:set var="attr" value=""></c:set>
	<c:set var="pageSize" value="10"></c:set>
	<%@include file="../common/header.jsp" %>
	
	<spring:message code="error.page.forbidden" var="errorText" />
	<spring:message code="error.page.forbidden.message" var="messageToUser" />
	
	
	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				${errorText}
				<br/>
				${pageContext.request.userPrincipal.name} -> ${messageToUser}
			</div>
		</div>
	</section>

	<%@include file="../common/jsFoot.jsp" %>

</body>
</html>