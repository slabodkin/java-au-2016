package ru.spbau.mit;

import java.io.*;
/**
 * Created by vbv on 29.02.16.
 */
public class StringSetImpl implements StringSet, StreamSerializable {

    private static final int ALPHABET_SIZE = 26 * 2;
    private final Node root = new Node();


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

    public void serialize(OutputStream out) {
        nodeSerialize(out, root);
    }

    public void deserialize(InputStream in) {
        nodeDeserialize(in, root);
    }


    private int getNextSymbolNumber(int index, String str) {
        if (Character.isLowerCase(str.charAt(index))) {
            return str.charAt(index) - 'a';
        } else {
            return str.charAt(index) - 'A' + ALPHABET_SIZE / 2;
        }
    }

    private boolean nodeAdd(Node nd, int index, String str) {
        if (index != str.length()) {
            int nextSymbol = getNextSymbolNumber(index, str);

            if (nd.symbolPointers[nextSymbol] == null) {
                nd.symbolPointers[nextSymbol] = new Node();
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

    private boolean nodeContains(Node nd, int index, String str) {
        if (index != str.length()) {
            int nextSymbol = getNextSymbolNumber(index, str);

            if (nd.symbolPointers[nextSymbol] == null) {
                return false;
            }
            return nodeContains(nd.symbolPointers[nextSymbol], index + 1, str);
        }
        return nd.marked;
    }

    private boolean nodeRemove(Node nd, int index, String str) {
        if (index != str.length()) {
            int nextSymbol = getNextSymbolNumber(index, str);

            if (nd.symbolPointers[nextSymbol] == null) {
                return false;
            }
            if (nodeRemove(nd.symbolPointers[nextSymbol], index + 1, str)) {
                nd.markedAncestorsNumber--;
                if (nd.markedAncestorsNumber == 0) {
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

    private int nodeHowManyStartsWithPrefix(Node nd, int index, String str) {
        if (index == str.length()) {
            return nd.markedAncestorsNumber;
        }
        int nextSymbol = getNextSymbolNumber(index, str);

        if (nd.symbolPointers[nextSymbol] == null) {
            return 0;
        }
        return nodeHowManyStartsWithPrefix(nd.symbolPointers[nextSymbol], index + 1, str);
    }

    private void nodeSerialize(OutputStream out, Node nd) {
        try {
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeBoolean(nd.marked);
            dataOut.writeInt(nd.markedAncestorsNumber);
            boolean tmp;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                tmp = nd.symbolPointers[i] != null;
                dataOut.writeBoolean(tmp);
                if (tmp) {
                    nodeSerialize(out, nd.symbolPointers[i]);
                }
            }
        } catch (IOException e) {
            throw new SerializationException();
        }
    }

    private void nodeDeserialize(InputStream in, Node nd) {
        try {
            DataInputStream dataIn = new DataInputStream(in);
            nd.marked = dataIn.readBoolean();
            nd.markedAncestorsNumber = dataIn.readInt();
            boolean tmp;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                tmp = dataIn.readBoolean();
                if (tmp) {
                    nd.symbolPointers[i] = new Node();
                    nodeDeserialize(in, nd.symbolPointers[i]);
                }
            }
        } catch (IOException e) {
            throw new SerializationException();
        }

    }

    private static final class Node {
        private final Node[] symbolPointers;
        private boolean marked;
        private int markedAncestorsNumber;

        private Node() {
            marked = false;
            markedAncestorsNumber = 0;
            symbolPointers = new Node[ALPHABET_SIZE];
        }
    }
}
