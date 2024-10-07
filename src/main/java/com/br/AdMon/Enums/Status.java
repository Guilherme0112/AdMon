package com.br.AdMon.Enums;

public enum Status {
    PENDENTE("Pendente"),
    VENCIDO("Vencido"),
    PAGO("Pago");

    private String status;

    private Status(String status){
        this.status = status;
    }
}
