<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<div class="center"> 

<ul class="pagination">
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><button>&lt;&lt;</button></li>
                <li class="disabled"><button>&lt;</button></li>
            </c:when>
            <c:otherwise>
                <li><button id="first">&lt;&lt;</button></li>
                <li><button id="prev">&lt;</button></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">    
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><button><c:out value="${i}" /></button></li>
                </c:when>
                <c:otherwise>
                    <li><button name="getContent" id="${i}"><c:out value="${i}" /></button></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == pages.totalPages}">
                <li class="disabled"><button>&gt;</button>
                <li class="disabled"><button>&gt;&gt;</button>
            </c:when>
            <c:otherwise>
                <li><button id="next">&gt;</button></li>
                <li><button id="last">&gt;&gt;</button></li>
            </c:otherwise>
        </c:choose>
        
</ul>
</div>
