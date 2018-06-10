<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table border=1 width="50%">
<tr>
<th>COMPOFFID</th>
<th>EMPLOYEEID</th>
<th>MANAGERID</th>
<th>CURRENTLY WITH</th>
<th>APPLIED ON</th>
<th>FOR DATE</th>
<th>WORK TYPE</th>
<th>STATUS</th>
</tr>

<c:forEach items="${compoffs}" var="compoff">
<tr>
<td>${compoff.compOffId}</td>
<td>${compoff.employeeEntity}</td>
<td>${compoff.managerEntity}</td>
<td>${compoff.currentlyManager}</td>
<td>${compoff.appliedOn}</td>
<td>${compoff.forDate}</td>
<td>${compoff.workType}</td>
<td>${compoff.compOffStatus}</td>
</tr>   
</c:forEach>
</table>
