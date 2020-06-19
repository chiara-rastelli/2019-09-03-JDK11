package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao db;
	SimpleWeightedGraph<String, DefaultWeightedEdge> graph;
	
	public Model() {
		this.db = new FoodDao();
	}

	public void creaGrafo(int numeroCalorie) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for (String s : this.db.listTipiDiPorzioneByCaloriesMax(numeroCalorie))
			this.graph.addVertex(s);
		System.out.println("Grafo creato con "+this.graph.vertexSet().size()+" vertici\n");
		
		List<Adiacenza> listaAdiacenze = this.db.listAdiacenze(numeroCalorie);
		
		for (Adiacenza a : listaAdiacenze) {
			Graphs.addEdge(this.graph, a.getS1(), a.getS2(), a.getPeso());
		}
		System.out.println("Al grafo sono stati aggiunti "+this.graph.edgeSet().size()+" archi!\n");
	}
	
	public List<Adiacenza> getCorrelati(String tipoPorzione){
		List<Adiacenza> daRitornare = new ArrayList<Adiacenza>();
		
		for (String s : Graphs.neighborListOf(this.graph, tipoPorzione))
			daRitornare.add(new Adiacenza(tipoPorzione, s, (int)this.graph.getEdgeWeight(this.graph.getEdge(tipoPorzione, s))));
		
		return daRitornare;
	}
	
}
