import java.util.function.Consumer;

public class AVL {
    Node root;
    int elementsInTree = 0;
    Node[] outputNodesArray;

    // basic methods: insert, findNodeByIndex, delete
    public void insert(Node newNode) {
        elementsInTree++;

        if (root == null) root = newNode;

        else {
            Node current = root;
            while (true) {
                if (newNode.index < current.index) {
                    if (current.leftChild == null) {
                        current.leftChild = newNode;
                        current.leftChild.parent = current;
                        balance(current);
                        break;
                    }
                    current = current.leftChild;
                } else {
                    if (current.rightChild == null) {
                        current.rightChild = newNode;
                        current.rightChild.parent = current;
                        balance(current);
                        break;
                    }
                    current = current.rightChild;
                }
            }
        }
    }
    public Node findNodeByIndex(int index) {
        if (root == null) return null;
        Node currentNode = root;
        if (currentNode.index == index) return root;
        else {
            while (currentNode != null) {
                if (currentNode.index == index) return currentNode;

                if (currentNode.index < index) {
                    currentNode = currentNode.rightChild;
                } else currentNode = currentNode.leftChild;
            }
            return currentNode;
        }
    }
    public boolean delete(Node y) {
        if (y == null) return false;
        Node z = y.parent;
        if (y.leftChild == null && y.rightChild == null) {
            if (z == null) {
                root = null;
                return true;
            }
            if (y.equals(z.rightChild))
                z.rightChild = null;
            else
                z.leftChild = null;
            elementsInTree--;
            balance(z);
            return true;
        } else if (y.leftChild == null ^ y.rightChild == null) {
            Node x = y.rightChild!=null ? y.rightChild : y.leftChild;
            if (z == null) {
                root = x;
                x.parent = null;
            } else {
                if (y.equals(z.rightChild)) {
                    z.rightChild = x;
                } else
                    z.leftChild = x;
                 x.parent=z;
            }
            elementsInTree--;
            balance(x);
            return true;
        } else if (y.leftChild != null && y.rightChild != null) {
            Node sub = y.rightChild;
            while (sub.leftChild != null) {
                sub = sub.leftChild;
            }
            if (sub.rightChild != null && !sub.equals(y.rightChild)) {
                Node subParent = sub.parent;
                subParent.leftChild = sub.rightChild;
                sub.rightChild.parent = subParent;

                sub.rightChild = y.rightChild;
                sub.rightChild.parent = sub;
                sub.leftChild = y.leftChild;
                sub.leftChild.parent = sub;

                if (z == null) {
                    root = sub;
                    sub.parent = null;
                } else {
                    if (y.equals(z.rightChild)) {
                        z.rightChild = sub;
                    } else
                        z.leftChild = sub;
                    sub.parent = z;
                }
                balance(subParent);
                elementsInTree--;
                return true;
            } else if (sub.equals(y.rightChild)) {
                if (z == null) {
                    root = sub;
                    sub.parent = null;
                } else {
                    if (y.equals(z.rightChild)) {
                        z.rightChild = sub;
                    } else
                        z.leftChild = sub;
                    sub.parent = z;
                }
                sub.leftChild = y.leftChild;
                sub.leftChild.parent = sub;
                elementsInTree--;
                balance(sub);
                return true;
            } else if (sub.rightChild == null) {
                Node subParent = sub.parent;
                sub.parent.leftChild = null;

                if (z == null) {
                    root = sub;
                    sub.parent = null;
                } else {
                    if (y.equals(z.rightChild)) {
                        z.rightChild = sub;
                    } else
                        z.leftChild = sub;
                    sub.parent = z;
                }
                sub.rightChild = y.rightChild;
                sub.rightChild.parent = sub;
                sub.leftChild = y.leftChild;
                sub.leftChild.parent = sub;

                elementsInTree--;
                balance(subParent);
                return true;
            }
        }
        return false;
    }

    //rotations
    private void RotateRight(Node current) {
        System.out.println("Right Rotation");

        Node x = current.parent;
        Node y = current;
        Node z = current.leftChild;

        if (x != null) {
            if (x.rightChild != null && x.rightChild.equals(y))
                x.rightChild = z;
            else {
                x.leftChild = z;
            }

        }

        z.parent = x;
        if (z.rightChild != null) {
            y.leftChild = z.rightChild;
            y.leftChild.parent = y;
        } else y.leftChild = null;

        if (y.parent == null) root = z;
        y.parent = z;
        z.rightChild = y;

        updateHeight(x);
        updateHeight(z);
        updateHeight(y);
    }
    private void RotateLeft(Node current) {

        System.out.println("Left Rotation");
        Node x = current.parent;
        Node y = current;
        Node z = current.rightChild;

        if (x != null) {
            if (x.rightChild != null && x.rightChild.equals(y)) {
                x.rightChild = z;
            } else {
                x.leftChild = z;
            }
        }
        z.parent = x;
        if (z.leftChild != null) {
            y.rightChild = z.leftChild;
            y.rightChild.parent = y;
        } else y.rightChild = null;
        if (y.parent == null) root = z;
        y.parent = z;
        z.leftChild = y;

        updateHeight(x);
        updateHeight(z);
        updateHeight(y);
    }
    private void RotateRightThenLeft(Node current) {
        RotateRight(current.rightChild);
        RotateLeft(current);
    }
    private void RotateLeftThenRight(Node current) {
        RotateLeft(current.leftChild);
        RotateRight(current);
    }

