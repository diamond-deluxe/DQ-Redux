package RogueLike.Main.AI;

import java.util.ArrayList;

import RogueLike.Main.Creature;
import RogueLike.Main.World;
import RogueLike.Main.Factories.ObjectFactory;

public class MimicAI extends CreatureAI{
	private Creature player;
	private int turnsWithoutPlayer = 0;
	
	public MimicAI(Creature creature, Creature player, ObjectFactory factory, World world) {
		super(creature, factory, world);
		this.player = player;
	}
	
	public void selectAction() {
		actionQueue = new ArrayList<Integer>();
		if(turnsWithoutPlayer >= 10) {
			//Hide
			actionQueue.add(1);
			actionQueue.add(0);
			System.out.println(this.toString() + " uses [Hide]");
		}else if(creature.canSee(player.x, player.y, player.z) && player.isInvisible() == false) {
			//Hunt
			actionQueue.add(2);
			actionQueue.add(1000);
			System.out.println(this.toString() + " uses [Hunt Player]");
		}else {
			//Wander
			actionQueue.add(3);
			actionQueue.add(1000);
			System.out.println(this.toString() + " uses [Wander]");
		}
	}
	
	public void decodeAction(int action) {
		switch(action) {
			case 1: this.creature.hide(); turnsWithoutPlayer = 0; break;
			case 2: this.hunt(player); break;
			default: this.wander(); break;
		}
	}
	
	public void onUpdate() {
		if(creature.isDisguised() == true) {
			return;
		}
		
		if((creature.isParalyzed() == true)) {
			if((int)(Math.random()*10) < 8) {
				creature.doAction("struggle to move!");
				return;
			}else {
				creature.doAction("move with difficulty");
			}
		}
		
		if((creature.isFrozen() == true)) {
			creature.doAction("struggle to move!");
			return;

		}else{
			if(!creature.canSee(player.x, player.y, player.z) || player.isInvisible() == true) {
				turnsWithoutPlayer++;
			}
			decodeAction(actionQueue.get(0)); 
		}
	}
	
}
