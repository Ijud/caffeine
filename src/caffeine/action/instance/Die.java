package caffeine.action.instance;

import caffeine.action.Action;
import caffeine.entity.Entity;

public class Die implements Action {
  String cause;
  
  public Die(String cause) {
    this.cause = cause;
  }
  
  public void performBy(Entity performer) {
    performer.alive(false);
    performer.tile().remove(performer);
    System.out.println(performer + " has died from " + cause);
  }
}
