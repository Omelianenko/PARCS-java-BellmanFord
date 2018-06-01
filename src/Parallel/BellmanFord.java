package Parallel;

import parcs.*;

import java.util.ArrayList;

public class BellmanFord implements AM {
    public static void main() {
        task curtask = new task();
        curtask.addJarFile("PARCS.jar");
        (new BellmanFord()).run(new AMInfo(curtask, (channel) null));
        curtask.end();
    }

    public void run(AMInfo info) {
        long start = System.currentTimeMillis();

        int num_of_threads = 1;
        point[] P = new point[num_of_threads];
        channel[] C = new channel[num_of_threads];
        System.out.println("Random initialization...");
        Graph g = new Graph();
        g.RandInit();
        for (int i = 0; i < num_of_threads; ++i) {
            P[i] = info.createPoint();
            C[i] = P[i].createChannel();
            P[i].execute(OneStep.class.getName());
            Graph cur_g = g.Filtered(num_of_threads, i);
            C[i].write(cur_g);
        }
        Distances d = new Distances();
        d.Init(g.n);
        Distances cur_d;
        System.out.println("Waiting for result...");
        for (int step = 0; step < g.n - 1; ++step) {
            //System.out.println("Step " + step);
            for (int i = 0; i < num_of_threads; ++i) {
                C[i].write(d);
            }
            for (int i = 0; i < num_of_threads; ++i) {
                cur_d = (Distances) C[i].readObject();
                for (int j = 0; j < g.n; ++j) {
                    d.d[j] = Math.min(d.d[j], cur_d.d[j]);
                }
            }
        }
        System.out.println("Result found.");
        System.out.println(d.n);
        for (int i = 0; i < d.n; ++i) { System.out.print(d.d[i] + " "); }
        System.out.println();
        long timeSpent = System.currentTimeMillis() - start;
        System.out.println("timeSpent = " + timeSpent);
    }
}
