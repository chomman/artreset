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
						<h1><spring:message code="label.art.add.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
		
        <div class="section">
	    	<div class="container">
				<div class="row">
					<div class="col-sm-12">
						<div class="basic-login">
							<c:url value="/art/update" var="updateUrl" />
							<form:form commandName="art" action="${updateUrl }" method="POST" cssClass="forms contact-form" enctype="multipart/form-data">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="form-group control-group">
		        				 	<label for="art-title"><i class="icon-user"></i> <b><spring:message code="label.art.add.page.art.title" /></b></label>
									<form:input id="art-title" path="title" title="넥네임" cssClass="form-control"/>
									<form:errors id="error-title" path="title" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="art-category"><i class="icon-lock"></i> <b><spring:message code="label.art.add.page.category" /></b></label>
		        				 	<%-- <category:select name="categoryId" /> --%>
		        				 	<form:select path="categoryId" >
		        				 		<form:options items="${art.categories }" itemValue="id" itemLabel="name" />
		        				 	</form:select>
								</div>
								<div class="form-group control-group">
		        				 	<label for="art-description"><i class="icon-lock"></i> <b><spring:message code="label.art.add.page.description" /></b></label>
									<form:textarea id="art-description" path="description" type="text" />
									<form:errors id="error-description" path="description" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="art-width"><i class="icon-lock"></i> <b>크기</b></label>
		        				 	<form:input path="width" title="가로"/>
		        				 	<form:input path="length" title="세로"/>
		        				 	<form:input path="height" title="높이"/>
		        				 	<form:select path="unitLength" items="${art.unitLengths }" itemLabel="unit" multiple="false"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="art-assential"><i class="icon-lock"></i> <b><spring:message code="label.art.add.page.assential" /></b></label>
		        				 	<c:if test="${not empty art.assentialImage }"><img src="<c:url value="${art.assentialImage.thumbnailUrl }"/>" /></c:if>
									<form:input id="art-assential" path="assential" type="file" />
									<form:errors id="error-assential" path="assential" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="art-details"><i class="icon-lock"></i> <b><spring:message code="label.art.add.page.details" /></b></label>
									<form:input id="art-details" path="details" type="file" />
									<form:errors id="error-details" path="details" cssClass="help-inline"/>
								</div>
								<div class="form-group">
									<div class="pull-right">
										<a href="<c:url value="/art/" />" class="btn"><spring:message code="label.cancel"/></a>
										<button id="add-art-button" type="submit" class="btn btn-primary">
											<spring:message code="label.add.art.button"/>
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