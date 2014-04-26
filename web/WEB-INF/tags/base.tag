<%-- 
    Document   : base
    Created on : 25.3.2014, 12:27:59
    Author     : Pavel
--%>

<%@tag description="base html code for all pages in QuesAns" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="pageTitle"%>
<%@attribute name="objectFromID"%>
<%@attribute name="modpage"%>

<%-- any content can be specified here e.g.: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${pageTitle}</title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="container-fluid" style="${loggedIn.moderator == true ? 'background-color:#FF99CC' : 'background-color:#DDDDFF'}">
            <div class="row">
                <div class="col-md-offset-1 col-md-2">
                    <a href="index" type="button" class="btn btn-link" style="color:#000; font-size:48px; font-weight:bold">QuesAns</a>
                </div>
                <div class="col-md-offset-1 col-md-3">
                    <c:if test="${loggedIn.moderator}">
                        <p align="center">Moderate content:</p>
                        <ul class="nav nav-pills">
                            <li class="${modpage == 0 ? 'active' : ''}"><a href="modusers">Users</a></li>
                            <li class="${modpage == 1 ? 'active' : ''}"><a href="modquestions">Questions</a></li>
                            <li class="${modpage == 2 ? 'active' : ''}"><a href="modanswers">Answers</a></li>
                            <li class="${modpage == 3 ? 'active' : ''}"><a href="modtags">Tags</a></li>
                        </ul>
                    </c:if>
                </div>
                <c:if test="${loggedIn == null}">
                    <div class="col-md-offset-3 col-md-2">
                        <div class="col-md-12">
                            <a href="login" type="button" class="btn btn-primary">Sign in</a>
                        </div>
                        <div class="col-md-12">
                            <a href="register" type="button" class="btn btn-primary">Sign up</a>
                        </div>
                    </div>
                </c:if>
                <c:if test="${loggedIn != null}">
                    <div class="col-md-offset-1 col-md-2">
                        <c:set var="avatar" value="${loggedIn.avatar}"/>
                        <c:if test="${avatar == null}">
                            <img src="defavatar.png" alt="avatar" class="avatar" height="80" width="80" align="right">
                        </c:if>
                        <c:if test="${avatar != null}">
                            <img src="data:image/jpg;base64,${avatar}" alt="avatar" class="avatar" height="80" width="80" align="right">
                        </c:if>
                    </div>
                    <div class="col-md-2">
                        <div class="col-md-12">
                            <a href="accquestions" type="button" class="btn btn-primary">${loggedIn.name}</a>
                        </div>
                        <div class="col-md-12">
                            <a href="logout" type="button" class="btn btn-primary">Log out</a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <c:if test="${fn:length(errors) > 0}">
            <div class="container-fluid" style="background-color:#FFDDDD">
                <div class="row">
                    <div class="col-ms-12"><br></div>
                    <label class="col-md-offset-4">
                        <c:if test="${fn:length(errors) == 1}">
                            Oh no! <c:forEach var="error" items="${errors}">${error}</c:forEach>
                        </c:if>
                        <c:if test="${fn:length(errors) > 1}">
                            Oh no! There were following errors:
                            <ul>
                                <c:forEach var="error" items="${errors}">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </label>
                    <div class="col-ms-12"><br></div>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:length(infos) > 0}">
            <div class="container-fluid" style="background-color:#DDFFDD">
                <div class="row">
                    <div class="col-ms-12"><br></div>
                    <label class="col-md-offset-4">
                        <c:if test="${fn:length(infos) == 1}">
                            <c:forEach var="info" items="${infos}">
                                ${info}
                            </c:forEach>
                        </c:if>
                        <c:if test="${fn:length(infos) > 1}">
                            <ul>
                                <c:forEach var="info" items="${infos}">
                                    <li>${info}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </label>
                    <div class="col-ms-12"><br></div>
                </div>
            </div>
        </c:if>
        <jsp:doBody/>
    </body>
</html>
