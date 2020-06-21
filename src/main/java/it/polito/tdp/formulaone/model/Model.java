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
	
	public Model() {
		this.db = new FormulaOneDAO();
		this.racesIdMap = new HashMap<>();
	}
	
	public List<Integer> getAllYears(){
		return this.db.getAllYears();
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
