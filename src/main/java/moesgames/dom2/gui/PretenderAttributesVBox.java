package moesgames.dom2.gui;


import java.util.Map.Entry;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import moesgames.dom2.models.MagicPath;
import moesgames.dom2.models.PretenderConfiguration;

public class PretenderAttributesVBox extends VBox{

	    public PretenderAttributesVBox(PretenderConfiguration pc) {

	    	Label points = new Label("Design Points: "+30);
	    	getChildren().add(points);
	    	for (Entry<MagicPath, Integer> entry : pc.getPaths().getMagic_paths().entrySet()) {
	    	    MagicPath key = entry.getKey();
	    	    Integer value = entry.getValue();
	    	    System.out.println(key + " -> " + value);
	    	    Spinner<Integer> spinner = new Spinner<>();
		        spinner.setValueFactory(
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0)
		        );

		        spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
		        	if (oldVal < newVal) {
						pc.getPaths().increase(pc, key);
					}else {
						pc.getPaths().decrease(pc, key);
					}		    
		        	points.setText("Design Points: "+pc.getPoints());
		           	spinner.getValueFactory().setValue(pc.getPaths().getMagic_paths().get(key));
		        }); 
		        Label label = new Label(key.toString());
		        HBox box = new HBox(10);
		        box.getChildren().addAll(label,spinner);
		        getChildren().add(box);
	    	}


	        setSpacing(5);
	        setAlignment(Pos.CENTER);
	        
	    }

}
