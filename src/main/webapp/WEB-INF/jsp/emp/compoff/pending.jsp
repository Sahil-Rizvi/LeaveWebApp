<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pending CompOffs</title>

<c:url value="/resources/js/mypagination.js" var="paginationJS" />
    
</head>
<body>
<c:if test="${not empty currentIndex}">
	<c:url var="firstUrl" value="/compOff/emp/getPendingCompOffsOfEmp/1" />
	<c:url var="baseUrl" value="/compOff/emp/getPendingCompOffsOfEmp/" />
	<c:if test="${not empty pages.totalPages && pages.totalPages>0}">
		<c:url var="lastUrl" value="/compOff/emp/getPendingCompOffsOfEmp/${pages.totalPages}" />
			<c:if test="${currentIndex - 1 >=1}">
				<c:url var="prevUrl" value="/compOff/emp/getPendingCompOffsOfEmp/${currentIndex - 1}" />
			</c:if>
			<c:if test="${currentIndex + 1 <= pages.totalPages}">
				<c:url var="nextUrl" value="/compOff/emp/getPendingCompOffsOfEmp/${currentIndex + 1}" />
			</c:if>
	</c:if>
</c:if>
<c:if test="${not empty pages.content}">
<table border=1 width="50%">
<tr>
<th>COMPOFFID</th>
<th>FOR DATE</th>
<th>APPLIED ON</th>
<th>MANAGER CODE</th>
<th>MANAGER NAME</th>
</tr>
<c:forEach items="${pages.content}" var="compoff">
<tr>
<td>${compoff.compOffId}</td>
<td>${compoff.forDate}</td>
<td>${compoff.appliedOn}</td>
<td>${compoff.pendingWith.empCode}</td>
<td>${compoff.pendingWith.empName}</td>
</tr>   
</c:forEach>
</table>
<jsp:include page="../../mypagination.jsp" />
</c:if>
<c:if test="${empty pages.content  && empty message}">
 No Pending CompOffs Are Available
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
console.log("***");
console.log("pending");

console.log(firstUrl);
console.log(lastUrl);
console.log(prevUrl);
console.log(nextUrl);
console.log(baseUrl);
console.log("***");
</script>
<script src="${paginationJS}"></script>
</body>
</html>