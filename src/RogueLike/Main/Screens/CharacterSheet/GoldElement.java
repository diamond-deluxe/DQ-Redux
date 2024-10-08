package RogueLike.Main.Screens.CharacterSheet;

import RogueLike.Main.Creatures.Creature;

public class GoldElement extends CharacterSheetElement {
    private final Creature player;

    public GoldElement(Creature player) {
        this.player = player;
    }

    @Override
    public String header() {
        return String.format("Gold: %d gold", player.gold());
    }

    @Override
    public String details() {
        return String.format("You are carrying %d gold pieces in your purse.\n", player.gold())
            + String.format("You are wielding equipment worth %d gold pieces.", player.equipmentValue());
    }
}
