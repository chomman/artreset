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
						<h1><spring:message code="label.todo.view.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	        <div class="well container">
	        	<h2><c:out value="${todo.title}"/></h2>
	        	<div class="row">
	        		<div class="col-md-12">
        				<p><c:out value="${todo.description}"/></p>
	        		</div>
	        	</div>
	        	
	        	<div class="action-buttons">
		            <a href="<c:url value="/todo/update/${todo.id }" />" class="btn btn-primary"><spring:message code="label.update.todo.link"/></a>
		            <button class="btn btn-primary btn-md" data-toggle="modal" data-target="#deleteDialog"><spring:message code="label.delete.todo.link"/></button>
		        </div>
	        </div>
	    </div>

		<!-- Modal -->
		<div class="modal fade" id="deleteDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">
							<spring:message code="label.todo.delete.dialog.title" />
						</h4>
					</div>
					<div class="modal-body">
						<spring:message code="label.todo.delete.dialog.message" />
					</div>
					<div class="modal-footer">
						<form action="<c:url value="/todo/delete/${todo.id }"/>" method="get">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<spring:message code="label.cancel" />
							</button>
							<button type="submit" class="btn btn-primary">
								<spring:message code="label.delete.todo.button" />
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>

</body>
</html>