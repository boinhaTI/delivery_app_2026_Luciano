package br.edu.catolica.customer_ms.enums;

public enum OrderStatus {

    ANALYZE("Em analise"),
    ACCEPTED("Aceito"),
    PRODUCTION("Em producao"),
    REFUSED("Recusado"),
    CANCELED("Cancelado"),
    DELIVERY("Saiu pra entrega"),
    FAILED_TO_SEND("Falha ao enviar pedido")
    ;
    private String descricao;
    OrderStatus(String descricao) {
        this.descricao = descricao;
    }
}
