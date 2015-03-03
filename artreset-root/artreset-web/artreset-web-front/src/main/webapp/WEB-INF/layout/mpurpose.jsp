<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><sitemesh:write property="title"/></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <link rel="stylesheet" href="<c:url value="/assets/theme/mpurpose/css/bootstrap.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/assets/theme/mpurpose/css/icomoon-social.css"/>">
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800' rel='stylesheet' type='text/css'>

        <link rel="stylesheet" href="<c:url value="/assets/theme/mpurpose/css/leaflet.css"/>" />
		<!--[if lte IE 8]>
		    <link rel="stylesheet" href="<c:url value="/assets/theme/mpurpose/css/leaflet.ie.css"/>" />
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/assets/theme/mpurpose/css/main.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">

        <script src="<c:url value="/assets/theme/mpurpose/js/modernizr-2.6.2-respond-1.1.0.min.js"/>"></script>

        <!-- Javascripts -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="<c:url value="/assets/theme/mpurpose/js/jquery-1.9.1.min.js"/>"><\/script>');</script>
        <script src="<c:url value="/assets/theme/mpurpose/js/bootstrap.min.js"/>"></script>
        <script src="http://cdn.leafletjs.com/leaflet-0.5.1/leaflet.js"></script>
        <script src="<c:url value="/assets/theme/mpurpose/js/jquery.fitvids.js"/>"></script>
        <script src="<c:url value="/assets/theme/mpurpose/js/jquery.sequence-min.js"/>"></script>
        <script src="<c:url value="/assets/theme/mpurpose/js/jquery.bxslider.js"/>"></script>
        <script src="<c:url value="/assets/theme/mpurpose/js/main-menu.js"/>"></script>
        <script src="<c:url value="/assets/theme/mpurpose/js/template.js"/>"></script>
        <script src="<c:url value="/static/js/vendor/handlebars-1.0.rc.1.js"/>"></script>
        <script src="<c:url value="/static/js/message-handler.js"/>"></script>
        
        <sitemesh:write property="head"/>
        
    </head>
	<body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
        

        <!-- Navigation & Logo-->
        <div class="mainmenu-wrapper">
	        <div class="container">
	        	<div class="menuextras">
					<div class="extras">
						<ul>
							<li class="shopping-cart-items"><i class="glyphicon glyphicon-shopping-cart icon-white"></i> <a href="page-shopping-cart.html"><b>3 items</b></a></li>
							<li>
								<div class="dropdown choose-country">
									<a class="#" data-toggle="dropdown" href="#"><img src="<c:url value="/assets/theme/mpurpose/img/flags/gb.png"/>" alt="Great Britain"> UK</a>
									<ul class="dropdown-menu" role="menu">
										<li role="menuitem"><a href="#"><img src="<c:url value="/assets/theme/mpurpose/img/flags/us.png"/>" alt="United States"> US</a></li>
										<li role="menuitem"><a href="#"><img src="<c:url value="/assets/theme/mpurpose/img/flags/de.png"/>" alt="Germany"> DE</a></li>
										<li role="menuitem"><a href="#"><img src="<c:url value="/assets/theme/mpurpose/img/flags/es.png"/>" alt="Spain"> ES</a></li>
									</ul>
								</div>
							</li>
			        		<sec:authorize access="isAnonymous()">
			        			<li><a href="<c:url value="/login"/>"><spring:message code="label.navigation.login.link" /></a></li>
			        		</sec:authorize>
			        		<sec:authorize access="isAuthenticated()">
			        			<li><a href="<c:url value="/logout"/>"><spring:message code="label.navigation.logout.link" /></a></li>
						    </sec:authorize>
			        	</ul>
					</div>
		        </div>
		        <nav id="mainmenu" class="mainmenu">
					<ul>
						<li class="logo-wrapper">
							<a href="<c:url value="/"/>">
								<%-- <img src="<c:url value="/assets/theme/mpurpose/img/mPurpose-logo.png"/>" alt="Multipurpose Twitter Bootstrap Template"> --%>
								Artreset
							</a>
						</li>
						<li class="active">
							<a href="<c:url value="/"/>">Home</a>
						</li>
						<li>
							<a href="<c:url value="/todo"/>">TODO</a>
						</li>
						<li>
							<a href="<c:url value="/image"/>">Image 테스트</a>
						</li>
						<li>
							<a href="<c:url value="/artist"/>">Artist</a>
						</li>
						<li>
							<a href="<c:url value="/art"/>">Art</a>
						</li>
					</ul>
				</nav>
			</div>
		</div>
		
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


        
	    <!-- Footer -->
	    <div class="footer">
	    	<div class="container">
		    	<div class="row">
		    	<!-- 
		    		<div class="col-footer col-md-3 col-xs-6">
		    			<h3>Our Latest Work</h3>
		    			<div class="portfolio-item">
							<div class="portfolio-image">
								<%-- <a href="page-portfolio-item.html"><img src="<c:url value="/assets/theme/mpurpose/img/portfolio6.jpg"/>" alt="Project Name"></a> --%>
							</div>
						</div>
		    		</div>
		    		<div class="col-footer col-md-3 col-xs-6">
		    			<h3>Navigate</h3>
		    			<ul class="no-list-style footer-navigate-section">
		    				<li><a href="page-blog-posts.html">Blog</a></li>
		    				<li><a href="page-portfolio-3-columns-2.html">Portfolio</a></li>
		    				<li><a href="page-products-3-columns.html">eShop</a></li>
		    				<li><a href="page-services-3-columns.html">Services</a></li>
		    				<li><a href="page-pricing.html">Pricing</a></li>
		    				<li><a href="page-faq.html">FAQ</a></li>
		    			</ul>
		    		</div>
		    		
		    		<div class="col-footer col-md-4 col-xs-6">
		    			<h3>Contacts</h3>
		    			<p class="contact-us-details">
	        				<b>Address:</b> 123 Fake Street, LN1 2ST, London, United Kingdom<br/>
	        				<b>Phone:</b> +44 123 654321<br/>
	        				<b>Fax:</b> +44 123 654321<br/>
	        				<b>Email:</b> <a href="mailto:getintoutch@yourcompanydomain.com">getintoutch@yourcompanydomain.com</a>
	        			</p>
		    		</div>
		    		<div class="col-footer col-md-2 col-xs-6">
		    			<h3>Stay Connected</h3>
		    			<ul class="footer-stay-connected no-list-style">
		    				<li><a href="#" class="facebook"></a></li>
		    				<li><a href="#" class="twitter"></a></li>
		    				<li><a href="#" class="googleplus"></a></li>
		    			</ul>
		    		</div>
		    	</div>
		    	 -->
		    	<div class="row">
		    		<div class="col-md-12">
		    			<div class="footer-copyright">&copy; 2013 artreset. All rights reserved.</div>
		    		</div>
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