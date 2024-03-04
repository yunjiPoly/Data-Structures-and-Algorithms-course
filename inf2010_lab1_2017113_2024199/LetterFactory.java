package tp1;

import java.util.Set;

public final class LetterFactory {
    final static Double maxHeight = 200.0;
    final static Double maxWidth = maxHeight / 2;
    final static Double halfMaxHeight = maxHeight / 2;
    final static Double halfMaxWidth = maxWidth / 2;
    final static Double stripeThickness = maxHeight / 10;

    public static BaseShape create_H() {
        BaseShape leftStripe = new Rectangle(stripeThickness, maxHeight)
                .translate(new Point2d(-halfMaxWidth, 0.0));
        BaseShape rightStripe = new Rectangle(stripeThickness, maxHeight)
                .translate(new Point2d(halfMaxWidth, 0.0));
        BaseShape centerStripe = new Rectangle(maxWidth,stripeThickness);
        BaseShape H = new BaseShape();
        H.add(leftStripe);
        H.add(rightStripe);
        H.add(centerStripe);
        return H;
    }

    public static BaseShape create_e() {
        BaseShape e = new BaseShape();
        e = create_o();
        e.remove(new Rectangle(halfMaxWidth,2*stripeThickness).translate(new Point2d(halfMaxWidth/2,stripeThickness)));
        e.add(new Rectangle(maxWidth,stripeThickness));
        return e;
    }

    public static BaseShape create_l() {
        BaseShape l = new Rectangle(stripeThickness+1.5, maxHeight);
        return l;
    }

    public static BaseShape create_o() {
        BaseShape O = new BaseShape();
        O.add(new Ellipse(halfMaxWidth,halfMaxHeight));
        O.remove(new Ellipse(halfMaxWidth-stripeThickness,halfMaxHeight-stripeThickness));
        return O;
    }

    public static BaseShape create_W() {
        BaseShape W = new BaseShape();

        double rightTilt = Math.PI/25;
        double leftTilt = -Math.PI/25;

        double distance = maxHeight*Math.sin(rightTilt);

        BaseShape W1 = new BaseShape();
        W1 = create_l().rotate(leftTilt).translate(new Point2d(-3*distance/2,0.0));

        BaseShape W2 = new BaseShape();
        W2 = create_l().rotate(rightTilt).translate(new Point2d(-distance/2,0.0));

        BaseShape W3 = new BaseShape();
        W3 = create_l().rotate(leftTilt).translate(new Point2d(distance/2,0.0));

        BaseShape W4 = new BaseShape();
        W4 = create_l().rotate(rightTilt).translate(new Point2d(3*distance/2,0.0));

        W.add(W1);
        W.add(W2);
        W.add(W3);
        W.add(W4);
        return W;
    }

    public static BaseShape create_r() {
        BaseShape r = new BaseShape();
        r = create_l().translate(new Point2d(-halfMaxWidth+stripeThickness/2,0.0));
        BaseShape rTop = new Circle(halfMaxWidth);
        rTop.remove(new Circle(halfMaxWidth-stripeThickness));
        rTop.remove(new Square(maxWidth).translate(new Point2d(0.0,halfMaxWidth)));
        rTop.translate(new Point2d(0.0,-halfMaxWidth+stripeThickness/2));
        r.add(rTop);
        return r;
    }

    // On vous donne la lettre d comme exemple.
    public static BaseShape create_d() {
        Double hookRadius = maxWidth / 2;
        BaseShape mainHook = new Circle(hookRadius);
        BaseShape mainHookToRemove = new Circle(hookRadius - stripeThickness);
        BaseShape mainStripe = new Rectangle(stripeThickness, maxHeight)
                .translate(new Point2d(hookRadius - stripeThickness / 2, 0.0));
        mainHook.remove(mainHookToRemove);
        mainHook = mainHook.translate(new Point2d(0.0, maxHeight / 2.1 - hookRadius));
        mainHook.add(mainStripe);
        return mainHook;
    }
}
