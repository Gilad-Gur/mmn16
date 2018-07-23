package core;
import shapes.Circle;

/**
 * A Java implementation of a core.DoubleThreadedBinaryTree.
 *
 * @author Gilad Gur & Shahar Linial
 * @version 18 JULY 2018
 */
public class DoubleThreadedBinaryTree
{
    // instance variables
    private Node root;
    private Node median;
    private int numOfNodes;
    private final int INSERT = 0;
    private final int DELETE = 1;

    /**
     * core.DoubleThreadedBinaryTree instance constructor
     */
    public DoubleThreadedBinaryTree()
    {
        // initialise instance variables
        root = null;
        median = null;
        numOfNodes = 0;
    }

    /**
     * A method to fetch this tree's root.
     *
     * @return root this tree's root core.Node
     */
    public Node getRoot()
    {
        return this.root;
    }


    // private method to update median of tree
    // keep an upper median if numOfNodes is even
    private void updateMedian(Node node, int status)
    {
        if (numOfNodes == 0)
            median = null;

        else if (numOfNodes == 1)
            median = root;

        else // numOfNodes > 1
        {
            if (status == INSERT)
            {
                if (numOfNodes%2 == 0) // even
                {
                    if (node.getId() > median.getId())
                        median = successor(median, false);
                }
                else // numOfNodes is odd
                {
                    if (node.getId() < median.getId())
                        median = predecessor(median, false);
                }
            }
            else // status is DELETE
            {
                if (numOfNodes%2 == 0) // even
                {
                    // == if median itself is deleted
                    if (node.getId() <= median.getId())
                        median = successor(median, false);
                }
                else // numOfNodes is odd
                {
                    // == if median itself is deleted
                    if (node.getId() >= median.getId())
                        median = predecessor(median, false);
                }
            }
        }
    }


    /**
     * This method returns the median node of the tree.
     *
     * @return median the median node of the tree. Return null if tree is empty.
     */
    public Node getMedian()
    {
        if(median != null)System.out.println("Median = " + median.getId() + " : " + median.getName());
        else System.out.println("Median Error: There are no nodes in the tree.");
        return median;
    }
    public void setMedian(Node tNode){
        median = tNode;
    }
    /**
     * This method returns the node with minimal key in the tree.
     *
     * @param rootNode The root of the sub-tree
     *
     * @return min the node with key==min{SubTree keys}
     */
    public Node minimum(Node rootNode, boolean to_print)
    {
        Node min = rootNode;
        if (min != null)
        {
            while (!min.isLeftThreaded())
                min = min.getLeft();
        }else{
            if(to_print)System.out.println("Min Error : There are no nodes in the tree.");
        }
        if(to_print && min != null)System.out.println("Min = " + min.getId() + " : " + min.getName());
        return min;
    }

    /**
     * This method returns the node with maximal key in the sub-tree.
     *
     * @param rootNode The root of the sub-tree
     *
     * @return max the node with key==max{SubTree keys}
     */
    public Node maximum(Node rootNode, boolean to_print)
    {
        Node max = rootNode;
        if (max != null)
        {
            while (!max.isRightThreaded())
                max = max.getRight();
        }else{
            if(to_print)System.out.println("Max Error : There are no nodes in the tree.");
        }
        if(to_print && max != null)System.out.println("Max = " + max.getId() + " : " + max.getName());
        return max;
    }

