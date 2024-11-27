// Dado 1
fetch("/dashboard_grafico_1")
    .then(response => response.json())
    .then(resposta => {
      

        // A cor da fonte fica vermelho caso o valor seja negativo
        var total = document.getElementById("valor");
        resposta > -1 ? total.style.color = "#0fa868" : total.style.color = "#db0000";

        // Formata o valor para BRL
        const numeroFormatado = resposta.toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL'
          });

        //  Ajusta o link para a data atual
        const linkMonthNow = document.getElementById("dataNowMonth");
        const dateNow = new Date();
        linkMonthNow.href = "/calendario/" + (dateNow.getMonth() + 1) + "/" + dateNow.getFullYear();

        // Desativa o load e exibe os dados após o carregamento
        document.getElementById("valor").innerHTML = numeroFormatado;
        document.getElementById('dado_1').style.display = "block";
        document.getElementById('load_1').style.display = "none";
    })

    // Se der erro, retorna a mensagem no  console
    .catch(err => {
        console.log(err);
    })

// Dado 2
fetch("/dashboard_rosca_contas")
    .then(response => response.json())
    .then(resposta => {

        // console.log(resposta)
        // Cria o modelo de array 
        var dados = [["Categoria", "Valor"]]; 
        var index = [];
        resposta.forEach(element => {

            index = [element.conta, element.valor]
            dados.push(index);

        });

        google.charts.load("current", { packages: ["corechart"] });
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {


            // Converter a array para o modelo aceito
            var data = google.visualization.arrayToDataTable(dados);

            // Cofigurações
            var options = {
                title: 'Gráfico das Contas',
                width: 1000,
                height: 400,
                pieHole: 0.4,
                backgroundColor: "transparent",
                titleTextStyle: {
                    color: "white",
                    fontSize: 25,
                },
                legend: {
                    textStyle: {
                        color: 'white'
                    }
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
            chart.draw(data, options);

            document.getElementById('load_2').style.display = "none";
        }

    })

    // Dado 3
    fetch("/dashboard_rosca_ganhos")
    .then(response => response.json())
    .then(resposta => {

        // console.log(resposta)

        // Cria o modelo de array 
        var dados = [["Categoria", "Valor"]]; 
        var index = [];
        resposta.forEach(element => {

            index = [element.ganho, element.valor];
            dados.push(index);
        });


        google.charts.load("current", { packages: ["corechart"] });
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {


            // Converter a array para o modelo aceito
            var data = google.visualization.arrayToDataTable(dados);

            // Cofigurações
            var options = {
                title: 'Gráfico dos Ganhos',
                width: 800,
                height: 400,
                pieHole: 0.4,
                backgroundColor: "transparent",
                titleTextStyle: {
                    color: "white",
                    fontSize: 25,
                },
                legend: {
                    textStyle: {
                        color: 'white'
                    }
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('donutchart_2'));
            chart.draw(data, options);

            document.getElementById('load_3').style.display = "none";
        }

    })

