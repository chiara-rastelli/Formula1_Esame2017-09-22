package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private Button btnSelezionaStagione;

    @FXML
    private ComboBox<Race> boxGara;

    @FXML
    private Button btnSimulaGara;

    @FXML
    private TextField textInputK;

    @FXML
    private TextField textInputK1;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	this.txtResult.clear();
    	if (this.boxAnno.getValue()==null) {
    		this.txtResult.appendText("Devi prima selezionare un anno e creare un grafo!\n");
    		return;
    	}
    	this.model.creaGrafo(this.boxAnno.getValue());
    	this.boxGara.getItems().addAll(this.model.getRacesGrafo());
    	List<Adiacenza> list = model.getArcoPesoMassimo(this.boxAnno.getValue());
    	if (list.size() == 0) {
    		this.txtResult.appendText("Mi dispiace, non ci sono archi!\n");
    		return;
    	}
		if (list.size() == 1) {
			this.txtResult.appendText("Ecco l'arco di peso massimo: \n");
			this.txtResult.appendText(list.get(0).toString());
			return;
		}
		for (Adiacenza a : list)
			this.txtResult.appendText(a.toString()+"\n");
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	int secondiPitStop = Integer.parseInt(this.textInputK1.getText());
    	Double probabilitaPitStop = Double.parseDouble(this.textInputK.getText());
    	Race r = this.boxGara.getValue();
    	Map<Integer, Integer> mappaPuntiSimulazione = new HashMap<>(this.model.simula(probabilitaPitStop, secondiPitStop, r));
    	for (int i : mappaPuntiSimulazione.keySet())
    		System.out.println("Driver "+i+" --> punti "+mappaPuntiSimulazione.get(r)+"\n");
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getAllYears());
	}
}
