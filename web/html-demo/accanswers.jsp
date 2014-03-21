<%-- 
    Document   : accanswers
    Created on : Mar 21, 2014, 1:16:26 AM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User's answers</title>
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
                        <li class="active"><a href="accanswers.jsp">Given answers</a></li>
                        <li><a href="accsettings.jsp">Change account settings</a></li>
                    </ul>
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
                                <td width="40%">Talk to your parents.</td>
                                <td width="40%">Can I go out tonight?</td>
                                <td width="10%">0</td>
                                <td width="5%"><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                                <td width="5%"><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></button></td>
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
                                <td>Parents won't talk to me.</td>
                                <td>1337</td>
                                <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></button></td>
                                <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></button></td>
                            </tr>
                        </tbody>
                    </table>
                    <ul class="pagination">
                        <li><a href="#">&laquo;</a></li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">&raquo;</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
