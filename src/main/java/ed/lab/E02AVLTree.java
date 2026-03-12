package ed.lab;

import java.util.Comparator;

public class E02AVLTree<T> {

    private class AVLNode {
        T value;
        AVLNode left;
        AVLNode right;
        int height;

        AVLNode(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private final Comparator<T> comparator;
    private AVLNode root;
    private int size;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    /**
     * 1. ¿Cuál es la complejidad de tiempo y espacio de cada una de las funciones?
     *    - insert(T value): Tiempo O(log n), Espacio O(log n) (pila de recursión). Al ser AVL, la altura está garantizada como log n.
     *    - delete(T value): Tiempo O(log n), Espacio O(log n) (pila de recursión). El rebalanceo ocurre en el camino de regreso.
     *    - search(T value): Tiempo O(log n), Espacio O(1) (implementación iterativa).
     *    - height(): Tiempo O(1), Espacio O(1) (valor precalculado).
     *    - size(): Tiempo O(1), Espacio O(1) (contador mantenido).
     *
     * 2. ¿Cuál es la variación de las complejidades respecto a un árbol binario de búsqueda común? ¿Por qué?
     *    - Variación: En un BST común, el peor caso para búsqueda, inserción y eliminación es O(n). En un AVL, el peor caso es O(log n).
     *    - ¿Por qué?: Un BST común puede desequilibrarse y volverse lineal (como una lista) si los elementos se insertan en orden.
     *      El AVL realiza rotaciones para mantener la propiedad de equilibrio (|altura_izq - altura_der| <= 1), garantizando una altura logarítmica.
     */

    public void insert(T value) {
        root = insert(root, value);
    }

    private AVLNode insert(AVLNode node, T value) {
        if (node == null) {
            size++;
            return new AVLNode(value);
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            // Duplicates not handled specifically or replaced.
            return node;
        }

        return rebalance(node);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private AVLNode delete(AVLNode node, T value) {
        if (node == null) {
            return null;
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            // Node found
            size--;
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode temp = findMin(node.right);
                node.value = temp.value;
                node.right = delete(node.right, temp.value);
                size++; // Correct size decrement after recursive call
            }
        }

        if (node == null) {
            return null;
        }

        return rebalance(node);
    }

    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public T search(T value) {
        AVLNode current = root;
        while (current != null) {
            int cmp = comparator.compare(value, current.value);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public int height() {
        return getHeight(root);
    }

    public int size() {
        return size;
    }

    private int getHeight(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private AVLNode rebalance(AVLNode node) {
        updateHeight(node);
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }
}
