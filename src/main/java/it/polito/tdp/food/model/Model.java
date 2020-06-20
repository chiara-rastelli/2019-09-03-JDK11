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
	List<String> bestCammino;
	int n;
	int pesoMassimo;
	
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
	
	public List<String> cercaCammino(int n, String iniziale) {
		this.n = n;
		this.bestCammino = new ArrayList<String>();
		this.pesoMassimo = 0;
		
		List<String> parziale = new ArrayList<String>();
		parziale.add(iniziale);
		this.ricorri(parziale, 0);
		if (this.bestCammino.size() == n)
			return this.bestCammino;
		else
			return null;
	}

	private void ricorri(List<String> parziale, int pesoParziale) {
		
		//caso terminale: ho fatto n passi, se il peso del nuovo cammino (parziale) e' maggiore di quello che 
		//avevo prima, sostituisco
		if (parziale.size() == this.n) {
			if (pesoParziale > this.pesoMassimo) {
				this.bestCammino = new ArrayList<>(parziale);
				this.pesoMassimo = pesoParziale;
			}
		return;
		}
		
		//se no, vado avanti
		for (String s : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
			if (!parziale.contains(s)) {
				DefaultWeightedEdge e = this.graph.getEdge(s, parziale.get(parziale.size()-1));
				parziale.add(s);
				this.ricorri(parziale, (int)this.graph.getEdgeWeight(e)+pesoParziale);
				parziale.remove(parziale.get(parziale.size()-1));
			}
		}
		
	}	
}
