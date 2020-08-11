package Visualization;

import java.awt.geom.*;

public final class GGeometry {

    private GGeometry() {
    }

    public static Point2D add(Point2D pA, Point2D pB) {
        return new Point2D.Double(pA.getX()+pB.getX(),pA.getY()+pB.getY());
    }

    public static Point2D subtract(Point2D pA, Point2D pB) {
        return new Point2D.Double(pA.getX()-pB.getX(),pA.getY()-pB.getY());
    }

    public static Point2D average(Point2D pA, Point2D pB) {
        return new Point2D.Double((pA.getX()+pB.getX())/2,(pA.getY()+pB.getY())/2);
    }

    public static double pointProduct(Point2D pA, Point2D pB) {
        return pA.getX()*pB.getX()+pA.getY()*pB.getY();
    }

    public static double crossProduct(Point2D pA, Point2D pB) {
        return pA.getX()*pB.getY()-pA.getY()*pB.getX();
    }

    public static double crossProduct(Point2D pO, Point2D pA, Point2D pB) {
        return crossProduct(subtract(pA,pO),subtract(pB,pO));
    }

    public static double norm(Point2D pA) {
        return Math.sqrt(pointProduct(pA,pA));
    }

    public static double distance(Point2D pA, Point2D pB) {
        return pA.distance(pB);
    }

    public static double angle(Point2D pA, Point2D pB) {
        return Math.atan2(pB.getY()-pA.getY(),pB.getX()-pA.getX());
    }

    public static double angleBetween(Point2D pO, Point2D pA, Point2D pB) {
        Point2D p1=subtract(pA,pO),p2=subtract(pB,pO);
        return Math.acos(Math.max(-1.0,Math.min(1.0,pointProduct(p1,p2)/(norm(p1)*norm(p2)))));
    }

}
