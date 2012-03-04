package caffeine.action;

import caffeine.entity.Actor;
import caffeine.entity.Projectile;

public class CreateProjectile implements Action {
  
  public void perform(Actor performer) {
    new Projectile(performer, 100);
  }
  
}