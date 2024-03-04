package tp1;

import java.util.*;
import java.util.stream.Collectors;

public class BaseShape implements Cloneable {
    // Vous aller apprendre plus en details le fonctionnement d'un Set lors
    // du cours sur les fonctions de hashage, vous pouvez considerez ceci comme une liste normale.
    // Les Sets ont la propriete qu'un element ne peux se retrouver qu'une seule fois dans la liste
    private Set<Point2d> coords;

    public BaseShape() {
        this.coords = new HashSet<Point2d>();
    }

    public BaseShape(Collection<Point2d> coords) {
        this.coords = new HashSet<Point2d>();
        Point2d[] coordsArray = coords.toArray(new Point2d[coords.size()]);
        for(int i = 0; i < coordsArray.length; i++){
            this.coords.add(coordsArray[i]);
        }
    }

    public void add(Point2d coord) {
        coords.add(coord);
    }

    public void add(BaseShape shape) {
        for(Point2d coord : shape.coords){
            add(coord);
        }
    }

    public void addAll(Collection<Point2d> coords) {
        Point2d[] coordsArray = coords.toArray(new Point2d[coords.size()]);
        for(int i =0; i < coordsArray.length; i++){
            add(coordsArray[i]);
        }
    }

    public void remove(Point2d coord) {
        coords.remove(coord);
    }

    public void remove(BaseShape shape) {
        for(Point2d coord : shape.coords){
            remove(coord);
        }
    }

    public void removeAll(Collection<Point2d> coords) {
        Point2d[] coordsArray = coords.toArray(new Point2d[coords.size()]);
        for(int i =0; i < coordsArray.length; i++){
            remove(coordsArray[i]);
        }
    }

    public Set<Point2d> getCoords() {
        Set<Point2d> copy = new HashSet<Point2d>();
        for(Point2d point2d : coords){
            copy.add(point2d);
        }
        return copy;
    }

    public Set<Point2d> getCoordsDeepCopy() {
        Set<Point2d> deepCopy = new HashSet<Point2d>();
        for(Point2d point2d : coords){
            deepCopy.add(point2d.clone());
        }
        return deepCopy;
    }

    public BaseShape translate(Point2d point) {
        for (Point2d coord: coords){
            coord.translate(point);
        }
        return this;
    }

    public BaseShape rotate(Double angle) {
        for (Point2d coord: coords){
            coord.rotate(angle);
        }
        return this;
    }

    public BaseShape clone() {
        BaseShape shapeCloned = new BaseShape(coords);
        return shapeCloned;
    }
}
