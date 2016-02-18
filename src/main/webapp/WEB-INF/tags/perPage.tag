<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags" %>

<%@ attribute name="pageSize" required="true" type="java.lang.Integer" description="The number of computer per page" %>

<c:forEach items="10,50,100" var="computerPerPageValue">
	<c:choose>
		<c:when test="${computerPerPageValue == pageSize}">
			<li class="active btn btn-default">
				<links:link pageNumber="1" pageSize="${pageSize}" target="#" text="${computerPerPageValue}"></links:link>
			</li>
		</c:when>
		<c:otherwise>
			<li class="btn btn-default">
				<links:link pageNumber="1" pageSize="${computerPerPageValue}" target="dashboard" text="${computerPerPageValue}"></links:link>
			</li>
		</c:otherwise>
	</c:choose>
</c:forEach>