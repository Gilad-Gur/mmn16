package controller;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import core.*;
import shapes.*;
import java.util.Objects;

/**
 * Draws the tree and updates the graphics to display according to the
 * searching, inserting, deleting, and traversal options.
 * @author Shahar Linial
 * @version 1.0
 */
@SuppressWarnings("serial")
public final class GFXTree extends Canvas {

    /**
     * The initial input values for the tree.
     */
    private static final Integer[] NUMBERS_ARRAY = { 50, 25, 30, 12, 10, 75, 70, 80, 110 };

    private DoubleThreadedBinaryTree tree;  	// The Double Threaded Binary Tree.
    // private TreeIterator treeIterator;  // The BST Iterator
    private Circle insertCircle;        // Insert circle
    private int maxTreeHeight; 			// Max tree height;

    /**
     * Draws the tree and updates the graphics to display according to the
     * searching, inserting, deleting, and traversal options.
     */
    public GFXTree() {

        widthProperty().addListener(evt -> drawTree());
        heightProperty().addListener(evt -> drawTree());

        createTree();
    }

    /**
     * Changes the tree rendered by this panel.
     */
    public void setTree(DoubleThreadedBinaryTree root) {  tree = root; }

    /**
     * Creates the initial binary search tree with the default values
     * in the numbers array.
     */
    public void createTree() {

        tree = new DoubleThreadedBinaryTree(); // Create an empty tree
        setMaxTreeHeight(7); 		   // Set the default max tree height

        for (Integer number : NUMBERS_ARRAY) {
//            Circle circle = new Circle(number, "");
            tree.insert(number, "sha");
        }

        drawTree();
    }

    /**
     * Set the max tree height.
     * @param size a <code>Integer</code> number for the tree max size
     * @return An <code>Integer</code> max tree size
     */
    private int setMaxTreeHeight(int size) {
        this.maxTreeHeight = size;
        return size;
    }

    /**
     * Searches for an search key number in the tree. If the number is found the tree will
     * be repainted to show the path. If the number cannot be found a notification
     * message will be displayed.
     *
     * @param searchKey a <code>Integer</code> number for finding a tree.TreeNode
     */
    public void search(Integer searchKey) {

        // Try to search for a number.
        tree.search(tree.getRoot(), searchKey, true); // number was found
        drawTree();
    }

    /**
     * Prints the tree traversal order to the upper left-hand
     * side of the screen.
     * @return outputString
     */
    public void showMedian(){
        if (tree.getMedian() != null){
            tree.getMedian().highlightFlag = true;
            drawTree();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Tree is empty. Please insert valid records.",
                    ButtonType.CLOSE);
            alert.showAndWait()
                    .ifPresent(response -> alert.close());
        }
    }
    public void showMin(){
        if (tree.getRoot() != null){
            tree.minimum(tree.getRoot()).highlightFlag = true;
            drawTree();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Tree is empty. Please insert valid records.",
                    ButtonType.CLOSE);
            alert.showAndWait()
                    .ifPresent(response -> alert.close());
        }
    }
    public void showMax(){
        if (tree.getRoot() != null){
            tree.maximum(tree.getRoot()).highlightFlag = true;
            drawTree();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Tree is empty. Please insert valid records.",
                    ButtonType.CLOSE);
            alert.showAndWait()
                    .ifPresent(response -> alert.close());
        }
    }
    /**
     * Retrieves the pre-order traversal option.
     */
    public String preorder(){
        return tree.preorderPrint(tree.getRoot());
    }

    /**
     * Retrieves the in-order traversal option.
     */
    public String inorder() {
        return tree.inorderPrint();
    }
    /**
     * Retrieves the post-order traversal option.
     */
    public String postorder() {
        return tree.postorderPrint(tree.getRoot());
    }


