<%-- 
    Document   : signin
    Created on : Mar 21, 2014, 8:43:48 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Log in">
    <div class="container">
        <h1>Log in to the QuesAns</h1>
        <div class="col-md-12"><br></div>
        <form class="form-horizontal" role="form" action="login" method="POST">
            <div class="form-group">
                <label for="inputEmail" class="col-md-2 control-label">Username or e-mail</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" id="inputEmail" name="username" placeholder="Nickname or email" value="${givenName}">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword" class="col-md-2 control-label">Password</label>
                <div class="col-md-8">
                    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password">
                </div>
            </div>
            <!--div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> Muista kirjautuminen
                        </label>
                    </div>
                </div>
            </div-->
            <div class="col-md-12"><br></div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <button type="submit" class="btn btn-primary">Log in</button>
                </div>
            </div>
        </form>
    </div>
</t:base>
