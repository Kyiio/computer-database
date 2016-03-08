<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
				text="Application - Computer Database" cssClass="navbar-brand" ></pagination:link>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computerDto.computerId}</div>
					<h1>Edit Computer</h1>

					<form:form action="./edit-computer" method="POST"
						name="computerDto" commandName="computerDto" id="editComputerForm">
						
						<form:input path="computerId" id="computerId" name="computerId"
							type="hidden" value="${computerDto.computerId}" />
						
						<form:errors path="" element="div" class="alert alert-danger" />	
							
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label>
								<form:input path="computerName" type="text" class="form-control"
									id="computerName" name="computerName"
									value="${computerDto.computerName}" placeholder="Computer name" />
								<form:errors path="computerName" element="div"
									class="alert alert-danger" />
								<div id="computerNameErr"></div>
							</div>
							<div class="form-group">
								<label for="introducedDate">Introduced date (Format
									YYYY-MM-DD)</label>
								<form:input type="text" path="introducedDate"
									class="form-control" id="introducedDate" name="introducedDate"
									value="${computerDto.introducedDate}"
									placeholder="Introduced date" />
								<form:errors path="introducedDate" element="div"
									class="alert alert-danger" />
								<div id="introducedErr"></div>
							</div>
							<div class="form-group">
								<label for="discontinuedDate">Discontinued date (Format
									YYYY-MM-DD)</label>
								<form:input type="text" path="discontinuedDate"
									class="form-control" id="discontinuedDate"
									name="discontinuedDate" value="${computerDto.discontinuedDate}"
									placeholder="Discontinued date" />
								<form:errors path="discontinuedDate" element="div"
									class="alert alert-danger" />
								<div id="discontinuedErr"></div>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
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
							<input id="submit" type="submit" value="Edit"
								class="btn btn-primary"> or
							<pagination:link pageNumber="1" pageSize="10" target="dashboard"
								text="Cancel" cssClass="btn btn-default" ></pagination:link>
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