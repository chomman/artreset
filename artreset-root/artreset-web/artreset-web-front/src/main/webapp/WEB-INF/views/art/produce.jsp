<!DOCTYPE html>
<%@page import="com.artreset.common.model.UnitLength"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="category" uri="/WEB-INF/tld/category.tld" %>
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
						<h1><spring:message code="label.art.produce.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
		
        <div class="section">
	    	<div class="container">
				<div class="row">
					<div class="col-sm-12">
						<div class="basic-login">
							<c:url value="/art/produce" var="produceeUrl" />
							<form:form commandName="product" action="${produceeUrl }" method="POST" cssClass="forms contact-form">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<form:hidden path="id" />
								<form:hidden path="art" />
								<div class="form-group control-group">
		        				 	<label for="art-productStatus"><i class="icon-lock"></i> <b><spring:message code="label.art.produce.page.art.status" /></b></label>
		        				 	<form:select path="productStatus" items="${product.productStatusList }" itemLabel="description" multiple="false"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="art-price"><i class="icon-user"></i> <b><spring:message code="label.art.produce.page.art.price" /></b></label>
									<form:input id="art-price" path="price" cssClass="form-control"/>
									<form:errors id="error-price" path="price" cssClass="help-inline"/>
								</div>
								<div class="form-group">
									<div class="pull-right">
										<a href="<c:url value="/art/" />" class="btn"><spring:message code="label.cancel"/></a>
										<button id="produce-art-button" type="submit" class="btn btn-primary">
											<spring:message code="label.produce.art.button"/>
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