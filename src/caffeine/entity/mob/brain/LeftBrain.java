package caffeine.entity.mob.brain;

import caffeine.entity.mob.Mob;
import caffeine.world.Dir;

public class LeftBrain extends Brain {
  protected Dir dir = Dir.S;
  protected final double turnThresh = .99;
  protected boolean lastFailed = false;
  private int timetick = 0;

  public LeftBrain(Mob self) {
    super(self);
  }

  @Override
  public void tick() {

  }
}
