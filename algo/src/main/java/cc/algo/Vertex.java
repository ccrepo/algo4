package cc.algo;

public class Vertex {

  public enum COLOR { UNKNOWN, BLACK, WHITE, GRAY };
  
  final private Character _name;
  
  // NOTE: added so not to waste time rewriting this to use string not char names.
  // TODO fix
  final private boolean _extended; 

  private Vertex _PI = null;
  
  private COLOR _color;

  private Integer _start_time = 0;
  
  private Integer _end_time = 0;
  
  public Vertex(char name, COLOR color) {
    
    this(name, color, false);
  }
  
  private Vertex(char name, COLOR color, boolean extended) {
    
    _name = name;
    
    _color = color;
    
    _extended = extended;
  }

  public Character name() {
    
    return _name;
  }

  //public int nameI() {
  //  
  //  return (int) _name;
  //}

  public COLOR color() {
    
    return _color;
  }

  public void color(COLOR color) {
    
    _color = color;
  }

  public int start_time() {
    
    return _start_time;
  }

  public void start_time(int time) {
    
    _start_time = time;
  }

  public int end_time() {
    
    return _end_time;
  }
  
  public void end_time(int time) {
    
    _end_time = time;
  }

  public void pi(Vertex PI) {
    
    _PI = PI;
  }
  
  public Vertex pi() {
    
    return _PI;
  }

  public String toString() {
    
    StringBuilder buffer = new StringBuilder();
    
    buffer.append("{ ");
    
    if (!_extended) {
     
      buffer.append(_name);
    
    } else {
      
      buffer.append((int) (_name));
    }
    
    switch (_color) {

    case BLACK:
      buffer.append(" color: BLACK");
      break;

    case GRAY:
      buffer.append(" color: GRAY");
      break;

    case WHITE:
      buffer.append(" color: WHITE");
      break;

    case UNKNOWN:
      break;

    default:
      buffer.append(" color: " + _color);
    } 

    buffer.append(" ts[");
    
    buffer.append(String.format("%2d", start_time()));
    
    buffer.append("-");
    
    buffer.append(String.format("%-2d", end_time()));

    buffer.append("]");

    buffer.append(" pi: ");

    buffer.append(_PI != null ? _PI.name() : " ");
    
    buffer.append(" }");    
    
    return buffer.toString();
  }
}
