<%-- 
    Document   : account
    Created on : Mar 19, 2014, 6:45:10 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="User's questions">
    <t:accbase page="0">
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
                    <td width="5%"><a href="question.jsp" type="button" class="btn btn-xs btn-default center-block"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                </tr>
                <tr>
                    <td>What's the meaning of life?</td>
                    <td>42</td>
                    <td><a href="question.jsp" type="button" class="btn btn-xs btn-default center-block"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                </tr>
                <tr>
                    <td>How do I read?</td>
                    <td>12</td>
                    <td><a href="question.jsp" type="button" class="btn btn-xs btn-default center-block"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
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