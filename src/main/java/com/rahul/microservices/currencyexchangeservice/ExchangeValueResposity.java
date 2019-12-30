package com.rahul.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueResposity extends JpaRepository<ExchangeValue,Long>{

	ExchangeValue findByFromAndTo(String from, String to);
}
