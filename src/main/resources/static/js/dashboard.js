import last4Months from "./functions/lastFourMonths.js";

// Função que busca os dados
async function fetchData(url) {
    try {

        const response = await fetch(url);

        // Caso houer erros
        if (!response.ok) {
            throw new Error("Erro: " + response.status + " | " + response.statusText);
        }

        // Converte para JSON
        const data = await response.json();

        return data;

    } catch (e) {

        console.error("Erro ao buscar dados: " + e);

    }
}

// Exibe o total, os ganhos subtraído pelas contas
fetchData("/dashboard_grafico_1")
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


// Dado 2
fetchData("/dashboard_rosca_contas")
    .then(resposta => {

        if (resposta && resposta.length == 0) {

            document.getElementById("donutchart").classList = "no-data";
            document.getElementById('donutchart').innerHTML = "Sem dados por agora";
            document.getElementById('load_2').style.display = "none";

            return;
        }

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
                height: 450,
                pieHole: 0.4,
                backgroundColor: "#81f4c3",
                titleTextStyle: {
                    color: "#0fa868",
                    fontSize: 30,
                },
                legend: {
                    textStyle: {
                        color: '#06613b'
                    }
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
            chart.draw(data, options);

        }

        document.getElementById('load_2').style.display = "none";
        document.getElementById('dado_2_box').style.borderRadius = "10px";
        document.getElementById('dado_2_box').style.overflow = "hidden";
    });



// Dado 3
fetchData("/dashboard_rosca_ganhos")
    .then(resposta => {

        // Se não houver dados
        if (resposta && resposta.length == 0) {

            document.getElementById("donutchart_2").classList = "no-data";
            document.getElementById('donutchart_2').innerHTML = "Sem dados por agora";
            document.getElementById('load_3').style.display = "none";

            return;
        }

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
                width: 1000,
                height: 450,
                pieHole: 0.4,
                backgroundColor: "#81f4c3",
                titleTextStyle: {
                    color: "#0fa868",
                    fontSize: 30,
                },
                legend: {
                    textStyle: {
                        color: '#06613b'
                    }
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('donutchart_2'));
            chart.draw(data, options);

            document.getElementById('load_3').style.display = "none";
            document.getElementById('dado_3_box').style.borderRadius = "10px";
            document.getElementById('dado_3_box').style.overflow = "hidden";

        }
    })




// Gráfico de montanha russa
fetchData("/grafico_montanha_russa")
    .then(dados => {
        if (dados) {

            const meses = last4Months();
            var dadosConvertidos = dados.map(dado => parseInt(dado));

            google.charts.load('current', { 'packages': ['corechart'] });
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                var data = google.visualization.arrayToDataTable([
                    ['Mês', 'Saldo'],
                    [meses[3], dadosConvertidos[3]],
                    [meses[2], dadosConvertidos[2]],
                    [meses[1], dadosConvertidos[1]],
                    [meses[0], dadosConvertidos[0]]
                ]);

                var options = {
                    title: 'Últimos 4 meses',
                    backgroundColor: "#81f4c3",
                    width: 1000,
                    height: 450,
                    titleTextStyle: {
                        fontSize: 30,
                        color: "#0fa868",
                    },
                    legend: {
                        textStyle: {
                            color: '#06613b'
                        }
                    },
                    hAxis: {
                        title: '',
                        textStyle: {
                            color: '#06613b'
                        }
                    },
                    vAxis: {
                        minValue: 0,
                        textStyle: {
                            color: "#06613b"
                        }
                    }
                };

                var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                chart.draw(data, options);

            }
            document.getElementById('load_4').style.display = "none";
            document.getElementById('chart_div').style.borderRadius = "10px";
            document.getElementById('chart_div').style.overflow = "hidden";

        }
    })

