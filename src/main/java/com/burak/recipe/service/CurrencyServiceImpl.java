package com.burak.recipe.service;

import com.burak.recipe.exception.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Override
    public Double findCurrencyRate(String currency) {
        httpHeaders.add("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com");
        httpHeaders.add("X-RapidAPI-Key", "e1c2b927e7msh1cc785e23cbb81cp190d42jsn0bea88281d7a");
        HttpEntity<Double> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            String url = "https://currency-exchange.p.rapidapi.com/exchange?from=USD&to=" + currency +"&q=1.0";
            ResponseEntity<String> responseCurrencyRate = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            return Double.valueOf(responseCurrencyRate.getBody());
        } catch (Exception e) {
            throw new BadRequestException("Invalid currency! Please select a currency from the list:" +
                    "SGD,MYR,EUR,USD,AUD,JPY,CNH,HKD,CAD,INR,DKK,GBP,RUB,NZD,MXN,IDR,TWD,THB,VND");
        }
    }
}
