package Parallel;

import java.io.Serializable;

/**
 * Created by Yevgen on 16.05.2015.
 */
public class Distances implements Serializable {
    public int n;
    public long d[];
    void Init(int N) {
        n = N;
        d = new long[n];
        d[0] = 0;
        for (int i = 1; i < n; ++i) { d[i] = (long)Graph.INF * (long)Graph.INF; }
    }
}
