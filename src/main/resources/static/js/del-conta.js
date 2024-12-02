const del = document.querySelector("#del");
const fechar = document.querySelector("#close");


del.addEventListener("click", function () {
    document.getElementById("confirm-del").style.display = "block";
    document.getElementById("overlay").style.display = "block";

    // Caso confirme
    document.querySelector("#confirm").addEventListener("click", function () {
        fetch("/perfil/deletar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ del: true })
        })
            .then(response => response.json())
            .then(resposta => {
                if (resposta.redirect) {
                    window.location = resposta.redirect;
                }
            })
            .catch(err => {
                console.error("Error: " + err);
            });
    })

    // Caso cancele
    fechar.addEventListener("click", function(){
        document.getElementById("confirm-del").style.display = "none";
        document.getElementById("overlay").style.display = "none";
    })
});


