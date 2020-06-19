package it.polito.tdp.food.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo(100);
		
		for(Adiacenza a : model.getCorrelati("teaspoon"))
			System.out.println("Teaspoon e' correlato al tipo di porzione '"+a.getS2()+"' con peso "+a.getPeso()+"\n");

	}

}
