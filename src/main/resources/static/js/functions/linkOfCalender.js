export default function linkOfCalender(){

    //  Ajusta o link para a data atual
    const linkMonthNow = document.getElementById("dataNowMonth");
    const dateNow = new Date();
    linkMonthNow.href = "/calendario/" + (dateNow.getMonth() + 1) + "/" + dateNow.getFullYear();

}