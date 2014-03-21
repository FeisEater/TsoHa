<%-- 
    Document   : accsettings
    Created on : Mar 20, 2014, 8:07:25 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change account settings</title>
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href="../css/bootstrap-theme.css" rel="stylesheet">
        <link href="../css/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="container-fluid" style="background-color:#DDDDFF">
            <div class="row">
                <div class="col-md-offset-1 col-md-3">
                    <a href="index.jsp" type="button" class="btn btn-link" style="color:#000; font-size:48px; font-weight:bold">QuesAns</a>
                </div>
                <div class="col-md-offset-4 col-md-2">
                    <img src="defavatar.png" alt="avatar" height="80" align="right">
                </div>
                <div class="col-md-2">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-primary">FeisEater</button>
                    </div>
                    <div class="col-md-12">
                        <button type="button" class="btn btn-primary">Log out</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <h2>FeisEater</h2>
                    <div class="panel panel-default">
                        <img src="defavatar.png" class="img-responsive" alt="avatar">
                    </div>
                    <a href="">myemail@zmail.com</a>
                </div>
                <div class="col-md-9">
                    <ul class="nav nav-pills">
                        <li><a href="accquestions.jsp">Asked questions</a></li>
                        <li><a href="accanswers.jsp">Given answers</a></li>
                        <li class="active"><a href="accsettings.jsp">Change account settings</a></li>
                    </ul>
                    <div class="col-md-12"><br></div>
                    <div class="col-md-12">
                        <p>
                            You can change individual settings by filling out specific forms.
                            <br>
                            Changing the password, however, requires two forms (password must be typed twice).
                        </p>
                    </div>
                    <div class="col-md-12"><br></div>
                    <form class="form-horizontal" role="form" action="account.jsp" method="POST">
                        <div class="form-group">
                            <label for="inputNick" class="col-md-3 control-label">Change user Name</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="inputNick" name="userName" placeholder="FeisEater">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail" class="col-md-3 control-label">Change e-mail</label>
                            <div class="col-md-6">
                                <input type="email" class="form-control" id="inputEmail" name="email" placeholder="myemail@zmail.com">
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
                </div>
            </div>
        </div>
    </body>
</html>
