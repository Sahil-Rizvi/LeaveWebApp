<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  



<div>

<c:url value="/resources/js/myemppagination.js" var="paginationJS" />  
<c:if test="${not empty currentIndex}">
<c:url var="firstUrl" value="/hr/findByEmpDepartment/1" />
<c:url var="baseUrl" value="/hr/findByEmpDepartment/" />

<c:if test="${not empty pages.totalPages && pages.totalPages>0}">
<c:url var="lastUrl" value="/hr/findByEmpDepartment/${pages.totalPages}" />
<c:if test="${currentIndex - 1 >=1}">
<c:url var="prevUrl" value="/hr/findByEmpDepartment/${currentIndex - 1}" />
</c:if>
<c:if test="${currentIndex + 1 <= pages.totalPages}">
<c:url var="nextUrl" value="/hr/findByEmpDepartment/${currentIndex + 1}" />
</c:if>
</c:if>
</c:if>
<c:if test="${not empty pages.content}">
<div class="table-responsive">          
<table class="table table-hover">
<thead>
<tr>
<th>EMPLOYEE CODE</th>
<th>EMPLOYEE NAME</th>
<th>DESIGNATION</th>
<th>DATE OF JOINING</th>
<th>DATE OF BIRTH</th>
<th>MANAGER'S CODE</th>
<th>MANAGER'S NAME</th>
<th>DEPARTMENT</th>
<th>MANAGER STATUS</th>
</tr>
</thead>
<tbody>
<c:forEach items="${pages.content}" var="emp">
<tr>
<td id="${emp.employeeName}" class="emp-code">${emp.employeeCode}</td>
<td>${emp.employeeName}</td>
<td>${emp.designation}</td>
<td>${emp.dateofJoining}</td>
<td>${emp.dateofBirth}</td>
<td>${emp.managerCode}</td>
<td>${emp.managerName}</td>
<td>${emp.department}</td>
<td>${emp.managerStatus}</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<jsp:include page="../mypagination.jsp" />
</c:if>
<c:if test="${empty pages.content }">
 <h3>No Employee Found</h3>
</c:if>


<script>
firstUrl = "${firstUrl}";
lastUrl = "${lastUrl}";
prevUrl = "${prevUrl}";
nextUrl = "${nextUrl}";
baseUrl = "${baseUrl}";
</script>
<script src="${paginationJS}"></script>
</div>

     

