<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="category" uri="/WEB-INF/tld/category.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="<c:url value="/static/js/error-handler.js"/> "></script>
</head>
<body>
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal.username" var="username" />
		</sec:authorize>
    
		<!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1><spring:message code="label.art.view.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
		
		
        <div class="section">
	    	<div class="container">
				<div class="row">
					<!-- Image Column -->
					<div class="col-sm-6">
						<div class="portfolio-item">
							<div class="portfolio-image">
								<a href="#"><img src="<c:url value="${art.assentialImage.url }"/>" alt="${art.title }"></a>
							</div>
						</div>
						
						<c:forEach items="${art.detailImages }" var="detail">
						<div class="col-sm-3">
							<div class="portfolio-item">
								<div class="portfolio-image">
									<a href="#"><img src="<c:url value="${detail.thumbnailUrl }"/>" alt="${art.title }"></a>
								</div>
							</div>
						</div>
						</c:forEach>
					</div>
					<!-- End Image Column -->
					
					<!-- Project Info Column -->
					<div class="portfolio-item-description col-sm-6">
						<h3>${art.title }</h3>
						<p>
							${art.description }
						</p>
						<ul class="no-list-style">
							<li><b>작품유형:</b> ${art.category.name }</li>
							<li><b>날짜:</b> <joda:format value="${art.creationTime}" pattern="yyyy-MM-dd"/></li>
							<li><b>크기:</b> 
								<c:if test="${art.width > 0 }">(가로)${art.width }${art.unitLength.unit }</c:if> 
								<c:if test="${art.length > 0 }">(세로)${art.length }${art.unitLength.unit }</c:if> 
								<c:if test="${art.height > 0 }">(높이)${art.height }${art.unitLength.unit }</c:if>
							</li>
							<c:if test="${not empty product }">
							<li><b>가격:</b> ${product.price }</li>
							</c:if>
							<li class="portfolio-visit-btn"><a href="<c:url value="/artist/${art.artist.id }"/>" class="btn">작가 정보</a></li>
							<c:choose>
							<c:when test="${art.artist.artistUserInfo.email eq username }">
							<li class="portfolio-visit-btn"><a href="<c:url value="/art/update/${art.id }"/>" class="btn">수정하기</a></li>
							<li class="portfolio-visit-btn"><a href="<c:url value="/art/produce/${art.id }"/>" class="btn">상품등록</a></li>
							<li class="portfolio-visit-btn"><button class="btn btn-primary btn-md" data-toggle="modal" data-target="#deleteDialog"><spring:message code="label.delete.art.link"/></button></li>
							</c:when>
							<c:otherwise>
							<li class="portfolio-visit-btn"><a href="<c:url value="/art/buy/${art.id }"/>" class="btn">바로구매</a></li>
							<li class="portfolio-visit-btn"><a href="<c:url value="/art/cart/${art.id }"/>" class="btn">카트에 담기</a></li>
							</c:otherwise>
							</c:choose>
							<c:if test="${art.artist.artistUserInfo.email eq username }">
							</c:if>
							
							<br/>
							
							<div id="fb-root">
								<script>
									(function(d, s, id) {
										var js, fjs = d.getElementsByTagName(s)[0];
										if (d.getElementById(id))
											return;
										js = d.createElement(s);
										js.id = id;
										js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=your_API_id";
										fjs.parentNode.insertBefore(js, fjs);
								
									}(document, 'script', 'facebook-jssdk'));
								</script>
		
								<div class="fb-like"
									data-href="https://developers.facebook.com/docs/plugins/"
									data-width="16" data-height="16" data-colorscheme="light"
									data-layout="standard" data-action="like" data-show-faces="true"
									data-send="false"></div>
							</div><!-- //fb-root -->

					</ul>
					</div>
					<!-- End Project Info Column -->
				</div>
				
				
				
				<c:if test="${not empty arts[0] }">
				<!-- Related Arts -->
				<h3>작가의 다른 작품</h3>
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
				<!-- End Related Arts -->
				</c:if>
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
							<spring:message code="label.art.delete.dialog.title" />
						</h4>
					</div>
					<div class="modal-body">
						<spring:message code="label.art.delete.dialog.message" />
					</div>
					<div class="modal-footer">
						<form action="<c:url value="/art/delete/${art.id }"/>" method="get">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<spring:message code="label.cancel" />
							</button>
							<button type="submit" class="btn btn-primary">
								<spring:message code="label.delete.art.button" />
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		</c:if>

</body>
</html>