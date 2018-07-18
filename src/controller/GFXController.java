package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Constructs the GUI components and performs events for displaying and
 * manipulating the binary tree.
 * @author Shahar Linial
 * @version 1.0
 */
public final class GFXController implements Initializable {

    // Panels and other GUI components
    @FXML private BorderPane root_container;
    @FXML private TextArea traversal_textarea;
    @FXML private TextField input_field;

    private GFXTree graphicsTree;

    /**
     * Constructs the GUI components and performs events for displaying and
     * changing the data in the binary tree.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // The center panel for drawing the tree
        graphicsTree = new GFXTree();
        // Add the panels onto the border pane
        root_container.setCenter(graphicsTree);

        // Bind canvas size to stack pane size.
        graphicsTree.widthProperty().bind(root_container.widthProperty());
        graphicsTree.heightProperty().bind(root_container.heightProperty().subtract(50));
    }

    /**
     *  Performs the action when the search button is clicked.
     */
    @FXML private void searchOnAction(ActionEvent event) {
        try {
            graphicsTree.search(Integer.parseInt(input_field.getText().trim()));
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Error searching for value. The input field can only accept numbers.", 	ButtonType.OK);

            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());

        }
    }

    /**
     * Performs the action when the delete button is clicked.
     */
    @FXML private void deleteOnAction(ActionEvent event) {
        try {
            graphicsTree.delete(Integer.parseInt(input_field.getText().trim()));
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting value. The input field can only accept numbers.",
                    ButtonType.OK);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
        }
    }

    private void clearTree() {
        graphicsTree.makeEmpty();
        traversal_textarea.setText("");
    }
    /**
     * Performs the action when the clear button is clicked.
     */
    @FXML private void clearOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to empty the tree?", ButtonType.OK);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> clearTree());

    }

    /**
     *  Performs the action when the insert button is clicked.
     */
    @FXML private void insertOnAction(ActionEvent event) {
        try {
            graphicsTree.insert(Integer.parseInt(input_field.getText().trim()), "");
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting value. The input field can only accept numbers.",
                    ButtonType.OK);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
        }
    }
    @FXML private void showMedianOnAction(ActionEvent event){

        graphicsTree.showMedian();
    }
    /**
     * Performs the action when the first traversal button is clicked.
     */
    @FXML private void showMinAction(ActionEvent event) {
        graphicsTree.showMin();
    }
    @FXML private void showMaxAction(ActionEvent event) {
        graphicsTree.showMax();
    }
    @FXML private void inorderOnAction(ActionEvent event) {
        traversal_textarea.setText(graphicsTree.inorder());
    }
    /**
     *  Performs the action when the second traversal button is clicked.
     */
    @FXML private void preorderOnAction(ActionEvent event) {
        traversal_textarea.setText(graphicsTree.preorder());
    }

    /**
     *  Performs the action when the third traversal button is clicked.
     */
    @FXML private void postorderOnAction(ActionEvent event) {
        traversal_textarea.setText(graphicsTree.postorder());
    }

    @FXML private void openFileOnAction(ActionEvent event){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(root_container.getScene().getWindow());
        if (file != null) {

        }
    }
}
