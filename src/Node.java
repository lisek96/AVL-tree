public class Node {
    Node parent;
    Node rightChild;
    Node leftChild;
    int index;
    int value;
    int height;

    Node(int index, int value){
        this.index = index;
        this.value = value;
        height=0;
    }

    public String toString(){
        return index + " value: " + value;
    }
}