    /**
     * This method determines the node's successor in the tree.
     *
     * @param node the node to find its successor.
     *
     * @return result the node's successor (null if no succ found or param is null)
     */
    public Node successor(Node node, boolean to_print) {
        Node successor;
        if (node == null) {
            if(to_print) System.out.println("Successor Error : There are no nodes in the tree.");
            return null;
        }
            // if node is right threaded
            // then return its right child (its successor)
        else if (node.isRightThreaded()) {
            successor = node.getRight();
            if(to_print){
                if(successor != null){
                    System.out.println("Successor: " + successor.getId() + " : " + successor.getName());
                }else{
                    System.out.println("Successor Error : Node " + node.getId() + " : " + node.getName() +" does not have a successor.");
                }
            } return successor;
        }
            // if node has right sub tree
            //  return the minimum of that sub tree.
        else if (node.getRight() != null) {
            successor = minimum((node.getRight()), false);
            if(to_print) System.out.println("Successor: " + successor.getId() + " : " + successor.getName());
            return successor;
        }
            // node doesn't have a right sub tree
            // therefore its successor is the lowest ancestor of node
            // whose left child is also an ancestor of node (CLRS 12.2-6)
        else {
            Node ancestor = node.getParent();
            Node previous = node;

            while (ancestor != null && previous == ancestor.getRight()) {
                previous = ancestor;
                ancestor = ancestor.getParent();
            }
            successor = ancestor;
            if(successor == null){
                if(to_print) System.out.println("Successor Error : Node " + node.getId() + " : " + node.getName() +" does not have a successor");
            }else{
                if(to_print) System.out.println("Successor: " + successor.getId() + " : " + successor.getName());
            }
            return successor;
        }
    }
    /**
     * This method determines the node's predecessor in the tree.
     *
     * @param node the node to find its predecessor.
     *
     * @return result the node's predecessor (null if no pred found or param is null)
     */
    public Node predecessor(Node node, boolean to_print)
    {
        Node predecessor;

        if (node == null) {
            if(to_print) System.out.println("Predecessor Error : There are no nodes in the tree.");
            return null;
        }

        // if node is left threaded
        // then return it left child (its predecessor)
        else if (node.isLeftThreaded()) {
            predecessor =  node.getLeft();
            if(to_print){
                if(predecessor != null){
                    System.out.println("Predecessor: " + predecessor.getId() + " : " + predecessor.getName());
                }else{
                    System.out.println("Predecessor Error : Node " + node.getId() + " : " + node.getName() +" does not have a predecessor.");
                }
            }
            return predecessor;
        }

        // if node has left sub tree
        // return the maximum of that sub tree
        else if (node.getLeft() != null) {
            predecessor = maximum(node.getLeft(), false);
            if(to_print) System.out.println("Predecessor: " + predecessor.getId() + " : " + predecessor.getName());
            return predecessor;
        }

        // node doesn't have a right sub tree
        // therefore its predecessor is the lowest ancestor of node
        // whose right child is also an ancestor of node (corollary of CLRS 12.2-6)
        else
        {
            Node ancestor = node.getParent();
            Node previous = node;

            while (ancestor != null && previous == ancestor.getLeft())
            {
                previous = ancestor;
                ancestor = ancestor.getParent();
            }
            predecessor = ancestor;
            if(predecessor == null){
                if(to_print)System.out.println("Predecessor Error : Node " + node.getId() + " : " + node.getName() +" does not have a predecessor.");
            }else{
                if(to_print)System.out.println("Predecessor: " + predecessor.getId() + " : " + predecessor.getName());
            }
            return predecessor;
        }
    }

    /**
     * This method inserts a new core.Node to the tree
     *
     * @param id the id of the new core.Node to be added
     * @param name the student name to be inserted
     */
    public void insert(int id, String name)
    {
        Node newNode = new Node(id, name);
        Node parent = null;
        Node current = getRoot();

        while (current != null)
        {
            parent = current;

            if ( current.isLeftThreaded() && id <= current.getId() )
                break;

            if (id <= current.getId())
                current = current.getLeft();

            else if ( current.isRightThreaded() && id > current.getId() )
                break;

            else
                current = current.getRight();
        }

        newNode.setParent(parent);

        if (parent == null) // tree was empty
        {
            this.root = newNode;
        }

        // make newNode left/right child of parent
        else if (id <= parent.getId())
        {
            parent.setLeft(newNode);
            parent.setLeftThreaded(false);
        }

        else
        {
            parent.setRight(newNode);
            parent.setRightThreaded(false);
        }

        // thread new core.Node
        newNode.setLeft(predecessor(newNode, false));
        newNode.setLeftThreaded(true);
        newNode.setRight(successor(newNode, false));
        newNode.setRightThreaded(true);

        numOfNodes++;
        updateMedian(newNode, INSERT);
        System.out.println("Successfully inserted new node "+ newNode.getId() + " : " + newNode.getName());

    }

