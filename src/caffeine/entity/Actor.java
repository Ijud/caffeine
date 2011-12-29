package caffeine.entity;

import caffeine.action.Action;
import caffeine.action.Motion;
import caffeine.action.StaticMotion;
import caffeine.entity.brain.Brain;
import caffeine.entity.brain.LeftBrain;
import caffeine.world.Location;

public class Actor extends Entity{
	public int attack = 1;
	public int attackRadius = 12;
	public int health = 10;
	protected boolean isAlive = true;
	protected Brain brain = new LeftBrain();
	protected Motion motion;

	public Actor(Location l){
		super(l);
		motion = new StaticMotion(loc);
	}

	public Motion motion(){return motion;}

	public boolean alive(){return isAlive;}

	public void alive(Boolean b){isAlive = b;}

	public void brain(Brain b){
		brain = b;
	}

	public void tick(){
		if(isAlive){
			for(Action a : brain.next(this)){
				a.perform(this);
			}
			loc.tile().remove(this);
			motion.tick();
			loc.tile().add(this);
		}
	}
}
