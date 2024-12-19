package com.example.dandanfinalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class StudentManager extends Application implements popWindow {
    private SearchPane searchPane =new SearchPane();
    private Connection conn;
    private Statement stm;
    private PreparedStatement pstm;
    private int [] startingPoint=new int []{0};
    private ArrayList<Student> students =new ArrayList<Student>();//shadow arraylist



    @Override
    public void start(Stage primaryStage) {

        /**Connect to a database  */
        connectToDataBase();


        listAllStudents();

        students.addAll(searchPane.data);

        /**List all records in the database */
        searchPane.listAllButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                searchPane.data.clear();
                searchPane.data.addAll(students);

            }
        });


        /**Add a new student  */
        searchPane.addStudent.setOnAction(new EventHandler<ActionEvent>(){
            public void handle (ActionEvent event)
            {
                new AddStudent(primaryStage,stm, searchPane.data, startingPoint, students);

            }
        });



        /**Delete a student */
        searchPane.deleteStudent.setOnAction(e->{
            new DeleteStudent(primaryStage,pstm,searchPane.data, students);

        });




        /**Update a student information*/
        searchPane.updateStudent.setOnAction(e->{new UpdateStudent(primaryStage, stm, searchPane.data, startingPoint, students);});



        /**Search */
        searchPane.setOnKeyPressed(e->{

            if(e.getCode()== KeyCode.ENTER)
            {
                search(primaryStage);
            }

        });
        searchPane.searchButton.setOnAction(e->{
            search(primaryStage);
        });


        searchPane.about.setOnAction(
                e->{
                    infoDialog(primaryStage,"Student Management System\n" +
                            "  Add: add a new student\n" +
                            "  Update: update a student\n"+
                            "  Delete: delete a student\n" +
                            "  Exit: exit the system");
                }
        );
        /**Exit the application*/
        searchPane.exit.setOnAction(e->{
                System.exit(0);
        });


        /**Create a scene  */
        Scene scene =new Scene(searchPane,610,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Online Management System");
        primaryStage.show();

    }

    public void connectToDataBase()
    {
        try {

            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/sunshinekindergarten","root","Xiazaisql911");
            System.out.println("Successfully connected!");
            stm=conn.createStatement();
            String queryDelete = "delete from students where id=?";
            pstm= conn.prepareStatement(queryDelete);



        }catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }



    public void listAllStudents()
    {

        try{

            String query ="select * from students where id>'"+startingPoint[0]+"'";
            ResultSet resultSet=stm.executeQuery(query);

            while(((ResultSet) resultSet).next())
            {
                searchPane.data.add(new Student(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("address"),
                        resultSet.getDouble("weight"),
                        Boolean.parseBoolean(resultSet.getString("allergy"))
                ));

                startingPoint[0]=Integer.parseInt(resultSet.getString(1));
            }


        }catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }

    }




    /**Search method */
    public void search(Stage primaryStage)
    {
        int index=-1;
        int exist=0;
        ArrayList<Integer> indexes=new ArrayList<>();

        if (searchPane.searchField.getText().equals(""))
        {
            searchPane.searchField.requestFocus();
        }
        else
        {

            if (searchPane.comboBoxSearch.getValue().equals("Id"))
            {

                for(int i = 0; i< students.size(); i++)
                {

                    if (students.get(i).getId()==Integer.parseInt(searchPane.searchField.getText()))
                    {
                        exist=1;
                        index=i;
                    }
                }
                if (exist==1)
                {
                    searchPane.data.clear();
                    searchPane.data.add(students.get(index));
                   // startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Record Does Not Exist !!!");
                }
            }
            else if (searchPane.comboBoxSearch.getValue().equals("First name"))
            {

                for(int i = 0; i< students.size(); i++)
                {

                    if (students.get(i).getFirstName().equals(searchPane.searchField.getText()))
                    {
                        exist=1;
                        indexes.add(i);
                    }
                }
                if (exist==1)
                {
                    searchPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        searchPane.data.add(students.get(indexes.get(i)));
                    }

                   // startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Record Does Not Exist!!!");
                }
            }

            else if (searchPane.comboBoxSearch.getValue().equals("Last name"))
            {

                for(int i = 0; i< students.size(); i++)
                {

                    if (students.get(i).getLastName().equals(searchPane.searchField.getText()))
                    {
                        exist=1;
                        indexes.add(i);
                    }
                }
                if (exist==1)
                {
                    searchPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        searchPane.data.add(students.get(indexes.get(i)));
                    }

                   // startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Record Does Not Exist!!!");
                }
            }
            else if (searchPane.comboBoxSearch.getValue().equals("Address"))
            {

                for(int i = 0; i< students.size(); i++)
                {

                    if (students.get(i).getAddress().equals(searchPane.searchField.getText()))
                    {
                        exist=1;
                        indexes.add(i);
                    }
                }
                if (exist==1)
                {
                    searchPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        searchPane.data.add(students.get(indexes.get(i)));
                    }

                   // startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Record Does Not Exist!!!");
                }
            }
            else if (searchPane.comboBoxSearch.getValue().equals("Weight"))
            {

                for(int i = 0; i< students.size(); i++)
                {

                    if (students.get(i).getWeight()==Double.parseDouble(searchPane.searchField.getText()))
                    {
                        exist=1;
                        indexes.add(i);
                    }
                }
                if (exist==1)
                {
                    searchPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        searchPane.data.add(students.get(indexes.get(i)));
                    }

                   // startingPoint[0]=0;
                }
                else
                {

                    popErrorMessage(primaryStage,"Record Does Not Exist!!!");
                }
            }
        }
    }




}
