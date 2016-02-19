<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags"%>
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
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computer.computerId}</div>
					<h1>Edit Computer</h1>

					<form action="./edit-computer" method="POST" id="editComputerForm">
						<input id="computerId" name="computerId" type="hidden"
							value="${computer.computerId}" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${computer.computerName}" placeholder="Computer name">
								<div id="computerNameErr"></div>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date (Format
									YYYY-MM-DD)</label> <input type="date" class="form-control"
									id="introduced" name="introduced" value="${computer.introducedDate}"
									placeholder="Introduced date">
								<div id="introducedErr"></div>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date (Format
									YYYY-MM-DD)</label> <input type="date" class="form-control"
									id="discontinued" name="discontinued" value="${computer.discontinuedDate}"
									placeholder="Discontinued date">
								<div id="discontinuedErr"></div>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companyList}" var="company">
										<c:choose>
											<c:when test="${company.id == computer.companyId}">
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
								text="Cancel" cssClass="btn btn-default"></pagination:link>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src="js/jquery.min.js"></script>
	<script src="js/computerCheckForm.js"></script>
</body>
</html>