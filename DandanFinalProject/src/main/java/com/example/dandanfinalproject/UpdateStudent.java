package com.example.dandanfinalproject;

import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdateStudent implements popWindow{
    int elementPosition=-1;
    /**Create text fields */
    private TextField tfId=new TextField();
    private TextField tfFirstName=new TextField ();
    private TextField tfLastName  =new TextField();
    private TextField tfAddress =new TextField();
    private TextField tfWeight =new TextField ();
    private TextField tfAllergy = new TextField();

    /**Create Labels */
    private ArrayList<Label> labels= new ArrayList<Label>(Arrays.asList( new Label("Id"),
            new Label("First name: "), new Label("Last name: "),
            new Label("Address: "), new Label("Weight: "), new Label("Allergy*: ")));

    private Button update=new Button("Update");
    private Button clear=new Button("Clear");
    private Button cancel =new Button("Cancel");

    private GridPane gridPane =new GridPane();
    private VBox vbox =new VBox();
    private HBox hbox =new HBox();

    private HBox hbox2 = new HBox();
    private Label info = new Label("*answer true if student has any allergy, otherwise answer false");

    private Label title =new Label("Update a student information: ");

    /**Create a default constructor */
    public UpdateStudent(Stage primaryStage, Statement stm, ObservableList<Student> data, int [] startingPoint, ArrayList<Student> students)
    {

        title.setStyle("-fx-font-family:Arial;"+"-fx-font-size:15;");


        /**Set the text fields properties  */
        tfId.setStyle("-fx-background:skyblue;");
        tfFirstName.setStyle("-fx-text-box-border:skyblue;");
        tfFirstName.setPrefWidth(200);
        tfLastName.setStyle("-fx-text-box-border:skyblue;");
        tfAddress.setStyle("-fx-text-box-border:skyblue;");
        tfWeight.setStyle("-fx-text-box-border:skyblue;");
        tfAllergy.setStyle("-fx-text-box-border:skyblue;");

        info.setStyle("-fx-font-style:italic");

        /**Set the label properties */
        for (int i=0; i<labels.size();i++)
        {
            labels.get(i).setStyle("-fx-text-fill:black;"+"-fx-font-family:Arial;"+"-fx-font-size:15;");
        }

        /**Set the button properties*/
        update.setPrefWidth(100);
        update.setStyle("-fx-background-color:lightyellow;");
        clear.setPrefWidth(100);
        clear.setStyle("-fx-background-color:aqua;");
        cancel.setPrefWidth(100);
        cancel.setStyle("-fx-background-color:pink;");

        /**Set the grid pane properties */
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(labels.get(0),0,0);
        gridPane.add(tfId, 1, 0);
        gridPane.add(labels.get(1),0,1);
        gridPane.add(tfFirstName,1,1);
        gridPane.add(labels.get(2),0,2);
        gridPane.add(tfLastName,1,2);
        gridPane.add(labels.get(3),0,3);
        gridPane.add(tfAddress,1,3);
        gridPane.add(labels.get(4),0,4);
        gridPane.add(tfWeight,1,4);
        gridPane.add(labels.get(5),0,5);
        gridPane.add(tfAllergy,1,5);

        gridPane.add(hbox2,0,6);
        gridPane.add(hbox,0,7);



        /**Set the HBox properties */
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(update,clear,cancel);

        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(20);
        hbox2.getChildren().add(info);


        /**Set the VBox properties */
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,15,0,0));
        vbox.getChildren().addAll(title,gridPane,hbox2,hbox);

        /** Create a Scene */
        Scene scene=new Scene(vbox,450,350);
        final Stage mainStage=new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);

        mainStage.setScene(scene);
        mainStage.setTitle("Update");
        mainStage.show();



        /**Register and handle the event fired by the clear button */
        clear.setOnAction(e->{clearTextFields();});


        /**Register and handle the event fires by the cancel button */
        cancel.setOnAction(e->{mainStage.close();});

        /**Register and handle the event fires by the update button */
        update.setOnAction(e->{
            try{

                updateStudent(mainStage,data,stm, students);

            }catch(Exception exception)
            {
                popErrorMessage(mainStage,exception.getMessage());
            }

        });



        /** Register and handle the event fires by enter key */
        tfId.setOnKeyPressed(e->{

            if(e.getCode()== KeyCode.ENTER)
            {
                try{
                    studentInfo(data,mainStage);
                }catch(Exception exception)
                {
                    popErrorMessage(mainStage,exception.getMessage());
                }
            }

        });

    }


    /**Clear text fields */
    public void clearTextFields ()
    {
        tfId.clear ();
        tfFirstName.clear();
        tfLastName.clear();
        tfAddress.clear();
        tfWeight.clear();
        tfAllergy.clear();
    }




    /**studentInfo method */
    public void studentInfo(ObservableList<Student> data, Stage stage) throws Exception
    {

        int exist=0;


        if(tfId.getText().equals(""))
        {
            tfId.requestFocus();
        }
        else
        {
            for(int i=0;i<data.size();i++)
            {
                if(data.get(i).getId()==Integer.parseInt(tfId.getText()))
                {
                    exist=1;
                    elementPosition=i;

                    break;

                }
            }
            if(exist==1)
            {
                tfFirstName.setText(data.get(elementPosition).getFirstName());
                tfLastName.setText(data.get(elementPosition).getLastName());
                tfAddress.setText(data.get(elementPosition).getAddress());
                tfWeight.setText(String.valueOf(data.get(elementPosition).getWeight()));
                tfAllergy.setText(String.valueOf(data.get(elementPosition).getAllergy()));

            }
            else
            {
                popErrorMessage(stage,"Record does not exist.\nPlease Enter Another Id Number!!!");
            }
        }
    }


    /**Update information */

    public void updateStudent(Stage stage, ObservableList<Student> data, Statement stm, ArrayList<Student> students) throws Exception
    {
        int id=Integer.parseInt(tfId.getText());
        String firstName=tfFirstName.getText();
        String lastName=tfLastName.getText();
        String address =tfAddress.getText();
        double weight=Double.parseDouble(tfWeight.getText());
        String allergy = tfAllergy.getText();

        String query="update students set firstname='"+firstName+"', lastname='"+lastName+"', address='"+address+"', weight='"+weight+"'," +
                " allergy = '"+allergy+"' where id='"+id+"'";


        if(tfWeight.getText().equals(""))
        {
            tfId.requestFocus();
        }
        else
        {
            stm.executeUpdate(query);

            data.set(elementPosition, new Student(id,firstName, lastName, address, weight,Boolean.parseBoolean(allergy)));
            students.set(elementPosition, new Student(id,firstName, lastName, address, weight,Boolean.parseBoolean(allergy)));

            clearTextFields();

            infoDialog(stage,"Record was successfully updated.");
        }
    }

}
