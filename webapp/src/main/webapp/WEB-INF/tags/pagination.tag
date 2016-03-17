<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags" %>
<%@ attribute name="pageNumber" required="true" type="java.lang.Integer" description="The page number" %>
<%@ attribute name="pageSize" required="true" type="java.lang.Integer" description="The number of computer per page" %>
<%@ attribute name="maxPageNumber" required="true" type="java.lang.Integer" description="The total number of pages" %>
<%@ attribute name="searchName" required="false" type="java.lang.String" description="The name we will search for both computer and company" %>
<%@ attribute name="orderBy" required="false" type="java.lang.String" description="The attribute we will to sort by " %>
<%@ attribute name="orderType" required="false" type="java.lang.String" description="The sort type we will use" %>

<spring:message code="button.next" var="NextButton" />
<spring:message code="button.previous" var="PreviousButton" />

<c:set var="radius" value="4"/>
<c:set var="begin" value="${(pageNumber - radius > 1 ? pageNumber - radius : 1)}"/>
<c:set var="end" value="${(pageNumber + radius < maxPageNumber ? pageNumber + radius : maxPageNumber)}"/>

<!-- Display the previous button if the current page isn't the first one -->
<c:choose>
	<c:when test="${pageNumber > 1}">
		<li>
			<links:link pageNumber="${pageNumber-1}" pageSize="${pageSize}" target="computers" text="${PreviousButton}" searchName="${searchName}" orderType="${orderType}" orderBy="${orderBy}"></links:link>
		</li>	
	</c:when>
	<c:otherwise>
		<li class="disabled">
			<links:link pageNumber="${pageNumber}" pageSize="${pageSize}" target="#" text="${PreviousButton}" ></links:link>
		</li>	
	</c:otherwise>
</c:choose>

<!-- Display a ... if their is more page button available then what we put on screen -->

<c:if test="${begin > 1}">
	<li>
		<links:link pageNumber="1" pageSize="${pageSize}" target="computers" text="1" searchName="${searchName}" orderType="${orderType}" orderBy="${orderBy}"></links:link>
	</li>
	<c:if test="${begin > 2}">
		<li>
			<links:link pageNumber="" pageSize="" target="#" text="..."></links:link>
		</li>
	</c:if>
</c:if>


<!-- Display all the other page buttons between pageNumber-radius and pageNumber+radius -->

<c:forEach var="i" begin="${begin}" end="${end}">

	<li class="${((pageNumber == i)?'active':'')}">
		<links:link pageNumber="${i}" pageSize="${pageSize}" target="computers" text="${i}" searchName="${searchName}" orderType="${orderType}" orderBy="${orderBy}"></links:link>
	</li>
</c:forEach>

<!-- Display a ... if their is more page button available then what we put on screen -->

<c:if test="${end < maxPageNumber}">
	
	<c:if test="${end < maxPageNumber-1}">
		<li>
			<links:link pageNumber="" pageSize="" target="#" text="..."></links:link>
		</li>
	</c:if>
	<li>
		<links:link pageNumber="${maxPageNumber}" pageSize="${pageSize}" target="computers" text="${maxPageNumber}" searchName="${searchName}" orderType="${orderType}" orderBy="${orderBy}"></links:link>
	</li>
</c:if>

<!-- Display the next button if the current page isn't the last one -->

<c:choose>
	<c:when test="${pageNumber < maxPageNumber}">
		<li>
			<links:link pageNumber="${pageNumber+1}" pageSize="${pageSize}" target="computers" text="${NextButton}" searchName="${searchName}" orderType="${orderType}" orderBy="${orderBy}"></links:link>
		</li>	
	</c:when>
	<c:otherwise>
		<li class="disabled">
			<links:link pageNumber="${pageNumber}" pageSize="${pageSize}" target="#" text="${NextButton}"></links:link>
		</li>	
	</c:otherwise>
</c:choose>
