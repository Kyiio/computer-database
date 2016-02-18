<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="target" required="true" type="java.lang.String" description="URL of the page we want to go to" %>
<%@ attribute name="text" required="true" type="java.lang.String" description="text of the link" %>
<%@ attribute name="pageSize" required="true" type="java.lang.String" description="Number of computer shown per page" %>
<%@ attribute name="pageNumber" required="true" type="java.lang.String" description="Number of the page we want to go to" %>

<c:choose>
	<c:when test="${target != '#'}">
		<a href="${target}?computer-per-page=${pageSize}&page-number=${pageNumber}">${text}</a>
	</c:when>
	<c:otherwise>
		<a href="#">${text}</a>
	</c:otherwise>
</c:choose>