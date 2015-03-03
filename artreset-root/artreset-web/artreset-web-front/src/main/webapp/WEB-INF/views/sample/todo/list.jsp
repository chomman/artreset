<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="category" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Artreset Sample Application - TODO</title>
</head>
<body>

		<!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1><spring:message code="label.todo.list.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
		
		<category:categorySelect />
		
		
		<div class="section">
	    	<div class="container">
	    		<div class="row">
	    			<!-- Open Vacancies List -->
	    			<div class="col-md-8">
	    				<c:choose>
	    				<c:when test="${empty todos}">
				        	<p><spring:message code="label.todo.list.empty"/></p>
				        </c:when>
				        <c:otherwise>
								<table class="table table-bordered table-striped">
									<!--<colgroup>
										<col class="span1">
										<col class="span5">
									</colgroup>-->
									
									<thead>
										<tr>
											<th>진행</th>
											<th>할일</th>
										</tr>
									</thead>
									
									<tbody>
					                <c:forEach items="${todos}" var="todo">
					                        
										<tr>
											<td><i class="icon-hourglass"></i></td>
											<td><a href="/todo/${todo.id}"><c:out value="${todo.title}"/></a></td>
										</tr>
					                </c:forEach>
									</tbody>
									
								</table>
				        </c:otherwise>
				        </c:choose>
	    			</div>
	    			<!-- End Open Vacancies List -->
	    			<!-- Sidebar -->
	    			<div class="col-md-4 col-sm-6">
	    				<div class="join-us-promo">
	    					<!-- Quote -->
							<div class="join-us-bubble">
								<blockquote>
									<p class="quote">
			                            "당신의 할일을 관리하세요."
	                        		</p>
	                        	</blockquote>
	                        	<div>
							        <a href="<c:url value="/todo/add" />" class="btn btn-primary"><spring:message code="label.add.todo.link"/></a>
							    </div>
	                        	<div class="sprite arrow-speech-bubble"></div>
	                        </div>
	                        <!-- Team Member Photo -->
	                        <div class="author-photo">
								<img src="<c:url value="/assets/theme/mpurpose/img/user2.jpg"/>" alt="Name Surname"/>
							</div>
	    				</div>
	    			</div>
	    			<!-- End Sidebar -->
	    		</div>
			</div>
		</div>
			
</body>
</html>



