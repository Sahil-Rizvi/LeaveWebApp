<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<c:if test="${empty message}">
<div class="table-responsive">          
<table class="table">
<thead>
<tr>
<th>SICK</th>
<th>CASUAL</th>
<th>PRIVILEGED</th>
<th>B'DAY</th>
<th>COMP-OFF</th>
</tr>
</thead>
<tbody>
<tr>
<td>${lc.sickCount}</td>
<td>${lc.casualCount}</td>
<td>${lc.privilegedCount}</td>
<td>${lc.bdayCount}</td>
<td>${lc.compOffCount}</td>
</tr>   
</tbody>
</table>
</div>
</c:if>
<c:if test="${not empty message}">
${message}
</c:if>
</div>
