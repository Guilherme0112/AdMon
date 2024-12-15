export default function last4Months(){

    const dataAtual = new Date();
    const mesAtual = dataAtual.getMonth();

    const meses = [
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    ];


    const last4Months = [
        meses[(mesAtual + 12) % 12],
        meses[(mesAtual - 1 + 12) % 12], 
        meses[(mesAtual - 2 + 12) % 12],
        meses[(mesAtual - 3 + 12) % 12]  
    ];

    return last4Months;
}
