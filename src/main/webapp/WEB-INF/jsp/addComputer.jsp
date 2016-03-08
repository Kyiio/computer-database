<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<pagination:link pageNumber="1" pageSize="10" target="dashboard"
				text="Application - Computer Database" cssClass="navbar-brand"></pagination:link>
		</div>
	</header>

	<spring:message code="title.addcomputer" var="Title" />

	<spring:message code="label.computername" var="ComputerNameLabel" />
	<spring:message code="label.introduced" var="IntroducedLabel" />
	<spring:message code="label.discontinued" var="DiscontinuedLabel" />
	<spring:message code="label.company" var="CompanyLabel" />

	<spring:message code="placeholder.computername"
		var="ComputerNamePlaceholder" />
	<spring:message code="placeholder.introduced"
		var="IntroducedPlaceholder" />
	<spring:message code="placeholder.discontinued"
		var="DiscontinuedPlaceholder" />

	<spring:message code="button.cancel" var="CancelButton" />
	<spring:message code="button.submitadd" var="AddButton" />

	<!-- We put all the needed strings in an array so that the js can access them -->

	<script type="text/javascript">
		var strings = new Array();
		strings['error.name'] = "<spring:message code='error.name' javaScriptEscape='true' />";
		strings['error.date.format'] = "<spring:message code='error.date.format' javaScriptEscape='true' />";
		strings['error.date.introducedNullDiscontinuedNotNull'] = "<spring:message code='error.date.introducedNullDiscontinuedNotNull' javaScriptEscape='true' />";
		strings['error.date.discontinuedNotNullIntroducedNull'] = "<spring:message code='error.date.discontinuedNotNullIntroducedNull' javaScriptEscape='true' />";
		strings['error.date.introducedAfterDiscontinued'] = "<spring:message code='error.date.introducedAfterDiscontinued' javaScriptEscape='true' />";
		strings['error.date.discontinuedBeforeIntroduced'] = "<spring:message code='error.date.discontinuedBeforeIntroduced' javaScriptEscape='true' />";
	</script>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>${Title}</h1>

					<form:form action="./add-computer" method="POST" name="computerDto"
						commandName="computerDto" id="addComputerForm">

						<form:errors path="" element="div" class="alert alert-danger" />

						<fieldset>
							<div class="form-group">
								<label for="computerName">${ComputerNameLabel}</label>
								<form:input type="text" path="computerName" class="form-control"
									id="computerName" name="computerName"
									placeholder="${ComputerNamePlaceholder}" />
								<form:errors path="computerName" element="div"
									class="alert alert-danger" />
								<div id="computerNameErr"></div>
							</div>
							<div class="form-group">
								<label for="introduced">${IntroducedLabel}</label>
								<form:input type="text" path="introducedDate"
									class="form-control" id="introducedDate" name="introducedDate"
									placeholder="${IntroducedPlaceholder}" />
								<form:errors path="introducedDate" element="div"
									class="alert alert-danger" />
								<div id="introducedErr"></div>
							</div>
							<div class="form-group">
								<label for="discontinued">${DiscontinuedLabel}</label>
								<form:input path="discontinuedDate" type="text"
									class="form-control" id="discontinuedDate"
									name="discontinuedDate"
									placeholder="${DiscontinuedPlaceholder}" />
								<form:errors path="discontinuedDate" element="div"
									class="alert alert-danger" />
								<div id="discontinuedErr"></div>
							</div>
							<div class="form-group">
								<label for="companyId">${CompanyLabel}</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companyList}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${AddButton}" class="btn btn-primary"
								id="submit"> or
							<pagination:link pageNumber="1" pageSize="10" target="dashboard"
								text="${CancelButton}" cssClass="btn btn-default"></pagination:link>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>

	<script src="js/jquery.min.js"></script>
	<script src="js/computerCheckForm.js"></script>
</body>
</html>