package com.gsg.assignment.netprice.service;

import java.math.BigDecimal;

public interface NetPriceCalculatorService {

  BigDecimal calculateNetPrice(BigDecimal grossPrice, String countryIso);
}
