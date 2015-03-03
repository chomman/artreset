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
						<h1><spring:message code="label.artist.add.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	    	<div class="container">
				<div class="row">
					<div class="col-sm-12">
						<div class="basic-login">
							<form:form commandName="artist" method="POST" cssClass="forms contact-form" enctype="multipart/form-data">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="form-group control-group">
		        				 	<label for="artist-nickName"><i class="icon-user"></i> <b>닉네임</b></label>
									<form:input id="artist-nickName" path="nickName" title="넥네임" cssClass="form-control"/>
									<form:errors id="error-nickName" path="nickName" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="artist-avatar"><i class="icon-lock"></i> <b>아바타 이미지</b></label>
									<form:input id="artist-avatar" path="avatar" type="file" />
									<form:errors id="error-avatar" path="avatar" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="artist-pageCover"><i class="icon-lock"></i> <b>페이지 커버 이미지</b></label>
									<form:input id="artist-pageCover" path="pageCover" type="file" />
									<form:errors id="error-pageCover" path="pageCover" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="artist-shortDescription"><i class="icon-lock"></i> <b>짧은 설명</b></label>
									<form:input id="artist-description" path="shortDescription" type="text" />
									<form:errors id="error-shortDescription" path="shortDescription" cssClass="help-inline"/>
								</div>
								<div class="form-group control-group">
		        				 	<label for="artist-description"><i class="icon-lock"></i> <b>작가 설명</b></label>
									<form:textarea id="artist-description" path="description" type="text" />
									<form:errors id="error-description" path="description" cssClass="help-inline"/>
								</div>
								<div class="form-group">
									<div class="pull-right">
										<a href="<c:url value="/artist/" />" class="btn"><spring:message code="label.cancel"/></a>
										<button id="add-artist-button" type="submit" class="btn btn-primary">
											<spring:message code="label.add.artist.button"/>
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