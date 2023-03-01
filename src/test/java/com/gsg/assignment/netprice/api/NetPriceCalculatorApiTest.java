package com.gsg.assignment.netprice.api;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gsg.assignment.netprice.exception.CountryNotFoundException;
import com.gsg.assignment.netprice.service.NetPriceCalculatorService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.ErrorResponseException;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = NetPriceCalculatorApi.class)
class NetPriceCalculatorApiTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private NetPriceCalculatorService calculatorService;

  @Test
  void shouldReturnNetPrice() throws Exception {
    BigDecimal grossPrice = new BigDecimal("100.00");
    BigDecimal taxRate = new BigDecimal("0.10");
    BigDecimal expectedNetPrice = new BigDecimal("90.91");

    when(calculatorService.calculateNetPrice(grossPrice, "AT")).thenReturn(expectedNetPrice);

    mockMvc.perform(get("/netprice")
            .param("grossPrice", grossPrice.toString())
            .param("countryIso", "AT"))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedNetPrice.toString()));
  }

  @Test
  void shouldReturnBadRequestIfGrossPriceIsMissing() throws Exception {
    mockMvc.perform(get("/netprice")
            .param("countryIso", "US"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestIfCountryIsoIsMissing() throws Exception {
    mockMvc.perform(get("/netprice")
            .param("grossPrice", "100.00"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestIfCountryIsoIsInvalid() throws Exception {
    BigDecimal grossPrice = new BigDecimal("100.00");

    when(calculatorService.calculateNetPrice(grossPrice, "XX"))
        .thenThrow(new CountryNotFoundException("Invalid country code: XX"));

    mockMvc.perform(get("/netprice")
            .param("grossPrice", grossPrice.toString())
            .param("countryIso", "XX"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnInternalServerErrorIfServiceThrowsException() throws Exception {
    BigDecimal grossPrice = new BigDecimal("100.00");

    when(calculatorService.calculateNetPrice(grossPrice, "AT")).thenThrow(new ErrorResponseException(INTERNAL_SERVER_ERROR));

    mockMvc.perform(get("/netprice")
            .param("grossPrice", grossPrice.toString())
            .param("countryIso", "AT"))
        .andExpect(status().isInternalServerError());
  }
}
