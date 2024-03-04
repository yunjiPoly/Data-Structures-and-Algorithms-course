//matricule: 2024199 et 2017113

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Solution {

    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Integer circleSize = Integer.parseInt(scanner.nextLine());
        List<Integer> centers = Arrays
                .stream(scanner.nextLine().split(" +"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        AtomicInteger id = new AtomicInteger();
        List<Point> points = Arrays
                .stream(scanner.nextLine().split(","))
                .map(s -> new Point(s, id.getAndIncrement()))
                .collect(Collectors.toList());

        // TODO modifiez ceci pour inclure votre algorithme
        System.out.println(circleSize);
        System.out.println(centers);
        System.out.println(points);


        // COMPLEXITE DE L'ALGORITHME : cn + (ca)²

        Algorithme algo = new Algorithme(points, circleSize, centers);
        System.out.println(algo.resolution());

        // COMPLEXITE DE L'ALGORITHME : cn + (ca)²

    }
}


class Distance {
    public Point point;
    public Integer distance;

    public Distance(Point point, Integer distance) {
        this.point = point;
        this.distance = distance;
    }
}


// COMPLEXITE DE L'ALGORITHME : cn + (ca)²
class Algorithme {
    private List<Point> points;
    private Integer a; // Taille du cercle d'amis
    private List<Integer> centres; // Les deux centres des cercles d'amis

    public Algorithme(List<Point> points, Integer a, List<Integer> centres) {
        this.points = points;
        this.a = a;
        this.centres = centres;
    }

    // Calcul la distance (Manhattan) entre deux points a et b
    private Integer distanceEntreDeuxPoints(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    // Crée la liste des distances pour de tous les points par rapport a un centre donné (donné par son id)
    private List<Distance> distanceDuCentreAuxAutresPoints(Integer id) {
        Point centre = points.get(id);
        List<Distance> result = new ArrayList<Distance>();
        for (Point point : this.points) {
            if (point.x != centre.x || point.y != centre.y) {
                result.add(new Distance(point, this.distanceEntreDeuxPoints(centre, point)));
            }
        }
        return result;
    }

    // Ordonne dans l'ordre croissant la liste des distances
    private List<Distance> ordonnerDistances(List<Distance> distances) {
        Collections.sort(distances, new Comparator<Distance>() {
            @Override
            public int compare(Distance d1, Distance d2) {
                return d1.distance.compareTo(d2.distance);
            }
        });
        return distances;
    }

    // Ordonne dans l'ordre croissant la liste des entiers
    private List<Integer> ordonnerEntiers(List<Integer> liste) {
        Collections.sort(liste, new Comparator<Integer>() {
            @Override
            public int compare(Integer d1, Integer d2) {
                return d1.compareTo(d2);
            }
        });
        return liste;
    }

    // Trouve les amis dangereux en comparant les deux listes de distances ordonnées
    private List<Integer> trouverLesDangereux(List<List<Distance>> listDistances) {
        Integer nMax = this.a;
        List<Integer> dangereux = new ArrayList<Integer>();
        for (List<Distance> dA : listDistances) {
            Integer i = 0;
            for (Distance da : dA) {
                if (i >= nMax) {
                    break;
                }
                for (List<Distance> dB : listDistances) {
                    Integer j = 0;
                    if (dB != dA) {
                        for (Distance db : dB) {
                            if (j >= nMax) {
                                break;
                            }
                            if (da.point.x == db.point.x && da.point.y == db.point.y) {
                                dangereux.add(da.point.id);
                            }
                            j++;
                        }
                    }
                }
                i++;
            }
        }
        if (dangereux.size() == 0) {
            dangereux.add(-1);
        }
        return dangereux;
    }

    // Convertit une liste d'entier en String (pour l'affichage final)
    private String listeEntierEnString(List<Integer> liste) {
        String listeString = liste.stream().map(String::valueOf)
                .collect(Collectors.joining(" "));
        return listeString;
    }

    private List<Integer> supprimerDoublons(List<Integer> liste) {
        Set<Integer> tempSet = new HashSet<Integer>(liste);
        return new ArrayList<Integer>(tempSet);
    }

    // Résolution finale
    // COMPLEXITE DE L'ALGORITHME : cn + (ca)²
    public String resolution() {
        String resultat = new String();
        Integer n = 0;
        List<List<Distance>> listDistances = new ArrayList<List<Distance>>();
        for (Integer i : this.centres) {
            List<Distance> distance = distanceDuCentreAuxAutresPoints(i);
            distance = ordonnerDistances(distance);
            listDistances.add(distance);
        }

        return listeEntierEnString(ordonnerEntiers(supprimerDoublons(trouverLesDangereux(listDistances))));
    }

}

// Vous n'êtes pas forcés d'utiliser cette classe, vous pouvez la modifier comme vous le voulez
class Point {
    public Integer x;
    public Integer y;
    public Integer id;

    public Point(String xy, Integer id) {
        String[] xAndY = xy.split(" +");
        this.id = id;
        this.x = Integer.parseInt(xAndY[0]);
        this.y = Integer.parseInt(xAndY[1]);
    }

    @Override
    public String toString() {
        return String.format("{Id: %d, X: %d, Y: %d}", id, x, y);
    }
}