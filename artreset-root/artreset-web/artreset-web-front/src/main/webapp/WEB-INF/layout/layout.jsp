<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
	<head>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<meta name="description" content="">
		<meta name="author" content="">
		
		<title><sitemesh:write property="title"/></title>
		
		<!-- Bootstrap Core CSS -->
		<link href="<c:url value="/assets/css/bootstrap.min.css"/>" rel="stylesheet">
		
		<!-- Customizable CSS -->
		<link href="<c:url value="/assets/css/main.css"/>" rel="stylesheet" data-skrollr-stylesheet>
		<link href="<c:url value="/assets/css/green.css"/>" rel="stylesheet" title="Color">
		<link href="<c:url value="/assets/css/owl.carousel.css"/>" rel="stylesheet">
		<link href="<c:url value="/assets/css/owl.transitions.css"/>" rel="stylesheet">
		<link href="<c:url value="/assets/css/animate.min.css"/>" rel="stylesheet">
		
		<!-- Fonts -->
		<link href="http://fonts.googleapis.com/css?family=Lato:400,900,300,700" rel="stylesheet">
		<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700,400italic,700italic" rel="stylesheet">
		
		<!-- Icons/Glyphs -->
		<link href="<c:url value="/assets/fonts/fontello.css"/>" rel="stylesheet">
		
		<!-- Favicon -->
		<link rel="shortcut icon" href="<c:url value="/assets/images/favicon.ico"/>">
		
		<!-- HTML5 elements and media queries Support for IE8 : HTML5 shim and Respond.js -->
		<!--[if lt IE 9]>
			<script src="<c:url value="/assets/js/html5shiv.js"/>"></script>
			<script src="<c:url value="/assets/js/respond.min.js"/>"></script>
		<![endif]-->
		
		<!-- JavaScripts placed at the end of the document so the pages load faster -->
		<script src="<c:url value="/assets/js/jquery-1.11.1.min.js"/>"></script>
		<script src="<c:url value="/assets/js/jquery.easing.1.3.min.js"/>"></script>
		<script src="<c:url value="/assets/js/bootstrap.min.js"/>"></script>
		<script src="<c:url value="/assets/js/bootstrap-hover-dropdown.min.js"/>"></script>
		<script src="<c:url value="/assets/js/skrollr.min.js"/>"></script>
		<script src="<c:url value="/assets/js/skrollr.stylesheets.min.js"/>"></script>
		<script src="<c:url value="/assets/js/waypoints.min.js"/>"></script>
		<script src="<c:url value="/assets/js/waypoints-sticky.min.js"/>"></script>
		<script src="<c:url value="/assets/js/owl.carousel.min.js"/>"></script>
		<script src="<c:url value="/assets/js/jquery.isotope.min.js"/>"></script>
		<script src="<c:url value="/assets/js/jquery.easytabs.min.js"/>"></script>
		<script src="<c:url value="/assets/js/jquery.slickforms.js"/>"></script>
		<script src="<c:url value="/assets/js/google.maps.api.v3.js"/>"></script>
		<script src="<c:url value="/assets/js/viewport-units-buggyfill.js"/>"></script>
		<script src="<c:url value="/assets/js/scripts.js"/>"></script>
		
		<!-- For demo purposes – can be removed on production -->
		<link href="<c:url value="/assets/css/green.css"/>" rel="alternate stylesheet" title="Green color">
		<link href="<c:url value="/assets/css/blue.css"/>" rel="alternate stylesheet" title="Blue color">
		<link href="<c:url value="/assets/css/red.css"/>" rel="alternate stylesheet" title="Red color">
		<link href="<c:url value="/assets/css/pink.css"/>" rel="alternate stylesheet" title="Pink color">
		<link href="<c:url value="/assets/css/purple.css"/>" rel="alternate stylesheet" title="Purple color">
		<link href="<c:url value="/assets/css/orange.css"/>" rel="alternate stylesheet" title="Orange color">
		<link href="<c:url value="/assets/css/navy.css"/>" rel="alternate stylesheet" title="Navy color">
		<link href="<c:url value="/assets/css/gray.css"/>" rel="alternate stylesheet" title="Gray color">
		
		<script src="<c:url value="/switchstylesheet/switchstylesheet.js"/>"></script>
		
		<script>
			$(document).ready(function(){ 
				$(".changecolor").switchstylesheet( { seperator:"color"} );
			});
		</script>
		
		
		<sitemesh:write property="head"/>
	</head>
	
	<body>
		
		<!-- ================================= HEADER ================================= -->
		
		<header>
			<div class="navbar">
				
				<div class="navbar-header">
					<div class="container">
						
						<ul class="info pull-left">
							<li><a href="#"><i class="icon-mail-1 contact"></i> info@artreset.com</a></li>
							<li><i class="icon-mobile contact"></i> +82 (2) 456 78 90</li>
						</ul><!-- /.info -->
						
						<ul class="social pull-right">
							<li><a href="#"><i class="icon-s-facebook"></i></a></li>
							<li><a href="#"><i class="icon-s-gplus"></i></a></li>
							<li><a href="#"><i class="icon-s-twitter"></i></a></li>
							<li><a href="#"><i class="icon-s-pinterest"></i></a></li>
							<li><a href="#"><i class="icon-s-behance"></i></a></li>
							<li><a href="#"><i class="icon-s-dribbble"></i></a></li>
						</ul><!-- /.social -->
						
						<!-- ================================= LOGO MOBILE ================================= -->
						
						<a class="navbar-brand" href="index.html"><img src="<c:url value="/assets/images/logo.svg"/>" class="logo" alt=""></a>
						
						<!-- ================================= LOGO MOBILE : END ================================= -->
						
						<a class="btn responsive-menu pull-right" data-toggle="collapse" data-target=".navbar-collapse"><i class='icon-menu-1'></i></a>
						
					</div><!-- /.container -->
				</div><!-- /.navbar-header -->
				
				<div class="yamm">
					<div class="navbar-collapse collapse">
						<div class="container">
							
							<!-- ================================= LOGO ================================= -->
							
							<a class="navbar-brand" href="index.html"><img src="<c:url value="/assets/images/logo.svg"/>" class="logo" alt=""></a>
							
							<!-- ================================= LOGO : END ================================= -->
							
							
							<!-- ================================= MAIN NAVIGATION ================================= -->
							
							<ul class="nav navbar-nav">
								
								<li><a href="<c:url value="/" />">Home</a></li>
								
								<li class="dropdown">
									<a href="#" class="dropdown-toggle js-activated">Portfolio</a>
									
									<ul class="dropdown-menu">
										<li><a href="portfolio.html">3 Columns Grid</a></li>
										<li><a href="portfolio2.html">3 Columns Details Grid</a></li>
										<li><a href="portfolio3.html">4 Columns Grid</a></li>
										<li><a href="portfolio4.html">4 Columns Details Grid</a></li>
										<li><a href="portfolio5.html">Fullscreen Grid</a></li>
										<li><a href="portfolio-post.html">Post with Slider I</a></li>
										<li><a href="portfolio-post2.html">Post with Slider II</a></li>
										<li><a href="portfolio-post3.html">Post with Images I</a></li>
										<li><a href="portfolio-post4.html">Post with Images II</a></li>
										<li><a href="portfolio-post5.html">Post with Video</a></li>
										<li><a href="portfolio-post6.html">Post with Audio</a></li>
									</ul><!-- /.dropdown-menu -->
								</li><!-- /.dropdown -->
								
								<li class="dropdown">
									<a href="#" class="dropdown-toggle js-activated">Blog</a>
									
									<ul class="dropdown-menu">
										<li><a href="blog.html">Sidebar right</a></li>
										<li><a href="blog2.html">Sidebar left</a></li>
										<li><a href="blog3.html">Without Sidebar</a></li>
										<li><a href="blog4.html">2 Columns Grid Sidebar right</a></li>
										<li><a href="blog5.html">2 Columns Grid Sidebar left</a></li>
										<li><a href="blog6.html">2 Columns Grid without Sidebar</a></li>
										<li><a href="blog7.html">3 Columns Grid without Sidebar</a></li>
										<li><a href="blog-post.html">Post Sidebar right</a></li>
										<li><a href="blog-post2.html">Post Sidebar left</a></li>
										<li><a href="blog-post3.html">Post without Sidebar</a></li>
									</ul><!-- /.dropdown-menu -->
								</li><!-- /.dropdown -->
								
								<li class="dropdown">
									<a href="#" class="dropdown-toggle js-activated">Pages</a>
									
									<ul class="dropdown-menu">
										<li><a href="about.html">About I</a></li>
										<li><a href="about2.html">About II</a></li>
										<li><a href="services.html">Services I</a></li>
										<li><a href="services2.html">Services II</a></li>
										<li><a href="services3.html">Services III</a></li>
										<li><a href="pricing.html">Pricing I</a></li>
										<li><a href="pricing2.html">Pricing II</a></li>
										<li><a href="sidenav.html">Side Navigation</a></li>
									</ul><!-- /.dropdown-menu -->
								</li><!-- /.dropdown -->
								
								<li class="dropdown">
									<a href="#" class="dropdown-toggle js-activated">Features</a>
									
									<ul class="dropdown-menu">
										
										<li><a href="slider-carousel.html">Slider/Carousel</a></li>
										<li><a href="tab.html">Tab</a></li>
										<li><a href="accordion.html">Accordion</a></li>
										<li><a href="isotope.html">Isotope</a></li>
										<li><a href="styles.html">Styles</a></li>
										<li><a href="font-icons.html">Font Icons</a></li>
										<li><a href="backgrounds.html">Backgrounds</a></li>
										
										<li class="dropdown-submenu">
											<a href="#">Colors</a>
											
											<ul class="dropdown-menu">
												<li><a class="changecolor green" title="Green color">Green</a></li>
												<li><a class="changecolor blue" title="Blue color">Blue</a></li>
												<li><a class="changecolor red" title="Red color">Red</a></li>
												<li><a class="changecolor pink" title="Pink color">Pink</a></li>
												<li><a class="changecolor purple" title="Purple color">Purple</a></li>
												<li><a class="changecolor orange" title="Orange color">Orange</a></li>
												<li><a class="changecolor navy" title="Navy color">Navy</a></li>
												<li><a class="changecolor gray" title="Gray color">Gray</a></li>
											</ul><!-- /.dropdown-menu -->
										</li><!-- /.dropdown-submenu -->
										
										<li class="dropdown-submenu">
											<a href="#">Submenu Levels</a>
											
											<ul class="dropdown-menu">
												<li><a href="#">Second Level</a></li>
												<li><a href="#">Second Level</a></li>
												
												<li class="dropdown-submenu">
													<a href="#">More</a>
													
													<ul class="dropdown-menu">
														<li><a href="#">Third Level</a></li>
														<li><a href="#">Third Level</a></li>
													</ul><!-- /.dropdown-menu -->
												</li><!-- /.dropdown-submenu -->
											</ul><!-- /.dropdown-menu -->
										</li><!-- /.dropdown-submenu -->
										
									</ul><!-- /.dropdown-menu -->
								</li><!-- /.dropdown -->
								
								<!-- ================================= MEGA MENU ================================= -->
								
								<li class="dropdown yamm-fullwidth">
									<a href="#" class="dropdown-toggle js-activated">Mega Menu</a>
									
									<ul class="dropdown-menu yamm-dropdown-menu">
										<li>
											<div class="yamm-content row">
											
												<div class="col-sm-3 inner">
													<h4>Focus on</h4>
													<figure>
														<div class="icon-overlay icn-link">
															<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work01.jpg"/>" alt=""></a>
														</div>
														<figcaption>
															<p>Consed quodips ameniat empernam que apid cust quas molor eatis numa estio.</p>
															<a href="portfolio-post.html" class="btn">View Project</a>
														</figcaption>
													</figure>
												</div><!-- /.col -->
												
												<div class="col-sm-3 inner">
													<h4>Special Pages</h4>
													
													<ul class="circled">
														<li><a href="portfolio2.html">3 Columns Details Grid Portfolio</a></li>
														<li><a href="portfolio5.html">Fullscreen Grid Portfolio</a></li>
														<li><a href="portfolio-post5.html">Portfolio Post with Video</a></li>
														<li><a href="blog5.html">2 Columns Grid Blog with Left Sidebar</a></li>
														<li><a href="blog7.html">3 Columns Grid Blog without Sidebar</a></li>
														<li><a href="blog-post.html">Blog Post with Right Sidebar</a></li>
														<li><a href="sidenav.html">Side Navigation Page</a></li>
														<li><a href="about2.html">About Page II</a></li>
														<li><a href="services.html">Service Page I</a></li>
														<li><a href="pricing.html">Pricing Page I</a></li>
														<li><a href="contact.html">Contact Page I</a></li>
													</ul><!-- /.circled -->
												</div><!-- /.col -->
												
												<div class="col-sm-3 inner">
													<h4>Latest Works</h4>
													
													<div class="row thumbs gap-xs">
														
														<div class="col-xs-6 thumb">
															<figure class="icon-overlay icn-link">
																<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work02.jpg"/>" alt=""></a>
															</figure>
														</div><!-- /.thumb -->
														
														<div class="col-xs-6 thumb">
															<figure class="icon-overlay icn-link">
																<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work03.jpg"/>" alt=""></a>
															</figure>
														</div><!-- /.thumb -->
														
														<div class="col-xs-6 thumb">
															<figure class="icon-overlay icn-link">
																<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work04.jpg"/>" alt=""></a>
															</figure>
														</div><!-- /.thumb -->
														
														<div class="col-xs-6 thumb">
															<figure class="icon-overlay icn-link">
																<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work05.jpg"/>" alt=""></a>
															</figure>
														</div><!-- /.thumb -->
														
														<div class="col-xs-6 thumb">
															<figure class="icon-overlay icn-link">
																<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work06.jpg"/>" alt=""></a>
															</figure>
														</div><!-- /.thumb -->
														
														<div class="col-xs-6 thumb">
															<figure class="icon-overlay icn-link">
																<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work07.jpg"/>" alt=""></a>
															</figure>
														</div><!-- /.thumb -->
														
													</div><!-- /.row -->
												</div><!-- /.col -->
												
												<div class="col-sm-3 inner">
													<h4>About Us</h4>
													<p>Voluptat ibusaped molorporro consequ idustibus. Reressi morum ut dolessiti tem nihicid ernatum, coria volore non pro officat ut autem accaborem conet. Omnis peribus qui dolent praeperrum coria.</p>
													<p>Equam conesti occum dolorest, quae venderes quistius, comnitatur sae dinam nonseculpa cum fugit is verciam.</p>
													<a href="about.html" class="btn">Read More</a>
												</div><!-- /.col -->
												
											</div><!-- /.yamm-content -->
										</li>
									</ul><!-- /.yamm-dropdown-menu -->
								</li><!-- /.yamm-fullwidth -->
								
								<!-- ================================= MEGA MENU : END ================================= -->
								
								<li class="dropdown">
									<a href="#" class="dropdown-toggle js-activated">관리메뉴</a>
									
									<ul class="dropdown-menu">
										<li><a href="<c:url value="/category" />">카테고리 관리</a></li>
										<li><a href="<c:url value="/todo" />">TODO</a></li>
									</ul><!-- /.dropdown-menu -->
								</li><!-- /.dropdown -->
								
								<li class="dropdown pull-right searchbox">
									<a href="#" class="dropdown-toggle js-activated"><i class="icon-search"></i></a>
											  
									<div class="dropdown-menu">
										<form id="search" class="navbar-form search" role="search">
											<input type="search" class="form-control" placeholder="Type to search">
											<button type="submit" class="btn btn-default btn-submit icon-right-open"></button>
										</form>
									</div><!-- /.dropdown-menu -->
								</li><!-- /.searchbox -->
								
							</ul><!-- /.nav -->
							
							<!-- ================================= MAIN NAVIGATION : END ================================= -->
							
						</div><!-- /.container -->
					</div><!-- /.navbar-collapse -->
				</div><!-- /.yamm -->
			</div><!-- /.navbar -->
		</header>
		
		<!-- ================================= HEADER : END ================================= -->
		
		
		<!-- ================================= MAIN ================================= -->
		
		<main>
			<sitemesh:write property="body"/>
		</main>
		
		<!-- ================================= MAIN : END ================================= -->
		
		
		<!-- ================================= FOOTER ================================= -->
		
		<footer class="dark-bg">
			<div class="container inner">
				<div class="row">
					
					<div class="col-md-3 col-sm-6 inner">
						<h4>Who we are</h4>
						<a href="index.html"><img class="logo img-intext" src="<c:url value="/assets/images/logo-white.svg"/>" alt=""></a>
						<p>Magnis modipsae voloratati andigen daepeditem quiate re porem que aut labor. Laceaque eictemperum quiae sitiorem rest non restibusaes maio es dem tumquam.</p>
						<a href="about.html" class="txt-btn">More about us</a>
					</div><!-- /.col -->
					
					<div class="col-md-3 col-sm-6 inner">
						<h4>Latest works</h4>
						
						<div class="row thumbs gap-xs">
														
							<div class="col-xs-6 thumb">
								<figure class="icon-overlay icn-link">
									<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work02.jpg"/>" alt=""></a>
								</figure>
							</div><!-- /.thumb -->
							
							<div class="col-xs-6 thumb">
								<figure class="icon-overlay icn-link">
									<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work03.jpg"/>" alt=""></a>
								</figure>
							</div><!-- /.thumb -->
							
							<div class="col-xs-6 thumb">
								<figure class="icon-overlay icn-link">
									<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work07.jpg"/>" alt=""></a>
								</figure>
							</div><!-- /.thumb -->
							
							<div class="col-xs-6 thumb">
								<figure class="icon-overlay icn-link">
									<a href="portfolio-post.html"><img src="<c:url value="/assets/images/art/work01.jpg"/>" alt=""></a>
								</figure>
							</div><!-- /.thumb -->
							
						</div><!-- /.row -->
					</div><!-- /.col -->
					
					
					<div class="col-md-3 col-sm-6 inner">
						<h4>Get In Touch</h4>
						<p>Doloreiur quia commolu ptatemp dolupta oreprerum tibusam eumenis et consent accullignis dentibea autem inisita.</p>
						<ul class="contacts">
							<li><i class="icon-location contact"></i> 84 Street, City, State 24813</li>
							<li><i class="icon-mobile contact"></i> +00 (123) 456 78 90</li>
							<li><a href="#"><i class="icon-mail-1 contact"></i> info@reen.com</a></li>
						</ul><!-- /.contacts -->
					</div><!-- /.col -->
					
					<div class="col-md-3 col-sm-6 inner">
						<h4>Free updates</h4>
						<p>Conecus iure posae volor remped modis aut lor volor accabora incim resto explabo.</p>
						<form id="newsletter" class="form-inline newsletter" role="form">
							<label class="sr-only" for="exampleInputEmail">Email address</label>
							<input type="email" class="form-control" id="exampleInputEmail" placeholder="Enter your email address">
							<button type="submit" class="btn btn-default btn-submit">Subscribe</button>
						</form>
					</div><!-- /.col -->
					
				</div><!-- /.row --> 
			</div><!-- .container -->
		  
			<div class="footer-bottom">
				<div class="container inner">
					<p class="pull-left">© 2014 Artreset. All rights reserved.</p>
					<ul class="footer-menu pull-right">
						<li><a href="index.html">Home</a></li>
						<li><a href="portfolio.html">Portfolio</a></li>
						<li><a href="blog.html">Blog</a></li>
						<li><a href="about.html">About</a></li>
						<li><a href="services.html">Services</a></li>
						<li><a href="contact.html">Contact</a></li>
					</ul><!-- .footer-menu -->
				</div><!-- .container -->
			</div><!-- .footer-bottom -->
		</footer>
		
		<!-- ================================= FOOTER : END ================================= -->
		
	</body>
</html>