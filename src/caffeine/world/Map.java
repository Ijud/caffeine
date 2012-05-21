package caffeine.world;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import caffeine.entity.Boulder;
import caffeine.entity.Entity;
import caffeine.view.Spritesheet;

public class Map implements Iterable<Tile> {
  private static int numMaps = 0;
  protected int id;
  protected int numRows, numCols;
  protected static int tileSize = 32;
  protected Tile[][] map;

  private static String defaultMapString = "40 20 "
      + "########################################"
      + "#...#...#...#..........................."
      + "#......................................."
      + "#.....#................................."
      + "##..#..................................."
      + "#.........#............................."
      + "#..#....#....#.........................."
      + "#......#.#.............................."
      + "##....#...#............................."
      + "#......#.#.............................."
      + "#....#..#..............................."
      + "#...........#..........................."
      + "##.........#............................"
      + "#.....#................................."
      + "#......................................."
      + "#......................................."
      + "#......................................."
      + "#......................................."
      + "#......................................."
      + "########################################";

  public Map() {
    this(Map.defaultMapString);
  }

  public Map(int cols, int rows) {
    id = Map.numMaps++;
    numRows = rows;
    numCols = cols;
    map = new Tile[cols][rows];

    for (int y = 0; y < numCols; y++) {
      for (int x = 0; x < numRows; x++) {
        map[x][y] = new Tile(x, y, Map.tileSize, this);
      }
    }
  }

  public Map(String s) {
    Scanner scans = new Scanner(s);
    id = Map.numMaps++;
    numCols = Integer.parseInt(scans.next());
    numRows = Integer.parseInt(scans.next());
    map = new Tile[numCols][numRows];

    String line = scans.next();
    for (int i = 0; i < numRows * numCols; i++) {
      int x = i % numCols;
      int y = i / numCols;
      char c = line.charAt(i);
      Tile t = new Tile(x, y, Map.tileSize, this);
      map[x][y] = t;
      if (c == '#') {
        t.addEntity(new Boulder(new Loc(id,
            x * Map.tileSize + Map.tileSize / 2, y * Map.tileSize
                + Map.tileSize / 2)));
        t.setPass(false);
      }

    }
  }

  public int getID() {
    return id;
  }

  public List<Tile> getOverlappingTiles(Rectangle r) {
    List<Tile> overlapping = new LinkedList<Tile>();
    for (int y = r.y / Map.tileSize; y <= (r.y + r.height) / Map.tileSize; y++) {
      for (int x = r.x / Map.tileSize; x <= (r.x + r.width) / Map.tileSize; x++) {
        overlapping.add(getTile(x, y));
      }
    }
    return overlapping;
  }

  private Tile getTile(int x, int y) {
    if (x < 0) {
      x = 0;
    }
    if (y < 0) {
      y = 0;
    }
    if (x >= numCols) {
      x = numCols - 1;
    }
    if (y >= numRows) {
      y = numRows - 1;
    }
    return map[x][y];
  }

  public Tile getTileAt(int x, int y) {
    return getTile(x / Map.tileSize, y / Map.tileSize);
  }

  public int numCols() {
    return numCols;
  }

  public int numRows() {
    return numRows;
  }

  public int height() {
    return numRows * Map.tileSize;
  }

  public int width() {
    return numCols * Map.tileSize;
  }

  public int tileSize() {
    return Map.tileSize;
  }

  public boolean onMap(int x, int y) {
    return 0 <= x && x < numCols * Map.tileSize && 0 <= y
        && y < numRows * Map.tileSize;
  }

  public boolean inRange(int x, int y) {
    return x >= 0 && x < numCols && y >= 0 && y < numRows;
  }

  public void tick() {
    Collection<Entity> entities = new LinkedList<Entity>();
    Iterator<Tile> tileIterator = iterator();

    while (tileIterator.hasNext()) {
      Tile t = tileIterator.next();
      entities.addAll(t.occupants());
      t.tick();
    }
    for (Entity e : entities) {
      e.tick(this);
    }
  }

  @Override
  public String toString() {
    String s = "map " + numCols + " " + numRows + " " + Map.tileSize + " ";
    for (int y = 0; y < numRows; y++) {
      for (int x = 0; x < numCols; x++) {
        s += map[x][y];
      }
    }
    s += "\n";
    return s;
  }

  public void renderTiles(Graphics2D g2, Spritesheet tilesheet) {
    /* Draw the world, tile by tile */
    for (int y = 0; y < numRows; y++) {
      for (int x = 0; x < numCols; x++) {

        Tile t = getTile(x, y);
        int spriteID = t.getSpriteID();
        Image img = tilesheet.get(spriteID);

        g2.drawImage(img, x * Map.tileSize, y * Map.tileSize, Map.tileSize,
            Map.tileSize, null);
      }
    }
  }

  public void renderEntities(Graphics2D g2) {
    for (Entity e : entities()) {
      e.render(g2);
    }
  }

  @Override
  public Iterator<Tile> iterator() {
    return new Iterator<Tile>() {
      int x = 0;
      int y = 0;

      @Override
      public boolean hasNext() {
        return inRange(x, y);
      }

      @Override
      public Tile next() {
        Tile t = getTile(x, y);
        if (++x == numCols) {
          x = 0;
          y++;
        }
        return t;
      }

      @Override
      public void remove() {
        // :V Yeah, no.
      }
    };
  }

  public List<Entity> entities() {
    List<Entity> entities = new LinkedList<Entity>();
    Iterator<Tile> tileIt = iterator();
    while (tileIt.hasNext()) {
      Tile t = tileIt.next();
      entities.addAll(t.occupants());
    }
    return entities;
  }

}