package caffeine.world.tile;

import caffeine.entity.Entity;
import caffeine.items.Item;
import caffeine.items.ItemType;
import caffeine.world.Dir;
import caffeine.world.Map;

public class StoneTile extends Tile {

  private int hp = 10;

  public StoneTile(Map map, int x, int y) {
    super(map, x, y);
    baseSprite = map.getBackground();
    maskSprite = 32;
  }


  public boolean interact(Entity entity, Item item, Dir dir) {
    if (item.getType() == ItemType.tool) {
      hp--;
    }
    if (hp <= 0) {
      map.setTile(x, y, new DefaultTile(map, x, y));
    }
    return true;
  }


  @Override
  public boolean blocksPC() {
    return true;
  }


  @Override
  public boolean blocksNPC() {
    return true;
  }


  @Override
  public boolean isSafe() {
    return true;
  }


  @Override
  public char getSymbol() {
    return 'D';
  }

  @Override
  public void onEnter(Entity entity) {

  }
}
