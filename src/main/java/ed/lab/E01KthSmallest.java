package ed.lab;

public class E01KthSmallest {

    private int count = 0;
    private int result = -1;

    /**
     * Análisis de Complejidad:
     * 1. ¿Cuál es la complejidad de tiempo en el mejor, peor y caso promedio? Justifique su respuesta y expliqué de qué depende la complejidad.
     *    - Mejor Caso: O(h + k) donde h es la altura del árbol. Si k es pequeño, llegamos al nodo más a la izquierda en O(h) y avanzamos k pasos.
     *      En un árbol equilibrado h = log n.
     *    - Peor Caso: O(n) donde n es el número de nodos. Ocurre si el árbol está degenerado (como una lista) y k es cercano a n.
     *    - Caso Promedio: O(h + k).
     *    La complejidad depende de la altura del árbol (para llegar al inicio) y del valor de k (cuántos nodos recorremos).
     *
     * 2. ¿Cuál es la complejidad de espacio en el mejor, peor y caso promedio? Justifique su respuesta y expliqué de qué depende la complejidad.
     *    - Mejor Caso: O(log n) para un árbol equilibrado (profundidad de la pila de recursión).
     *    - Peor Caso: O(n) para un árbol degenerado.
     *    - Caso Promedio: O(h) donde h es la altura del árbol.
     *    La complejidad depende exclusivamente de la altura del árbol debido a la pila de llamadas recursivas.
     *
     * 3. Si el árbol binario de búsqueda fuera modificado con mucha frecuencia (es decir, que se pudieran agregar y eliminar valores),
     *    ¿qué optimización realizaría para obtener el k-ésimo elemento más pequeño frecuentemente? Explique los beneficios
     *    de su solución y la complejidad de tiempo y espacio que esta tendría.
     *    - Optimización: Mantener el 'tamaño' (número de nodos) de cada subárbol en cada nodo.
     *    - Beneficios: Permite encontrar el k-ésimo elemento en O(h) (O(log n) si está equilibrado) sin recorrer los k elementos anteriores.
     *    - Complejidad de tiempo: O(h) para búsqueda del k-ésimo. Las operaciones de inserción/eliminación siguen siendo O(h) pero deben actualizar los tamaños.
     *    - Complejidad de espacio: O(n) en total (un entero adicional por cada nodo).
     */
    public int kthSmallest(TreeNode<Integer> root, int k) {
        count = 0;
        result = -1;
        traverse(root, k);
        return result;
    }

    private void traverse(TreeNode<Integer> node, int k) {
        if (node == null || result != -1) {
            return;
        }

        traverse(node.left, k);

        count++;
        if (count == k) {
            result = node.value;
            return;
        }

        traverse(node.right, k);
    }

}