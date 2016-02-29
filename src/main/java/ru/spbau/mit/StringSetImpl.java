package ru.spbau.mit;

/**
 * Created by vbv on 29.02.16.
 */
public class StringSetImpl implements StringSet {

    private static final int ALPHABET_SIZE = 26 * 2;

    public static class Node {
        public Node() {
            marked = false;
            markedAncestorsNumber = 0;
            symbolPointers = new Node[ALPHABET_SIZE];
        }

        private Node[] symbolPointers;
        private boolean marked;
        private int markedAncestorsNumber;
    }

    public boolean nodeAdd(Node nd, int index, String str) {
        if (index != str.length()) {
            int nextSymbol;
            if (Character.isLowerCase(str.charAt(index))) {
                nextSymbol = str.charAt(index) - 'a';
            } else {
                nextSymbol = str.charAt(index) - 'A';
            }
            if (nd.symbolPointers[nextSymbol] == null) {
                nd.symbolPointers[nextSymbol] = new Node();
                nd.markedAncestorsNumber++;
                boolean tmp = nodeAdd(nd.symbolPointers[nextSymbol], index + 1, str);
                return true;
            }
            if (nodeAdd(nd.symbolPointers[nextSymbol], index + 1, str)) {
                nd.markedAncestorsNumber++;
                return true;
            }
            return false;
        }
        if (!nd.marked) {
            nd.marked = true;
            nd.markedAncestorsNumber++;
            return true;
        }
        return false;
    }

    public boolean nodeContains(Node nd, int index, String str) {
        if (index != str.length()) {
            int nextSymbol;
            if (Character.isLowerCase(str.charAt(index))) {
                nextSymbol = str.charAt(index) - 'a';
            } else {
                nextSymbol = str.charAt(index) - 'A';
            }
            if (nd.symbolPointers[nextSymbol] == null) {
                return false;
            }
            return nodeContains(nd.symbolPointers[nextSymbol], index + 1, str);
        }
        return nd.marked;
    }

    public boolean nodeRemove(Node nd, int index, String str) {
        if (index != str.length()) {
            int nextSymbol;
            if (Character.isLowerCase(str.charAt(index))) {
                nextSymbol = str.charAt(index) - 'a';
            } else {
                nextSymbol = str.charAt(index) - 'A';
            }
            if (nodeRemove(nd.symbolPointers[nextSymbol], index + 1, str)) {
                if (nd.markedAncestorsNumber-- == 1) {
                    nd.symbolPointers[nextSymbol] = null;
                }
                return true;
            }
            return false;
        }
        if (nd.marked) {
            nd.marked = false;
            nd.markedAncestorsNumber--;
            return true;
        }
        return false;
    }

    public int nodeHowManyStartsWithPrefix(Node nd, int index, String str) {
        if (index == str.length()) {
            return nd.markedAncestorsNumber;
        }
        int nextSymbol;
        if (Character.isLowerCase(str.charAt(index))) {
            nextSymbol = str.charAt(index) - 'a';
        } else {
            nextSymbol = str.charAt(index) - 'A';
        }
        if (nd.symbolPointers[nextSymbol] == null) {
            return 0;
        }
        return nodeHowManyStartsWithPrefix(nd.symbolPointers[nextSymbol], index + 1, str);
    }

    private Node root = new Node();

    public boolean add(String str) {
        return nodeAdd(root, 0, str);
    }

    public boolean contains(String str) {
        return nodeContains(root, 0, str);
    }

    public boolean remove(String str) {
        return nodeRemove(root, 0, str);
    }

    public int howManyStartsWithPrefix(String str) {
        return nodeHowManyStartsWithPrefix(root, 0, str);
    }

    public int size() {
        return root.markedAncestorsNumber;
    }


}
