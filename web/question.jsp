<%-- 
    Document   : question
    Created on : Mar 21, 2014, 8:57:18 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cu" uri="/WEB-INF/custom.tld"%>
<t:base pageTitle="${objectFromID.title}">
    <div class="container">
        <div class="row">
            <div class="col-md-12"><br></div>
            <div class="col-md-10">
                <div class="col-md-1">
                    <c:set var="avatar" value="${objectFromID.asker.avatar}"/>
                    <c:if test="${avatar == null}">
                        <img src="defavatar.png" alt="avatar" class="avatar" height="128" width="128">
                    </c:if>
                    <c:if test="${avatar != null}">
                        <img src="data:image/jpg;base64,${avatar}" alt="avatar" class="avatar" height="128" width="128">
                    </c:if>
                    <div class="caption">
                        <c:if test="${objectFromID.asker == null}">
                            <c:if test="${objectFromID.askerBanned == true}">
                                <label><i><font color="red">Banned</font></i></label>
                            </c:if>
                            <c:if test="${objectFromID.askerBanned == false}">
                                <label><i>Guest</i></label>
                            </c:if>
                        </c:if>
                        <c:if test="${objectFromID.asker != null}">
                            <a href="accquestions" type="button" class="btn btn-link text-left">${objectFromID.asker.name}</a>
                        </c:if>
                        <c:if test="${loggedIn.moderator == true}">
                            <a href="modremove?type=user&id=${objectFromID.asker.ID}" type="button" class="btn btn-xs btn-danger">
                                Ban user <span class="glyphicon glyphicon-remove"></span>
                            </a>
                        </c:if>
                        <c:if test="${objectFromID.asker.moderator == true}">
                            <span class="label label-danger">Moderator</span>
                        </c:if>
                        <label>${objectFromID.asked}</label>
                    </div>
                </div>
                <div class="col-md-offset-1 col-md-10">
                    <h1>${objectFromID.title}</h1>
                </div>
                <c:if test="${fn:length(objectFromID.body) > 0}">
                    <div class="col-md-12">
                        <div class="well questiontext">${objectFromID.body}</div>
                    </div>
                </c:if>
                <t:list curUrl="question?id=${objectFromID.ID}&" size="${objectFromID.answerCount}">
                    <c:if test="${fn:length(list) > 0}">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Answer</th>
                                <th>Rating</th>
                                <th>Rate</th>
                                <c:if test="${loggedIn != null}">
                                    <th>Flag</th>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="unit" items="${list}">
                                <tr>
                                    <td width="10%">
                                        <c:set var="avatar" value="${unit.answerer.avatar}"/>
                                        <c:if test="${avatar == null}">
                                            <img src="defavatar.png" alt="avatar" class="avatar" height="80" width="80">
                                        </c:if>
                                        <c:if test="${avatar != null}">
                                            <img src="data:image/jpg;base64,${avatar}" alt="avatar" class="avatar" height="80" width="80">
                                        </c:if>
                                        <div class="caption">
                                            <c:if test="${unit.answerer == null}">
                                                <label><i><font color="red">Banned</font></i></label>
                                            </c:if>
                                            <c:if test="${unit.answerer != null}">
                                                <a href="accquestions" type="button" class="btn btn-link text-left">${unit.answerer.name}</a>
                                            </c:if>
                                            <c:if test="${loggedIn.moderator == true}">
                                                <a href="modremove?type=user&id=${unit.ID}" type="button" class="btn btn-xs btn-danger">
                                                    Ban user <span class="glyphicon glyphicon-remove"></span>
                                                </a>
                                            </c:if>
                                            <c:if test="${unit.answerer.moderator == true}">
                                                <span class="label label-danger">Moderator</span>
                                            </c:if>
                                            <c:if test="${loggedIn.ID == unit.answerer.ID}">
                                                <a href="append?id=${unit.ID}" type="button" class="btn btn-xs btn-primary">Edit answer</a>
                                            </c:if>
                                            <label>${unit.answered}</label>
                                            <c:if test="${loggedIn.moderator == true}">
                                                <a href="modremove?type=ans&id=${unit.ID}" type="button" class="btn btn-xs btn-danger">
                                                    Remove answer <span class="glyphicon glyphicon-remove"></span>
                                                </a>
                                            </c:if>
                                        </div>
                                    </td>
                                    <td width="60%"><p class="answertext">${unit.body}</p></td>
                                    <td width="10%">${unit.rating}</td>
                                    <td width="10%">
                                        <c:if test="${loggedIn != null}">
                                            <cu:Rate loggedIn="${loggedIn}" answer="${unit}" />
                                        </c:if>
                                        <c:if test="${loggedIn == null}">
                                            <cu:Rate rateList="${rated}" answer="${unit}" />
                                        </c:if>
                                    </td>
                                    <c:if test="${loggedIn != null}">
                                        <td width="5%"><a href="flag?type=ans&id=${unit.ID}" type="button" class="btn btn-xs btn-default">
                                            <cu:FlagAnswer loggedIn="${loggedIn}" answer="${unit}" />
                                        </a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </c:if>
                </t:list>
            </div>
            <div class="col-md-2">
                <div class="col-md-12">
                    <c:if test="${loggedIn != null}">
                        <a href="answer?id=${objectFromID.ID}" type="button" class="btn btn-primary btn-lg">Give an answer</a>
                        <div class="col-md-12"><br></div>
                        <a href="flag?type=ques&id=${objectFromID.ID}" type="button" class="btn btn-default pull-left btn-xs">
                            <cu:FlagQuestion loggedIn="${loggedIn}" question="${objectFromID}" />
                        </a>
                        <div class="col-md-12"><br></div>
                        <c:if test="${loggedIn.moderator == true}">
                            <a href="modremove?type=ques&id=${objectFromID.ID}" type="button" class="btn btn-danger">
                                Remove question <span class="glyphicon glyphicon-remove"></span>
                            </a>
                        </c:if>
                    </c:if>
                </div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <c:if test="${fn:length(objectFromID.tags) > 0}">
                    <div align="center">
                        <label class="col-md-12 control-label">Tags:</label>
                        <t:taglist>
                                <c:forEach var="tag" items="${taglist}">
                                    <a href="index?tags=${tag.text}" type="button" class="btn btn-xs btn-info">${tag.text}</a>
                                </c:forEach>
                        </t:taglist>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</t:base>