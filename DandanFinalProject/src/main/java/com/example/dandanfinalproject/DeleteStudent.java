package com.example.dandanfinalproject;

import javafx.collections.ObservableList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
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

import javax.swing.*;
import java.sql.PreparedStatement;

import java.util.ArrayList;

import static javafx.geometry.Pos.*;

public class DeleteStudent implements popWindow{

    private TextField tfId=new TextField();
    private Label idLabel=new Label("Id");
    private Label titleLabel=new Label("Delete a record: ");

    private Button delete =new Button ("Delete");
    private Button cancel =new Button("Cancel");

    private VBox vbox=new VBox();
    private HBox hbox=new HBox();
    private GridPane gridPane =new GridPane();

    public DeleteStudent(Stage primaryStage, PreparedStatement pstm, ObservableList<Student> data, ArrayList<Student> students)
    {
        tfId.setStyle("-fx-text-box-border:skyblue;");
        tfId.setPrefWidth(200);

        idLabel.setStyle("-fx-text-fill: black;"+"-fx-font-family:Arial;"+"-fx-font-size:14;");
        titleLabel.setStyle("-fx-text-fill: black;"+"-fx-font-family:Arial;"+"-fx-font-size:16;");

        delete.setPrefWidth(100);
        delete.setStyle("-fx-background-color:lavender");
        cancel.setPrefWidth(100);
        cancel.setStyle("-fx-background-color:pink;");

        hbox.setSpacing(10);

        hbox.getChildren().addAll(delete, cancel);

        gridPane.setAlignment(CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(20);
        gridPane.setPadding (new Insets(10,20,0,0));
        gridPane.add(idLabel,0,0);
        gridPane.add(tfId,1,0);
        gridPane.add(hbox,1,1);
        GridPane.setHalignment(hbox, HPos.RIGHT);

        vbox.setSpacing(25);
        vbox.setPadding(new Insets(10,20,0,0));
        vbox.getChildren().addAll(titleLabel,gridPane);

        Scene scene =new Scene(vbox,300,150);
        Stage mainStage=new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);

        mainStage.setScene(scene);
        mainStage.setTitle("Delete");
        mainStage.show();


        /**Register and handle the event fired by cancel button */
        cancel.setOnAction(e->{mainStage.close();});


        /**Register and handle the event fired by the delete button */
        delete.setOnAction(e->{
            try{

                deleteStudent(mainStage,pstm,data, students);

            }catch (Exception exception)
            {
                popErrorMessage(mainStage, exception.getMessage());
            }
        });


        /**Register and handle the event fired by the Enter key */
        vbox.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.ENTER)
            {
                try{

                    deleteStudent(primaryStage, pstm, data, students);
                }catch(Exception exception)
                {
                    popErrorMessage(mainStage,exception.getMessage());
                }
            }
        });

    }


    /**Delete a student */
    public void deleteStudent(Stage stage, PreparedStatement pstm, ObservableList<Student> data, ArrayList<Student> students)throws Exception
    {
        int exist = 0;
        int number=Integer.parseInt(tfId.getText());
        boolean isConfirm = false;

        for (int i=0;i<data.size();i++)
        {
            /**if the Id number is found in the database */
            if (data.get(i).getId()==number)
            {
                int result = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this record",
                        "Delete Record",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (result == JOptionPane.YES_OPTION){

                    exist=1;
                    isConfirm=true;
                    pstm.setInt(1,number);
                    pstm.executeUpdate();

                    data.remove(data.get(i));
                    students.remove(students.get(i));

                    break;
                }
                else{
                    exist=1;
                    isConfirm=false;
                    break;
                }
            }

        }
        /**if the user confirms to delete the record */
        if (isConfirm)
        {
            tfId.clear();
            infoDialog(stage,"Record Was Successfully Deleted!!!");

        }
        /**if the Id number is not found in the database */
        if (exist==0)
        {
            popErrorMessage(stage,"Record Doesn't Exist!!! ");
        }

    }


}

