package external;

import java.io.*;
import core.*;

public class inputFromFile {

    private static final String[] COMMANDS = {"insert", "delete", "search",
            "predecessor", "successor", "minimum", "maximum", "getmedian",
            "inorderprint", "preorderprint", "postorderprint"};

    // validate String contains only alphabetical letters
    private static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    // method to check id id arg is in range
    private static boolean inRange(int id) {
        return (0 <= id && id <= 999999999);
    }

    private static boolean validateArgs(String [] argv)
    {
        int command = -1;

        // get command name and lower case it
        String cmdArg = argv[0].toLowerCase();

        // find the num to represent requested command by user
        for (int i=0; i<COMMANDS.length; i++)
            if (cmdArg.equals(COMMANDS[i]))
                command = i;

        // check num of args are valid
        if ((command == 0 && argv.length < 4) ||
                (1 <= command && command <= 4 && argv.length < 2)) {
            System.out.println("Error: too few arguments");
            return false;
        }
        if ((5 <= command && argv.length > 1) ||
                (1 <= command && command <= 4 && argv.length > 2)) {
            System.out.println("Error: too many arguments");
            return false;
        }

        // found valid command with no args
        if (argv.length == 1 && 5 <= command)
            return true;

        int idArg; // the id of the student node

        // get other arguments (if they exist)
        // if errors found in input, return false
        try {
            if (0 <= command) {
                if (argv.length > 1) {
                    idArg = Integer.parseInt(argv[1]);
                    if (!inRange(idArg)) {
                        System.out.println("Error: Invalid first name");
                        return false;
                    }
                }

                if (argv.length > 2)
                    if (!isAlpha(argv[2])) {
                        System.out.println("Error: Invalid first name");
                        return false;
                    }

                if (argv.length > 3) {
                    for (int i = 3; i < argv.length; i++) {
                        if (!isAlpha(argv[i])) {
                            System.out.println("Error: Invalid last name");
                            return false;
                        }
                    } // end for
                } // end if
            } // end if
        } // end try
        catch (NumberFormatException e) {
            System.out.println("Error: Invalid id number");
            return false;
        }

        return true;
    }

    // Method to invoke the appropriate method requested by user
    // with the given user args.
    // return true if all went well
    // return false if process failed or error found in input
    private static void addressCommands(String line, DoubleThreadedBinaryTree tree) {

        // split command line into parts
        String [] argv = line.split(" ");

        // check input is valid. if not valid then exit routine
        if (!validateArgs(argv))
            return;

        // initialize command representation number for switch case
        int command = -1;

        // get command name and lower case it
        String cmdArg = argv[0].toLowerCase();

        // find the num to represent requested command by user
        for (int i=0; i<COMMANDS.length; i++)
            if (cmdArg.equals(COMMANDS[i]))
                command = i;

        // declare variables to keep other args if they exist
        int idArg=0; // the id of the student node
        String fullName = "", firstName = "", lastName = ""; // only in insertion
        Node node; // to be used in switch case

        if ( 0 <= command && command <= 4)
            idArg = Integer.valueOf(argv[1]);

        switch (command) {

            case 0: // insert
                firstName = argv[2];
                for (int i=3; i<argv.length; i++)
                    lastName += argv[i];
                fullName = firstName + " " + lastName;
                tree.insert(idArg, fullName);
                break;

            case 1: // delete
                Node deleteResult = tree.delete(idArg);
                if (deleteResult != null)
                    System.out.println("deleted: " + deleteResult.toString());
                else
                    System.out.println("Key not found in tree");
                break;

            case 2: // search
                Node searchResult = tree.search(tree.getRoot(), idArg, false);
                if (searchResult != null)
                    System.out.println("found: " + searchResult.toString());
                else
                    System.out.println("Key not found in tree");
                break;

            case 3: // predecessor
                node = tree.search(tree.getRoot(), idArg, false);
                Node predecessor = tree.predecessor(node);
                System.out.println("Predecessor of "+ node.toString() +
                        " is " + predecessor.toString());
                break;


            case 4: // successor
                node = tree.search(tree.getRoot(), idArg, false);
                Node successor = tree.successor(node);
                System.out.println("successor of "+ node.toString() +
                        " is " + successor.toString());
                break;

            case 5: // minimum
                node = tree.minimum(tree.getRoot());
                if (node != null)
                    System.out.println("Minimum is " + node.toString());
                else
                    System.out.println("Empty tree");
                break;

            case 6: // maximum
                node = tree.maximum(tree.getRoot());
                if (node != null)
                    System.out.println("Maximum is " + node.toString());
                else
                    System.out.println("Empty tree");
                break;

            case 7: // getmedian
                node = tree.getMedian();
                if (node != null)
                    System.out.println("Median is " + node.toString());
                else
                    System.out.println("Empty tree");
                break;

            case 8: // inorderprint
                System.out.println("Inorder print:");
                tree.inorderPrint();
                break;

            case 9: // preorderprint
                System.out.println("Preorder print:");
                tree.preorderPrint(tree.getRoot());
                break;

            case 10: // postorderprint
                System.out.println("Postorder print:");
                tree.postorderPrint(tree.getRoot());
                break;

            default:
                System.out.println("Error: invalid command");
                break;
        }
    }

    public static void main(String [] args) {

        // create the tree
        DoubleThreadedBinaryTree tree;
        tree = new DoubleThreadedBinaryTree();

        // The name of the file to open.
        String fileName = "wrongtest.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println("line: " + line);
                addressCommands(line, tree);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
}
