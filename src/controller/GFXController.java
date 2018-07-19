package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.File;
import javafx.stage.FileChooser;

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
            String x = graphicsTree.search(Integer.parseInt(input_field.getText().trim()));

            traversal_textarea.setText(x);
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
            traversal_textarea.setText("");
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
            TextInputDialog dialog = new TextInputDialog("walter");
            dialog.setTitle("Adding new item");
            dialog.setHeaderText("A Student name is needed.");
            dialog.setContentText("Please enter "+ input_field.getText() +"'s name:");
            Optional<String> result = dialog.showAndWait();
            String studentName = "";
            if (result.isPresent()){ studentName = result.get();}
            graphicsTree.insert(Integer.parseInt(input_field.getText().trim()), studentName);
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting value. The input field can only accept numbers.",
                    ButtonType.OK);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());
        }
    }
    @FXML private void showMedianOnAction(ActionEvent event){
        String median = graphicsTree.showMedian();
        traversal_textarea.setText(median);
    }
    /**
     * Performs the action when the first traversal button is clicked.
     */
    @FXML private void showMinAction(ActionEvent event) {
        String min = graphicsTree.showMin();
        traversal_textarea.setText(min);

    }
    @FXML private void showMaxAction(ActionEvent event) {
        String max = graphicsTree.showMax();
        traversal_textarea.setText(max);
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
            graphicsTree.createTreeFromFile(file);
        }
    }
}
