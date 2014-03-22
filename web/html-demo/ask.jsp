<%-- 
    Document   : ask
    Created on : 21.3.2014, 11:30:15
    Author     : Pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ask question</title>
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
                <div class="col-md-offset-6 col-md-2">
                    <div class="col-md-12">
                        <a href="signin.jsp" type="button" class="btn btn-primary">Sign in</a>
                    </div>
                    <div class="col-md-12">
                        <a href="register.jsp" type="button" class="btn btn-primary">Sign up</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1>Ask your question</h1>
                    <div class="col-md-offset-10 col-md-2">
                        <a href="question.jsp" type="submit" class="btn btn-primary btn-lg">Ask away!</a>
                    </div>
                    <br>
                    <label for="title" class="control-label">Short title for your question. This text will show up in the search results. A good title fits within one line.</label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="Question title">
                    <br>
                    <label for="tags" class="control-label">Add tags to help your question show up in relevant searches. Separate tags with space.</label>
                    <textarea class="form-control" id="tags" name="tags" spellcheck="false" placeholder="Tags"></textarea>
                    <br>
                    <label for="body" class="control-label">Write your overall question here.</label>
                    <textarea class="form-control" rows="20" spellcheck="false" placeholder="Question body"></textarea>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>

                </div>
            </div>
        </div>
    </body>
</html>
