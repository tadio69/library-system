import java.util.ArrayList;
import java.util.List;

public class ActivityStack {
  private List<String> stack = new ArrayList<>();

  public ActivityStack(){}
  
  public void push(String activity){
    stack.add(activity);
  }

  public String pop(){
    if(!stack.isEmpty()){
      return stack.remove(stack.size() - 1);
    }
    return null;
  }

  public String peek(){
    if(!stack.isEmpty()){
      return stack.get(stack.size() - 1);
    }
    return null;
  }

  public List<String> getStack() {
      return stack;
  }
}
