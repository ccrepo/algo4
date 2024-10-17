package cc.algo;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class DIJKSTRA {

  private String _solution = "";
  
  final private LinkedHashMap<Vertex, LinkedHashMap<Vertex, Integer>> _vertices = new LinkedHashMap<>();

  final private LinkedHashMap<Vertex, Integer> _scores = new LinkedHashMap<Vertex, Integer>();
      
  public DIJKSTRA(String[] lines) {

    init(lines);
  }

  public boolean solve() {
    
    if (!dijkstra()) {
      
      return false;
    }
    
    return true;
  }
  
  private boolean relax(Vertex u, Vertex v, Integer w) {
    
    if (_scores.get(v) > _scores.get(u) + w) {
      
      _scores.put(v, _scores.get(u) + w);
      
      v.pi(u);
    }
      
    return false;
  }

  private boolean initialize_single_source() {

    for (Vertex v : _scores.keySet()) {

      if (v.name() == ((Vertex) (_vertices.keySet().toArray()[0])).name()) {
        
        _scores.put(v, 0);
        
        continue;
      }

      _scores.put(v, Integer.MAX_VALUE);
    }
    
    return true;
  }

  private Vertex extract_min(Set<Vertex> Q) {
    
    Vertex v = null;
    
    int min = Integer.MAX_VALUE;

    for (Vertex u : Q) {
      
      if (_scores.get(u) < min || v == null) {
       
        v = u;
        
        min = _scores.get(u);
      }
    }
    
    if (v != null) {

      Q.remove(v);
      
      System.out.println("EXTRACT MIN: " + v.name() + " [ D = " + _scores.get(v) + " ]");
    }

    return v;
  }
  
  public boolean dijkstra() {

    if (!initialize_single_source()) {
      
      return false;
    }
    
    Set<Vertex> S = new HashSet<Vertex>();

    Set<Vertex> Q = new HashSet<Vertex>();

    for (Vertex w : _vertices.keySet()) {

      Q.add(w);
    }
    
    System.out.println("\nADJACENCY LIST: " + grid());
    
    System.out.println("Q: " + fold(Q));

    System.out.println("S: " + fold(S));      

    while(!Q.isEmpty()) {
      
      Vertex v = extract_min(Q);
      
      S.add(v);
      
      for (Vertex u : _vertices.get(v).keySet()) {

        Integer w = _vertices.get(v).get(u);
        
        relax(v, u, w);
      }

      System.out.println("\nADJACENCY LIST: " + grid());
      
      System.out.println("Q: " + fold(Q));

      System.out.println("S: " + fold(S));      
    }
    
    System.out.println("");
    
    return true;
  }
  
  public String solution() {
    
    return _solution;
  }
 
  public String grid() {
    
    StringBuilder buffer = new StringBuilder();
    
    buffer.append("{\n");
    
    for (Vertex u : _vertices.keySet()) {

      buffer.append(u.name());

      buffer.append(" [ D = ");

      if (_scores.get(u) < Integer.MAX_VALUE) {

        if (_scores.get(u) < 10) {
          
          buffer.append(" ");
        }
        
        buffer.append(" " + _scores.get(u));
      } else {
        
        buffer.append("INF");
      }

      buffer.append("  PI = ");

      if (u.pi() != null) {
        
        buffer.append(u.pi().name());
      
      } else {
        
        buffer.append(" ");
      }
      
      buffer.append(" ] -> ");

      buffer.append(" {");
      
      LinkedHashMap<Vertex, Integer> children = _vertices.get(u);
      
      for (Vertex v : children.keySet()) {
        
        buffer.append(" " + v.name() + "(" + children.get(v) + ")");
      }
            
      buffer.append(" }");

      buffer.append("\n");
    }

    buffer.append("}");

    return buffer.toString();
  }

  private void init(String[] lines) {
    
    LinkedHashMap<Character, Vertex> mapper = 
        new LinkedHashMap<>();
        
    for (String line : lines) {

      String[] splits = line.split("\\.");
      
      if (splits[0].length() != 1) {
        
        throw new IllegalArgumentException("bad parameter");
      }
      
      Vertex vertex = new Vertex(splits[0].charAt(0), 
          Vertex.COLOR.UNKNOWN);
      
      _vertices.put(vertex, new LinkedHashMap<>());
      
      mapper.put(vertex.name(), vertex);
      
      _scores.put(vertex, 0);
    }

    for (String line : lines) {

      String[] splits = line.split("\\.");

      if (splits.length < 2) {
        
        continue;
      }

      for (int i = 1; i < splits.length; i++) {
        
        String[] pair = splits[i].split("/"); // v.name and directional weight 
        
        if (pair.length != 2) {
          
          throw new IllegalArgumentException("bad parameter");
        }
        
        LinkedHashMap<Vertex, Integer> map = _vertices.get(mapper.get(splits[0].charAt(0)));
        
        Vertex v = mapper.get(pair[0].charAt(0));
        
        Integer w = Integer.parseInt(pair[1]);
        
        map.put(v, w);
      }
    }
  } 
  
  private String fold(Set<Vertex> vs) {

    StringBuilder buffer = new StringBuilder();
    
    buffer.append("[");
    
    for (Vertex v : vs) {
      
      buffer.append(" " + v.name());
    }
    
    buffer.append(" ]");
    
    return buffer.toString();
  }  
}
