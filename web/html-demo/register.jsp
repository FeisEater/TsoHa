<%-- 
    Document   : register
    Created on : Mar 19, 2014, 12:40:54 AM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register user</title>
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href="../css/bootstrap-theme.css" rel="stylesheet">
        <link href="../css/main.css" rel="stylesheet">
    </head>
    <body>
      <div class="container">
        <h1>Register to the QuesAns</h1>
        <form class="form-horizontal" role="form" action="muutettunimi.html" method="POST">
          <div class="form-group">
            <label for="inputNick" class="col-md-2 control-label">User Name</label>
            <div class="col-md-10">
              <input type="text" class="form-control" id="inputNick" name="userName" placeholder="User Name">
            </div>
          </div>
        <div class="form-group">
            <label for="inputEmail" class="col-md-2 control-label">E-mail</label>
            <div class="col-md-10">
              <input type="email" class="form-control" id="inputEmail" name="email" placeholder="Email">
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword1" class="col-md-2 control-label">Password</label>
            <div class="col-md-10">
              <input type="password" class="form-control" id="inputPassword1" name="password" placeholder="Password">
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword2" class="col-md-2 control-label">Password again</label>
            <div class="col-md-10">
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
            <div class="col-md-offset-2 col-md-10">
              <button type="submit" class="btn btn-default">Register</button>
            </div>
          </div>
        </form>
      </div>
    </body>
</html>
