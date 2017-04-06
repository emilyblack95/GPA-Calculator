package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.HashSet;

/** 
 * Controller for GPA-Calculator
 * Last modified: 4/06/17
 * @author GPA-Calculator Team
 * This program calculates your GPA based on credits/grade. Also has graph/chart feature. 
 */
public class Controller implements Initializable
{
	@FXML
	private GridPane inputGrid;
	@FXML
	private Text text0;
	@FXML
	private TextField creditsInput1;
	@FXML
	private Text text00;
	@FXML
	private ComboBox<String> gradeInput1;
	@FXML
	private Button removeButton1;
	@FXML
	private Text text1;
	@FXML
	private TextField creditsInput2;
	@FXML
	private Text text11;
	@FXML
	private ComboBox<String> gradeInput2;
	@FXML
	private Button removeButton2;
	@FXML
	private Text text2;
	@FXML
	private TextField creditsInput3;
	@FXML
	private Text text22;
	@FXML
	private ComboBox<String> gradeInput3;
	@FXML
	private Button removeButton3;
	@FXML
	private Text gpaOutput;
	@FXML
	private Text cumGPAOutput;
	@FXML
	private Button calcButton;
	@FXML
	private Button addButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button newButton;
	@FXML
	private AreaChart<String, Double> graph;
	@FXML
	private PieChart pieChart;
	
	private XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
	private ArrayList<TextField> listOfCredits = new ArrayList<TextField>();
	private ArrayList<ComboBox<String>> listOfGrades = new ArrayList<ComboBox<String>>();
	int numberOfRows = 3; 
	int numberOfSemesters = 1; 
	boolean isSaved = false;
	private double totalCreditPoints = 0.0;
	private double totalGradePoints = 0.0;

