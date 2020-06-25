package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {

	FormulaOneDAO db;
	SimpleWeightedGraph<Race, DefaultWeightedEdge> graph;
	Map<Integer, Race> racesIdMap;
	Boolean grafoCreato;
	Simulator s;
	Map<Integer, SuperPilota> superpilotiMap;
	
	public Model() {
		this.db = new FormulaOneDAO();
		this.racesIdMap = new HashMap<>();
		this.grafoCreato = false;
	}
	
	public Map<Integer, Integer> simula(Double probabilitaPitStop, int secondiPitStop, Race r){
		superpilotiMap = new HashMap<>();
		for (Integer i : this.db.getAllDriversIdByRace(r)) {
			List<Integer> listaTempi = new ArrayList<>(this.db.getInfoGara(r, i));
			this.superpilotiMap.put(i, new SuperPilota(i, listaTempi));
		}
		s = new Simulator(this, secondiPitStop, probabilitaPitStop, r);
		return s.getRisultatiSimulazione();
	}
	
	public int getNumeroLapsRace(Race r){
		return this.db.getNumeroGiri(r);
	}
	
	public void getPunteggiPilota() {
		
	}
	
	public List<LapTime> getAllLapTimesRace(Race r){
		return this.db.getAllLapTimesByRace(r);
	}
	
	public List<Integer> getAllDriversIdRace(Race r){
		return this.db.getAllDriversIdByRace(r);
	}
	
	public List<Integer> getAllYears(){
		return this.db.getAllYears();
	}
	
	public List<Race> getRacesGrafo(){
		List<Race> result = new ArrayList<Race>();
		for(Race r : this.graph.vertexSet())
			result.add(r);
		return result;
	}
	
	public int getLapsRace(Race r) {
		return this.db.getNumeroGiri(r);
	}
	
	public void creaGrafo(int year) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for (Race r : this.db.getAllRacesBySeason(year)) {
			this.graph.addVertex(r);
			this.racesIdMap.put(r.getRaceId(), r);
		}
		System.out.println("Grafo creato con "+this.graph.vertexSet().size()+" vertici\n");
		
		for (Adiacenza a : this.db.getAllAdiacenze(year, racesIdMap))
			Graphs.addEdgeWithVertices(this.graph, a.getR1(), a.getR2(), a.getPeso());
		System.out.println("Al grafo sono stati aggiunti "+this.graph.edgeSet().size()+" archi\n");
		this.grafoCreato = true;
	}
	
	public List<Adiacenza> getArcoPesoMassimo(int year) {
		List<Adiacenza> archi = new ArrayList<>(this.db.getAllAdiacenze(year, racesIdMap));
		Collections.sort(archi);
		if (archi.size() == 0)
			return null;
		
		int pesoMassimo = archi.get(0).getPeso();
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		for (Adiacenza a : archi)
			if (a.getPeso() == pesoMassimo)
				result.add(a);
		return result;
	}
	
}
