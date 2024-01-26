package RogueLike.Main.AI;

import java.util.ArrayList;

import RogueLike.Main.Creature;
import RogueLike.Main.World;
import RogueLike.Main.Factories.ObjectFactory;

public class FungusAI extends CreatureAI{
	private int spreadcount;

	public FungusAI(Creature creature, ObjectFactory factory, World world) {
		super(creature, factory, world);
		this.factory = factory;
		
	}
	
	public void selectAction() {
		actionQueue = new ArrayList<Integer>();
		if(spreadcount < 3 && Math.random() < 0.0025) {
			//Spread
			actionQueue.add(1);
			actionQueue.add(0);
			System.out.println(this.toString() + " uses [Spread]");
		}else {
			//Idle
			actionQueue.add(2);
			actionQueue.add(1000);
			System.out.println(this.toString() + " uses [Idle]");
		}
	}
	
	public void decodeAction(int action) {
		switch(action) {
			case 1: this.spread(); break;
			case 2: break;
			default: break;
		}
	}
	
	public void onUpdate() {
		if(creature.isParalyzed() == false && creature.isFrozen() == false) {
			decodeAction(actionQueue.get(0)); 
		}
	}
	
	private void spread() {
		int x = creature.x + (int)(Math.random()*11) - 6;
		int y = creature.y + (int)(Math.random()*11) - 6;
		if(!creature.canEnter(x,y,creature.z) || creature.tile(x, y, creature.z).isBars()) {
			return;
			
		}
		if(creature.item(x,y,creature.z) != null && creature.item(x,y,creature.z).isTrap()) {
			return;
			
		}
		creature.doAction("spawn a child");		
		Creature child = factory.newFungus(creature.z, 1);
		child.x = x;
		child.y = y;
		spreadcount++;		
	}
	
	
	
	
	
	

}
