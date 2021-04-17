<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Home</title>
    <%@ include file="/bootstrap-css.html" %>
</head>
<body>
<div class="container" id="container">
    <br>
    <h3 class="text-center"></h3>
    <hr>
    <a type="button" class="btn btn-dark">Load from file</a>
    <a type="button" class="btn btn-dark" href="">Upload to file</a>
    <a type="button" class="btn btn-dark float-right" href="">Add new object</a>
    <hr>
    <table id="geometricalObjects" class="table table-striped">
        <thead class="thead-dark">
        <th>
            ID
        </th>
        <th>
            Name
        </th>
        <th>
            Type
        </th>
        <th>
            Sides number
        </th>
        <th>
            Coordinates
        </th>
        </thead>
    </table>
</div>
</body>
<%@ include file="/bootstrap-js.html" %>
</html>