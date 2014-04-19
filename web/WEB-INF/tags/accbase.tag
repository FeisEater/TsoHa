<%-- 
    Document   : accbase
    Created on : Mar 26, 2014, 8:20:01 PM
    Author     : FeisEater
--%>

<%@tag description="base html code for account pages" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="page"%>

<%-- any content can be specified here e.g.: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-md-3">
            <h2>${loggedIn.name}</h2>
            <c:set var="avatar" value="${loggedIn.avatar}"/>
            <c:if test="${avatar == null}">
                <img src="defavatar.png" class="avatar" width="256" height="256" alt="avatar">
            </c:if>
            <c:if test="${avatar != null}">
                <img src="data:image/jpg;base64,${avatar}" class="avatar" width="256" height="256" alt="avatar">
            </c:if>
            <a href="">${loggedIn.email}</a>
        </div>
        <div class="col-md-9">
            <ul class="nav nav-pills">
                <li class="${page == 0 ? 'active' : ''}"><a href="accquestions">Asked questions</a></li>
                <li class="${page == 1 ? 'active' : ''}"><a href="accanswers">Given answers</a></li>
                <li class="${page == 2 ? 'active' : ''}"><a href="accsettings">Change account settings</a></li>
                <c:if test="${loggedIn.moderator == true}">
                    <li class="${page == 3 ? 'active' : ''}"><a href="modquestions">Delete flagged questions</a></li>
                </c:if>
            </ul>
            <jsp:doBody/>
        </div>
    </div>
</div>
