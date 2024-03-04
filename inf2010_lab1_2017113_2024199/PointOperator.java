package tp1;

import org.apache.xmlgraphics.image.loader.ImageException;

import java.util.*;

public final class PointOperator {

    public static void translate (Double[] vector, Double[] translateVector) {
        for (int i = 0; i < vector.length; i++){
            vector[i] += translateVector[i];
        }
    }

    public static void rotate(Double[] vector, Double[][] rotationMatrix) {
        Double[] vectorFinal = new Double[vector.length];
        for(int j =0; j < vector.length; j++) {
            Double elemPosition = 0.0;
            for(int i = 0; i < vector.length; i++) {
                elemPosition += (vector[i] * rotationMatrix[j][i]);
            }
            vectorFinal[j] = elemPosition;
        }
        for (int i = 0; i < vectorFinal.length;i++){
            vector[i]=vectorFinal[i];
        }
    }

    public static void divide(Double[] vector, Double divider) {
        for (int i = 0; i < vector.length; i++){
            vector[i] /= divider;
        }
    }

    public static void multiply(Double[] vector, Double multiplier) {
        for (int i = 0; i < vector.length; i++){
            vector[i] *= multiplier;
        }
    }

    public static void add(Double[] vector, Double adder) {
        for (int i = 0; i < vector.length; i++){
            vector[i] += adder;
        }
    }

    public static Point2d getMaxCoord(Collection<Point2d> coords) {
        if(coords.isEmpty()){
            return null;
        }
        Point2d[] coordsArray = coords.toArray(new Point2d[coords.size()]);
        Double xMax = coordsArray[0].X();
        Double yMax = coordsArray[0].Y();
        for (int i = 1; i < coordsArray.length; i++) {
            if (coordsArray[i].X() > xMax) {
                xMax = coordsArray[i].X();
            }
            if (coordsArray[i].Y() > yMax) {
                yMax = coordsArray[i].Y();
            }
        }
        return new Point2d(xMax,yMax);
    }

    public static Point2d getMinCoord(Collection<Point2d> coords) {
        if(coords.isEmpty()){
            return null;
        }
        Point2d[] coordsArray = coords.toArray(new Point2d[coords.size()]);
        Double xMin = coordsArray[0].X();
        Double yMin = coordsArray[0].Y();
        for (int i = 1; i < coordsArray.length; i++) {
            if (coordsArray[i].X() < xMin) {
                xMin = coordsArray[i].X();
            }
            if (coordsArray[i].Y() < yMin) {
                yMin = coordsArray[i].Y();
            }
        }
        return new Point2d(xMin,yMin);
    }

}
