function obterUltimos4meses(){
    const meses = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];

    const hoje = new Date();

    const converterMes = hoje.getMonth();
    const ultimosMeses = [];

    for (let i = 0; i < 4; i++){
        const MesIndex = (converterMes - i + 12) % 12;
        ultimosMeses.push(meses[MesIndex]);
    }

    return ultimosMeses;

}
const ultimos4Meses = obterUltimos4meses();
console.log(ultimos4Meses);

google.charts.load("current", {packages:['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        [ultimos4Meses[0], "Total (R$)", { role: "style" } ],
        [ultimos4Meses[0], 1118.94, "#b87333"],
        [ultimos4Meses[1], 10.49, "silver"],
        [ultimos4Meses[2], 19.30, "gold"],
        [ultimos4Meses[3], -6.45, "color: #e5e4e2"]
      ]);

      var view = new google.visualization.DataView(data);
      view.setColumns([0, 1,
                       { calc: "stringify",
                         sourceColumn: 1,
                         type: "string",
                         role: "annotation" },
                       2]);

      var options = {
        title: "Últimos 5 meses",
        titleTextStyle:{
            color: "white",
            fontSize: 20,
        },
        width: 800,
        height: 400,
        bar: {groupWidth: "95%"},
        legend: { position: "none" },
        backgroundColor: "transparent",
        hAxis:{
            textStyle:{
                color: "white"
            }
        },
        vAxis:{
            format: "R$#,##0.00",
            textStyle:{
                color: "white"
            }
        }
      };
      var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
      chart.draw(view, options);
  }
