package moesgames.dom2.gui;


import java.util.HashMap;
import java.util.Map.Entry;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import moesgames.dom2.models.MagicPath;
import moesgames.dom2.models.PretenderAttribute;
import moesgames.dom2.models.PretenderConfiguration;
import moesgames.dom2.tools.DomPersistanceManager;

public class MagicPathBuilder extends VBox{
	
	private PretenderConfiguration pc;
	
	private HashMap<Spinner, MagicPath> spinnersMagic = new HashMap<Spinner, MagicPath>();
	private HashMap<Spinner, PretenderAttribute> spinnersAttribute = new HashMap<Spinner, PretenderAttribute>();

	    public MagicPathBuilder(PretenderConfiguration p) {
	    	this.pc = p;
	    	
	    	Label points = new Label("Design Points: "+30);
	    	getChildren().add(points);
	    	
	    	HBox main = new HBox();
	    	
	    	GridPane grid = new GridPane();
	    	grid.setHgap(15);
	    	grid.setVgap(8);
	    	grid.setPadding(new Insets(20));
	    	
	    	VBox box_paths = new VBox();
	    	VBox box_attributes = new VBox();
	    	int row = 0;
	    	for (Entry<MagicPath, Integer> entry : pc.getPaths().getMagic_paths().entrySet()) {
	    	    MagicPath key = entry.getKey();
	    	    Integer value = entry.getValue();
	    	   
	    	    Spinner<Integer> spinner = new Spinner<>();
		        spinner.setValueFactory(
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(key.getBase_value(), key.getBase_value()+9, value)
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
		        spinner.setPrefWidth(50);
		        spinnersMagic.put(spinner,key);
		        grid.add(label, 0, row);
		        grid.add(spinner, 1, row);
		        row++;
	    	}

	    	GridPane grid2 = new GridPane();
	    	grid2.setHgap(15);
	    	grid2.setVgap(8);
	    	grid2.setPadding(new Insets(20));
	    	
	    	row = 0;
	    	for (Entry<PretenderAttribute, Integer> entry : pc.getAttributes().getAttributes().entrySet()) {
	    		PretenderAttribute key = entry.getKey();
	    	    Integer value = entry.getValue();
	    	    Spinner<Integer> spinner = new Spinner<>();
		        spinner.setValueFactory(
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(key.getBase_value(), key.getMax_value(), value)
		        );

		        spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
		        	if (oldVal < newVal) {
						pc.getAttributes().increase(pc, key);
					}else {
						pc.getAttributes().decrease(pc, key);
					}		    
		        	points.setText("Design Points: "+pc.getPoints());
		           	spinner.getValueFactory().setValue(pc.getAttributes().getAttributes().get(key));
		        }); 
		        Label label = new Label(key.toString());
		        spinner.setPrefWidth(50);
		        spinnersAttribute.put(spinner, key);
		        grid2.add(label, 0, row);
		        grid2.add(spinner, 1, row);
		        row++;
	    	}
	    	
	    	main.getChildren().add(grid);
	    	main.getChildren().add(grid2);
	    	
	    	getChildren().add(main);
	        setSpacing(5);
	        setAlignment(Pos.CENTER);
	        
	        TextField nameField = new TextField();
	        nameField.setPromptText("Enter pretender name");
	        nameField.setPrefWidth(250);

	        Button saveButton = new Button("Save");
	        saveButton.setOnAction(e -> {
	            String name = nameField.getText();
	            pc.setName(name);
	            DomPersistanceManager.savePretender(pc);
	            System.out.println("Saved: " + name);
	        });
	        
	        Button loadButton = new Button("Load");
	        loadButton.setOnAction(e -> {
	            String name = nameField.getText();
	            pc = DomPersistanceManager.tryloadPretender(name);
	            reload();
	        });

	        HBox bottomBar = new HBox(10, nameField, saveButton,loadButton);
	        bottomBar.setAlignment(Pos.CENTER_RIGHT);
	        bottomBar.setPadding(new Insets(15, 0, 0, 0));
	        
	        getChildren().add(bottomBar);
	        
	        
	    }

		private void reload() {
			for (Entry<Spinner,PretenderAttribute> entry : spinnersAttribute.entrySet()) {
	    		PretenderAttribute key = entry.getValue();
	    		entry.getKey().getValueFactory().setValue(pc.getAttributes().getAttributes().get(key));
			}
			for (Entry<Spinner,MagicPath> entry : spinnersMagic.entrySet()) {
				MagicPath key = entry.getValue();
	    		entry.getKey().getValueFactory().setValue(pc.getPaths().getMagic_paths().get(key));
			}
			
		}

}
