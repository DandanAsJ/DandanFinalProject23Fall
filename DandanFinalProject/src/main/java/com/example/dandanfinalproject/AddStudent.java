package com.example.dandanfinalproject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class AddStudent implements popWindow {

    private TextField tfFirstName = new TextField();

    private TextField tfLastName = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfWeight = new TextField();
    private TextField tfAllergy = new TextField();

    private ArrayList<Label> labels = new ArrayList<Label>(Arrays.asList(
            new Label("First name: "), new Label("Last name: "),
            new Label("Address: "), new Label("Weight: "),
            new Label("Allergy*: ")));

    private Button add = new Button("Add");
    private Button clear = new Button("Clear");
    private Button cancel = new Button("Cancel");

    private GridPane gridPane = new GridPane();
    private VBox vbox = new VBox();
    private HBox hbox = new HBox();
    private HBox hbox2 = new HBox();
    private Label info = new Label("*answer true if student has any allergy, otherwise answer false");

    private Label title = new Label("Add a new student");


    /**
     * Create a default constructor
     */
    public AddStudent(Stage primaryStage, Statement stm,
                      ObservableList<Student> data, int[] startingPoint, ArrayList<Student> students) {

        title.setStyle("-fx-font-family:Arial;-fx-font-size:16;");



        /**Set the text fields properties  */
        tfFirstName.setStyle("-fx-text-box-border:skyblue;");
        tfFirstName.setPrefWidth(200);
        tfLastName.setStyle("-fx-text-box-border:skyblue;");
        tfAddress.setStyle("-fx-text-box-border:skyblue;");
        tfAddress.setPrefWidth(200);
        tfWeight.setStyle("-fx-text-box-border:skyblue;");
        tfAllergy.setStyle("-fx-text-box-border:skyblue;");

        info.setStyle("-fx-font-style:italic");

        /**Set the label properties */
        for (int i = 0; i < labels.size(); i++) {
            labels.get(i).setStyle("-fx-text-fill:black;" + "-fx-font-family:Arial;" + "-fx-font-size:15;");
        }

        /**Set the button properties*/
        add.setPrefWidth(110);
        add.setStyle("-fx-background-color:orange;");
        clear.setPrefWidth(110);
        clear.setStyle("-fx-background-color:aqua;");
        cancel.setPrefWidth(110);
        cancel.setStyle("-fx-background-color:pink;");

        /**Set the grid pane properties */
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(labels.get(0), 0, 0);
        gridPane.add(tfFirstName, 1, 0);
        gridPane.add(labels.get(1), 0, 1);
        gridPane.add(tfLastName, 1, 1);
        gridPane.add(labels.get(2), 0, 2);
        gridPane.add(tfAddress, 1, 2);
        gridPane.add(labels.get(3), 0, 3);
        gridPane.add(tfWeight, 1, 3);
        gridPane.add(labels.get(4), 0, 4);
        gridPane.add(tfAllergy, 1, 4);

        gridPane.add(hbox2, 0, 5);

        gridPane.add(hbox, 0, 6);


        /**Set the HBox properties */
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(add, clear, cancel);

        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(20);
        hbox2.getChildren().add(info);


        /**Set the VBox properties */
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(title, gridPane, hbox2, hbox);

        /** Create a Scene */
        Scene scene = new Scene(vbox, 450, 350);
        final Stage mainStage = new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);

        mainStage.setScene(scene);
        mainStage.setTitle("Add");
        mainStage.show();


        /**Register and handle the event fired by the clear button */
        clear.setOnAction(e -> {
            clearTextFields();
        });


        /**Register and handle the event fires by the cancel button */
        cancel.setOnAction(e -> {
            mainStage.close();
        });

        /**Register and handle the event fires by the add button */
        add.setOnAction(e -> {
            try {
                addNewStudent(primaryStage, stm, data, startingPoint, students);
            } catch (Exception exception) {
                popErrorMessage(mainStage, exception.getMessage());
            }

        });

        /**Enter Key */
        vbox.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    addNewStudent(primaryStage, stm, data, startingPoint, students);
                } catch (Exception exception) {
                    popErrorMessage(mainStage, exception.getMessage());
                }
            }
        });

    }


    /**
     * Clear text fields
     */
    public void clearTextFields() {
        tfFirstName.clear();

        tfLastName.clear();
        tfAddress.clear();
        tfWeight.clear();
        tfAllergy.clear();
    }


    /**
     * Add a new student
     */
    public void addNewStudent(Stage stage, Statement stm, ObservableList<Student> data, int[] startingPoint, ArrayList<Student> students) throws Exception {
        if (tfFirstName.getText().equals("")) {
            tfFirstName.requestFocus();
        } else if (tfLastName.getText().equals("")) {
            tfLastName.requestFocus();
        } else if (tfAddress.getText().equals("")) {
            tfAddress.requestFocus();
        } else if (tfWeight.getText().equals("")) {
            tfWeight.requestFocus();
        } else if (tfAllergy.getText().equals("")) {
            tfAllergy.requestFocus();
        } else {

            String query = "insert into students (firstname, lastname, address, weight, allergy) values "
                    + "('" + tfFirstName.getText() + "','" + tfLastName.getText() + "','" + tfAddress.getText() + "'," +
                    " '" + tfWeight.getText() + "', '" + tfAllergy.getText() + "' )";
            stm.executeUpdate(query);

            clearTextFields();

            infoDialog(stage, "Record was Added Successfully!!!");

            query = "select * from students where id>'" + startingPoint[0] + "'";
            ResultSet resultSet = stm.executeQuery(query);

            while (resultSet.next()) {
                data.add(new Student(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("address"),
                        resultSet.getDouble("weight"),
                        Boolean.parseBoolean(resultSet.getString("allergy"))
                ));

                students.add(new Student(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("address"),
                        resultSet.getDouble("weight"),
                        Boolean.parseBoolean(resultSet.getString("allergy"))
                ));

                startingPoint[0] = Integer.parseInt(resultSet.getString(1));

            }
        }
    }
}


