<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">
    <title><spring:message code="spring.social.mvc.normal.title"/></title>
    <!-- Bootstrap core CSS -->
    <link href="../../dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="../../dist/css/bootstrap-theme.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="theme.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/vendor/jquery-2.0.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/vendor/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/vendor/handlebars-1.0.rc.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/message-handler.js"></script>
    <sitemesh:write property="head"/>
</head>
<body>
<div class="page">
    <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only"><spring:message code="label.navigation.toggle.navigation.button"/></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand">Artreset</span>
        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav navbar-left">
                <sec:authorize access="isAuthenticated()">
                    <li><a href="/"><spring:message code="label.navigation.home.link"/></a></li>
                </sec:authorize>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAnonymous()">
                    <li><a href="/user/register"><spring:message code="label.navigation.registration.link"/></a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <form action="/logout" method="POST">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-default navbar-btn">
                                <spring:message code="label.navigation.logout.link"/>
                            </button>
                        </form>
                    </li>
                </sec:authorize>
            </ul>
            <sec:authorize access="isAuthenticated()">
                <p class="nav navbar-nav navbar-right navbar-text sign-in-text">
                    <sec:authentication property="principal.socialSignInProvider" var="signInProvider"/>
                    <c:if test="${signInProvider == 'FACEBOOK'}">
                        <i class="icon-facebook-sign"></i>
                    </c:if>
                    <c:if test="${signInProvider == 'TWITTER'}">
                        <i class="icon-twitter-sign"></i>
                    </c:if>
                    <c:if test="${empty signInProvider}">
                        <spring:message code="label.navigation.signed.in.as.text"/>
                    </c:if>
                    <sec:authentication property="principal.username"/>
                </p>
            </sec:authorize>
        </div><!-- /.navbar-collapse -->
    </div>
    <div class="content">
    	<div id="message-holder">
            <c:if test="${feedbackMessage != null}">
                <div class="messageblock hidden">${feedbackMessage}</div>
            </c:if>
            <c:if test="${errorMessage != null}">
                <div class="errorblock hidden">${errorMessage}</div>
            </c:if>
        </div>
        <div id="view-holder">
            <sitemesh:write property="body"/>
        </div>
    </div>
</div>


   <script id="template-alert-message-error" type="text/x-handlebars-template">
        <div id="alert-message-error" class="alert alert-error fade in">
            <a class="close" data-dismiss="alert">&times;</a>
            {{message}}
        </div>
    </script>

   <script id="template-alert-message" type="text/x-handlebars-template">
        <div id="alert-message" class="alert alert-success fade in">
            <a class="close" data-dismiss="alert">&times;</a>
            {{message}}
        </div>
    </script>
</body>
</html>