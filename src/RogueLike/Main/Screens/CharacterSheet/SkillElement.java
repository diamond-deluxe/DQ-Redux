package RogueLike.Main.Screens.CharacterSheet;

import RogueLike.Main.Creatures.Creature;
import RogueLike.Main.ExtraMaths;
import RogueLike.Main.Skill;
import RogueLike.Main.SkillInstance;
import RogueLike.Main.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class SkillElement extends CharacterSheetElement {
    final Creature player;
    final SkillInstance skill;

    public SkillElement(Creature player, Skill skill) {
        this.player = player;
        this.skill = player.skills().get(skill);
    }

    @Override
    public String header() {
        return String.format("%s: %s", skill.name(), ExtraMaths.toRomanNumerals(skill.level()));
    }

    @Override
    public String details() {
        Map<String, Object> playerDetails = new HashMap<>();
        playerDetails.put("proficiencyBonus", player.proficiencyBonus());
        playerDetails.put("proficiencyBonusTimesTwo", player.proficiencyBonus() * 2);
        playerDetails.put("proficiencyBonusTimesFive", player.proficiencyBonus() * 5);
        playerDetails.put("halfMaxMana", player.maxMana() / 2);
        return String.join(
            "\n\n",
            skill.level() >= 1 ? skill.name() + " I: " + TextUtils.formatWithDict(skill.type.descriptionLevel1, playerDetails) : "",
            skill.level() >= 2 ? skill.name() + " II: " + TextUtils.formatWithDict(skill.type.descriptionLevel2, playerDetails) : "",
            skill.level() >= 3 ? skill.name() + " III: " + TextUtils.formatWithDict(skill.type.descriptionLevel3, playerDetails) : ""
        );
    }
}
