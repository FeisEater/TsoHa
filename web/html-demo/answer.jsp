<%-- 
    Document   : answer
    Created on : Mar 22, 2014, 1:36:00 AM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Answer to HELP! Code doesn't compile!</title>
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
            <div class="row" style="background-color:#FFDDFF">
                <div class="col-md-12"><br></div>
                <div class="col-md-1">
                    <img src="defavatar.png" alt="avatar" height="80">
                    <div class="caption">
                        <a href="accquestions.jsp" type="button" class="btn btn-link text-left">User123</a>
                    </div>
                </div>
                <div class="col-md-offset-1 col-md-10">
                    <h1>HELP! Code doesn't compile!</h1>
                </div>
                <div class="col-md-12">
                    <p>Please answer as soon as you can! I have a deadline tomorrow!</p>
                </div>
                <div class="col-md-12" style="background-color:#FFFFFF">
                    <div class="col-md-12"><br></div>
                    <div class="col-md-offset-10 col-md-2">
                        <a href="question.jsp" type="button" class="btn btn-primary btn-lg">Post answer</a>
                    </div>
                    <label class="control-label">Write your answer here.</label>
                    <textarea class="form-control" rows="20" spellcheck="false" placeholder="Your answer"></textarea>
                </div>
            </div>
        </div>
    </body>
</html>
