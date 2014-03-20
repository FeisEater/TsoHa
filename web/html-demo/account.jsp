<%-- 
    Document   : account
    Created on : Mar 19, 2014, 6:45:10 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account settings</title>
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href="../css/bootstrap-theme.css" rel="stylesheet">
        <link href="../css/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
          <div class="row">
            <div class="col-md-3">
              <h2>FeisEater</h2>
              <div class="panel panel-default">
                <img src="defavatar.png" class="img-responsive" alt="avatar">
              </div>
            </div>
            <div class="col-md-9">
                <h1>Asked questions</h1>
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Question</th>
                      <th>Amount of answers</th>
                      <th>Visit question page</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>How to dispose of a body?</td>
                      <td>4</td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                    </tr>
                    <tr>
                      <td>What's the meaning of life?</td>
                      <td>42</td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                    </tr>
                    <tr>
                      <td>How do I read?</td>
                      <td>12</td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                    </tr>
                  </tbody>
                </table>
                <h1>User's answers</h1>
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Answer</th>
                      <th>Question</th>
                      <th>Rating</th>
                      <th>Show question</th>
                      <th>Edit answer</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>Talk to your parents.</td>
                      <td>Can I go out tonight?</td>
                      <td>0</td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></button></td>
                    </tr>
                    <tr>
                      <td>Talk to your parents.</td>
                      <td>HELP! Code doesn't compile!</td>
                      <td>71</td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></button></td>
                    </tr>
                    <tr>
                      <td>Talk to your parents.</td>
                      <td>Parent's won't talk to me.</td>
                      <td>1337</td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                      <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></button></td>
                    </tr>
                  </tbody>
                </table>
            </div>
          </div>
        </div>
    </body>
</html>
