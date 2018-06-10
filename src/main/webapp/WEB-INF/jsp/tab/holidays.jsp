<div class="col-sm-8 text-left"> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<div class="table-responsive">
<table class="table table-hover">
<c:forEach items="${holidays}" var="entry">
<tbody>
<tr>
<td>${entry.key}</td>
<td>${entry.value}</td>
</tr>
</tbody>
</c:forEach>
</table>
</div>

</div>