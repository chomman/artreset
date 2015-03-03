<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>카테고리 추가</title>
    <link href="<c:url value="/assets/theme/adminlte/css/datatables/dataTables.bootstrap.css"/>" rel="stylesheet" type="text/css" />
    <!-- DATA TABES SCRIPT -->
    <script src="<c:url value="/assets/theme/adminlte/js/plugins/datatables/jquery.dataTables.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/assets/theme/adminlte/js/plugins/datatables/dataTables.bootstrap.js"/>" type="text/javascript"></script>
    <script type="text/javascript">
        $(function() {
            //$("#categoryTable").dataTable();
            $('#categoryTable').dataTable({
                "bPaginate": true,
                "bLengthChange": false,
                "bFilter": false,
                "bSort": true,
                "bInfo": true,
                "bAutoWidth": false
            });
        });
    </script>
</head>
<body>
				<!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        카테고리
                        <small>목록</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="<c:url value="/" />"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="<c:url value="/category" />">카테고리</a></li>
                        <li class="active">카테고리 목록</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                 	<div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">카테고리 목록</h3>
                                    <div class="box-tools">
                                        <div class="input-group">
                                            <input type="text" name="table_search" class="form-control input-sm pull-right" style="width: 150px;" placeholder="Search"/>
                                            <div class="input-group-btn">
                                                <button class="btn btn-sm btn-default"><i class="fa fa-search"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- /.box-header -->
                                <div class="box-body table-responsive">
                                    <table id="categoryTable" class="table table-bordered table-hover">
                                        <thead>
	                                        <tr>
	                                            <th>ID</th>
	                                            <th>상위 카테고리</th>
	                                            <th>카테고리명</th>
	                                            <th>카테고리 설명</th>
	                                        </tr>
                                        </thead>
                                        <tbody>
	                                        <c:forEach var="category" items="${categories }" >
	                                        <tr>
	                                            <td>${category.id }</td>
	                                            <td>${category.parentCategory.name }</td>
	                                            <td><a href="<c:url value="/category/${category.id }"/>">${category.name }</a></td>
	                                            <td>${category.description }</td>
	                                        </tr>
	                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    
                                    
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-xs-12">
                    		<a class="btn btn-primary btn-flat" href="<c:url value="/category/add"/>">카테고리 새로 입력</a>
                    	</div>
                    </div>
                </section><!-- /.content -->  

</body>
</html>