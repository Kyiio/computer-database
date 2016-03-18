<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<pagination:link pageNumber="1" pageSize="${pageSize}"
			target="computers" text="Application - Computer Database"
			cssClass="navbar-brand"></pagination:link>
		<div>
			<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
				<a
					href="${pageContext.request.contextPath}/logout?lang=${pageContext.response.locale}">
					<img class="flags" src="${pageContext.request.contextPath}/fonts/logout.png" alt="logout">
				</a>
			</sec:authorize>
			<a
				href="?lang=en&${attr}">
				<img class="flags" src="${pageContext.request.contextPath}/fonts/england.png" alt="uk flag">
			</a> 
			<a
				href="?lang=fr&${attr}">
				<img class="flags" src="${pageContext.request.contextPath}/fonts/french.png" alt="fr flag">
			</a>
		</div>
	</div>
</header>