	//Written by: Elizabeth Nondorf
	@FXML
	private void calculateGPA(ActionEvent e)
	{
		//If any input fields are empty
		for(int i = 0; i < inputGrid.getChildren().size(); i++)
		{
			if(inputGrid.getChildren().get(i) == null)
			{
				try {
					throw new Exception("Field(s) empty. ABORTING.");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		saveButton.setDisable(false);
		
		newButton.setDisable(false);
		
		double numberSemesterHours = 0.0;
		
		double totalClassPoints = 0.0;
		
		double gradeScaled=0.0;
		
		int classHours = 0;
		
		String grade = "";
		
		for(int i = 0; i < numberOfRows; i++)
		{
			//get the row's class hours
			classHours = Integer.parseInt(listOfCredits.get(i).getText());
			
			numberSemesterHours += classHours;
			
			//get the row's grade
			grade = (listOfGrades.get(i).getValue()).toString();
			
			if(grade.equals("A+"))
			{
				gradeScaled = 4.0;
			}
			else if(grade.equals("A"))
			{
				gradeScaled = 4.0;
			}
			else if(grade.equals("A-"))
			{
				gradeScaled = 3.7;
			}
			else if(grade.equals("B+"))
			{
				gradeScaled = 3.3;
			}
			else if(grade.equals("B"))
			{
				gradeScaled = 3.0;
			}
			else if(grade.equals("B-"))
			{
				gradeScaled = 2.7;
			}
			else if(grade.equals("C+"))
			{
				gradeScaled = 2.3;
			}
			else if(grade.equals("C"))
			{
				gradeScaled = 2.0;
			}
			else if(grade.equals("C-"))
			{
				gradeScaled = 1.7;
			}
			else if(grade.equals("D+"))
			{
				gradeScaled = 1.3;
			}
			else if(grade.equals("D"))
			{
				gradeScaled = 1.0;
			}
			else if(grade.equals("D-"))
			{
				gradeScaled = 0.7;
			}
			else if(grade.equals("F"))
			{
				gradeScaled = 0.0;
			}
			
			totalClassPoints += (classHours*gradeScaled);
		}
		
		//Math for cumulativeGPA
		totalGradePoints += totalClassPoints;
		totalCreditPoints += numberSemesterHours;
		cumGPAOutput.setText(Double.toString(Math.round((totalGradePoints/totalCreditPoints) * 100.0) / 100.0));
		
		//Current semesters GPA
		gpaOutput.setText(Double.toString(Math.round(totalClassPoints/numberSemesterHours * 100.0) / 100.0));
	}

	//Written by: Emily Black
	@SuppressWarnings({ "static-access", "unchecked" })
	@FXML
	private void addRow(ActionEvent e)
	{
		saveButton.setDisable(true);
		
		newButton.setDisable(true);
		numberOfRows++;
		
		final Text creditsName = new Text("Credits:");
		creditsName.fontProperty().setValue(new Font(15));
		creditsName.setId("text" + numberOfRows);
		
		final TextField creditsInput4 = new TextField();
		creditsInput4.addEventFilter(KeyEvent.KEY_TYPED , numericalValue(2));
		creditsInput4.setPromptText("Ex: 3");
		creditsInput4.setId("creditsInput" + numberOfRows);
		listOfCredits.add(creditsInput4);
		
		final Text gradeName = new Text("Grade:");
		gradeName.fontProperty().setValue(new Font(15));
		gradeName.setId("text" + numberOfRows + "" + numberOfRows);
		
		final ComboBox<String> gradeInput4 = new ComboBox<String>();
		gradeInput4.getItems().addAll("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F");
		creditsInput4.setId("gradeInput" + numberOfRows);
		listOfGrades.add(gradeInput4);
		
		final Button removeButton4 = new Button("-");
		removeButton4.fontProperty().setValue(new Font(15));
		removeButton4.setId("removeButton" + numberOfRows);
		
		removeButton4.addEventHandler(ActionEvent.ACTION, (event)-> {
			int row = inputGrid.getRowIndex((Node) event.getSource());
			Set<Node> deleteNodes = new HashSet<>(5);
			 
			for (Node child : inputGrid.getChildren()) {
		        	// get index from child
			        Integer rowIndex = GridPane.getRowIndex(child);
		
			        // handle null values for index=0
			        int r = rowIndex == null ? 0 : rowIndex;
		
			        if (r > row) {
			            // decrement rows for rows after the deleted row
			            GridPane.setRowIndex(child, r-1);
			        } else if (r == row) {
			            // collect matching rows for deletion
			            deleteNodes.add(child);
			            child.setManaged(false);
			        }
			}
			
			// remove nodes from row
			inputGrid.getChildren().removeAll(deleteNodes);
			numberOfRows--;
        });
		
		inputGrid.add(creditsName, 0, numberOfRows);
		inputGrid.setHalignment(creditsName, HPos.CENTER);
		inputGrid.setValignment(creditsName, VPos.CENTER);
		inputGrid.setRowIndex(creditsName, numberOfRows);
		
		inputGrid.add(creditsInput4, 1, numberOfRows);
		inputGrid.setHalignment(creditsInput4, HPos.CENTER);
		inputGrid.setValignment(creditsInput4, VPos.CENTER);
		inputGrid.setRowIndex(creditsInput4, numberOfRows);
		
		inputGrid.add(gradeName, 2, numberOfRows);
		inputGrid.setHalignment(gradeName, HPos.CENTER);
		inputGrid.setValignment(gradeName, VPos.CENTER);
		inputGrid.setRowIndex(gradeName, numberOfRows);
		
		inputGrid.add(gradeInput4, 3, numberOfRows);
		inputGrid.setHalignment(gradeInput4, HPos.CENTER);
		inputGrid.setValignment(gradeInput4, VPos.CENTER);
		inputGrid.setRowIndex(gradeInput4, numberOfRows);
		
		inputGrid.add(removeButton4, 4, numberOfRows);
		inputGrid.setHalignment(removeButton4, HPos.CENTER);
		inputGrid.setValignment(removeButton4, VPos.CENTER);
		inputGrid.setRowIndex(removeButton4, numberOfRows);
		
	}

	//Written by: Emily Black
	@SuppressWarnings({ "unchecked" })
	@FXML
	private void saveSemester(ActionEvent e)
	{
		// If we have a gpa to graph
		if(!(gpaOutput.getText().equals(null)))
		{
			series.getData().add(new XYChart.Data<String, Double>("Semester " + numberOfSemesters, Double.parseDouble(gpaOutput.getText())));
			graph.getData().clear(); //clears data so we dont add duplicates
			graph.getData().addAll(series);
			graph.setLegendVisible(false);
			numberOfSemesters++;
			isSaved = true;
		}
	}

	//Written by: Elizabeth Nondorf
	@SuppressWarnings("unchecked")
	@FXML
	private void newSemester(ActionEvent e)
	{
		// save semester & clear elements if we have gpa to save
		if(!(gpaOutput.getText().equals(null)))
		{
			//If we havent saved
			if(!isSaved)
			{
				series.getData().add(new XYChart.Data<String, Double>("Semester " + numberOfSemesters, Double.parseDouble(gpaOutput.getText())));
				graph.getData().clear(); //clears data so we dont add duplicates
				graph.getData().addAll(series);
				graph.setLegendVisible(false);
				numberOfSemesters++;
				isSaved = true;
			}
			if(isSaved)
			{
				// clear grid
				for(int i = numberOfRows; i > 3; i--)
				{
					int row = i;
					Set<Node> deleteNodes = new HashSet<>(5);
					 
					for (Node child : inputGrid.getChildren()) {
				        	// get index from child
					        Integer rowIndex = GridPane.getRowIndex(child);
				
					        // handle null values for index=0
					        int r = rowIndex == null ? 0 : rowIndex;
				
					        if (r > row) {
					            // decrement rows for rows after the deleted row
					            GridPane.setRowIndex(child, r-1);
					        } else if (r == row) {
					            // collect matching rows for deletion
					            deleteNodes.add(child);
					            child.setManaged(false);
					        }
					}
					
					// remove nodes from row
					inputGrid.getChildren().removeAll(deleteNodes);
				}
				
				//Remove extra rows from list
				for(int j = numberOfRows-1; j > 3; j--)
				{
					listOfCredits.remove(listOfCredits.get(j));
					listOfGrades.remove(listOfGrades.get(j));
				}
				
				numberOfRows = 3;
				
				//Clear first 3 rows
				for(int k = 0; k < numberOfRows; k++)
				{
					listOfCredits.get(k).clear();
					listOfGrades.get(k).valueProperty().set(null);
				}	
			}
			
			gpaOutput.setText("");
			isSaved = false;
		}
		else
		{
			try {
				throw new Exception("No GPA to save. ABORTING.");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}

	//Written by: Emily Black
	@FXML
	private void removeRow(ActionEvent event)
	{
		@SuppressWarnings("static-access")
		int row = inputGrid.getRowIndex((Node) event.getSource());
		Set<Node> deleteNodes = new HashSet<>(5);
		 
		for (Node child : inputGrid.getChildren()) {
	        	// get index from child
		        Integer rowIndex = GridPane.getRowIndex(child);
	
		        // handle null values for index=0
		        int r = rowIndex == null ? 0 : rowIndex;
	
		        if (r > row) {
		            // decrement rows for rows after the deleted row
		            GridPane.setRowIndex(child, r-1);
		        } else if (r == row) {
		            // collect matching rows for deletion
		            deleteNodes.add(child);
		            child.setManaged(false);
		        }
		}
		
		// remove nodes from row
		inputGrid.getChildren().removeAll(deleteNodes);
		numberOfRows--;
	}
	
	//Written by: Emily Black, Elizabeth Nondorf
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		saveButton.setDisable(true);
		newButton.setDisable(true);
		
		// Constrains first row	
		inputGrid.setRowIndex(text0, 0);
		inputGrid.setRowIndex(creditsInput1, 0);
		inputGrid.setRowIndex(text00, 0);
		inputGrid.setRowIndex(gradeInput1, 0);
		inputGrid.setRowIndex(removeButton1, 0);
		
		// Constrains second row
		inputGrid.setRowIndex(text1, 1);
		inputGrid.setRowIndex(creditsInput2, 1);
		inputGrid.setRowIndex(text11, 1);
		inputGrid.setRowIndex(gradeInput2, 1);
		inputGrid.setRowIndex(removeButton2, 1);
		
		// Constrains third row
		inputGrid.setRowIndex(text2, 2);
		inputGrid.setRowIndex(creditsInput3, 2);
		inputGrid.setRowIndex(text22, 2);
		inputGrid.setRowIndex(gradeInput3, 2);
		inputGrid.setRowIndex(removeButton3, 2);
		
		listOfCredits.add(creditsInput1);
		gradeInput1.getItems().addAll("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F");
		listOfGrades.add(gradeInput1);
		
		listOfCredits.add(creditsInput2);
		gradeInput2.getItems().addAll("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F");
		listOfGrades.add(gradeInput2);
		
		listOfCredits.add(creditsInput3);
		gradeInput3.getItems().addAll("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F");
		listOfGrades.add(gradeInput3);
		
		creditsInput1.addEventFilter(KeyEvent.KEY_TYPED , numericalValue(2));
		creditsInput2.addEventFilter(KeyEvent.KEY_TYPED , numericalValue(2));
		creditsInput3.addEventFilter(KeyEvent.KEY_TYPED , numericalValue(2));
	}

	//Written by: Emily Black
	@SuppressWarnings("rawtypes")
	private EventHandler numericalValue(int maxLength) {
		return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            TextField txt_TextField = (TextField) e.getSource();                
	            if (txt_TextField.getText().length() >= maxLength) {                    
	                e.consume();
	            }
	            if(e.getCharacter().matches("[0-9]")){ 
	                if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
	                    e.consume();
	                }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
	                    e.consume(); 
	                }
	            }else{
	                e.consume();
	            }
	        }
	    };
	}

}