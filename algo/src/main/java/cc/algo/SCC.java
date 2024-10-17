package cc.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class SCC {

  final private LinkedHashMap<Vertex, List<Vertex>> _vertices = new LinkedHashMap<>();

  final private String _name;
  
  private StringBuilder _solution_starts = new StringBuilder();

  private StringBuilder _solution_stops = new StringBuilder();

  private String _solution = "";

  private int _time = 0;
  
  public SCC(String[] lines, String name) {

    _name = name;
    
    init(lines);
  }

  private boolean topological_sort(List<Vertex> starts, List<Vertex> stops) {
      
    if (!dfs(starts, stops)) {

      return false;
    }
    
    return true;
  }

  private boolean dfs(List<Vertex> starts, List<Vertex> stops) {
    
    for (Vertex v : _vertices.keySet()) {
      
      v.color(Vertex.COLOR.WHITE);
      
      v.pi(null);
    }
    
    _time = 0;

    System.out.println("grid " + _name + ": " + grid());

    for (Vertex v : _vertices.keySet()) {
      
      if (v.color() == Vertex.COLOR.WHITE) {
        
        if (!dfs_visit(v, starts, stops)) {
          
          return false;
        }
      }
    }
    
    return true;
  }

  private boolean dfs_visit(Vertex u, List<Vertex> starts, List<Vertex> stops) {
    
    _time++;
    
    u.start_time(_time);

    u.color(Vertex.COLOR.GRAY);

    starts.add(0, u);

    //System.out.println("grid " + _name + "[" + _time + "]: name:" + u.name() + " " + grid());

    for (Vertex v : _vertices.get(u)) {
      
      if (v.color() == Vertex.COLOR.WHITE) {
        
        v.pi(u);
        
        if (!dfs_visit(v, starts, stops)) {
          
          return false;
        }
      }
    }

    u.color(Vertex.COLOR.BLACK);

    //System.out.println("grid " + _name + "[" + _time + "]: name:" + u.name() + " " + grid());

    stops.add(0, u);
    
    _time++;

    u.end_time(_time);

    //System.out.println("grid " + _name + "[" + _time + "]: name:" + u.name() + " " + grid());

    return true;
  }

  public boolean solve() {
    
    ArrayList<ArrayList<Vertex>> sorts = new ArrayList<>();

    sorts.add(new ArrayList<Vertex>());
    
    sorts.add(new ArrayList<Vertex>());

    if (!topological_sort(sorts.get(0), sorts.get(1))) {
      
      return false;
    }
    
    if (sorts.size() != sorts.size()) {
    
      return false;
    }

    if (!set_solutions(sorts)) {
      
      return false;
    }
    
    return true;
  }
  
  private boolean set_solutions(ArrayList<ArrayList<Vertex>> sorts) {
  
    StringBuilder[] solutions = new StringBuilder[] { _solution_starts, _solution_stops };

    for (int i = 0; i < sorts.size(); i++) {
    
      ArrayList<Vertex> sort = sorts.get(i);
      
      StringBuilder buffer = new StringBuilder();

      buffer.append("{\n");
      
      for (int j = 0; j < sort.size(); j++) {
        
        Vertex v = sort.get(j);
      
        buffer.append(v.toString());

        buffer.append(" -> ");

        for (Vertex u : _vertices.get(v)) {
        
          buffer.append(" " + u.name());
        }

        buffer.append(" ]\n");
      }

      buffer.append("}");
      
      solutions[i].setLength(0);

      solutions[i].append(buffer.toString());
    }
      
    StringBuilder buffer = new StringBuilder();

    for (Vertex v : sorts.get(1)) {
      
      buffer.append(v.name() + " ");
    }

    _solution = buffer.toString();
    
    return true;
  }
  
  public String solution_starts() {
    
    return _solution_starts.toString();
  }

  public String solution_stops() {
    
    return _solution_stops.toString();
  }

  public String solution() {
    
    return _solution;
  }

  public String grid() {
    
    StringBuilder buffer = new StringBuilder();
    
    buffer.append("{\n");
    
    for (Vertex u : _vertices.keySet()) {

      buffer.append(u.toString());
      
      buffer.append(" -> ");

      buffer.append("[");

      for (Vertex v : _vertices.get(u)) {
        
        buffer.append(" " + v.name());
      }
            
      buffer.append(" ]");

      buffer.append("\n");
    }

    buffer.append("}");

    return buffer.toString();
  }

  private void init(String[] lines) {
    
    LinkedHashMap<Character, Vertex> mapper = 
        new LinkedHashMap<>();
        
    for (String line : lines) {
      
      Vertex vertex = new Vertex(line.charAt(0), 
          Vertex.COLOR.UNKNOWN);
      
      _vertices.put(vertex, new ArrayList<>());
      
      mapper.put(vertex.name(), vertex);
    }

    for (String line : lines) {

      if (line.length() < 2) {
        
        continue;
      }
      
      for (int c : line.substring(1).chars().sorted().toArray()) {
        
        _vertices.get(mapper.get(line.charAt(0))).add(
            mapper.get((char) c));
      }
    }
  }  
  
  public StringBuilder[] partition() {
    
    Set<Vertex> vertices = new HashSet<Vertex>();

    List<StringBuilder> result = new ArrayList<>();
    
    _vertices.keySet().stream().forEach(v->{ vertices.add(v); });

    for (Vertex v : _vertices.keySet()) {

      if (v.pi() == null) {
        
        vertices.remove(v);
      
        StringBuilder buffer = new StringBuilder();

        buffer.append(v.name());
        
        result.add(buffer);
        
        partition(v, buffer, vertices);
      }
    }
    
    return result.toArray(new StringBuilder[result.size()]);
  }

  public void partition(Vertex v, StringBuilder buffer, Set<Vertex> vertices) {

    boolean found = true;
    
    while (found) {

      found = false;
      
      for (Vertex w : vertices) {
       
        if (w.pi() != null &&
            w.pi().name() == v.name()) {

          vertices.remove(v);
          
          buffer.append(" -> ");

          buffer.append(w.name());
          
          found = true;
          
          partition(w, buffer, vertices);
          
          return;
        }
      }
    } 
  }
  
}
