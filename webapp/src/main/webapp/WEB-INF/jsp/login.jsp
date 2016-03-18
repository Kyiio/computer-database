<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<c:set var="attr" value=""></c:set>
	<c:set var="pageSize" value="10"></c:set>
	<%@include file="header.jsp" %>


	<spring:message code="login.title" var="loginTitle" />
	<spring:message code="login.placeholder" var="loginPlaceholder" />
	<spring:message code="login.label" var="loginLabel" />
	<spring:message code="password.label" var="passwordLabel" />
	<spring:message code="login.button.submit" var="submitButton" />
	<spring:message code="logout" var="logoutRef" />
	<spring:message code="alreadyLogged" var="alreadyLogged" />
	
	<section id="main">
	
		<div class="container">
			<c:if test="${not empty logout}">
				<div style="text-align: center;" class="alert alert-info">${logout}</div>
			</c:if>
		
			<sec:authorize access="!hasAnyRole('ROLE_USER','ROLE_ADMIN')">
				<div class="row">
					<div class="col-xs-8 col-xs-offset-2 box">
	
						<h1>${loginTitle}</h1>
					
						<div id="login-box">
						
							<c:if test="${not empty error}">
								<div class="alert alert-danger">${error}</div>
							</c:if>
					
							<form name="loginForm"
							  action="<c:url value="/j_spring_security_check" />" method="POST">
								<fieldset>
									<div class="form-group">
										<label for="username">${loginLabel}</label>
										<input type="text" id="username" name="username" class="form-control" placeholder="${loginPlaceholder}">
									</div>
									
									<div class="form-group">
										<label for="passwd">${passwordLabel}</label>
										<input type="password" id="passwd" name="passwd" class="form-control">
									</div>
									<div class="actions pull-right">
										<input name="submit" type="submit" value="${submitButton}" class="btn btn-primary"/>
									</div>
								</fieldset>		
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
				${alreadyLogged} <a href="${pageContext.request.contextPath}/logout">${logoutRef}</a> 
			</sec:authorize>
		</div>
	</section>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>