<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/static/js/todo.view.js"></script>
</head>
<body>
		<!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1><spring:message code="label.artist.exist.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	        <div class="well container">
	        	<h2>안내</h2>
	        	<div class="row">
	        		<div class="col-md-12">
        				이미 작가로 등록하셨습니다.
        				<br>
        				<a href="<c:url value="/artist/${artist.id }"/>">작가 페이지</a>
	        		</div>
	        	</div>
	        	
	        	<div class="action-buttons">
		            <a href="<c:url value="/artist/update/${artist.id }" />" class="btn btn-primary"><spring:message code="label.update.artist.link"/></a>
		        </div>
	        </div>
	    </div>

</body>
</html>