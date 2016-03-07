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
					<h1>Add Computer</h1>
					<form action="./add-computer" method="POST" name="computerDto" id="addComputerForm">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name *</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name">
								<div id="computerNameErr"></div>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date (Format
									YYYY-MM-DD)</label> <input type="date" class="form-control"
									id="introducedDate" name="introducedDate" placeholder="Introduced date">
								<div id="introducedErr"></div>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date (Format
									YYYY-MM-DD)</label> <input type="date" class="form-control"
									id="discontinuedDate" name="discontinuedDate"
									placeholder="Discontinued date">
								<div id="discontinuedErr"></div>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companyList}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary"
								id="submit"> or
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