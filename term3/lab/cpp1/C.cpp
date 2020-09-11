//
// Created by covariance on 05.09.2020.
//
#include <bits/stdc++.h>

#define INF 1e9
#define fs first
#define sc second

using namespace std;

vector<vector<pair<int, int>>> g;
vector<int> dist;



int main() {
  int n, m;
  cin >> n >> m;
  g.resize(n);
  dist.resize(n, 0);
  for (int i = 0; i != m; ++i) {
    int from, to;
    size_t weight;
    cin >> from >> to >> weight;
    from--;
    to--;
    g[from].emplace_back(weight, to);
  }

  
}
