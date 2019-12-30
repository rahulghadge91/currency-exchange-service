package com.rahul.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
//import org.springframework.cloud.config.environment.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueResposity repository;
	
	double defaultConversion =10;
	
	//@HystrixCommand(fallbackMethod="fallbackRetriveExchangeValue")
	@HystrixCommand(commandKey = "APPLY-DISCOUNT-COMMAND", fallbackMethod = "fallbackRetriveExchangeValue", threadPoolKey = "fallbackRetriveExchangeValue",
			commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") })
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public double retriveExchangeValue(@PathVariable String from,@PathVariable String to) {
		ExchangeValue exchangeValue= repository.findByFromAndTo(from, to);
	
		//exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        //System.out.println(exchangeValue.getCoversionMultiple());
		return exchangeValue.getCoversionMultiple();
	}
		public double fallbackRetriveExchangeValue(String from,String to)
		{
			return defaultConversion;
		}
		
	@PostMapping("/currency-exchange/conversion")
	public ResponseEntity<Object> createCurrencyExchange(@RequestBody ExchangeValue exchangeValue)
	{
		ExchangeValue exchangeValueSave = repository.save(exchangeValue);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(exchangeValueSave.getId()).toUri();
		return ResponseEntity.created(location).build();
	} 
		
	@PutMapping("/currency-exchange/conversion/{id}")
	public ResponseEntity<Object> updateCurrencyExchange(@RequestBody ExchangeValue exchangeValue,@PathVariable Long id)
	{
		Optional<ExchangeValue> exchangeValueOp = repository.findById(id);
		
		if(!exchangeValueOp.isPresent())
			return ResponseEntity.notFound().build();
		
		ExchangeValue exchangeValueSave = repository.save(exchangeValue);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(exchangeValueSave.getId()).toUri();
		return ResponseEntity.created(location).build();
	} 
	

	
}
