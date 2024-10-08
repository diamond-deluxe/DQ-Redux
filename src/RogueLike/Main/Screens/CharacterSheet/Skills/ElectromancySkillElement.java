package RogueLike.Main.Screens.CharacterSheet.Skills;

import RogueLike.Main.Creatures.Creature;
import RogueLike.Main.Screens.CharacterSheet.SkillElement;
import RogueLike.Main.SkillInstance;

public class ElectromancySkillElement extends SkillElement {

    public ElectromancySkillElement(Creature player) {
        super(player);
    }

    @Override
    protected SkillInstance skill() {
        return player.skills().electromancy;
    }

    @Override
    protected String descriptionLevel1() {
        return String.format("Electromancy I: Add your proficiency bonus (%+d) to attack rolls made with Electromancy spells. Add your proficiency bonus to the duration of Electromancy spell effects, making them last %d extra turns.", player.proficiencyBonus(), player.proficiencyBonus());
    }

    @Override
    protected String descriptionLevel2() {
        return String.format("Electromancy II: Add your proficiency bonus (%+d) to damage rolls made with Electromancy spells. Add your proficiency bonus to the duration of Electromancy spell effects again, making them last a total of %d extra turns.", player.proficiencyBonus(), player.proficiencyBonus() * 2);
    }

    @Override
    protected String descriptionLevel3() {
        return String.format("Electromancy III: Critical hits with Electromancy spells grant Electrocharged for a number of turns equal to your proficiency bonus (%d turns).", player.proficiencyBonus(), player.proficiencyBonus());
    }
}
