package com.example.dandanfinalproject;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public interface popWindow {

    default void infoDialog (Stage primaryStage, String text1)
    {
        /**Create a Stage  */
        final Stage dialog=new Stage ();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        /**Set text properties */
        Text localText=new Text();
        localText.setText(text1);
        localText.setFill(Color.DARKGREEN);
        localText.setFont(Font.font("Arial",18));

        /**Set dialog HBox properties */
        HBox dialogHbox=new HBox();
        dialogHbox.setSpacing(10);
        dialogHbox.getChildren().add(localText);
        dialogHbox.setAlignment(Pos.CENTER);

        /**Set VBox properties */
        VBox dialogVBox=new VBox();
        Button dialogOk=new Button("Ok");
        dialogOk.setAlignment(Pos.BOTTOM_CENTER);
        dialogVBox.setSpacing(10);
        dialogVBox.getChildren().addAll(dialogHbox,dialogOk);

        VBox.setMargin(dialogOk,new Insets(10,10,10,150));

        /**Set the button properties */
        dialogOk.setPrefWidth(100);

        //exit the info message window
        dialogOk.setOnAction(e->{dialog.close();});


        /**Create a scene  */
        Scene scene=new Scene (dialogVBox,400,200);
        dialog.setScene(scene);
        dialog.setTitle("Info Message");
        dialog.show();
    }
    default void popErrorMessage(Stage primaryStage,String text2)
    {
        /**Create a Stage  */
        final Stage dialog=new Stage ();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        /**Set text properties */
        Text text=new Text();
        text.setText(text2);
        text.setFill(Color.RED);
        text.setFont(Font.font("Times new roman",18));


        /**Set the button properties */
        Button dialogOk=new Button("OK");
        dialogOk.setPrefWidth(100);

        /**Set dialog HBox properties */
        HBox dialogHbox=new HBox ();
        dialogHbox.setSpacing(10);
        dialogHbox.getChildren().add(text);

        /**Set VBox properties */
        VBox dialogVBox =new VBox();
        dialogVBox.setSpacing(10);
        dialogVBox.getChildren().addAll(dialogHbox,dialogOk);
        VBox.setMargin(dialogOk,new Insets(0,0,0,150));



        /** exit the pop up window*/
        dialogOk.setOnAction(e->{dialog.close();});

        /**Create a scene  */
        Scene scene=new Scene (dialogVBox,400,200);

        dialog.setScene(scene);
        dialog.setTitle("Error Message");
        dialog.show();
    }
}
