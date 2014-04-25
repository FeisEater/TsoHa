<%-- 
    Document   : list
    Created on : Apr 2, 2014, 1:43:34 AM
    Author     : FeisEater
--%>

<%@tag description="handles listing" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cu" uri="/WEB-INF/custom.tld"%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="list"%>
<%@attribute name="curUrl"%>
<%@attribute name="size"%>

<%-- any content can be specified here e.g.: --%>
<div class="col-md-12">
    <table class="table table-striped">
        <jsp:doBody/>
    </table>
    <ul class="pagination">
        <c:set var="page" value="${param.page == null ? 1 : param.page}"></c:set>
        <c:set var="n"><cu:CountPages size="${size}" /></c:set>
        <c:if test="${page != 1}">
            <li><a href="${curUrl}page=${page - 1}">&laquo;</a></li>
        </c:if>
        <c:if test="${page == 1}">
            <li class="disabled"><a>&laquo;</a></li>
        </c:if>
        <c:if test="${n <= 5}">
            <c:forEach var="i" begin="1" end="${n}">
                <li class="${page == i ? 'active' : ''}"><a href="${curUrl}page=${i}">${i}</a></li>
            </c:forEach>
        </c:if>
        <c:if test="${n > 5}">
            <li class="${page == 1 ? 'active' : ''}"><a href="${curUrl}page=1">1</a></li>
            <c:if test="${page > 3}">
                <li><a>...</a></li>
            </c:if>
            <c:if test="${page > 2}">
                <li><a href="${curUrl}page=${page - 1}">${page - 1}</a></li>
            </c:if>
            <c:if test="${page != n && page != 1}">
                <li class="active"><a>${page}</a></li>
            </c:if>
            <c:if test="${page < (n - 1)}">
                <li><a href="${curUrl}page=${page + 1}">${page + 1}</a></li>
            </c:if>
            <c:if test="${page < (n - 2)}">
                <li><a>...</a></li>
            </c:if>
            <li class="${page == n ? 'active' : ''}"><a href="${curUrl}page=${n}">${n}</a></li>
        </c:if>
        <c:if test="${page == n}">
            <li class="disabled"><a>&raquo;</a></li>
        </c:if>
        <c:if test="${page != n}">
            <li><a href="${curUrl}page=${page + 1}">&raquo;</a></li>
        </c:if>
    </ul>
</div>
