// Animação de load, iniciado assim que a página é renderizada
document.getElementById('load_1').style.display = "block";
document.getElementById('load_2').style.display = "block";
document.getElementById('load_3').style.display = "block";

// Dado 1
fetch("/dashboard_grafico_1")
    .then(response => response.json())
    .then(resposta => {
        // console.log(resposta)

        // Renderiza o gráfico com os dados do endpoint
        google.charts.load("current", { packages: ['corechart'] });
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {

            // Valores do gráfico
            var data = google.visualization.arrayToDataTable([
                ["", "Total (R$)", { role: "style" }],
                ["", resposta, "#b87333"],
            ]);

            var view = new google.visualization.DataView(data);
            view.setColumns([0, 1,
                {
                    calc: "stringify",
                    sourceColumn: 1,
                    type: "string",
                    role: "annotation"
                },
                2]);

            // Configurações da div do gráfico
            var options = {
                title: "Ganhos subtraído pelas contas",
                titleTextStyle: {
                    color: "white",
                    fontSize: 25,
                },
                width: 800,
                height: 400,
                bar: { groupWidth: "50%" },
                legend: { position: "none" },
                backgroundColor: "transparent",
                hAxis: {
                    textStyle: {
                        color: "white"
                    }
                },
                vAxis: {
                    format: "R$#,##0.00",
                    textStyle: {
                        color: "white"
                    }
                }
            };
            var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
            chart.draw(view, options);
            document.getElementById('load_1').style.display = "none";
        }
    })

    // Se der erro, retorna a mensagem no  console
    .catch(err => {
        console.log(err)
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
            dados.push(index)

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

            index = [element.ganho, element.valor]
            dados.push(index)
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

