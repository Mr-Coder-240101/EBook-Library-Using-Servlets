function loadAllEvents() {
    document.addEventListener("DOMContentLoaded", contentLoadedListener);
}

loadAllEvents();

function contentLoadedListener(event) {
    if(window.location.pathname === "/") {
        var searchParams = new URLSearchParams(decodeURIComponent(window.location.search));
        var option = searchParams.get("option");
        var keyword = searchParams.get("keyword");
        fetch(`http://localhost:8080/books?option=${option}&keyword=${keyword}`, {
           method: 'GET'
        })
        .then(response => response.text())
        .then(result => loadDataIntoPage(JSON.parse(result)))
        .catch(error => console.log('error', error));
    } else {
        var form = document.getElementById("book-upload-form");
        form.addEventListener('submit', fileUploadListener);
        form.elements["book"].addEventListener('change', fileChangeHandler);
    }
}

function loadDataIntoPage(data) {
    if(data.length > 0) {
        document.getElementById("book-table-heading").innerText = "Book Details";

        var table = document.getElementById("book-table");
        var tbody = document.createElement("tbody");

        var trHeader = document.createElement("tr");

        var titleHeader = document.createElement("th");
        titleHeader.innerHTML = "Title";

        var authorHeader = document.createElement("th");
        authorHeader.innerHTML = "Author";

        var pagesHeader = document.createElement("th");
        pagesHeader.innerHTML = "Pages";

        var linkHeader = document.createElement("th");
        linkHeader.innerHTML = "Download";

        trHeader.appendChild(titleHeader);
        trHeader.appendChild(authorHeader);
        trHeader.appendChild(pagesHeader);
        trHeader.appendChild(linkHeader);

        tbody.appendChild(trHeader);

        table.appendChild(tbody);

        data.forEach((item) => {
            var tr = document.createElement("tr");

            var title = document.createElement("td");
            title.innerHTML = item.title;

            var author = document.createElement("td");
            author.innerHTML = item.author;

            var pages = document.createElement("td");
            pages.innerHTML = item.pages;

            var link = document.createElement("td");
            link.innerHTML = "<a href='/book/download?id=" + item.id + "'>Download</a>";

            tr.appendChild(title);
            tr.appendChild(author);
            tr.appendChild(pages);
            tr.appendChild(link);

            tbody.appendChild(tr);
        })
    }
}

function fileUploadListener(event) {
    event.preventDefault();

    var form = document.getElementById("book-upload-form");
    var title = form.elements["title"];
    var author = form.elements["author"];
    var pages = form.elements["pages"];

    var formdata = new FormData();
    formdata.append("title", title.value);
    formdata.append("author", author.value);
    formdata.append("pages", parseInt(pages.value));
    formdata.append("book", file);

    var requestOptions = {
      method: 'POST',
      headers: new Headers({'Content-Transfer-Encoding': 'multipart/form-data'}),
      body: formdata,
      redirect: 'follow'
    };

    fetch("http://localhost:8080/book/upload", requestOptions)
      .then(response => response.text())
      .then(result => console.log(result))
      .catch(error => console.log('error', error));

    alert("File Uploaded Successfully");
    window.location.reload();
}

var file = null;

function fileChangeHandler(event) {
    file = event.target.files[0];
}
