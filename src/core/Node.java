/**
 * Class Node represents a node in a tree.
 *
 * @author Gilad Gur
 * @version 14 JULY 2018
 */

public class Node
{
    // Node instance variables
    private int id;
    private String name;
    private Node left;
    private Node right;
    private Node parent;
    private boolean leftThreaded;
    private boolean rightThreaded;

    /**
     * Node instance constructor
     *
     * @param id the id of newly created Node
     * @param name the name of the student
     */
    Node(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.leftThreaded = false;
        this.rightThreaded = false;
    }

    /**
     * Method to copy source Node's data to this node.
     *
     * @param source the node to copy data from.
     */
    public void copyData(Node source)
    {
        this.id = source.id;
        this.name = source.name;
    }

    // private method to make id number into string
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
        return "id: " + idToString(id) + ", name: " + name;
    }

    /**
     * Method to fetch this Node's id
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
        return left;
    }
    public Node getRight()
    {
        return right;
    }
    public Node getParent()
    {
        return parent;
    }

    public boolean isLeftThreaded()
    {
        return leftThreaded;
    }
    public boolean isRightThreaded()
    {
        return rightThreaded;
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
