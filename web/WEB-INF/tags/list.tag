<%-- 
    Document   : list
    Created on : Apr 2, 2014, 1:43:34 AM
    Author     : FeisEater
--%>

<%@tag description="handles listing" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="list"%>

<%-- any content can be specified here e.g.: --%>
<div class="col-md-12">
    <table class="table table-striped">
        <jsp:doBody/>
    </table>
    <ul class="pagination">
        <li><a href="#">&laquo;</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">&raquo;</a></li>
    </ul>
</div>
