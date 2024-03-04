package tp1;

import java.util.Set;

public class Rectangle extends BaseShape {
    public Rectangle(Double width, Double height) {
        Double xMax = width/2.0;
        Double yMax = height/2.0;
        xMax = Math.round(xMax * 2) / 2.0;
        yMax = Math.round(yMax * 2) / 2.0;
        for(Double i = -xMax; i<=xMax; i+=0.5){
            for(Double j = -yMax; j<= yMax; j+=0.5){
                add(new Point2d(i,j));
            }
        }
    }

    public Rectangle(Point2d dimensions) {
        new Rectangle(dimensions.X(),dimensions.Y());
    }

    private Rectangle(Set<Point2d> coords) {
        super(coords);
    }

    @Override
    public Rectangle translate(Point2d point) {
        super.translate(point);
        return this;
    }

    @Override
    public Rectangle rotate(Double angle) {
        super.rotate(angle);
        return this;
    }

    @Override
    public Rectangle clone() {
        double x = PointOperator.getMaxCoord(getCoords()).X()*2;
        double y = PointOperator.getMaxCoord(getCoords()).Y()*2;
        Rectangle rectangle = new Rectangle(x,y);
        return rectangle;
    }
}