    //height, balance
    private void balance(Node startingNode) {
        Node current = startingNode;
        while (current != null) {
            updateHeight(current);
            int currentBalance = getBalance(current);
            if (currentBalance == 0 || currentBalance == 1 || currentBalance == -1) {
                current = current.parent;
                continue;
            }

            if (currentBalance == 2) {
                Node leftChild = current.leftChild;
                if (getBalance(leftChild) == 0) RotateRight(current);
                else if (getBalance(leftChild) == 1) RotateRight(current);
                else if (getBalance(leftChild) == -1) RotateLeftThenRight(current);
            } else if (currentBalance == -2) {
                Node rightChild = current.rightChild;
                if (getBalance(rightChild) == 0) RotateLeft(current);
                else if (getBalance(rightChild) == -1) RotateLeft(current);
                else if (getBalance(rightChild) == 1) RotateRightThenLeft(current);
            }

            current = current.parent;
        }
    }
    private void updateHeight(Node node) {
        if (node != null)
            node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
    }
    private int getHeight(Node node) {
        if (node == null) return -1;
        return node.height;
    }
    private int getBalance(Node node) {
        return getHeight(node.leftChild) - getHeight(node.rightChild);
    }

    //preparing indexes for deletion/addition, when adding - increase
    public void increaseBiggerIndexesAndDeleteNode(Node toDelete) {
        if(toDelete==null) return;
        goThroughNodeAndNodesWithBiggerIndexes(toDelete, n -> n.index--);
        delete(toDelete);

    }
    public void increaseBiggerIndexesAndInsertNode(Node nodeToInsert) {

        Node startingPoint = findNodeByIndex(nodeToInsert.index);
        if(startingPoint==null) startingPoint= findNodeByIndex(nodeToInsert.index-1);

        if(startingPoint.leftChild==null){
            startingPoint.leftChild = nodeToInsert;
            nodeToInsert.parent = startingPoint;
            balance(startingPoint);
        }
        else {
            Node current = startingPoint.leftChild;
            while (current.rightChild != null) {
                current = current.rightChild;
            }
            current.rightChild = nodeToInsert;
            nodeToInsert.parent = current;
            balance(current);
        }
        elementsInTree++;
        goThroughNodeAndNodesWithBiggerIndexes(startingPoint, n -> n.index++);
    }
    private void goThroughNodeAndNodesWithBiggerIndexes(Node startingNode, Consumer<Node> operation) {
        boolean rightTree = false;
        boolean leftTree = false;

        if (startingNode.index < root.index) leftTree = true;
        else if (startingNode.index > root.index) rightTree = true;
        else {
            operation.accept(root);
            goDownNodeAndActOperation(root.rightChild, operation);
        }


        Node x;
        Node y;
        if (leftTree) {
            if (startingNode.rightChild != null)
                goDownNodeAndActOperation(startingNode.rightChild, operation);

            Node current = startingNode;
            operation.accept(current);
            while (current.parent != null) {
                x = current;
                y = current.parent;
                if (y.leftChild!=null && y.leftChild.equals(x)) {
                    operation.accept(y);
                    if (y.rightChild != null)
                        goDownNodeAndActOperation(y.rightChild, operation);
                }
                current = y;
            }
        } else if (rightTree) {

            Node endPoint;
            Node current = root;
            while (current.rightChild != null) {
                if (current.index >= startingNode.index) {
                    endPoint = current;
                    break;
                }
                current = current.rightChild;
            }
            endPoint = current;

            if (startingNode.rightChild != null)
                goDownNodeAndActOperation(startingNode.rightChild, operation);

            current = startingNode;
            operation.accept(current);

            while (!current.equals(endPoint)) {
                x = current;
                y = current.parent;
                if (y.leftChild!=null && y.leftChild.equals(x)) {
                    operation.accept(y);
                    if (y.rightChild != null)
                        goDownNodeAndActOperation(y.rightChild, operation);
                }
                current = y;
            }
        }
    }
    private void goDownNodeAndActOperation(Node node, Consumer<Node> operation) {
        if (node == null) return;
        operation.accept(node);
        goDownNodeAndActOperation(node.leftChild, operation);
        goDownNodeAndActOperation(node.rightChild, operation);
    }

    //output
    public void avtToArray(Node node){
        if(node==null) return;
        avtToArray(node.leftChild);
        outputNodesArray[node.index] = node;
        avtToArray(node.rightChild);
    }
}
