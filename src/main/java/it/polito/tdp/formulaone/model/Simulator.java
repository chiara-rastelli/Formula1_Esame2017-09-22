package it.polito.tdp.formulaone.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.formulaone.model.Event.EventType;

public class Simulator {
	
	List<Integer> listaPilotiGara;
	Map<Integer, Integer> puntiPiloti;
	Map<Integer, Boolean> mappaGiri;
	Map<Integer, Driver> driverIdMap;
	List<LapTime> listaLapTimes;
	PriorityQueue<Event> queue;
	Double probabilitaPitStop;
	int secondiPitStop;
	int millisPitStop;
	Map<String, Integer> durationLaptimes;
	
	public Simulator(List<Integer> listaPilotiGara, Double probabilitaPitStop, int secondiPitStop, int numeroGiri, List<LapTime> listaLapTimes) {
		this.listaPilotiGara = new ArrayList<>(listaPilotiGara);
		this.puntiPiloti = new HashMap<>();
		this.mappaGiri = new HashMap<>();
		for (Integer d : this.listaPilotiGara)
			this.puntiPiloti.put(d, 0);
		for (int i = 1; i <= numeroGiri; i++)
			this.mappaGiri.put(i, false);
		this.probabilitaPitStop = probabilitaPitStop;
		this.secondiPitStop = secondiPitStop;
		this.millisPitStop = this.secondiPitStop * 1000;
		this.queue = new PriorityQueue<Event>();
		this.listaLapTimes = new ArrayList<>(listaLapTimes);
		this.durationLaptimes = new HashMap<>();
		for (LapTime l : this.listaLapTimes) {
			if (l.getLap()==1)
				this.queue.add(new Event(EventType.NUOVO_GIRO, Duration.ofMillis(0), 1, l.getDriverId()));
			this.durationLaptimes.put(""+l.getDriverId()+""+l.getLap(), l.getMiliseconds());
		}
		
	}
	
	// ESECUZIONE
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
			case NUOVO_GIRO:
			if (this.durationLaptimes.containsKey(""+e.getDriver()+""+(e.giro+1)))	{
				if (this.verificaPitStop(e)) {
					int durata = this.durationLaptimes.get(""+e.getDriver()+""+e.giro)+this.millisPitStop;
					this.queue.add(new Event(EventType.FINE_GIRO, e.getMilliseconds().plusMillis(durata),e.giro, e.getDriver()));
				}else {
					int durata = this.durationLaptimes.get(""+e.getDriver()+""+e.giro);
					this.queue.add(new Event(EventType.FINE_GIRO, e.getMilliseconds().plusMillis(durata),e.giro, e.getDriver()));
				}
			}
				break;
			case FINE_GIRO:
				if (!this.mappaGiri.containsKey(e.giro)) {
					this.mappaGiri.put(e.giro, true);
					this.puntiPiloti.put(e.getDriver(), this.puntiPiloti.get(e.driver)+1);
				}
				this.queue.add(new Event(EventType.NUOVO_GIRO, e.getMilliseconds(), e.giro++, e.getDriver()));
				break;
		}
		
	}

	private boolean verificaPitStop(Event e) {
		if (Math.random() < this.probabilitaPitStop) {
			return true;
		}else
			return false;
	}

}
