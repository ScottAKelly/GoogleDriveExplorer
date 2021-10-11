const loadData = function (url) {
    $.ajax({
        url: url
    }).then(function (data) {
        if (data.parent) {
            loadBreadCrumbs(data.parent);
        }
        else {
            $('.breadcrumbs').html("");
        }

        loadFileHtml(data.files);

    });
}

const loadChildrenData = function (parentId) {
    var fileId = $(this).data('folder-id');
    if (fileId !== "") {
        var url = "/rest-drive/files/children/" + parentId;
        loadData(url);
    }
}

const loadBreadCrumbs = function (initParent) {
    var breadcrumbs = `Current Directory: <ul>`;
    var atRoot = false;
    var parent = initParent;
    while (!atRoot) {
        if (parent) {
            if (parent.id !== initParent.id) {
                breadcrumbs += "<";
            }
            breadcrumbs += `<li class="folder-select" onclick="loadChildrenData('${parent.id}')">${parent.name}</li>`;
            parent = parent.parent;
        }
        else {
            atRoot = true;
        }
    }
    breadcrumbs += `</ul>`;
    $('.breadcrumbs').html(breadcrumbs);
}

const loadFileHtml = function (files) {
    var html = "";
    if (files.length > 0) {
        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            var clickable = "";

            if (file.mimeType.includes("folder")) {
                clickable = `class="folder-select" onclick="loadChildrenData('${file.id}')"`;
            }

            html += `<div ${clickable}>
                    <img src="${file.iconLink}" /> ${file.name} 
                    </div>
                    <br />
                `;
        }
    }

    $('.files').html(html);
    $('#file-results-count').html(`Showing ${files.length} item(s)`)
}

$(document).ready(function (){
    $('#search-query').on("input", function() {
        var searchQuery = $(this).val();
        var url = "/rest-drive/files";
        if (searchQuery.trim() !== "") {
            var exactMatch = $('#search-type').prop("checked");
            url += "/" + searchQuery + "/" + exactMatch;
        }

        loadData(url);
    });

    $('#search-type').on("change", function() {
        var searchQuery = $('#search-query').val();
        var url = "/rest-drive/files";
        if (searchQuery.trim() !== "") {
            var exactMatch = $('#search-type').prop("checked");
            url += "/" + searchQuery + "/" + exactMatch;
            loadData(url);
        }
    });

    loadData("/rest-drive/files");
});

