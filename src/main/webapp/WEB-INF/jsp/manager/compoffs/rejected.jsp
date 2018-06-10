<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rejected CompOffs</title>

<spring:url value="/resources/js/mypagination.js" var="paginationJS" />
<link href="${paginationJS}" lang="javascript"/>

</head>
<body>
<c:if test="${not empty currentIndex}">
<c:url var="firstUrl" value="/compOff/manager/getCompOffsRejectedByManager/1" />
<c:url var="baseUrl" value="/compOff/manager/getCompOffsRejectedByManager/" />
	
<c:if test="${not empty pages.totalPages && pages.totalPages>0}">
<c:url var="lastUrl" value="/compOff/manager/getCompOffsRejectedByManager/${pages.totalPages}" />
<c:if test="${currentIndex - 1 >=1}">
<c:url var="prevUrl" value="/compOff/manager/getCompOffsRejectedByManager/${currentIndex - 1}" />
</c:if>
<c:if test="${currentIndex + 1 <= pages.totalPages}">
<c:url var="nextUrl" value="/compOff/manager/getCompOffsRejectedByManager/${currentIndex + 1}" />
</c:if>
</c:if>
</c:if>
<c:if test="${not empty pages.content}">
<table border=1 width="50%">
<tr>
<th>COMPOFF ID</th>
<th>FOR DATE</th>
<th>APPLIED ON</th>
<th>EMPLOYEE CODE</th>
<th>EMPLOYEE NAME</th>
<th>REJECTED ON</th>
<th>REJECTION REASON</th>
</tr>
<c:forEach items="${pages.content}" var="compoff">
<tr>
<td>${compoff.compOffId}</td>
<td>${compoff.forDate}</td>
<td>${compoff.appliedOn}</td>
<td>${compoff.employee.empCode}</td>
<td>${compoff.employee.empName}</td>
<td>${compoff.rejectedOn}</td>
<td>${compoff.rejectionReason}</td>
</tr>   
</c:forEach>
</table>
<jsp:include page="../../mypagination.jsp" />
</c:if>
<c:if test="${empty pages.content  && empty message}">
 No Rejected CompOffs Are Available
</c:if>
<c:if test="${not empty message}">
 ${message}
</c:if>
<script>
firstUrl = "${firstUrl}";
lastUrl = "${lastUrl}";
prevUrl = "${prevUrl}";
nextUrl = "${nextUrl}";
baseUrl = "${baseUrl}";
</script>
<script src="${paginationJS}"></script></body>
</html>