//    public void setPostorder() {
//        treeIterator = new TreeIterator(tree);
//        treeIterator.setPostorder();
//    }

    /**
     * Inserts a circle into the tree. If the tree height reaches the max height
     * displays an notification request to change the max height.
     * @param searchKey a <code>integer</code> number to insert in the tree
     */
    public void insert(Integer searchKey, String studentName) {
//        insertCircle = new Circle(searchKey, studentName);
        tree.insert(searchKey, studentName);
        int heightOption = 2;
        drawTree();


        //	 If the height of the tree equals max height
        if (tree.heightOfTree(tree.getRoot()) == maxTreeHeight) {
            // Display option message

            Alert alert = new Alert(Alert.AlertType.WARNING, "Reached max height. Would you like to increase the height?",
                    ButtonType.YES);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(response -> alert.close());
        }
//			heightOption = JOptionPane.showConfirmDialog(null,
//					"Reached max height. Would you like to increase the height?", " Height Adjust",
//					JOptionPane.YES_NO_OPTION);

        // Option 'Yes'selected: Change the height
        if (heightOption == 0) {
//				String height = JOptionPane.showInputDialog("Enter a new height between 7 and 10.");
            heightOption = 7;
            try {
                int newHeight = heightOption;
//					int newHeight = Integer.parseInt(heightOption);
                // Confirm height input is within acceptable range
                if (newHeight > tree.heightOfTree(tree.getRoot()) && newHeight <= 10) {
                    setMaxTreeHeight(newHeight);
                } else {
                    throw new Exception("tree.TreeException on change height.");
                }

                // Error occurred: Reverse changes and exit
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input error. The height was not changed.",
                        ButtonType.OK);
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());

                tree.delete(searchKey); // Remove the inserted circle
                //tree.setResetColor(tree.root); 				  // Reset highlight flag for all nodes
            }

            // Option 'No' selected: Reverse changes and exit
        } else if (heightOption == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The number was not inserted.",
                    ButtonType.OK);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> alert.close());


            tree.delete(searchKey); // Remove the inserted circle
            //tree.setResetColor(tree.root);                // Reset highlight flag for all nodes



        } else {

        }
    }

    /**
     * Deletes a number from the tree. If the number is not able to be deleted display
     * a notification message.
     * @param searchKey <code>integer</code> number to delete from the tree
     */
    public void delete(Integer searchKey) {
        try {
            tree.delete(searchKey);
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Unable to delete " + searchKey);
        }

        drawTree();
    }

    /**
     * Deletes all the nodes in the tree.
     */
    public void makeEmpty() {
        tree.makeEmpty();
        maxTreeHeight = 6;
        getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Draws the binary tree on the component.
     */
    protected void drawTree() {
        double width = getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        // If the tree is not empty; draw the lines and circles
        if (tree.getRoot() != null) {
            int treeHeight = tree.heightOfTree(tree.getRoot());

            // Get the tree height
            drawTree(gc, tree.getRoot(), 0, this.getWidth(), 0, this.getHeight() / treeHeight);
            drawCircles(gc, tree.getRoot(), 0, this.getWidth(), 0, this.getHeight() / treeHeight);
        }
    }

    /**
     * Draws the lines recursively until there are no more tree nodes.
     * @param gc graphics2D class for extending drawing tools
     * @param treeNode a tree with <code>integer</code> index numbers
     * @param xMin the minimum width to draw on the component
     * @param xMax the minimum width to draw on the component
     * @param yMin the maximum width to draw on the component
     * @param yMax the maximum height to draw on the component
     */
    protected void drawTree(GraphicsContext gc, Node treeNode, double xMin, double xMax, double yMin, double yMax) {

        Point2D linePoint1; 	// Point_1
        Point2D linePoint2;   // Point_2
        Line newLine = new Line();  // Blank line

        // If left node is not null then draw a line to it
        if (!treeNode.isLeftThreaded()) {
            newLine.setHighlighter(false);

            if (treeNode.getLeft().highlightFlag) {
                newLine.setHighlighter(true);
            }

            // Determine the start and end points of the line
            linePoint1 = new Point2D(((xMin + xMax) / 2), yMin + yMax / 2);
            linePoint2 = new Point2D(((xMin + (xMin + xMax) / 2) / 2), yMin + yMax + yMax / 2);
            newLine.setPoint(linePoint1, linePoint2);// Set the points
            newLine.draw(gc);// Draw the line

            // Recurse left circle nodes
             drawTree(gc, treeNode.getLeft(), xMin, (xMin + xMax) / 2, yMin + yMax, yMax);
        }

        // If right node is not null then draw a line to it
        if (!treeNode.isRightThreaded()) {
            newLine.setHighlighter(false);

            // Color the line if the tree circle is flagged for color
            if (treeNode.getRight().highlightFlag) {
                newLine.setHighlighter(true);
            }

            // Determine the start and end points of the line
            linePoint1 = new Point2D((xMin + xMax) / 2, yMin + yMax / 2);
            linePoint2 = new Point2D((xMax + (xMin + xMax) / 2) / 2, yMin + yMax + yMax / 2);
            newLine.setPoint(linePoint1, linePoint2);
            newLine.draw(gc);// Draw the line

            // Recurse right circle nodes
             drawTree(gc, treeNode.getRight(), (xMin + xMax) / 2, xMax, yMin + yMax, yMax);
        }
    }

    /**
     * Draws circles for every root, parent and child tree nodes.
     * @param gc graphics2D class for expanding the drawing tools
     * @param treeNode a tree with <code>Integer</code> index numbers
     * @param xMin the minimum width to draw on the component
     * @param xMax the maximum width to draw on the component
     * @param yMin the minimum height to draw on the component
     * @param yMax the maximum height to draw on the component
     */
    public void drawCircles(GraphicsContext gc, Node treeNode, double xMin, double xMax, double yMin, double yMax) {

        // Create a new point
        Point2D point = new Point2D((xMin + xMax) / 2, yMin + yMax / 2);

        // treeNodes are flagged for highlight: Search and insertion nodes
        if (treeNode.highlightFlag || Objects.equals(treeNode.rootCircle, insertCircle)) {
            insertCircle = null;		    // Reset insert circle
            treeNode.highlightFlag = false; // Reset highlight flag
            treeNode.rootCircle.setHighlighter(true); // Highlight turned on
            treeNode.rootCircle.setPoint(point);

//             default no highlight
        } else {
            treeNode.rootCircle.setHighlighter(false); // Highlight turned off
            treeNode.rootCircle.setPoint(point);
        }

        // Draw the circle
        treeNode.rootCircle.setPoint(point);
        treeNode.rootCircle.draw(gc);

        // Recurse left circles
        if (!treeNode.isLeftThreaded()) {
            drawCircles(gc, treeNode.getLeft(), xMin, (xMin + xMax) / 2, yMin + yMax,	yMax);
        }

        // Recurse right circles
        if (!treeNode.isRightThreaded()) {
            drawCircles(gc, treeNode.getRight(), (xMin + xMax) / 2, xMax, yMin + yMax, yMax);
        }
    }

    public void clearCanvas() {
        getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());
    }
}


