// matricule: 2024199 et 2017113

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Solution {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String ascending = scanner.nextLine();
        String listString = scanner.nextLine();

        // TODO prendre les donnees en entree, les convertir et les utiliser dans le monceau
        int min_max = Integer.parseInt(ascending);
        boolean isMin = min_max == 1 ? true : false;

        String str[] = listString.split(" ");
        List<Integer> data = new ArrayList();
        for (int i = 0; i < str.length; i++) {
            Integer value = Integer.parseInt(str[i]);
            data.add(value);
        }

        Heap2 firstHeap = new Heap2(isMin, data);
        firstHeap.print();
    }
}

class Heap2 {
    private List<Integer> data;
    private boolean isMin;

    // O(1): construction sans donnees initiales
    public Heap2(boolean isMin) {
        this.isMin = isMin;
        this.data = new ArrayList();
    }

    // O(n): construction avec donnees initiales, allez voir le lien dans la description pour vous aider
    public Heap2(boolean isMin, List<Integer> data) {
        this.isMin = isMin;                     // O(1)
        this.data = new ArrayList();
        this.data.addAll(data);                 // O(n)
        build();                                // O(n)
    }

    public void print() {
        int initialSize = size();
        for (int i = 0; i < initialSize; i++) {
            System.out.print(pop() + " ");
            build();
        }
    }

    // O(1): on retourne le nombre d'elements dans la liste
    public int size() {
        return data.size();
    }

    // O(log(n)): on ajoute un element et on preserve les proprietes du monceau
    public void insert(Integer element) {
        data.add(element);
        for (int i = size() - 1; i >= 0; i = i/2) {
            if(compare(parentIdx(i), i)) {
                swap(parentIdx(i), i);
            }
        }
    }

    // O(log(n)): on retire le min ou le max et on preserve les proprietes du monceau
    public Integer pop() {
        Integer oldValue = data.get(0);
        swap(0,size() - 1);
        data.remove(size() - 1);
        return oldValue;
    }



    // O(n): on s'assure que tous les elements sont bien places dans le tableau,
    // allez voir le lien dans la description pour vous aider
    public void build() {
        for (int i = (size() / 2) - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    // O(1): on compare deux elements en fonction du type de monceau
    private boolean compare(Integer first, Integer second) {
        if (isMin == true) {
            return first >= second ? true : false;
        }
        return first >= second ? false : true;
    }

    // O(1): on donne l'indice du parent
    private int parentIdx(int idx) {
        return idx / 2;
    }

    // O(1): on donne l'indice de l'enfant de gauche
    private int leftChildIdx(int idx) {
        int idxLeft = (idx * 2) + 1;
        return idxLeft < size() ? idxLeft : -1;
    }

    // O(1): on donne l'indice de l'enfant de droite
    private int rightChildIdx(int idx) {
        int idxRight = (idx * 2) + 2;
        return idxRight < size() ? idxRight : -1;
    }

    // O(1): on echange deux elements dans le tableau
    private void swap(int firstIdx, int secondIdx) {
        Collections.swap(data, firstIdx, secondIdx);
    }

    // O(log(n)): l'index courant represente le parent, on s'assure que le parent soit le min/max avec ses enfants
    // De facon visuelle, ceci ammene un noeud le plus haut possible dans l'arbre
    // Par exemple: si le min/max est une feuille, on appelera resursivement log(n) fois la methode pour monter le noeud
    private void heapify(int idx) {
        if (compare(data.get(idx),data.get(leftChildIdx(idx)))) {
            swap(idx, leftChildIdx(idx));
        }
        if (rightChildIdx(idx) != -1 && compare(data.get(idx),data.get(rightChildIdx(idx)))) {
            swap(idx, rightChildIdx(idx));
        }
    }
}
