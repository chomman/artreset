<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>카테고리 추가</title>
</head>
<body>
				<!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        카테고리
                        <small>상세</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="<c:url value="/" />"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="<c:url value="/category" />">카테고리</a></li>
                        <li class="active">카테고리 상세</li>
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
                                    <h3 class="box-title">카테고리 상세</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <dl class="dl-horizontal">
                                        <dt>카테고리명</dt>
                                        <dd>${category.name }</dd>
                                        <dt>설명</dt>
                                        <dd>${category.description }</dd>
                                        <dt>부모 카테고리</dt>
                                        <dd>${category.parentCategory.name }</dd>
                                        <dt>생성일시</dt>
                                        <dd>${category.creationTime }</dd>
                                    </dl>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->
						</div>
					</div>
					
                    <div class="row pull-right">
                        <div class="col-xs-12 ">
                    		<a class="btn btn-primary btn-flat" href="<c:url value="/category"/>">카테고리 목록</a>
                    		<a class="btn btn-primary btn-flat" href="<c:url value="/category/update/${category.id }"/>">수정</a>
                    		<a class="btn btn-primary btn-flat" href="<c:url value="/category/delete/${category.id }"/>">삭제</a>
                    		<button class="btn btn-primary btn-md" data-toggle="modal" data-target="#deleteDialog">삭제</button>
                    	</div>
                    </div>
                </section><!-- /.content -->
                
                
				<!-- Modal -->
				<div class="modal fade" id="deleteDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">
									카테고리 삭제
								</h4>
							</div>
							<div class="modal-body">
								카테고리 삭제
							</div>
							<div class="modal-footer">
								<form action="<c:url value="/category/delete/${category.id }"/>" method="get">
									<button type="button" class="btn btn-default" data-dismiss="modal">
										<spring:message code="label.cancel" />
									</button>
									<button type="submit" class="btn btn-primary">
										정말로 삭제하시겠습니까?
									</button>
								</form>
							</div>
						</div>
					</div>
				</div>                
                    

</body>
</html>