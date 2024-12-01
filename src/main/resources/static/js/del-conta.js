const del = document.querySelector("#del");


del.addEventListener("click", function () {
    fetch("/perfil/deletar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ del: true })
    })
    .then(response => response.json())
    .then(resposta => {
        if(resposta.redirect){
            window.location = resposta.redirect;
        }
    })
    .catch(err => {
        console.error("Error: " + err);
    });
});
