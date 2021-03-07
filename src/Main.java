import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

   
	public static void main(String[] args) throws FileNotFoundException {


        final AVL AVL = new AVL();
        Scanner scanner = new Scanner(new File("input2"));
        Scanner scanner1 = new Scanner(new File("output2"));

        boolean firstCharacter = true;
        int operations = 0;
        int indicator = 0;
        int i = 0;


        while (scanner.hasNext()) {
            if (firstCharacter) {
                firstCharacter = false;
                operations = Integer.parseInt(scanner.next());
            } else {
                AVL.insert(new Node(i, Integer.parseInt(scanner.next())));
                i++;
            }
        }


        for (int j = 0; j < operations; j++) {

            if (AVL.root == null) break;

            Node X = AVL.findNodeByIndex(indicator);

            if (X.value % 2 != 0) {
                if (AVL.elementsInTree - 1 == indicator)
                    AVL.insert(new Node(indicator + 1, X.value - 1));
                else
                    AVL.increaseBiggerIndexesAndInsertNode(new Node(indicator + 1, X.value - 1));
                indicator = (X.value + indicator) % AVL.elementsInTree;
            } else {
                Node toDelete;
                if (AVL.elementsInTree - 1 == indicator) {
                    toDelete= AVL.findNodeByIndex(0);
                    indicator--;
                } else toDelete = AVL.findNodeByIndex(indicator + 1);
                AVL.increaseBiggerIndexesAndDeleteNode(toDelete);
                indicator = (toDelete.value + indicator) % AVL.elementsInTree;
            }
        }

        AVL.outputNodesArray = new Node[AVL.elementsInTree];
        AVL.avtToArray(AVL.root);
        if (AVL.root != null) {
            for (int k = indicator; k < AVL.outputNodesArray.length; k++) {
                System.out.print(AVL.outputNodesArray[k].value + " ");
            }
            for (int k = 0; k < indicator; k++) {
                System.out.print(AVL.outputNodesArray[k].value + " ");
            }
            System.out.println();
            while (scanner1.hasNext()) {
                System.out.print(scanner1.next() + " ");
            }
        }



    }


}
