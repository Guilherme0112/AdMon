// Adcionar a input automaticamente ao selecionar com checkbox

const checkInput = document.getElementById("checkJustForMonthNow");

checkInput.addEventListener("change", function() {
    if(this.checked){
        document.getElementById("div-check").style.display = "block";
    } else {
        document.getElementById("div-check").style.display = "none";

    }
})