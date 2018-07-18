package core;

import shapes.Circle;

/**
 * Class core.Node represents a node in a tree.
 *
 * @author Gilad Gur & Shahar Linial
 * @version 18 JULY 2018
 */

public class Node
{
    // core.Node instance variables
    public Circle rootCircle;
    private int id;
    private String name;
    private Node left;
    private Node right;
    private Node parent;
    private boolean leftThreaded;
    private boolean rightThreaded;
    public boolean highlightFlag;

    /**
     * core.Node instance constructor
     *
     * @param id the id of newly created core.Node
     * @param name the name of the student
     */
    public Node(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.rootCircle = new Circle(id,name);
        this.left = null;
        this.right = null;
        this.parent = null;
        this.leftThreaded = false;
        this.rightThreaded = false;
    }

    /**
     * Method to copy source core.Node's data to this node.
     *
     * @param source the node to copy data from.
     */
    public void copyData(Node source)
    {
        this.id = source.id;
        this.name = source.name;
    }

    // private method to make id number into string
    //TODO: do we need that?
    private String idToString(int id)
    {
        String result = "";
        String str = Integer.toString(id);
        int zeros = 9 - str.length();
        for (int i=0; i<zeros; i++)
            result += "0";
        result += str;

        return result;
    }

    /**
     * This method returns a String representation of a node's data:
     * id number and name of student.
     *
     * @return str the string to represent node
     */
    public String toString()
    {
        return idToString(id);
    }

    /**
     * Method to fetch this core.Node's id
     */
    public int getId()
    {
        return this.id;
    }
    public String getName()
    {
        return this.name;
    }
    public Node getLeft()
    {
        return this.left;
    }
    public Node getRight()
    {
        return this.right;
    }
    public Node getParent()
    {
        return this.parent;
    }

    public boolean isLeftThreaded()
    {
        return this.leftThreaded;
    }
    public boolean isRightThreaded()
    {
        return this.rightThreaded;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setLeft(Node left)
    {
        this.left = left;
    }
    public void setRight(Node right)
    {
        this.right = right;
    }
    public void setParent(Node parent)
    {
        this.parent = parent;
    }
    public void setLeftThreaded(boolean leftThreaded)
    {
        this.leftThreaded = leftThreaded;
    }
    public void setRightThreaded(boolean rightThreaded)
    {
        this.rightThreaded = rightThreaded;
    }
}
