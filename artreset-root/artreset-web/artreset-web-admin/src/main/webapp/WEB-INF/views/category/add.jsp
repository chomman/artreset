<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>카테고리 추가</title>
    <script type="text/javascript" src="<c:url value="/static/js/form.validation.js"/> "></script>
</head>
<body>
				<!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        카테고리
                        <small>추가</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="<c:url value="/" />"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="<c:url value="/category" />">카테고리</a></li>
                        <li class="active">카테고리 추가</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                 	<div class="row">
                        <!-- left column -->
                        <div class="col-md-12">
                            <!-- general form elements -->
                            <div class="box box-primary">
                                <div class="box-header">
                                    <h3 class="box-title">카테고리 추가</h3>
                                </div><!-- /.box-header -->
                                <!-- form start -->
                                <form:form commandName="category" method="POST" enctype="utf8" cssClass="forms contact-form">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="box-body">
                                        <div class="form-group control-group">
                                            <label for="name">상위 카테고리</label>
                                            <form:select id="category-parent" path="parentId" title="상위 카테고리" cssClass="form-control">
                                            	<option value="0">최상위</option>
                                            	<c:forEach items="${categorySelector }" var="option" >
                                            	<form:option value="${option.id }" label="${option.name }" />
                                            	</c:forEach>
                                            </form:select>
											<form:errors id="error-name" path="parentId" cssClass="help-inline"/>
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="name">카테고리명</label>
                                            <form:input id="category-name" path="name" title="카테고리명" cssClass="form-control"/>
											<form:errors id="error-name" path="name" cssClass="help-inline"/>
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="description">카테고리 설명</label>
                                            <form:input id="category-description" path="description" title="카테고리 설명" cssClass="form-control"/>
											<form:errors id="error-description" path="description" cssClass="help-inline"/>
                                        </div>
                                    </div><!-- /.box-body -->

                                    <div class="box-footer">
                                        <button type="submit" class="btn btn-primary">확인</button>
                                    </div>
                                </form:form>
                            </div><!-- /.box -->
						</div>
					</div>
                </section><!-- /.content -->

</body>
</html>