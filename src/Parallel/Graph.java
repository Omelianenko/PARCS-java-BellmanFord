package Parallel;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Yevgen on 16.05.2015.
 */
public class Graph implements Serializable {
    public static final int INF = 1000000000;
    public int n, m;
    public int[] from, to, w;

    private void InitArrays() {
        from = new int[m];
        to = new int[m];
        w = new int[m];
    }

    public void RandInit() {
        n = 2000;
        m = n * n / 2;
        InitArrays();
        Random r = new Random();
        for (int i = 0; i < m; ++i) {
            from[i] = r.nextInt(n);
            to[i] = r.nextInt(n);
            w[i] = r.nextInt(INF);
        }
    }

    public Graph Filtered(int threads, int id) {
        Graph res = new Graph();
        res.n = n;
        res.m = m / threads;
        if (id < m % threads) { res.m++; }
        res.InitArrays();
        for (int i = id; i < m; i += threads) {
            res.from[i / threads] = from[i];
            res.to[i / threads] = to[i];
            res.w[i / threads] = w[i];
        }
        return res;
    }
}


