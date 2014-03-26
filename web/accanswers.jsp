<%-- 
    Document   : accanswers
    Created on : Mar 21, 2014, 1:16:26 AM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="User's answers">
    <t:accbase page="1">
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
                    <td width="5%"><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                    <td width="5%"><a href="answer.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
                </tr>
                <tr>
                    <td>Talk to your parents.</td>
                    <td>HELP! Code doesn't compile!</td>
                    <td>71</td>
                    <td><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                    <td><a href="answer.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
                </tr>
                <tr>
                    <td>Talk to your parents.</td>
                    <td>Parents won't talk to me.</td>
                    <td>1337</td>
                    <td><a href="question.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                    <td><a href="answer.jsp" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-pencil"></span></a></td>
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
    </t:accbase>
</t:base>