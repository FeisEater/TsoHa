<%-- 
    Document   : index
    Created on : 14.3.2014, 1:49:19
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="QuesAns">
    <div class="container-fluid" style="background-color:#DDFFDD">
        <br>
        <div class="row">
            <div class="col-md-offset-1 col-md-5">
                <p>Ask a question! Answer a question!</p>
            </div>
            <div class="col-md-6">
                <a href="ask" type="button" class="btn btn-primary btn-lg">Ask a question!</a>
            </div>
            <div class="col-md-12"><br></div>
            <div class="col-md-12"><br></div>
            <div class="col-md-offset-6 col-md-5">
                <div class="input-group">
                    <input type="text" class="form-control" id="inputNick" name="search" placeholder="Search...">
                    <span class="input-group-btn">
                        <button type="button" class="btn btn-default pull-left"><span class="glyphicon glyphicon-search"></span></button>
                    </span>
                </div>
            </div>
        </div>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Question</th>
                            <th>Amount of answers</th>
                            <th>Show question</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td width="80%">How to dispose of a body?</td>
                            <td width="15%">4</td>
                            <td width="5%"><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                        </tr>
                        <tr>
                            <td>What's the meaning of life?</td>
                            <td>42</td>
                            <td><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                        </tr>
                        <tr>
                            <td>How do I read?</td>
                            <td>12</td>
                            <td><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                        </tr>
                        <tr>
                            <td>Can I go out tonight?</td>
                            <td>1</td>
                            <td><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                        </tr>
                        <tr>
                            <td>HELP! Code doesn't compile!</td>
                            <td>71</td>
                            <td><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                        </tr>
                        <tr>
                            <td>Parents won't talk to me.</td>
                            <td>1337</td>
                            <td><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
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
</t:base>
