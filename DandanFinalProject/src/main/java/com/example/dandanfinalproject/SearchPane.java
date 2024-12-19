package com.example.dandanfinalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public  class SearchPane extends BorderPane implements popWindow{

    protected final ObservableList<Student> data = FXCollections.observableArrayList();

    /**
     * Create a menu bar
     */
    private MenuBar menuBar = new MenuBar();
    protected Menu fileMenu = new Menu("File");
    protected Menu helpMenu = new Menu("Help");
    protected MenuItem addStudent = new MenuItem("Add");
    protected MenuItem deleteStudent = new MenuItem("Delete");
    protected MenuItem updateStudent = new MenuItem("Update");
    protected MenuItem about = new MenuItem("About");
    protected MenuItem exit = new MenuItem("Exit");


    /**
     * List All button
     */

    protected Button listAllButton = new Button("List all students");
    protected Button searchButton = new Button("Search");

    /**
     * Create a combo box
     */
    protected ComboBox<String> comboBoxSearch = new ComboBox<>();


    /**
     * Create a  search field
     */
    protected TextField searchField = new TextField();

    /**Create table view and table columns*/
    protected TableView <Student>table =new TableView();
    protected TableColumn idCol=new TableColumn("Id");
    protected TableColumn firstNameCol =new TableColumn("First name");
    protected TableColumn lastNameCol=new TableColumn("Last name");

    protected TableColumn addressCol=new TableColumn("Address");
    protected TableColumn weightCol=new TableColumn("Weight");
    protected TableColumn allergyCol=new TableColumn("Allergy");


    /**Create a VBox */
    private HBox searchBox=new HBox();
    private VBox vbox=new VBox();


    /**Default constructor  */
    public SearchPane()
    {

        /**Set the menu properties  */
        menuBar.setStyle("-fx-background-color:aquamarine;");
        fileMenu.setStyle("-fx-font-size:12;"+"-fx-font-family:Arial;");
        helpMenu.setStyle("-fx-font-size:12;"+"-fx-font-family:Arial;");

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        fileMenu.getItems().addAll(addStudent,deleteStudent,updateStudent);
        helpMenu.getItems().addAll(about, exit);

        listAllButton.setPrefWidth(150);


        /**Set the  table view properties */
        table.getColumns().addAll(idCol,firstNameCol,lastNameCol,addressCol,weightCol,allergyCol);
        firstNameCol.setMinWidth(100);
        lastNameCol.setMinWidth(100);
        addressCol.setMinWidth(100);
        weightCol.setMinWidth(150);
        idCol.setMinWidth(50);
        allergyCol.setMinWidth(50);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        allergyCol.setCellValueFactory(new PropertyValueFactory<>("allergy"));

        table.setItems(data);


        /**Set the search field properties */
        searchField.setStyle("-fx-text-box-border:skyblue;");
        searchField.setPrefWidth(250);

        searchButton.setPrefWidth(150);


        /**HBox properties */
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(5,0,5,0));

        searchBox.getChildren().addAll(comboBoxSearch,searchField,searchButton);


        /**Set combo box properties */
        comboBoxSearch.getItems().addAll("Id","First name","Last name","Address","Weight");
        comboBoxSearch.setPrefWidth(150);
        comboBoxSearch.setValue("Id");


        /**HBox properties */
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.getChildren().addAll(listAllButton,searchBox,table);



        /**Border pane*/
        setTop(menuBar);
        setCenter(vbox);


    }



}
