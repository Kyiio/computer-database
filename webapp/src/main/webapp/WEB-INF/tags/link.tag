<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="target" required="true" type="java.lang.String" description="URL of the page we want to go to" %>
<%@ attribute name="text" required="true" type="java.lang.String" description="text of the link" %>
<%@ attribute name="pageSize" required="false" type="java.lang.String" description="Number of computer shown per page" %>
<%@ attribute name="pageNumber" required="false" type="java.lang.String" description="Number of the page we want to go to" %>
<%@ attribute name="cssClass" required="false" type="java.lang.String" description="The classes that we want to add" %>
<%@ attribute name="searchName" required="false" type="java.lang.String" description="The name we will search for both computer and company" %>
<%@ attribute name="orderBy" required="false" type="java.lang.String" description="The attribute we will to sort by " %>
<%@ attribute name="orderType" required="false" type="java.lang.String" description="The sort type we will use" %>

<c:choose>
	<c:when test="${target != '#'}">
		<a class="${cssClass}" href="${target}?pageSize=${pageSize}&pageNumber=${pageNumber}&orderBy=${orderBy}&orderType=${orderType}&searchName=${searchName}">${text}</a>
	</c:when>
	<c:otherwise>
		<a class="${cssClass}" href="#">${text}</a>
	</c:otherwise>
</c:choose>