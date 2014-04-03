<%-- 
    Document   : ask
    Created on : 21.3.2014, 11:30:15
    Author     : Pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Ask question">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1>Ask your question</h1>
                <form class="form-horizontal" role="form" action="ask" method="POST">
                    <div class="form-group col-md-offset-10 col-md-2">
                        <button type="submit" class="btn btn-primary btn-lg">Ask away!</button>
                    </div>
                    <br>
                    <div class="form-group">
                        <label for="title" class="control-label">Short title for your question. This text will show up in the search results. A good title fits within one line.</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="Question title">
                    </div>
                    <br>
                    <div class="form-group">
                        <label for="tags" class="control-label">Add tags to help your question show up in relevant searches. Separate tags with space.</label>
                        <textarea class="form-control" id="tags" name="tags" spellcheck="false" placeholder="Tags"></textarea>
                    </div>
                    <br>
                    <div class="form-group">
                        <label for="body" class="control-label">Write your overall question here.</label>
                        <textarea class="form-control" rows="20" spellcheck="false" placeholder="Question body"></textarea>
                    </div>
                </form>
                <br>
                <br>
                <br>
                <br>
                <br>

            </div>
        </div>
    </div>
</t:base>