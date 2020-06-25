package it.polito.tdp.formulaone.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		NUOVO_GIRO,
		FINE_GIRO,
	}
	
	Integer tempo;
	SuperPilota superPilota;
	EventType type;
	int lap;

	public Event(Integer tempo, SuperPilota superPilota, EventType type, int lap) {
		super();
		this.tempo = tempo;
		this.superPilota = superPilota;
		this.type = type;
		this.lap = lap;
	}

	@Override
	public int compareTo(Event o) {
		return this.tempo.compareTo(o.tempo);
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public SuperPilota getSuperPilota() {
		return superPilota;
	}

	public void setSuperPilota(SuperPilota superPilota) {
		this.superPilota = superPilota;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public int getLap() {
		return lap;
	}

	public void setLap(int lap) {
		this.lap = lap;
	}
	
}
