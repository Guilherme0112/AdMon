document.addEventListener("DOMContentLoaded", function(){
    document.querySelector('#nav').addEventListener("click", function(){
        document.querySelector('.hide').classList.toggle('show');
    })
})

if(document.getElementById("pergunta-box")){
    const allBoxsPerguntas = document.querySelectorAll("#pergunta-box");

    allBoxsPerguntas.forEach(allBoxP => {
        allBoxP.addEventListener("click", function(){

            if(allBoxP.querySelector("p").style.display == "block"){
                
                allBoxP.querySelector("p").style.display = "none";
            } else if(allBoxP.querySelector("p").style.display == "none"){

                allBoxP.querySelector("p").style.display = "block";
            } else {
                
                allBoxP.querySelector("p").style.display = "block";
            }
        })
    })
}