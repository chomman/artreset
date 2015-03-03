<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="<c:url value="/static/js/error-handler.js"/> "></script>
</head>
<body>
    
		<!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1>할일 추가</h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	    	<div class="container">
				<div class="row">
					<div class="col-sm-12">
						<div class="basic-login">
							<form:form commandName="todo" method="POST" enctype="utf8" cssClass="forms contact-form">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="form-group control-group">
		        				 	<label for="login-username"><i class="icon-user"></i> <b>할일(필수)</b></label>
									<form:input id="todo-title" path="title" title="할일 (Required)" cssClass="form-control"/>
									<form:errors id="error-title" path="title" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="login-password"><i class="icon-lock"></i> <b>설명</b></label>
									<form:textarea id="todo-description" path="description" title="설명" cssClass="form-control"/>
									<form:errors id="error-description" path="description" cssClass="help-inline"/>
								</div>
								<div class="form-group">
									<div class="pull-right">
										<a href="<c:url value="/todo/" />" class="btn"><spring:message code="label.cancel"/></a>
										<button id="add-todo-button" type="submit" class="btn btn-primary">
											<spring:message code="label.add.todo.button"/>
										</button>
									</div>
									<div class="clearfix"></div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>