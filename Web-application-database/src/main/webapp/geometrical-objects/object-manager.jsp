<%--
  Created by IntelliJ IDEA.
  User: Julia
  Date: 4/16/2021
  Time: 12:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/bootstrap-css.html" %>
    <script src="js/object-manager.js"></script>
</head>
<body>
<div class="container align-content-center">
    <br>
    <h3 class="text-center">Object manager form</h3>
    <hr>

    <div class="card bg-light mx-auto" style="max-width: 600px;">
        <article class="card-body mx-auto" style="max-width: 400px;">
            <h4 class="card-title mt-3 text-center">Object manager</h4>
            <br>
            <form id="product-addition-form" method="POST" action="http://localhost:8080/Web_application_database_war/servlets/products/add">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input
                            type="text"
                            name="name"
                            id="name"
                            placeholder="Name"
                            required
                            class="form-control col">
                </div>
                <div class="form-group">
                    <label for="type">Type</label>
                    <input
                            type="text"
                            name="type"
                            id="type"
                            placeholder="Type"
                            required
                            class="form-control col">
                </div>
                <div class="form-group">
                    <label for="sides">Sides number</label>
                    <input
                            type="number"
                            name="sides"
                            id="sides"
                            placeholder="Sides number"
                            class="form-control col"
                            min="1" max="500">
                </div>
                <div class="form-group">
                    <label for="points">Points in the form (x, y) separated by comma</label>
                    <textarea
                            class="form-control"
                            id="points"
                            rows="3"></textarea>
                </div>

                <div class="form-group">
                    <button type="button" onclick="checkFormInputs().then()" id="product-submit"
                            class="btn btn-primary col">
                        Save
                    </button>
                </div>
            </form>
        </article>
    </div>
</div>
</body>
<%@ include file="/bootstrap-js.html" %>
</html>
