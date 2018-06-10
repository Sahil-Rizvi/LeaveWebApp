<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Leaves Of Team Mates</title>
<spring:url value="/resources/js/mypagination.js" var="paginationJS" />
<link href="${paginationJS}" lang="javascript"/>


</head>
<body>
<c:if test="${not empty currentIndex}">
<c:url var="firstUrl" value="/leave/emp/getLeavesOfSpecificEmployeeTeamMates/1" />
<c:url var="baseUrl" value="/leave/emp/getLeavesOfSpecificEmployeeTeamMates/" />

<c:if test="${not empty pages.totalPages && pages.totalPages>0}">
<c:url var="lastUrl" value="/leave/emp/getLeavesOfSpecificEmployeeTeamMates/${pages.totalPages}" />
<c:if test="${currentIndex - 1 >=1}">
<c:url var="prevUrl" value="/leave/emp/getLeavesOfSpecificEmployeeTeamMates/${currentIndex - 1}" />
</c:if>
<c:if test="${currentIndex + 1 <= pages.totalPages}">
<c:url var="nextUrl" value="/leave/emp/getLeavesOfSpecificEmployeeTeamMates/${currentIndex + 1}" />
</c:if>
</c:if>
</c:if>
<c:if test="${not empty pages.content}">
<table border=1 width="50%">
<tr>
<th>LEAVEID</th>
<th>EMPLOYEE CODE</th>
<th>EMPLOYEE NAME</th>
<th>FROM</th>
<th>TO</th>
<th>LEAVE TYPE</th>
</tr>
<c:forEach items="${pages.content}" var="leave">
<tr>
<td>${leave.leaveId}</td>
<td>${leave.employee.empCode}</td>
<td>${leave.employee.empName}</td>
<td>${leave.fromDate}</td>
<td>${leave.toDate}</td>
<td>${leave.leaveType}</td>
</tr>
</c:forEach>
</table>
<jsp:include page="../mypagination.jsp" />
</c:if>
<c:if test="${empty pages.content  && empty message}">
 No Teammate's Leave Is Available
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
<script src="${paginationJS}"></script>
</body>
</html>