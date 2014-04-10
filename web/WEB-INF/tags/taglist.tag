<%-- 
    Document   : taglist
    Created on : Apr 2, 2014, 1:43:34 AM
    Author     : FeisEater
--%>

<%@tag description="handles tag listing" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="taglist"%>

<%-- any content can be specified here e.g.: --%>
<div class="btn-group-vertical">
    <jsp:doBody/>
</div>
