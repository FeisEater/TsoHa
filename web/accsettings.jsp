<%-- 
    Document   : accsettings
    Created on : Mar 20, 2014, 8:07:25 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Change account settings">
    <t:accbase site="2">
        <div class="col-md-12"><br></div>
        <div class="col-md-12">
            <p>
                You can change individual settings by filling out specific forms.
                <br>
                Changing the password, however, requires two forms (password must be typed twice).
            </p>
        </div>
        <div class="col-md-12"><br></div>
        <form class="form-horizontal" role="form" action="accsettings" method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <label for="inputNick" class="col-md-3 control-label">Change user Name</label>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="inputNick" name="username" placeholder=${loggedIn.name}>
                </div>
            </div>
            <div class="form-group">
                <label for="inputEmail" class="col-md-3 control-label">Change e-mail</label>
                <div class="col-md-6">
                    <input type="email" class="form-control" id="inputEmail" name="email" placeholder=${loggedIn.email}>
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword1" class="col-md-3 control-label">Change password</label>
                <div class="col-md-6">
                    <input type="password" class="form-control" id="inputPassword1" name="password" placeholder="Password">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword2" class="col-md-3 control-label">Type password again</label>
                <div class="col-md-6">
                    <input type="password" class="form-control" id="inputPassword2" name="password2" placeholder="Same password">
                </div>
            </div>
            <div class="form-group">
                <label for="avatar" class="col-md-3 control-label">Change avatar</label>
                <input type="file" name="avatar" id="avatar">
                <p class="help-block">Choose a picture file you have stored locally.</p>
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
            <div class="form-group">
                <div class="col-md-offset-6 col-md-3">
                    <button type="submit" class="btn btn-primary">Confirm changes</button>
                </div>
            </div>
        </form>
    </t:accbase>
</t:base>