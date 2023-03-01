package com.gsg.assignment.netprice.provider.impl;

import static com.gsg.assignment.netprice.util.ResourceUtil.transformTaxRateJsonToMap;

import com.gsg.assignment.netprice.provider.TaxRateProvider;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MapTaxRateProvider implements TaxRateProvider {

  private final Map<String, BigDecimal> taxRateMap;

  public MapTaxRateProvider(@Value("classpath:vat.json") Resource vatJson) {
    this.taxRateMap = new ConcurrentHashMap<>(transformTaxRateJsonToMap(vatJson));
  }

  @Override
  public BigDecimal getTaxRate(String countryIso) {
    return taxRateMap.getOrDefault(countryIso, BigDecimal.ZERO);
  }
}
