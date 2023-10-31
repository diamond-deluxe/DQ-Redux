package RogueLike.Main.Items;

import java.awt.Color;

import RogueLike.Main.Dice;

public class ArrowsRangedWeapon extends BasicRangedWeapon{

	public ArrowsRangedWeapon(char glyph, Color color, String name, String appearance, Dice damageDice, int goldValue, int id) {
		super(glyph, color, name, appearance, damageDice, goldValue, id);
		this.setUsesArrowAmmunition(true);
	}

}
