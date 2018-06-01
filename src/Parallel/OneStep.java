package Parallel;

import parcs.AM;
import parcs.AMInfo;

public class OneStep implements AM {
    public void run(AMInfo info) {
        Graph g = (Graph)info.parent.readObject();
        System.out.println("Graph read.");
        Distances d;
        for (int step = 0; step < g.n - 1; ++step) {
            d = (Distances)info.parent.readObject();
            //System.out.println("Distances read. Step " + step);
            for (int i = 0; i < g.m; ++i) {
                int v = g.from[i];
                int to = g.to[i];
                int w = g.w[i];
                d.d[to] = Math.min(d.d[to], d.d[v] + w);
            }
            info.parent.write(d);
            //System.out.println("Distances wrote. Step " + step);
        }
    }
}
