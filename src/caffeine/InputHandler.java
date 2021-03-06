package caffeine;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pixl.interaction.SimpleInputListener;

public class InputHandler extends SimpleInputListener {
  public class Key {
    public boolean isClicked, isPressed;
    public int presses, absorbs;
    
    public Key() {
      keys.add(this);
    }
    
    public void toggle(boolean pressed) {
      if (isPressed != pressed) {
        isPressed = pressed;
        if (pressed) {
          presses++;
        }
      }
    }
    
    public void tick() {
      if (absorbs < presses) {
        absorbs++;
        isClicked = true;
      } else {
        isClicked = false;
      }
    }
  }
  
  public List<Key> keys = new ArrayList<Key>();
  public Key up = new Key();
  public Key down = new Key();
  public Key left = new Key();
  public Key right = new Key();
  public Key use = new Key();
  public Key jump = new Key();
  public Key menu = new Key();
  
  public List<Key> getKeys() {
    return keys;
  }
  
  public void releaseAll() {
    for (int i = 0; i < keys.size(); i++) {
      keys.get(i).isPressed = false;
    }
  }
  
  public void tick() {
    for (int i = 0; i < keys.size(); i++) {
      keys.get(i).tick();
    }
  }
  
  public void toggle(int keycode, boolean pressed) {
    if (keycode == KeyEvent.VK_UP) {
      up.toggle(pressed);
    }
    if (keycode == KeyEvent.VK_DOWN) {
      down.toggle(pressed);
    }
    if (keycode == KeyEvent.VK_LEFT) {
      left.toggle(pressed);
    }
    if (keycode == KeyEvent.VK_RIGHT) {
      right.toggle(pressed);
    }
    if (keycode == KeyEvent.VK_X) {
      use.toggle(pressed);
    }
    if (keycode == KeyEvent.VK_Z) {
      jump.toggle(pressed);
    }
  }
  
  public void keyPressed(KeyEvent event) {
    toggle(event.getKeyCode(), true);
  }
  
  public void keyReleased(KeyEvent event) {
    toggle(event.getKeyCode(), false);
  }
}
