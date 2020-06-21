package it.polito.tdp.formulaone.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo(2006);
		
		List<Adiacenza> list = m.getArcoPesoMassimo(2006);
		if (list.size() == 1)
			System.out.println(list.get(0));
		for (Adiacenza a : list)
			System.out.println(a.toString()+"\n");
	}

}
