<%-- 
    Document   : accbase
    Created on : Mar 26, 2014, 8:20:01 PM
    Author     : FeisEater
--%>

<%@tag description="base html code for account pages" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="page"%>

<%-- any content can be specified here e.g.: --%>
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
                <li class="${page == 0 ? 'active' : ''}"><a href="accquestions.jsp">Asked questions</a></li>
                <li class="${page == 1 ? 'active' : ''}"><a href="accanswers.jsp">Given answers</a></li>
                <li class="${page == 2 ? 'active' : ''}"><a href="accsettings.jsp">Change account settings</a></li>
            </ul>
            <jsp:doBody/>
        </div>
    </div>
</div>
