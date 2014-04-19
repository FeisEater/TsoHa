<%-- 
    Document   : base
    Created on : 25.3.2014, 12:27:59
    Author     : Pavel
--%>

<%@tag description="base html code for all pages in QuesAns" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="pageTitle"%>
<%@attribute name="userName"%>
<%@attribute name="errorMessage"%>
<%@attribute name="givenName"%>
<%@attribute name="objectFromID"%>

<%-- any content can be specified here e.g.: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <li class="${page == 0 ? 'active' : ''}"><a href="modusers">Users</a></li>
                            <li class="${page == 1 ? 'active' : ''}"><a href="modquestions">Questions</a></li>
                            <li class="${page == 2 ? 'active' : ''}"><a href="modanswers">Answers</a></li>
                            <li class="${page == 3 ? 'active' : ''}"><a href="modtags">Tags</a></li>
                        </ul>
                    </c:if>
                </div>
                <c:if test="${userName == null}">
                    <div class="col-md-offset-3 col-md-2">
                        <div class="col-md-12">
                            <a href="login" type="button" class="btn btn-primary">Sign in</a>
                        </div>
                        <div class="col-md-12">
                            <a href="register" type="button" class="btn btn-primary">Sign up</a>
                        </div>
                    </div>
                </c:if>
                <c:if test="${userName != null}">
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
                            <a href="accquestions" type="button" class="btn btn-primary">${userName}</a>
                        </div>
                        <div class="col-md-12">
                            <a href="logout" type="button" class="btn btn-primary">Log out</a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <c:if test="${errorMessage != null}">
            <div class="container-fluid" style="background-color:#FFDDDD">
                <div class="row">
                    <div class="col-ms-12"><br></div>
                    <label class="col-md-offset-4">
                        Shit! ${errorMessage}
                    </label>
                    <div class="col-ms-12"><br></div>
                </div>
            </div>
        </c:if>
        <jsp:doBody/>
    </body>
</html>
