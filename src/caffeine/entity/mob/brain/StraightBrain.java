package caffeine.entity.mob.brain;

import caffeine.entity.mob.Mob;
import caffeine.world.Dir;

public class StraightBrain extends Brain {
  protected Dir forward;
  private int timetick = 0;
  public StraightBrain(Mob self) {
    super(self);
    forward = self.getDir();
  }

  public void setForward(Dir dir) {
    forward = dir;
  }

  @Override
  public void tick() {
  }
}
