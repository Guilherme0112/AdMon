const allBtnEdit = document.querySelectorAll('#editar');

allBtnEdit.forEach(btnEdit => {
    btnEdit.addEventListener("click", function(){
        window.location = "/contas/editar/" + btnEdit.getAttribute("data-id");
    })
});

const allBtnExcluir = document.querySelectorAll("#excluir");

allBtnExcluir.forEach(btnExcluir => {
    btnExcluir.addEventListener("click", function(){
        
        const btnId = btnExcluir.getAttribute("data-id");

        try{    

            fetch(`/contas/excluir/${btnId}`, {
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


const allBtnMcl = document.querySelectorAll("#mcp");

allBtnMcl.forEach(btnMcl => {
    btnMcl.addEventListener("click", function(){
        
        const btnId = btnMcl.getAttribute("data-id");

        try{    

            fetch(`/contas/pago/${btnId}`, {

                method: 'POST',

              })
              .then(resposta => {

                location.reload();

              })
              .catch(err => {

                console.error(err)

              })

        } catch (error) {
            console.log(error)
        }

    })
})
