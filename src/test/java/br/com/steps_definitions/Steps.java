package br.com.steps_definitions;

import br.com.service.Service;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

public class Steps {

    Service service;

    @Dado("que estou na aplicação")
    public void que_estou_na_aplicacao() {
        service = new Service();
    }

    @Quando("feito requisicao para criar uma nova atividade")
    public void feito_requisicao_para_criar_uma_nova_atividade() {
        service.postRequest("activities", "activity.json");
    }

    @Quando("feito requisicao para obter dados de uma atividade especifica")
    public void feito_requisicao_para_obter_dados_de_uma_atividade_especifica(DataTable dataTable) {
        service.getRequestWithPathParam("find.activity", dataTable.row(0).get(0), "1");
    }

    @Quando("feito requisicao para obter as atividades")
    public void feito_requisicao_para_obter_as_atividades() {
        service.getRequest("activities");
    }

    @Então("deve ser garantido que o contrato de activity esta em conformidade")
    public void deve_ser_garantido_que_o_contrato_de_activity_esta_em_conformidade() {
        service.checkContractType(Integer.class, "id[0]");
        service.checkContractType(String.class, "title[0]");
        service.checkContractType(String.class, "dueDate[0]");
        service.checkContractType(Boolean.class, "completed[0]");

    }

    @Então("os dados da atividade devem ser apresentados em conformidade")
    public void os_dados_da_atividade_devem_ser_apresentados_em_conformidade(DataTable dataTable) {
        Assert.assertEquals(200, service.getStatusCode());
        Assert.assertEquals(dataTable.row(1).get(0), service.getBody("id"));
        Assert.assertEquals(dataTable.row(1).get(1), service.getBody("title"));
        Assert.assertNotNull(service.getBody("dueDate"));
        Assert.assertEquals(dataTable.row(1).get(2), service.getBody("completed"));

    }

    @Então("deve ser retornado sucesso na criação")
    public void deve_ser_retornado_sucesso_na_criacao(DataTable dataTable) {
        Assert.assertEquals(200, service.getStatusCode());
        Assert.assertEquals(dataTable.row(1).get(0), service.getBody("id"));
        Assert.assertEquals(dataTable.row(1).get(1), service.getBody("title"));
        Assert.assertEquals(dataTable.row(1).get(2), service.getBody("dueDate"));
        Assert.assertEquals(dataTable.row(1).get(3), service.getBody("completed"));
    }
}