    /**
     * This method searches and finds a node from a tree (if it exists).
     *
     * @param rootSubTree the root node of current sub-tree
     *
     * @param id the key of node to be searched
     * @return currentNode if a node is found, null otherwise
     */
    public Node search(Node rootSubTree, int id, boolean toHighlight, boolean to_print)
    {
        Node current = rootSubTree;
        while (current != null)
        {
            if (toHighlight) current.highlightFlag = true;
            if (id == current.getId()) {
                if(to_print) System.out.println("Successfully found node " + current.getId() + " : " + current.getName());
                return current;
            }
            if (current.isLeftThreaded() && id < current.getId())
                break;

            if (id < current.getId()) {
                current = current.getLeft();
            }
            else if (current.isRightThreaded() && id > current.getId())
                break;

            else {
                current = current.getRight();
            }
        }
        setResetColor();
        if (to_print) System.out.println("Could not find node " + id + " because it does not exists.");
        return null;
    }

    // private method to determine whether or not a given node is a leaf.
    private boolean isLeaf(Node node)
    {
        if ( node.isLeftThreaded() && node.isRightThreaded() )
        {
            return true;
        }
        return false;
    }

    // private method to determine if a node has only a left child
    private boolean hasOnlyLeftChild(Node node)
    {
        if (!node.isLeftThreaded() && node.isRightThreaded())
            return true;
        return false;
    }

    // private method to determine if a node has only a left child
    private boolean hasOnlyRightChild(Node node)
    {
        if (node.isLeftThreaded() && !node.isRightThreaded())
            return true;
        return false;
    }
    // private method to determine whether or not a node is a left child of its parent
    private boolean isLeftChild(Node child)
    {
        Node parent = child.getParent();

        return (child == parent.getLeft());
    }

    // private method to determine whether or not a node is a right child of its parent
//    private boolean isRightChild(core.Node child)
//    {
//        core.Node parent = child.getParent();
//
//        return (child == parent.getRight());
//    }

    /**
     * This method extracts (delete) a given node from tree.
     *
     * @param id the key of node to be removed from tree
     *
     *
     * @return toDelete the core.Node that has been removed, null if not found
     */
    public Node delete(int id)
    {
        Node toDelete = search(getRoot(), id, false, false);
        if (toDelete == null)
        {
            System.out.println("Could not delete node " + id + " because it does not exists.");
            return null;
        }
        else {

            numOfNodes--;
            String nameToDelete = toDelete.getName();
            Integer idToDelete = toDelete.getId();
            if (isLeaf(toDelete))
            {
                toDelete = deleteLeaf(toDelete);
                updateMedian(toDelete, DELETE);
            }
            else if (hasOnlyLeftChild(toDelete) || hasOnlyRightChild(toDelete))
            {
                toDelete = deleteNodeWithOneChild(toDelete);
                updateMedian(toDelete, DELETE);
            }
            else // node has both left and right children
            {
                toDelete = deleteNodeWithBothChildren(toDelete);
                // updating median inside deleteNodeWithBothChildren()
            }
            System.out.println("Successfully deleted node " + idToDelete + " : " + nameToDelete);
            return toDelete;
        }
    }

    // private method to delete a leaf from a tree
    private Node deleteLeaf(Node toDelete)
    {

            Node parent = toDelete.getParent();
            if (parent == null) // toDelete is the only node in tree
            {
                this.root = null;
            }
            else if (isLeftChild(toDelete))
            {
                parent.setLeft(toDelete.getLeft());
                parent.setLeftThreaded(true);
            }
            else // if (isRightChild(toDelete))
            {
                parent.setRight(toDelete.getRight());
                parent.setRightThreaded(true);
            }

            return toDelete;
    }

    // private method to delete a node with only one child from tree
    private Node deleteNodeWithOneChild(Node toDelete)
    {
        Node parent, child;

        parent = toDelete.getParent();

        if (hasOnlyLeftChild(toDelete))
            child = toDelete.getLeft();

        else // node to delete has only right child
            child = toDelete.getRight();

        if (parent == null) // node to delete is root
            this.root = child;

        else if (isLeftChild(toDelete))
            parent.setLeft(child);

        else // node to delete is right child of its parent
            parent.setRight(child);

        child.setParent(parent);

        // get successor and predecessor of node to delete
        Node predecessor = predecessor(toDelete, false);
        Node successor = successor(toDelete, false);

        // if node to delete has a left sub-tree
        // then right-thread its predecessor to node to delete's successor.
        if (hasOnlyLeftChild(toDelete))
        {
            predecessor.setRight(successor);
            predecessor.setRightThreaded(true);
        }
        // if node to delete has a right sub-tree
        // then left-thread its successor to node to delete's predecessor.
        else // if (hasOnlyRightChild(toDelete))
        {
            successor.setLeft(predecessor);
            successor.setLeftThreaded(true);
        }

        return toDelete;
    }

