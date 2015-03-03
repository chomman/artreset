<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/social-buttons-3.css"/>
</head>
<body>

        <!-- Page Title -->
		<div class="section section-breadcrumbs">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1><spring:message code="label.login.form.title"/></h1>
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
							<form action="/login/authenticate" method="POST" role="form">
                				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="form-group form-group-email">
		        				 	<label for="login-username"><i class="icon-user"></i> <b><spring:message code="label.user.email"/></b></label>
									<input name="username" class="form-control" id="login-username" type="text" placeholder="">
								</div>
								<div class="form-group form-group-password">
		        				 	<label for="login-password"><i class="icon-lock"></i> <b><spring:message code="label.user.password"/></b></label>
									<input name="password" class="form-control" id="login-password" type="password" placeholder="">
								</div>
								<div class="form-group">
									<label class="checkbox">
										<input type="checkbox" name="remember-me"> <spring:message code="text.user.login.remember.me.help" />
									</label>
									<a href="page-password-reset.html" class="forgot-password"><spring:message code="text.forgot.password.link" /></a>
									<button type="submit" class="btn pull-right"><spring:message code="label.user.login.submit.button"/></button>
									<div class="clearfix"></div>
								</div>
							</form>
						</div>
					</div>
					<div class="col-sm-7 social-login">
						<p><spring:message code="label.social.sign.in.title"/></p>
						<div class="social-login-buttons">
							<a href="<c:url value="/auth/facebook"/>" class="btn-facebook-login"><spring:message code="label.facebook.sign.in.button"/></a>
							<a href="<c:url value="/auth/twitter"/>" class="btn-twitter-login"><spring:message code="label.twitter.sign.in.button"/></a>
						</div>
						<div class="clearfix"></div>
						<div class="not-member">
							<p><spring:message code="text.not.a.member" /> <a href="<c:url value="/user/register"/>"><spring:message code="label.navigation.registration.link"/></a></p>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
				    <p><spring:message code="text.login.page.authenticated.user.help"/></p>
				</sec:authorize>
				</div>
			</div>
		</div>

</body>
</html>