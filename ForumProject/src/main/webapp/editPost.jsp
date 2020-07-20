<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="include/head.htm" %>
<body>
<%@ include file="include/menu.htm" %>
<div class="container">
        <div class="row">
            <div class=col-md-1>ID</div>
            <div class=col-md-2>Title</div>
            <div class=col-md-2>Content</div>
            <div class=col-md-2>Status</div>
        </div>
    </div>
<div class="container">
        <c:forEach items="${posts}" var="post">
            <form class="update-post-${post.id}" action="do?command=Edit Post" method=POST>
                <div class="row">
                    <div class=col-md-1>
                        <input id="id" class="form-control input-md" name="id"
                               value="${post.id}"/>
                    </div>
                    <div class=col-md-2>
                        <input id="title" class="form-control input-md" name="title"
                               value="${post.title}"/>
                    </div>
                    <div class=col-md-2>
                        <input id="content" class="form-control input-md" name="content"
                               value="${post.content}"/>
                    </div>
                    <div class=col-md-2>
                                  <div class="col-md-2" >${post.status}</div>
                    </div>

                    <button id="update" value="true" name="update" class="btn btn-success  md-1">
                        Update
                    </button>
                    <button id="delete" value="true" name="delete" class="btn btn-danger md-1">
                        Delete
                    </button>
                </div>
            </form>
        </c:forEach>
    </div></body>
</html>