    // private method to delete a node with both left & right children from tree
    private Node deleteNodeWithBothChildren(Node toDelete)
    {

        Node temp = new Node(0, "");
        Node successor = successor(toDelete, false);

        // save toDelete data temporarily
        temp.copyData(toDelete);

        if (!(median == toDelete && numOfNodes%2==0)) {
            updateMedian(toDelete,DELETE);
        }

        // copy successor data into toDelete node
        toDelete.copyData(successor);

        // copy toDelete data into its successor node
        successor.copyData(temp);


        // delete toDelete's successor
        if (isLeaf(successor))
            return deleteLeaf(successor);

        else // node has only one child (left or right but not both)
            return deleteNodeWithOneChild(successor);
    }

    /**
     * This method prints an inorder traversal of the tree.
     */
    public String inorderPrint()
    {
        Node runner = minimum(getRoot(), false);
        StringBuilder sb = new StringBuilder();
        while (runner != null)
        {
            sb.append(runner.toString()).append(" ");
            // If this node is right-threaded
            // then go to inorder successor
            if (runner.isRightThreaded())
                runner = runner.getRight();

            // Otherwise, go to the leftmost child in right sub-tree
            else
                runner = minimum(runner.getRight(), false);
        }
        System.out.println("Inorder : " + sb.toString());
        return sb.toString();
    }

    /**
     * This method prints a preorder traversal of the tree
     *
     * @param currentNode the node to be traversed
     */
    public String preorderPrint(Node currentNode, boolean to_print)
    {
        StringBuilder sb = new StringBuilder();

        if (currentNode != null)
        {
            sb.append(currentNode.toString()).append(" ");
            if ( ! currentNode.isLeftThreaded() )
                sb.append(preorderPrint(currentNode.getLeft(), false));

            if ( ! currentNode.isRightThreaded() )
                sb.append(preorderPrint(currentNode.getRight(), false));
        }
        if(to_print) System.out.println("Preorder : " + sb.toString());
        return sb.toString();
    }

    private void deleteAllNodes(){
        Node current = minimum(getRoot(), false);
        Node succ;
        while (current != null)
        {
            succ = successor(current, false);
            delete(current.getId());
            current = succ;
        }
    }
    /**
     * This method prints a postorder traversal of the tree
     *
     * @param currentNode the node to be traversed
     */
    public String postorderPrint(Node currentNode, boolean to_print)
    {
        StringBuilder sb = new StringBuilder();
        if (currentNode != null)
        {
            if ( ! currentNode.isLeftThreaded() )
                sb.append(postorderPrint(currentNode.getLeft(), false));

            if ( ! currentNode.isRightThreaded() )
                sb.append(postorderPrint(currentNode.getRight(), false));

            sb.append(currentNode.toString()).append(" ");
        }
        if(to_print)System.out.println("Postorder : " + sb.toString());
        return sb.toString();
    }

    /**
     * Method to fetch the height of the tree.
     *
     * @return height the number of
     */
    public int heightOfTree(Node node)
    {
        if (isLeaf(node))
        {
            return 1;
        }
        else if (hasOnlyLeftChild(node))
        {
            return 1 + heightOfTree(node.getLeft());
        }
        else if (hasOnlyRightChild(node))
        {
            return 1 + heightOfTree(node.getRight());
        }
        else // (!node.isLeftThreaded() && !node.isRightThreaded())
        {
            return 1 + Math.max(heightOfTree(node.getLeft()),
                            heightOfTree(node.getRight()));
        }
    }

    public void setResetColor() {
        resetColor();
    }

    public void makeEmpty() {
        deleteAllNodes();
        root = null;
        median = null;
        numOfNodes = 0;
    }


    protected void resetColor() {
        Node runner = minimum(getRoot(),false);
        while (runner != null)
        {

            runner.highlightFlag = false;
            if (runner.isRightThreaded())
                runner = runner.getRight();

                // Otherwise, go to the leftmost child in right sub-tree
            else
                runner = minimum(runner.getRight(),false);
        }
    }
}
