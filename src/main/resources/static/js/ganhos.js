const allBtnEdit = document.querySelectorAll('#editar');

allBtnEdit.forEach(btnEdit => {
    btnEdit.addEventListener("click", function(){
        window.location = "/ganhos/editar/" + btnEdit.getAttribute("data-id");
    })
});

const allsBtnExcluir = document.querySelectorAll("#excluir");

allsBtnExcluir.forEach(btnExcluir => {
    btnExcluir.addEventListener("click", function(){
        
        const btnId = btnExcluir.getAttribute("data-id");

        try{    

            fetch(`/ganhos/excluir/${btnId}`, {
                method: 'DELETE',
              })
            .then(response => response.json())
            .then(resposta => {

                location.reload();
              
            })
            .catch(err => {

                console.log(err);           
            })


        } catch (error) {
            console.log(error)
        }

    })
})