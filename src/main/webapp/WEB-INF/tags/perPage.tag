<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags" %>

<%@ attribute name="pageSize" required="true" type="java.lang.Integer" description="The number of computer per page" %>

<c:forEach items="10,50,100" var="computerPerPageValue">
	<c:choose>
		<c:when test="${computerPerPageValue == pageSize}">
			<links:link pageNumber="1" pageSize="${pageSize}" target="#" text="${computerPerPageValue}" cssClass="btn btn-default disabled"></links:link>
		</c:when>
		<c:otherwise>
			<links:link pageNumber="1" pageSize="${computerPerPageValue}" target="dashboard" text="${computerPerPageValue}" cssClass="btn btn-default"></links:link>
		</c:otherwise>
	</c:choose>
</c:forEach>