package tp1;

import java.util.Set;

public class Ellipse extends BaseShape {

    public Ellipse(Double widthRadius, Double heightRadius) {
        Double a = widthRadius;
        Double b = heightRadius;
        for(Double y = -b; y<=b;y+=0.5){
            Double xMax = Math.sqrt(Math.pow(a,2)*(1-(Math.pow(y,2)/Math.pow(b,2))));
            xMax = Math.round(xMax * 2) / 2.0;
            for(Double x = - xMax;x<=xMax;x+=0.5){
                add(new Point2d(x,y));
            }
        }
    }

    public Ellipse(Point2d dimensions) {
        new Ellipse(dimensions.X(),dimensions.Y());
    }

    private Ellipse(Set<Point2d> coords) {
        super(coords);
    }

    @Override
    public Ellipse translate(Point2d point) {
        super.translate(point);
        return this;
    }

    @Override
    public Ellipse rotate(Double angle) {
        super.rotate(angle);
        return this;
    }

    @Override
    public Ellipse clone() {
        double x = PointOperator.getMaxCoord(getCoords()).X()*2;
        double y = PointOperator.getMaxCoord(getCoords()).Y()*2;
        Ellipse ellipse = new Ellipse(x,y);
        return ellipse;
    }
}
