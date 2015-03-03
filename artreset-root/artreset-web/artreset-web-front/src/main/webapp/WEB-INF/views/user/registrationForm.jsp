<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="label.user.registration.page.title" /></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/user.form.js"></script>
</head>
<body>
        <!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1><spring:message code="label.user.registration.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	    	<div class="container">
				<div class="row">
				<sec:authorize access="isAnonymous()">
					<div class="col-sm-5">
						<div class="basic-login">
							<c:url value="/user/register" var="registerUrl" />
							<form:form action="${registerUrl }" commandName="user" method="POST" enctype="utf8" role="form">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<c:if test="${user.signInProvider != null}">
			                        <form:hidden path="signInProvider"/>
			                    </c:if>
		                        <div id="form-group-firstName" class="form-group">
		                            <label class="control-label" for="user-firstName"><b><spring:message code="label.user.firstName"/></b></label>
		                            <form:input id="user-firstName" path="firstName" cssClass="form-control"/>
		                            <form:errors id="error-firstName" path="firstName" cssClass="help-block"/>
		                        </div>
		                        <div id="form-group-lastName" class="form-group">
		                            <label class="control-label" for="user-lastName"><b><spring:message code="label.user.lastName"/></b></label>
		                            <form:input id="user-lastName" path="lastName" cssClass="form-control"/>
		                            <form:errors id="error-lastName" path="lastName" cssClass="help-block"/>
		                        </div>
		                        <div id="form-group-email" class="form-group">
		                            <label class="control-label" for="user-email"><i class="icon-user"></i> <b><spring:message code="label.user.email"/></b></label>
		                            <form:input id="user-email" path="email" cssClass="form-control"/>
		                            <form:errors id="error-email" path="email" cssClass="help-block"/>
		                        </div>
                    		<c:if test="${user.signInProvider == null}">
	                            <div id="form-group-password" class="form-group">
	                                <label class="control-label" for="user-password"><i class="icon-lock"></i> <b><spring:message code="label.user.password"/></b></label>
	                                <form:password id="user-password" path="password" cssClass="form-control"/>
	                                <form:errors id="error-password" path="password" cssClass="help-block"/>
	                            </div>
	                            <div id="form-group-passwordVerification" class="form-group">
	                                <label class="control-label" for="user-passwordVerification"><i class="icon-lock"></i> <b><spring:message code="label.user.passwordVerification"/></b></label>
	                                <form:password id="user-passwordVerification" path="passwordVerification" cssClass="form-control"/>
	                                <form:errors id="error-passwordVerification" path="passwordVerification" cssClass="help-block"/>
	                            </div>
							</c:if>
								<div class="form-group">
				                    <button type="submit" class="btn btn-default pull-right"><spring:message code="label.user.registration.submit.button"/></button>
									<div class="clearfix"></div>
								</div>
							</form:form>
						</div>
					</div>
					<div class="col-sm-6 col-sm-offset-1 social-login">
						<p><spring:message code="text.register.page.social.help" /></p>
						<div class="social-login-buttons">
							<a href="#" class="btn-facebook-login"><spring:message code="label.facebook.register.button" /></a>
							<a href="#" class="btn-twitter-login"><spring:message code="label.twitter.register.button" /></a>
						</div>
					</div>
					</sec:authorize>
				    <sec:authorize access="isAuthenticated()">
				        <p><spring:message code="text.registration.page.authenticated.user.help"/></p>
				    </sec:authorize>
				</div>
			</div>
		</div>
</body>
</html>