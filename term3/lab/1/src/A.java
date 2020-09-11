import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class A implements Runnable {

  static class FastReader {

    private final BufferedReader br;
    private StringTokenizer st;

    public FastReader(Reader r) {
      br = new BufferedReader(r);
    }

    public FastReader() {
      this(new InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreElements()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }

  static class Control extends Exception {

  }

  enum State {
    UNTOUCHED, ENTERED, LEFT;
  }

  static class Graph {

    private final int n;
    private final int m;
    private final List<List<Integer>> g;

    public Graph(int n, int m, List<List<Integer>> g) {
      this.n = n;
      this.m = m;
      this.g = g;
    }

    public int _dfs(int i, List<State> states, List<Integer> sort, int counter)
        throws Control {
      switch (states.get(i)) {
        case LEFT:
          return counter;
        case ENTERED:
          throw new Control();
        case UNTOUCHED:
          states.set(i, State.ENTERED);
      }
      sort.set(i, counter++);
      for (Integer son : g.get(i)) {
        counter = _dfs(son, states, sort, counter);
      }
      states.set(i, State.LEFT);
      return counter;
    }

    public List<Integer> topSort() throws Control {
      List<State> states = IntStream.range(0, n)
          .mapToObj(i -> State.UNTOUCHED)
          .collect(Collectors.toCollection(ArrayList::new));
      List<Integer> sort = IntStream.range(0, n)
          .mapToObj(i -> 0)
          .collect(Collectors.toCollection(ArrayList::new));
      List<Integer> result = IntStream.range(0, n)
          .mapToObj(i -> 0)
          .collect(Collectors.toCollection(ArrayList::new));
      int counter = 0;
      for (int i = 0; i != n; ++i) {
        if (states.get(i) == State.UNTOUCHED) {
          counter = _dfs(i, states, sort, counter);
        }
      }
      for (int i = 0; i != n; ++i) {
        result.set(sort.get(i), i + 1);
      }
      return result;
    }

  }

  public static void main(String[] args) {
    new A().run();
  }

  @Override
  public void run() {
    FastReader scanner = new FastReader();
    int n = scanner.nextInt(), m = scanner.nextInt();
    List<List<Integer>> g = IntStream.range(0, n)
        .parallel()
        .mapToObj(i -> new ArrayList<Integer>())
        .collect(Collectors.toCollection(ArrayList::new));
    for (int i = 0; i != m; ++i) {
      g.get(scanner.nextInt() - 1).add(scanner.nextInt() - 1);
    }
    try {
      List<Integer> result = new Graph(n, m, g).topSort();
      for (Integer i : result) {
        System.out.print(i + " ");
      }
    } catch (Control control) {
      System.out.println(-1);
    }
  }
}
