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
        <div class="container-fluid" style="background-color:#DDDDFF">
            <div class="row">
                <div class="col-md-offset-1 col-md-3">
                    <a href="index.jsp" type="button" class="btn btn-link" style="color:#000; font-size:48px; font-weight:bold">QuesAns</a>
                </div>
                <c:if test="${userName == null}">
                    <div class="col-md-offset-6 col-md-2">
                        <div class="col-md-12">
                            <a href="signin.jsp" type="button" class="btn btn-primary">Sign in</a>
                        </div>
                        <div class="col-md-12">
                            <a href="register.jsp" type="button" class="btn btn-primary">Sign up</a>
                        </div>
                    </div>
                </c:if>
                <c:if test="${userName != null}">
                    <div class="col-md-offset-4 col-md-2">
                        <img src="defavatar.png" alt="avatar" height="80" align="right">
                    </div>
                    <div class="col-md-2">
                        <div class="col-md-12">
                            <button type="button" class="btn btn-primary">${userName}</button>
                        </div>
                        <div class="col-md-12">
                            <button type="button" class="btn btn-primary">Log out</button>
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
