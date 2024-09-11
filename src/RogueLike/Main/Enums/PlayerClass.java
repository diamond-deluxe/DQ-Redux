package RogueLike.Main.Enums;

import RogueLike.Main.Skill;

import java.util.EnumSet;

public enum PlayerClass {
	WARRIOR("Warrior",
			"Clad in steel plate and armed with mighty blades, " +
			"warriors represent the archetypal adventurer for many. " +
			"Their extensive training ensures that wherever " +
			"a warrior goes, victory is sure to follow.",
			EnumSet.of(Skill.MARTIAL_WEAPONS, Skill.ARMOR_TRAINING)
	),
	ROGUE("Rogue",
			"Cutthroats and scoundrels, rogues rarely fight " +
			"on an even footing, prefering poisons, traps, " +
			"and a knife in the dark. Cunning beyond measure, " +
			"these heroes are always hiding a secret or two.",
			EnumSet.of(Skill.FINESSE_WEAPONS, Skill.STEALTH)
	),
	MAGE("Mage",
			"Gifted arcane scholars, mages possess a firm " +
			"grasp of the fundamentals of magic, and the sheer " +
			"power they can unleash ensures that many will " +
			"never even need to swing their weapons at all.",
			EnumSet.of(Skill.EVOCATION, Skill.PYROMANCY)
	),
	RANGER("Ranger",
			"Wandering the outskirts of civilisation, rangers " +
			"are masters of survival, hunting, and bushcraft. " +
			"Possessed of keen eyesight and keener aim, " +
			"many threats have been ended by rangers without " +
			"the knowledge of the villages they protect.",
			EnumSet.of(Skill.RANGED_WEAPONS, Skill.PERCEPTION)
	),
	WITCH("Witch",
			"Mysterious magic-wielders often working in the " +
			"shadows, witches trade the arcane studies undertaken " +
			"by traditional mages for a more hands-on approach. " +
			"Still, power must always come at a price..",
			EnumSet.of(Skill.FINESSE_WEAPONS, Skill.ALCHEMANCY)
	),
	PALADIN("Paladin",
			"Devout warriors with deep connections to " +
			"their weapons, paladins swear mighty oaths " +
			"before each quest. Every victory deepens their " +
			"bond, their conviction, and their bravery.",
			EnumSet.of(Skill.ARMOR_TRAINING, Skill.FERROMANCY)
	),
	MONK("Monk",
			"Warrior monks posessed of an incredible " +
			"fighting spirit, monks thrive in the toughest " +
			"of conditions, seeing each new trial as a way " +
			"to further strengthen their faith and resolve.",
			EnumSet.of(Skill.SIMPLE_WEAPONS, Skill.FORTITUDE)
	),
	;

	private final String name;
	public final String description;
	public final EnumSet<Skill> startingSkills;

	PlayerClass(String name, String description, EnumSet<Skill> startingSkills) {
		this.name = name;
		this.description = description;
		this.startingSkills = startingSkills;
	}

	@Override
	public String toString() {
		return name;
	}
}
