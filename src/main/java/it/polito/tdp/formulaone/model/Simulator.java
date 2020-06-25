package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.formulaone.model.Event.EventType;

public class Simulator {

	Model m;
	Integer millisecPitStop;
	Double probabilita;
	PriorityQueue<Event> queue;
	Map<Integer, SuperPilota> superPiloti;
	List<SuperPilota> listaSuperPiloti;
	Map<Integer, Boolean> mappaTraguardiLaps;
	Map<Integer, Integer> mappaPunteggiPiloti;
	
	public Simulator(Model m, int millisecPitStop, Double probabilita, Race r) {
		this.m = m;
		this.millisecPitStop = millisecPitStop;
		this.probabilita = probabilita;
		this.queue = new PriorityQueue<>();
		this.mappaTraguardiLaps = new HashMap<>();
		for (int i = 0; i < m.getLapsRace(r); i++)
			this.mappaTraguardiLaps.put(i, false);
		this.superPiloti = m.superpilotiMap;
		this.mappaPunteggiPiloti = new HashMap<>();
		this.listaSuperPiloti = new ArrayList<>(this.superPiloti.values());
		for (SuperPilota s : this.listaSuperPiloti) {
			this.queue.add(new Event(0, s, EventType.NUOVO_GIRO, 0));
		}
		this.run();
		for (SuperPilota s : this.listaSuperPiloti)
			this.mappaPunteggiPiloti.put(s.idPilota, s.punteggio+1);
	}
	
	public Map<Integer, Integer> getRisultatiSimulazione(){
		return this.mappaPunteggiPiloti;
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
			case NUOVO_GIRO:
				int tempoImpiegato = e.getSuperPilota().listaTempi.get(e.lap)+e.getTempo();
				if (this.effettuaPitStop())
					tempoImpiegato += this.millisecPitStop;
				this.queue.add(new Event(tempoImpiegato, e.superPilota, EventType.FINE_GIRO, e.getLap()));
			break;
			case FINE_GIRO:
				if (this.mappaTraguardiLaps.get(e.getLap())== false) {
					e.getSuperPilota().punteggio ++;
					this.mappaTraguardiLaps.put(e.getLap(), true);
				}
				if (e.getSuperPilota().getListaTempi().size() > e.getLap()+1)
					this.queue.add(new Event(e.getTempo(), e.superPilota, EventType.NUOVO_GIRO, e.getLap()+1));
			break;
		}
	}

	private boolean effettuaPitStop() {
		if (Math.random() <= this.probabilita)
			return true;
		else
			return false;
	}
	
}
