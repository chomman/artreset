<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="label.art.list.page.title"/></title>
    <script type="text/javascript" src="/static/js/todo.view.js"></script>
</head>
<body>

        <!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1><spring:message code="label.art.list.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	    	<div class="container">
				<div class="row">
					<c:forEach items="${arts }" var="art">
					<div class="col-md-4 col-sm-6">
						<div class="portfolio-item">
							<div class="portfolio-image">
								<a href="<c:url value="/art/${art.id }"/>"><img src="<c:url value="${art.assentialImage.thumbnailUrl }"/>" alt="${art.title }"></a>
							</div>
							<div class="portfolio-info">
								<ul>
									<li class="portfolio-project-name">${art.title }</li>
									<li>${art.artist.nickName }</li>
									<li>${art.category.name }</li>
									<li class="read-more"><a href="<c:url value="/art/${art.id }"/>" class="btn">Read more</a></li>
								</ul>
							</div>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>


    </body>
</html>