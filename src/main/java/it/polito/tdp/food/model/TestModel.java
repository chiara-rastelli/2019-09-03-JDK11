package it.polito.tdp.food.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo(100);
		
	//	for(Adiacenza a : model.getCorrelati("teaspoon"))
	//		System.out.println("Teaspoon e' correlato al tipo di porzione '"+a.getS2()+"' con peso "+a.getPeso()+"\n");

		List<String> camminoRicorsione = model.cercaCammino(15, "teaspoon");
		if (camminoRicorsione == null)
			System.out.println("Spiacente, non e' stato possibile trovare un cammino con queste dimensioni, prova con un n piu' piccolo\n");
		else {
		for (String s : camminoRicorsione)
			System.out.println(s+"\n");
		System.out.println("Peso del percorso: "+model.pesoMassimo);
		}
	}

}
