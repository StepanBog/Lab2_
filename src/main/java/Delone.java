import java.util.ArrayList;
import java.util.List;

public class Delone {
    Double e = 1e-7;

    public List<Point> superTriangle(List<Point> points) {
        Double minx = Double.MAX_VALUE;
        Double maxx = Double.MIN_VALUE;
        Double miny = Double.MAX_VALUE;
        Double maxy = Double.MIN_VALUE;
        for (int i = 0; i < points.size(); i++) {
            minx = Math.min(minx, points.get(i).x);
            miny = Math.min(miny, points.get(i).y);
            maxx = Math.max(maxx, points.get(i).x);
            maxy = Math.max(maxy, points.get(i).y);
        }
        Double dx = maxx - minx, dy = maxy - miny;
        Double dxy = Math.max(dx, dy);
        Double midx = dx * 0.5 + minx, midy = dy * 0.5 + miny;
        List<Point> result = new ArrayList<>();
        result.add(new Point(midx - 10 * dxy, midy - 10 * dxy));
        result.add(new Point(midx, midy + 10 * dxy));
        result.add(new Point(midx + 10 * dxy, midy - 10 * dxy));
        return result;
    }

    public Circle circumCircleOfTriangle(Point p1, Point p2, Point p3) {
        Double dy12 = Math.abs(p1.y - p2.y);
        Double dy23 = Math.abs(p2.y - p3.y);
        Double xc;
        Double yc;
        if (dy12 < e) {
            Double m2 = -((p3.x - p2.x) / (p3.y - p2.y));
            Double mx2 = (p2.x + p3.x) / 2;
            Double my2 = (p2.y + p3.y) / 2;
            xc = (p1.x + p2.x) / 2;
            yc = m2 * (xc - mx2) + my2;
        } else if (dy23 < e) {
            Double m1 = -((p2.x - p1.x) / (p2.y - p1.y));
            Double mx1 = (p1.x + p2.x) / 2;
            Double my1 = (p1.y + p2.y) / 2;
            xc = (p2.x + p3.x) / 2;
            yc = m1 * (xc - mx1) + my1;
        } else {
            Double m1 = -((p2.x - p1.x) / (p2.y - p1.y));
            Double m2 = -((p3.x - p2.x) / (p3.y - p2.y));
            Double mx1 = (p1.x + p2.x) / 2;
            Double my1 = (p1.y + p2.y) / 2;
            Double mx2 = (p2.x + p3.x) / 2;
            Double my2 = (p2.y + p3.y) / 2;
            xc = (m1 * mx1 - m2 * mx2 + my2 - my1) / (m1 - m2);
            if (dy12 > dy23) yc = m1 * (xc - mx1) + my1;
            else yc = m2 * (xc - mx2) + my2;
        }
        Double dx = p2.x - xc, dy = p2.y - yc;
        return new Circle(xc, yc, dx * dx + dy * dy);
    }

    public List<Edge> deleteMultiplesEdges(List<Edge> edges) {
        for (int j = edges.size() - 1; j >= 0; ) {
            Edge b = edges.get(j);
            j--;
            Edge a = edges.get(j);
            j--;
            Edge n, m;
            for (int i = j; i >= 0; ) {
                n = edges.get(i);
                i--;
                m = edges.get(i);
                i--;
                if (a.equals(m) && b.equals(n)) {
                    edges.remove(j + 1);
                    edges.remove(j + 1);
                    edges.remove(i + 1);
                    edges.remove(i + 1);
                    break;
                }
                if (a.equals(n) && b.equals(m)) {
                    edges.remove(j + 1);
                    edges.remove(j + 1);
                    edges.remove(i + 1);
                    edges.remove(i + 1);
                    break;
                }
            }
        }
        return edges;
    }

}
