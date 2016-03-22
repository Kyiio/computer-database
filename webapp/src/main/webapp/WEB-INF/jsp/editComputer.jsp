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
<%@include file="common/cssHead.jsp" %>
</head>
<body>
	<c:set var="attr" value="computerId=${computerDto.computerId}"></c:set>
	<c:set var="pageSize" value="10"></c:set>
	<%@include file="common/header.jsp" %>

	<spring:message code="title.editcomputer" var="Title" />
	<spring:message code="or" var="Or" />
	
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
	<spring:message code="button.submitedit" var="EditButton" />

	<!-- We put all the needed strings in an array so that the js can access them -->

	<script type="text/javascript">
		var local = "${pageContext.response.locale}";
		var strings = new Array();
		strings['error.name'] = "<spring:message code='error.name' javaScriptEscape='true' />";
		strings['error.date.format'] = "<spring:message code='error.date.format' javaScriptEscape='true' />";
		strings['error.date.introducedNullDiscontinuedNotNull'] = "<spring:message code='error.date.introducedNullDiscontinuedNotNull' javaScriptEscape='true' />";
		strings['error.date.discontinuedNotNullIntroducedNull'] = "<spring:message code='error.date.discontinuedNotNullIntroducedNull' javaScriptEscape='true' />";
		strings['error.date.introducedAfterDiscontinued'] = "<spring:message code='error.date.introducedAfterDiscontinued' javaScriptEscape='true' />";
		strings['error.date.discontinuedBeforeIntroduced'] = "<spring:message code='error.date.discontinuedBeforeIntroduced' javaScriptEscape='true' />";
		strings['date.format'] = "<spring:message code='date.format' javaScriptEscape='true' />";
		strings['date.jquery.regex'] = "<spring:message code='date.jquery.regex' javaScriptEscape='true' />";
	</script>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computerDto.computerId}</div>
					<h1>${Title}</h1>

					<form:form action="/computerdatabase/computers/edit" method="POST"
						name="computerDto" commandName="computerDto" id="editComputerForm">

						<form:input path="computerId" type="hidden" value="${computerDto.computerId}"/>

						<form:errors path="" element="div" class="alert alert-danger" />

						<fieldset>
							<div class="form-group">
								<label for="computerName">${ComputerNameLabel}</label>
								<form:input type="text" path="computerName" class="form-control"
									id="computerName" name="computerName" value="${computerDto.computerName}"
									placeholder="${ComputerNamePlaceholder}" />
								<form:errors path="computerName" element="div"
									class="alert alert-danger" />
								<div id="computerNameErr"></div>
							</div>
							<div class="form-group">
								<label for="introduced">${IntroducedLabel}</label>
								<form:input type="text" path="introducedDate"
									class="form-control" id="introducedDate" name="introducedDate" value="${computerDto.introducedDate}"
									placeholder="${IntroducedPlaceholder}" />
								<form:errors path="introducedDate" element="div"
									class="alert alert-danger" />
								<div id="introducedErr"></div>
							</div>
							<div class="form-group">
								<label for="discontinued">${DiscontinuedLabel}</label>
								<form:input path="discontinuedDate" type="text"
									class="form-control" id="discontinuedDate"
									name="discontinuedDate" value="${computerDto.discontinuedDate}"
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
										<c:choose>
											<c:when test="${company.id == computerDto.companyId}">
												<option selected="selected" value="${company.id}">${company.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${company.id}">${company.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${EditButton}" class="btn btn-primary"
								id="submit"> ${Or}
							<pagination:link pageNumber="1" pageSize="10" target="${pageContext.request.contextPath}/computers"
								text="${CancelButton}" cssClass="btn btn-default"></pagination:link>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>

	<%@include file="common/jsFoot.jsp" %>
	<script src="${pageContext.request.contextPath}/js/computerCheckForm.js"></script>
</body>
</html>