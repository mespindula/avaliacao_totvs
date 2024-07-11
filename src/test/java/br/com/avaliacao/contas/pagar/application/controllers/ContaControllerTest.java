package br.com.avaliacao.contas.pagar.application.controllers;

import br.com.avaliacao.contas.pagar.domain.data.model.conta.Conta;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaRequest;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaResponse;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.Situacao;
import br.com.avaliacao.contas.pagar.domain.service.ContaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContaControllerTest {

    @MockBean
    ContaServiceImpl contaService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
    }

    @Test
    public void createTestOk() throws Exception {
        ContaRequest request =  new ContaRequest();
        request.setValor(new BigDecimal(100));
        request.setDataVencimento(LocalDate.now());
        request.setDescricao("Teste");

        ContaResponse response = new ContaResponse();
        response.setValor(request.getValor());
        response.setDataVencimento(request.getDataVencimento());
        response.setDescricao(request.getDescricao());

        when(contaService.criar(any(ContaRequest.class))).thenReturn(response);

        mockMvc.perform(post("/contas-pagar/v1/conta")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value(request.getDescricao()))
                .andExpect(jsonPath("$.valor").value(request.getValor()))
                .andExpect(jsonPath("$.dataVencimento").value(request.getDataVencimento().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    @Test
    public void updateTestOk() throws Exception {
        Conta request =  new Conta();
        request.setValor(new BigDecimal(100));
        request.setSituacao(Situacao.LIQUIDADO);
        request.setDataVencimento(LocalDate.now());
        request.setDataPagamento(LocalDate.now());
        request.setDescricao("Teste");

        ContaResponse response = new ContaResponse();
        response.setValor(request.getValor());
        response.setSituacao(request.getSituacao());
        response.setDataVencimento(request.getDataVencimento());
        response.setDataPagamento(request.getDataPagamento());
        response.setDescricao(request.getDescricao());

        when(contaService.atualizar(any(Long.class), any(Conta.class))).thenReturn(response);

        mockMvc.perform(put("/contas-pagar/v1/conta/"+1)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value(request.getDescricao()))
                .andExpect(jsonPath("$.valor").value(request.getValor()))
                .andExpect(jsonPath("$.dataVencimento").value(request.getDataVencimento().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    @Test
    public void updateSituacaoTestOk() throws Exception {

        ContaResponse response = new ContaResponse();
        response.setId(1L);
        response.setSituacao(Situacao.LIQUIDADO);

        when(contaService.atualizar(any(Long.class), any(String.class))).thenReturn(response);

        mockMvc.perform(patch("/contas-pagar/v1/conta/1/LIQUIDADO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.situacao").value(Situacao.LIQUIDADO.name()));
    }


}
