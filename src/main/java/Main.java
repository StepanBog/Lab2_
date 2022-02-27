import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Integer size = 100;
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Double x = Math.random() * 100, y = Math.random() * 100;
            points.add(new Point(x, y));
        }

        int n = points.size();
        if (n < 3) throw new Exception("треугольников нет");
        ArrayList<Integer> ind = new ArrayList<>();
        for (int i = 0; i < n; i++) ind.add(i);

        Collections.sort(ind);

        // находим треугольник, содержащий все точки, и вставлем его в конец массива с вершинами
        let big = big_triangle(points);
        points.push(big[0]);
        points.push(big[1]);
        points.push(big[2]);

        let cur_points = [circumcircle_of_triangle(points, n, n + 1, n + 2)];
        let ans = [];
        let edges = [];

        // перебираем все точки
        for (let i = ind.length - 1; i >= 0; i--) {
            // перебираем все треугольники
            // если точка находится внутри треугольника, то нужно его удалить
            for (let j = cur_points.length - 1; j >= 0; j--) {
                // если точка справа от описанной окружности, то треугольник проверять больше не нужно
                // точки отсортированы и поэтому тоже будут справа
                let dx = points[ind[i]][0] - cur_points[j].x;
                if (dx > 0 && dx * dx > cur_points[j].r) {
                    ans.push(cur_points[j]);
                    cur_points.splice(j, 1);
                    continue;
                }

                // если точка вне окружности, то треугольник изменять не нужно
                let dy = points[ind[i]][1] - cur_points[j].y;
                if (dx * dx + dy * dy - cur_points[j].r > EPS) {
                    continue;
                }
                // удаляем треугольник и добавляем его стороны в список ребер
                edges.push(
                        cur_points[j].a, cur_points[j].b,
                        cur_points[j].b, cur_points[j].c,
                        cur_points[j].c, cur_points[j].a
                );
                cur_points.splice(j, 1);
            }
            // удаляем кратные ребра
            delete_multiples_edges(edges);
            // создаем новые треугольники последовательно по списку ребер
            for (let j = edges.length - 1; j >= 0;) {
                let b = edges[j]; j--;
                if (j < 0) break;
                let a = edges[j]; j--;
                cur_points.push(circumcircle_of_triangle(points, a, b, ind[i]));
            }
            edges = [];
        }
        // формируем массив с триангуляцией
        for (let i = cur_points.length - 1; i >= 0; i--) {
            ans.push(cur_points[i]);
        }
        let tr = []
        for (let i = 0; i < ans.length; i++) {
            if (ans[i].a < n && ans[i].b < n && ans[i].c < n) {
                tr.push(ans[i].a, ans[i].b, ans[i].c);
            }
        }
        console.log(tr);
        return tr;

    }


}
