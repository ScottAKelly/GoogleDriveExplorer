<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Drive Explorer</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" integrity="sha384-uWxY/CJNBR+1zjPWmfnSnVxwRheevXITnMqoEIeG1LJrdI0GlVs/9cVSyPYXdcSF" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">

</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1>Drive Explorer</h1>
            </div>
        </div>

        <hr />

        <div class="row form-group">
            <div class="col-5">
                <input id="search-query" class="form-control" type="text" placeholder="Search for files or folders..." />
                <br />
                <input type="checkbox" id="search-type">
                <label for="search-type">Check this option to search only exact matches</label>
            </div>
            <div class="col-3">
                <span id="file-results-count"></span>
            </div>
        </div>

        <hr />

        <div class="row">
            <div class="col-12">
                <div class="breadcrumbs"></div>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <p class="files"></p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.min.js" integrity="sha384-PsUw7Xwds7x08Ew3exXhqzbhuEYmA2xnwc8BuD6SEr+UmEHlX8/MCltYEodzWA4u" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/rest-drive.js"></script>
</body>
</html>
