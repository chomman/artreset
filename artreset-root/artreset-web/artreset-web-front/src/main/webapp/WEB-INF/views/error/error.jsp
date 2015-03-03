<!DOCTYPE html>
<%@ page isErrorPage="true" import="java.io.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
</head>
<body>
	<h1>
		<spring:message code="label.internalservererror.page.title" />
	</h1>
	<div class="page-content">
		<p>
			<spring:message code="label.internalservererror.page.message" />
		</p>
	</div>
	<div>
		<%
			// if there is an exception
			if (exception != null) {
				// print the stack trace hidden in the HTML source code for debug
				exception.printStackTrace(new PrintWriter(out));
			}
		%>
	</div>
</body>
</html>