package com.in28minutes.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping(path="/currency-converter/from/{from}/to/{to}/quantity/{quantity}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyConversionBean converCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

//        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate()
//                .getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}",CurrencyConversionBean.class,uriVariables);
//        CurrencyConversionBean response = responseEntity.getBody();

        CurrencyConversionBean response =  new RestTemplate().getForObject("http://localhost:8001/currency-exchange/from/{from}/to/{to}",CurrencyConversionBean.class,uriVariables);

        return new CurrencyConversionBean(response.getId()
                ,from,to,response.getConversionMultiple(),quantity,quantity.multiply(response.getConversionMultiple()),response.getPort());
    }

    @GetMapping(path = "/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyConversionBean converCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversionBean response =  proxy.getExchangeValue(from,to);
        logger.info("############################## Conversion-Service: " + response);
        return new CurrencyConversionBean(response.getId()
                ,from,to,response.getConversionMultiple(),quantity,quantity.multiply(response.getConversionMultiple()),response.getPort());
    }
}
