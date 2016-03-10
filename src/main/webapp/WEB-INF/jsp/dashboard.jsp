<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<pagination:link pageNumber="1" pageSize="${page.pageSize}"
				target="dashboard" text="Application - Computer Database"
				cssClass="navbar-brand"></pagination:link>
			<div>
				<a
					href="dashboard?pageSize=${page.pageSize}&pageNumber=${page.pageNumber}&orderBy=${page.orderBy}&orderType=${page.orderType}&searchName=${page.searchName}&lang=en">
					<img class="flags" src="fonts/england.png" alt="uk flag">
				</a> <a
					href="dashboard?pageSize=${page.pageSize}&pageNumber=${page.pageNumber}&orderBy=${page.orderBy}&orderType=${page.orderType}&searchName=${page.searchName}&lang=fr">
					<img class="flags" src="fonts/french.png" alt="fr flag">
				</a>
			</div>
		</div>
	</header>

	<spring:message code="button.filterbyname" var="SearchButtonName" />
	<spring:message code="button.filterbyname.placeholder"
		var="SearchPlaceholder" />
	<spring:message code="computerfound" var="ComputerFound" />
	<spring:message code="button.addcomputer" var="AddComputer" />
	<spring:message code="button.edit" var="Edit" />

	<spring:message code="field.computername" var="ComputerNameField" />
	<spring:message code="field.introduced" var="IntroducedField" />
	<spring:message code="field.discontinued" var="DiscontinuedField" />
	<spring:message code="field.companyname" var="CompanyNameField" />

	<!-- We put all the needed strings in an array so that the js can access them -->

	<script type="text/javascript">
		var strings = new Array();
		strings['button.edit'] = "<spring:message code='button.edit' javaScriptEscape='true' />";
		strings['button.view'] = "<spring:message code='button.view' javaScriptEscape='true' />";
		strings['delete.alert'] = "<spring:message code='delete.alert' javaScriptEscape='true' />";
	</script>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${page.totalNumberOfComputer}
				${ComputerFound}</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="./dashboard" method="GET"
						class="form-inline">
						<input type="hidden" name="pageSize" value="${page.pageSize}" />
						<input type="search" id="searchName" name="searchName"
							class="form-control" placeholder="${SearchPlaceholder}"
							value="${((page.searchName != '%')? page.searchName:'')}" /> <input
							type="submit" id="searchSubmit" value="${SearchButtonName}"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="add-computer">${AddComputer}</a>
					<a class="btn btn-default" id="editComputersButton" href="#"
						onclick="$.fn.toggleEditMode();">${Edit}</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="./dashboard" method="POST">
			<input type="hidden" name="selection" value=""> <input
				type="hidden" name="pageSize" value="${page.pageSize}" /><input
				type="hidden" name="searchName" value="${page.searchName}" />
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><pagination:link pageNumber="1"
								pageSize="${page.pageSize}" target="dashboard"
								text="${ComputerNameField}" searchName="${page.searchName}"
								orderBy="COMPUTER_NAME"
								orderType="${((page.orderBy == 'COMPUTER_NAME' && page.orderType == 'ASC' ) ? 'DESC' : 'ASC')}"></pagination:link></th>
						<th><pagination:link pageNumber="1"
								pageSize="${page.pageSize}" target="dashboard"
								text="${IntroducedField}" searchName="${page.searchName}"
								orderBy="INTRODUCED"
								orderType="${((page.orderBy == 'INTRODUCED' && page.orderType == 'ASC' ) ? 'DESC' : 'ASC')}"></pagination:link></th>
						<!-- Table header for Discontinued Date -->
						<th><pagination:link pageNumber="1"
								pageSize="${page.pageSize}" target="dashboard"
								text="${DiscontinuedField}" searchName="${page.searchName}"
								orderBy="DISCONTINUED"
								orderType="${((page.orderBy == 'DISCONTINUED' && page.orderType == 'ASC' ) ? 'DESC' : 'ASC')}"></pagination:link></th>
						<!-- Table header for Company -->
						<th><pagination:link pageNumber="1"
								pageSize="${page.pageSize}" target="dashboard"
								text="${CompanyNameField}" searchName="${page.searchName}"
								orderBy="COMPANY_NAME"
								orderType="${((page.orderBy == 'COMPANY_NAME' && page.orderType == 'ASC' ) ? 'DESC' : 'ASC')}"></pagination:link></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.content}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.computerId}"></td>
							<td><a
								href="edit-computer?computerId=${computer.computerId}"
								onclick=""><c:out value="${computer.computerName}" /></a></td>
							<td>${computer.introducedDate}</td>
							<td>${computer.discontinuedDate}</td>
							<td>${computer.companyName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center" style="display: inline-block;">
			<!-- margin-left: 20em;-->
			<ul class="pagination">
				<pagination:pagination pageSize="${page.pageSize}"
					pageNumber="${page.pageNumber}"
					maxPageNumber="${page.maxPageNumber}"
					searchName="${page.searchName}" orderType="${page.orderType}"
					orderBy="${page.orderBy}"></pagination:pagination>
			</ul>
		</div>

		<div class="btn-group btn-group-sm pull-right"
			style="display: inline-block;" role="group">
			<pagination:perPage pageSize="${page.pageSize}"
				searchName="${page.searchName}" orderType="${page.orderType}"
				orderBy="${page.orderBy}"></pagination:perPage>

		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>