package it.polito.tdp.formulaone.model;

import java.time.Duration;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		NUOVO_GIRO,
		FINE_GIRO,
	}
	
	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}


	public Duration getMilliseconds() {
		return milliseconds;
	}


	public void setMilliseconds(Duration milliseconds) {
		this.milliseconds = milliseconds;
	}


	public int getGiro() {
		return giro;
	}


	public void setGiro(int giro) {
		this.giro = giro;
	}


	public Integer getDriver() {
		return driver;
	}


	public void setDriver(Integer driver) {
		this.driver = driver;
	}


	EventType type;
	Duration milliseconds;
	int giro;
	Integer driver;
	
	
	@Override
	public int compareTo(Event o) {
		return this.milliseconds.compareTo(o.milliseconds);
	}


	public Event(EventType type, Duration milliseconds, int giro, Integer driver) {
		super();
		this.type = type;
		this.milliseconds = milliseconds;
		this.giro = giro;
		this.driver = driver;
	}
	

}
