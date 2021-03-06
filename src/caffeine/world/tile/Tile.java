package caffeine.world.tile;

import java.io.Serializable;

import pixl.Screen;
import caffeine.entity.Collideable;
import caffeine.entity.Entity;
import caffeine.entity.ItemEntity;
import caffeine.items.Item;
import caffeine.world.Dir;
import caffeine.world.Map;

public class Tile implements Serializable, Collideable {
  private static final long serialVersionUID = -4410353113874468565L;
  private long time = System.currentTimeMillis();
  
  public static Tile read(int x, int y, char data) {
    Tile tile = new Tile(x, y);
    if (data == 'm')
      tile.type = TileType.GRASS;
    if (data == '#') {
      TileObject bush = new Bush();
      tile.type = bush.type;
      tile.hold(bush);
    }
    if (data == 'D') {
      TileObject stone = new Stone();
      tile.type = stone.type;
      tile.hold(stone);
    }
    if (data == '~')
      tile.type = TileType.WATER;
    return tile;
  }
  
  protected int x, y;
  protected TileType type = TileType.DIRT;
  protected TileObject tileObject = null;
  
  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public boolean blocksNPC() {
    if (tileObject != null) {
      return tileObject.blocksNPC();
    }
    return false;
  }
  
  public boolean blocksPC() {
    if (tileObject != null) {
      return tileObject.blocksPC();
    }
    return false;
  }
  
  public long getAge() {
    return System.currentTimeMillis() - time;
  }
  
  public int getSprite() {
    if (tileObject != null) {
      return tileObject.getSprite();
    }
    return type.getSprite();
  }
  
  public char getSymbol() {
    if (tileObject != null) {
      return tileObject.getSymbol();
    }
    return type.getChar();
  }
  
  public void hold(TileObject tileObject) {
    this.tileObject = tileObject;
  }
  
  public boolean interact(Entity entity, Item item, Dir dir) {
    if (tileObject != null) {
      tileObject.interact(entity, item, dir);
      
      if (tileObject.isRemoved()) {
        Item dropped = tileObject.itemDropped();
        if (dropped != null) {
          Entity ie = new ItemEntity(dropped);
          int ts = Map.tileSize;
          ie.setLoc(x * ts + ts / 2, y * ts + ts / 2, 0);
          entity.getMap().addEntity(ie);
        }
        time = System.currentTimeMillis();
        tileObject = null;
      }
    }
    return true;
  }
  
  public void onEnter(Entity entity) {
  }
  
  public void setSprite(int spriteId) {
    this.sprite = spriteId;
  }
  
  int sprite = 1;
  public void render(Screen screen, Map map, int x, int y) {
    
    int sprite = getSprite(map);
    
    screen.render(sprite, x, y);
    if (tileObject != null) {
      tileObject.render(screen, this, x, y);
    }
  }

  private int getSprite(Map map) {
    boolean u = map.getTileSafe(this.x, this.y - 1).type == type;
    boolean d = map.getTileSafe(this.x, this.y + 1).type == type;
    boolean l = map.getTileSafe(this.x - 1, this.y).type == type;
    boolean r = map.getTileSafe(this.x + 1, this.y).type == type;
    
    int sprite = type.getSprite();
    
    if (sprite >= 32) {
      if (!u && d && !l && r)
        sprite += 1;
      if (!u && d && l && r)
        sprite += 2;
      if (!u && d && l && !r)
        sprite += 3;
      if (u && d && !l && r)
        sprite += 4;
      if (u && d && l && !r)
        sprite += 5;
      if (u && !d && !l && r)
        sprite += 6;
      if (u && !d && l && r)
        sprite += 7;
      if (u && !d && l && !r)
        sprite += 8;
      if (!u && !d && !l && !r)
        sprite += 9;
      if (u && d && !l && !r)
        sprite += 10;
      if (!u && !d && l && r)
        sprite += 11;
      if (!u && !d && l && !r)
        sprite += 12;
      if (!u && !d && !l && r)
        sprite += 13;
      if (u && !d && !l && !r)
        sprite += 14;
      if (!u && d && !l && !r)
        sprite += 15;
    }
    return sprite;
  }
  
  public void resetTime() {
    time = System.currentTimeMillis();
  }
  
  public void tick(double ticksPerSecond) {
    TileType.tick(this);
    if (tileObject != null) {
      tileObject.tick();
    }
  }
  
  @Override
  public String toString() {
    return "" + getSymbol();
  }

  @Override
  public boolean collides(double left, double top, double right, double bottom) {
    int width = Map.tileSize;
    int length = Map.tileSize;
    return !(x + width < left || y + length < top || x - width > right || y
        - length > bottom);
  }

  @Override
  public boolean onCollide() {
    return false;
  }

}