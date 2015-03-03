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
						<h1><spring:message code="label.artist.list.page.title"/></h1>
					</div>
				</div>
			</div>
		</div>
        
        <div class="section">
	    	<div class="container">
				<div class="row">
					<!-- Team Member -->
					<div class="col-md-4 col-sm-6">
						<div class="team-member">
							<!-- Team Member Photo -->
							<div class="team-member-image"><img src="<c:url value="/assets/theme/mpurpose/img/team1.jpg"/>" alt="Name Surname"></div>
							<div class="team-member-info">
								<ul>
									<!-- Team Member Info & Social Links -->
									<li class="team-member-name">
										Name Surname
										<!-- Team Member Social Links -->
										<span class="team-member-social">
											<a href="#"><i class="icon-facebook"></i></a>
											<a href="#"><i class="icon-github"></i></a>
											<a href="#"><i class="icon-tumblr"></i></a>
										</span>
									</li>
									<li>Web Developer</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- End Team Member -->
					<div class="col-md-4 col-sm-6">
						<div class="team-member">
							<div class="team-member-image"><img src="<c:url value="/assets/theme/mpurpose/img/team2.jpg"/>" alt="Name Surname"></div>
							<div class="team-member-info">
								<ul>
									<li class="team-member-name">
										Name Surname
										<span class="team-member-social">
											<a href="#"><i class="icon-facebook"></i></a>
											<a href="#"><i class="icon-dribbble"></i></a>
											<a href="#"><i class="icon-tumblr"></i></a>
										</span>
									</li>
									<li>Web Designer</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="team-member">
							<div class="team-member-image"><img src="<c:url value="/assets/theme/mpurpose/img/team3.jpg"/>" alt="Name Surname"></div>
							<div class="team-member-info">
								<ul>
									<li class="team-member-name">
										Name Surname
										<span class="team-member-social">
											<a href="#"><i class="icon-facebook"></i></a>
											<a href="#"><i class="icon-dribbble"></i></a>
											<a href="#"><i class="icon-tumblr"></i></a>
										</span>
									</li>
									<li>Project Manager</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="team-member">
							<div class="team-member-image"><img src="<c:url value="/assets/theme/mpurpose/img/team4.jpg"/>" alt="Name Surname"></div>
							<div class="team-member-info">
								<ul>
									<li class="team-member-name">
										Name Surname
										<span class="team-member-social">
											<a href="#"><i class="icon-facebook"></i></a>
											<a href="#"><i class="icon-dribbble"></i></a>
											<a href="#"><i class="icon-tumblr"></i></a>
										</span>
									</li>
									<li>Project Manager</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="team-member">
							<div class="team-member-image"><img src="<c:url value="/assets/theme/mpurpose/img/team5.jpg"/>" alt="Name Surname"></div>
							<div class="team-member-info">
								<ul>
									<li class="team-member-name">
										Name Surname
										<span class="team-member-social">
											<a href="#"><i class="icon-facebook"></i></a>
											<a href="#"><i class="icon-dribbble"></i></a>
											<a href="#"><i class="icon-tumblr"></i></a>
										</span>
									</li>
									<li>UX Designer</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6">
						<div class="team-member">
							<div class="team-member-image"><img src="<c:url value="/assets/theme/mpurpose/img/team6.jpg"/>" alt="Name Surname"></div>
							<div class="team-member-info">
								<ul>
									<li class="team-member-name">
										Name Surname
										<span class="team-member-social">
											<a href="#"><i class="icon-facebook"></i></a>
											<a href="#"><i class="icon-dribbble"></i></a>
											<a href="#"><i class="icon-tumblr"></i></a>
										</span>
									</li>
									<li>Systems Analyst</li>
								</ul>
							</div>
						</div>
					</div>
					<c:forEach items="${artists }" var="artist">
					<div class="col-md-4 col-sm-6">
						<div class="team-member">
							<div class="team-member-image">
								<a href="<c:url value="/artist/${artist.id }"/>">
									<img src="${artist.avatar.thumbnailUrl }" alt="Name Surname">
								</a>
							</div>
							<div class="team-member-info">
								<ul>
									<li class="team-member-name">
										<a href="<c:url value="/artist/${artist.id }"/>">${artist.nickName }</a>
										<span class="team-member-social">
											<a href="#"><i class="icon-facebook"></i></a>
											<a href="#"><i class="icon-dribbble"></i></a>
											<a href="#"><i class="icon-tumblr"></i></a>
										</span>
									</li>
									<li>${artist.shortDescription }</li>
								</ul>
							</div>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
		
		<div class="section section-white">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="calltoaction-wrapper">
							<h3>당신도 작가가 될 수 있습니다!</h3> <a href="/artist/add" class="btn btn-orange"><spring:message code="label.add.artist.link"/></a>
						</div>
					</div>
				</div>
			</div>
		</div>

    </body>
</html>