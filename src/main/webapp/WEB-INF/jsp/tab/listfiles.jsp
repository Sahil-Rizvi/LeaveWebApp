<div class="col-sm-8 text-left"> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<div id="leavepolicy" class="">
<c:if test="${not empty leavepolicyfiles}">
<table class="table table-hover">
<thead>
<tr>
<th>Id</th>
<th>Name</th>
<th>Type</th>
<th>Created On</th>
<th>Description</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach items="${leavepolicyfiles}" var="file">
<tr>
<td class="file-id">${file.id}</td>
<td>${file.name}</td>
<td>${file.fileType}</td>
<td>${file.createdOn}</td>
<td>${file.description}</td>
<td>
<a href="/LeaveWebApp/uploader/api/download?id=${file.id}">Download</a>
</td>
</tr>   
</c:forEach>
</tbody>
</table>
<br><br>
<hr>

</c:if>
<%-- <c:if test="${empty leavepolicyfiles}"> --%>
<!--  No Leave Policy file found -->
<%-- </c:if> --%>


</div>


<div id="notice" class="">
<c:if test="${not empty noticefiles}">
<table class="table table-hover">
<thead>
<tr>
<th>Id</th>
<th>Name</th>
<th>Type</th>
<th>Created On</th>
<th>Description</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach items="${noticefiles}" var="file">
<tr>
<td class="file-id">${file.id}</td>
<td>${file.name}</td>
<td>${file.fileType}</td>
<td>${file.createdOn}</td>
<td>${file.description}</td>
<td>
<a href="/LeaveWebApp/uploader/api/download?id=${file.id}">Download</a>
</td>
</tr>   
</c:forEach>
</table>
<br><br>
<hr>

</c:if>
<%-- <c:if test="${empty noticefiles}"> --%>
<!-- No Notice file found -->
<%-- </c:if> --%>

</div>



<div id="other" class="">
<c:if test="${not empty otherfiles}">
<table class="table table-hover">
<thead>
<tr>
<th>Id</th>
<th>Name</th>
<th>Type</th>
<th>Created On</th>
<th>Description</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach items="${otherfiles}" var="file">
<tr>
<td class="file-id">${file.id}</td>
<td>${file.name}</td>
<td>${file.fileType}</td>
<td>${file.createdOn}</td>
<td>${file.description}</td>
<td>
<a href="/LeaveWebApp/uploader/api/download?id=${file.id}">Download</a>
</td>
</tr>   
</c:forEach>
</table>
</c:if>
<%-- <c:if test="${empty otherfiles}"> --%>
<!--  No file found -->
<%-- </c:if> --%>

<c:if test="${empty leavepolicyfiles && empty noticefiles && empty otherfiles}">
 Oops !!! It seems no file has not been uploaded till now. 
</c:if>


</div>
</div>