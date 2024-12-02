const btnSubmit = document.querySelector("#submit");

btnSubmit.addEventListener("click", function(event){

    var senha = document.getElementById("senha").value;
    var rsenha = document.getElementById("rsenha").value;

    if(senha != rsenha){
        event.preventDefault();
        document.querySelector("#senha-error").textContent = "As senhas não coencidem";
    } else {
        document.querySelector("#senha-error").textContent = "";
    }
})