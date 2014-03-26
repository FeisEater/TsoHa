<%-- 
    Document   : question
    Created on : Mar 21, 2014, 8:57:18 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Question title here">
    <div class="container">
        <div class="row">
            <div class="col-md-12"><br></div>
            <div class="col-md-10">
                <div class="col-md-1">
                    <img src="defavatar.png" alt="avatar" height="80">
                    <div class="caption">
                        <a href="accquestions" type="button" class="btn btn-link text-left">User123</a>
                    </div>
                </div>
                <div class="col-md-offset-1 col-md-8">
                    <h1>HELP! Code doesn't compile!</h1>
                </div>
                <div class="col-md-2">
                    <a href="answer" type="button" class="btn btn-primary btn-lg">Give an answer</a>
                    <div class="col-md-12"><br></div>
                    <button type="button" class="btn btn-default pull-left btn-xs">Flag as inappropriate <span class="glyphicon glyphicon-flag"></span></button>
                </div>
                <div class="col-md-12">
                    <p>Please answer as soon as you can! I have a deadline tomorrow!</p>
                </div>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Answer</th>
                            <th>Rating</th>
                            <th>Mark as useful</th>
                            <th>Flag answer as inappropriate</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td width="10%">
                                <img src="defavatar.png" alt="avatar" height="64">
                                <div class="caption">
                                    <a href="accquestions" type="button" class="btn btn-link text-left">FeisEater</a>
                                </div>
                            </td>
                            <td width="70%">Talk to your parents.</td>
                            <td width="10%">72</td>
                            <td width="5%"><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-up"></span></button></td>
                            <td width="5%"><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-flag"></span></button></td>
                        </tr>
                        <tr>
                            <td>
                                <img src="defavatar.png" alt="avatar" height="64">
                                <div class="caption">
                                    <a href="accquestions" type="button" class="btn btn-link text-left">NotSureIfTrolling</a>
                                </div>
                            </td>
                            <td>Push the compile button.</td>
                            <td>14</td>
                            <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-up"></span></button></td>
                            <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-flag"></span></button></td>
                        </tr>
                        <tr>
                            <td>
                                <img src="defavatar.png" alt="avatar" height="64">
                                <div class="caption">
                                    <a href="accquestions" type="button" class="btn btn-link text-left">Jerk69</a>
                                </div>
                            </td>
                            <td>Give up!</td>
                            <td>0</td>
                            <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-up"></span></button></td>
                            <td><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-flag"></span></button></td>
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
            <div class="col-md-2">
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <label class="col-md-12 control-label">Tags:</label>
                <div class="btn-group-vertical">
                    <a href="index" type="button" class="btn btn-xs btn-info">compiler</a>
                    <a href="index" type="button" class="btn btn-xs btn-info">code</a>
                    <a href="index" type="button" class="btn btn-xs btn-info">urgent</a>
                    <a href="index" type="button" class="btn btn-xs btn-info">programming</a>
                    <a href="index" type="button" class="btn btn-xs btn-info">free</a>
                    <a href="index" type="button" class="btn btn-xs btn-info">money</a>
                    <a href="index" type="button" class="btn btn-xs btn-info">tag</a>
                </div>
            </div>
        </div>
    </div>
</t:base>