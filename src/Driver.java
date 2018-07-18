import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Driver Class to test DoubleThreaded data structure and methods.
 *
 * @author Gilad Gur
 * @version 12 JULY 2018
 */
public class Driver
{
    public static String generateRandomName()
    {
        final String vowels = "aeiou";
        final String constLetters = "bcdfghjklmnpqrstvwxyz";
        Random random = new Random();
        String name;
        name = "";
        for (int j=0; j<4; j++)
        {
            if (j%2==0)
                name += constLetters.charAt(random.nextInt(21));
            else
                name += vowels.charAt(random.nextInt(5));
        }
        name += " ";
        for (int j=0; j<6; j++)
        {
            if (j%2==0)
                name += constLetters.charAt(random.nextInt(21));
            else
                name += vowels.charAt(random.nextInt(5));
        }
        return name;
    }
    public static void main(String []args)
    {
        Scanner scan = new Scanner(System.in);

        Random random = new Random();
        int id;


        // create new tree
        DoubleThreadedBinaryTree myTree;
        myTree = new DoubleThreadedBinaryTree();

        // initialize the root
        for (int i=0; i<10; i++)
        {
            id = random.nextInt(1000000000);

            myTree.insert(id, generateRandomName());
        }

        // print inorder traversal of tree
        myTree.inorderPrint();

        // min validation
        System.out.println( "min{tree nodes} = " + (myTree.minimum(myTree.getRoot())).toString() );

        // max validation
        System.out.println( "max{tree nodes} = " + (myTree.maximum(myTree.getRoot())).toString() );

        // successor validation
        System.out.println("\nsuccessors run:");

        Node runner = myTree.minimum(myTree.getRoot());
        while (runner != null)
        {
            System.out.println(runner.toString());
            runner = myTree.successor(runner);
        }
        // predecessor validation
        System.out.println("\npredecessors run:");

        runner = myTree.maximum(myTree.getRoot());
        while (runner != null)
        {
            System.out.println(runner.toString());
            runner = myTree.predecessor(runner);
        }
        // search validation
        int keyToSearch = 0;

        while (true)
        {
            System.out.print("Input search id number (-1 to exit): ");
            try
            {
                keyToSearch = scan.nextInt();
                if (keyToSearch == -1)
                    break;
                Node searchResult = myTree.search(myTree.getRoot(), keyToSearch);
                if (searchResult != null)
                    System.out.println("found: " + (myTree.search(myTree.getRoot(), keyToSearch)).toString());
                else
                    System.out.println("Key not found in tree");
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid input");
            }
            scan.nextLine();
        }

        // delete validation
        while (true)
        {
            System.out.print("Input delete id number (-1 to exit): ");
            try
            {
                keyToSearch = scan.nextInt();
                if (keyToSearch == -1)
                    break;
                Node deleteResult = myTree.delete(keyToSearch);
                if (deleteResult != null)
                    System.out.println("deleted: " + deleteResult.toString());
                else
                    System.out.println("Key not found in tree");
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid input");
            }
            scan.nextLine();
            myTree.inorderPrint();
            System.out.println("Median is " + (myTree.getMedian()).toString());
            System.out.println("Root is " + (myTree.getRoot()).toString());
        }
        System.out.println("inorder:");
        myTree.inorderPrint();

        // preorder validation
        System.out.println("preorder validation");
        myTree.preorderPrint(myTree.getRoot());


        // postorder validation
        System.out.println("postorder validation");
        myTree.postorderPrint(myTree.getRoot());
    }
}