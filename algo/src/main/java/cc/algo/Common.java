package cc.algo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Common {

  static public String[] reorder(String[] lines, String order) {

    Map<Character, String> lookup = new HashMap<Character, String>();

    ArrayList<String> buffer = new ArrayList<>();

    if (lines.length != order.length()) {
      
      throw new IllegalArgumentException("bad arguments");
    }

    for (String line : lines) {
      
      lookup.put(line.charAt(0), line);
    }
          
    for (int c : order.chars().toArray()) {

      buffer.add(lookup.get((char) c));
    }      

    return buffer.toArray(new String[buffer.size()]);
  }
  
  static public String[] transpose(String[] lines) {
    
    List<String> buffer = new ArrayList<>();

    Map<Character, Set<Character>> scratch = new HashMap<>();

    for (String line : lines) {
      
      char key = line.charAt(0);
      
      if (!scratch.containsKey(key)) {
        
        scratch.put(key, new HashSet<>());
      } 
    }

    for (String line : lines) {
      
      if (line.length() <= 1) {
        
        continue;
      }
      
      char key = line.charAt(0);

      for (int c : line.substring(1).chars().sorted().toArray()) {

        scratch.get((char) c).add(key);
      }      
    }

    List<Character> keys = scratch.keySet().stream().sorted().collect(Collectors.toList());

    for (Character key : keys) {

      StringBuilder buffer2 = new StringBuilder();
      
      buffer2.append(key);
      
      for (Character c : scratch.get(key).stream().sorted().collect(Collectors.toList())) {
        
        buffer2.append(c);
      }
      
      buffer.add(buffer2.toString());
    }

    Collections.sort(buffer);

    return buffer.toArray(new String[buffer.size()]);
  }
}
