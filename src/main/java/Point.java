import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point implements Comparable<Point>{
    public Double x;
    public Double y;

    @Override
    public int compareTo(Point p) {
        return this.x > p.x ? 1 : 0;
    }
}
