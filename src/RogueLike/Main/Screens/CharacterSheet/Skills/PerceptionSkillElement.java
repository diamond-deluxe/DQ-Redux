package RogueLike.Main.Screens.CharacterSheet.Skills;

import RogueLike.Main.Creatures.Creature;
import RogueLike.Main.Screens.CharacterSheet.SkillElement;
import RogueLike.Main.SkillInstance;

public class PerceptionSkillElement extends SkillElement {

    public PerceptionSkillElement(Creature player) {
        super(player);
    }

    @Override
    protected SkillInstance skill() {
        return player.skills().perception;
    }

    @Override
    protected String descriptionLevel1() {
        return String.format("Perception I: You add your proficiency bonus (+%s) to checks made to detect traps.", player.proficiencyBonus());
    }

    @Override
    protected String descriptionLevel2() {
        return String.format("Perception II: You gain a bonus to your Vision Radius equal to your proficiency bonus (+%s).", player.proficiencyBonus());
    }

    @Override
    protected String descriptionLevel3() {
        return "Perception III: When you trigger a revealed trap, you instead gain a positive effect based on the trap's type.";
    }
}
