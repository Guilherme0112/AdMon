// Adcionar a input automaticamente ao selecionar com checkbox

if (document.getElementById("checkJustForMonthNow")) {
    const checkInput = document.getElementById("checkJustForMonthNow");

    checkInput.addEventListener("change", function () {
        if (this.checked) {
            document.getElementById("div-check").style.display = "block";
        } else {
            document.getElementById("div-check").style.display = "none";

        }
    })
}

// Validação da senha em perfil/perfil.html

if (document.querySelector("#formSenha")) {

    const btnSubmit = document.querySelector("#submit");

    btnSubmit.addEventListener("click", function(event){
        
        var senha_1 = document.getElementById("senhaNova").value;
        var senha_2 = document.getElementById("repeteSenha").value;
        
        if(senha_1 != senha_2){
            event.preventDefault();
            document.getElementById("err").textContent = "As senhas não coencidem";
        } else {
            document.getElementById("err").textContent = "";
        }
    })
}