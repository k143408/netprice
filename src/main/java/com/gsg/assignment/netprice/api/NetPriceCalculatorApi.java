package com.gsg.assignment.netprice.api;

import com.gsg.assignment.netprice.service.NetPriceCalculatorService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetPriceCalculatorApi {
  private final NetPriceCalculatorService netPriceCalculatorService;

  public NetPriceCalculatorApi(NetPriceCalculatorService calculatorService) {
    this.netPriceCalculatorService = calculatorService;
  }

  @GetMapping("/netprice")
  public BigDecimal calculateNetPrice(@RequestParam BigDecimal grossPrice, @RequestParam String countryIso) {
    return netPriceCalculatorService.calculateNetPrice(grossPrice, countryIso);
  }
}
