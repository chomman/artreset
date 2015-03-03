<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Artrest | ${artist.nickName }</title>
    <script type="text/javascript" src="/static/js/todo.view.js"></script>
        <style type="text/css">
		#sequence > .sequence-canvas > li.bg1 {
			background-image: url(<c:url value="${artist.pageCover.url }"/>);
			background-size: cover;
			background-repeat: no-repeat;
		}
    </style>
</head>
<body>
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal.username" var="username" />
		</sec:authorize>
		
		<!-- Homepage Slider -->
        <div class="homepage-slider">
        	<div id="sequence">
				<ul class="sequence-canvas">
					<!-- Slide 1 -->
					<li class="bg1">
						<!-- Slide Title -->
						<h2 class="title">${artist.nickName }</h2>
						<!-- Slide Text -->
						<h3 class="subtitle">${artist.shortDescription }</h3>
						<!-- Slide Image -->
						<img class="slide-img" src="<c:url value="${artist.avatar.thumbnailUrl }"/>" alt="" />
					</li>
					<!-- End Slide 1 -->
				</ul>
			</div>
        </div>
        <!-- End Homepage Slider -->
		<!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1><spring:message code="label.artist.view.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	        <div class="container">
	        	<div>${artist.description }</div>
	        	<c:if test="${artist.artistUserInfo.email eq username }">
	        	<div class="action-buttons">
		            <a href="<c:url value="/artist/update/${artist.id }" />" class="btn btn-primary"><spring:message code="label.update.artist.link"/></a>
		            <button class="btn btn-primary btn-md" data-toggle="modal" data-target="#deleteDialog"><spring:message code="label.delete.artist.link"/></button>
		        </div>
		        </c:if>
	        </div>
	    </div>
	    
	    
		
        <div class="section">
	    	<div class="container">
				<!-- Related Projects -->
				<h3>작품들</h3>
				<div class="row">
					<c:forEach items="${arts }" var="art">
					<div class="col-md-4 col-sm-6">
						<div class="portfolio-item">
							<div class="portfolio-image">
								<a href="<c:url value="/art/${art.id }"/>">
									<img src="<c:url value="${art.assentialImage.thumbnailUrl }"/>" alt="${art.title }">
								</a>
							</div>
							<div class="portfolio-info-fade">
								<ul>
									<li class="portfolio-project-name">${art.title }</li>
									<li>${art.description }</li>
									<li>작품유형: ${art.category.name }</li>
									<li class="read-more"><a href="<c:url value="/art/${art.id }"/>" class="btn">상세보기</a></li>
								</ul>
							</div>
						</div>
					</div>
					</c:forEach>
				</div>
				<!-- End Related Projects -->
			</div>
		</div>
		
		
		<c:if test="${art.artist.artistUserInfo.email eq username }">
		<!-- Modal -->
		<div class="modal fade" id="deleteDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">
							<spring:message code="label.artist.delete.dialog.title" />
						</h4>
					</div>
					<div class="modal-body">
						<spring:message code="label.artist.delete.dialog.message" />
					</div>
					<div class="modal-footer">
						<form action="<c:url value="/artist/delete/${artist.id }"/>" method="get">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<spring:message code="label.cancel" />
							</button>
							<button type="submit" class="btn btn-primary">
								<spring:message code="label.delete.artist.button" />
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		</c:if>

</body>
</html>