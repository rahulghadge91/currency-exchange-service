package com.rahul.microservices.currencyexchangeservice;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	
	@Autowired
	private ExchangeValueResposity repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public double retriveExchangeValue(@PathVariable String from,@PathVariable String to) {
		ExchangeValue exchangeValue=null;
		try {
		 exchangeValue= repository.findByFromAndTo(from, to);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
		//exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        //System.out.println(exchangeValue.getCoversionMultiple());
		return exchangeValue.getCoversionMultiple();
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
