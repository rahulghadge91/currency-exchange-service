package com.rahul.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="exchange_value")
public class ExchangeValue {

	    @Id
		private Long id;
	    
	    @Column(name="currency_from")
		private String from;
	    
	    @Column(name="currency_to")
		private String to;
	    
	   @Column(name="conversion_multiple")
		private Double conversionMultiple;
		
		private  int port;
		
		public ExchangeValue() {
		}
		public ExchangeValue(Long id, String from, String to, Double conversionMultiple) {
			super();
			this.id = id;
			this.from = from;
			this.to = to;
			this.conversionMultiple = conversionMultiple;
		}


		public Long getId() {
			return id;
		}


		public String getFrom() {
			return from;
		}


		public String getTo() {
			return to;
		}

		public Double getCoversionMultiple() {
			return conversionMultiple;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public void setConversionMultiple(Double conversionMultiple) {
			this.conversionMultiple = conversionMultiple;
		}
		
		
		
}
