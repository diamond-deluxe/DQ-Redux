package RogueLike.Main.Screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import RogueLike.Main.Effect;
import RogueLike.Main.Enums.DamageType;
import RogueLike.Main.ExtendedAsciiPanel;
import RogueLike.Main.ExtraMaths;
import RogueLike.Main.Creatures.Creature;
import RogueLike.Main.Managers.KeybindManager;
import RogueLike.Main.Screens.CharacterSheet.*;
import RogueLike.Main.Screens.CharacterSheet.Skills.*;
import RogueLike.Main.TextUtils;

import javax.print.attribute.standard.Finishings;

public class CharacterSheetScreen implements Screen{

	protected Creature player;
	protected List<Effect> effects;

	public CharacterSheetScreen(Creature player) {
		this.player = player;
		this.effects = player.effects();

		elements = new ArrayList<>();
		elements.add( // column 1: stats
			List.of(
				new NameElement(player),
				new LevelElement(player),
				new ExpElement(player),
				new GoldElement(player),
				//
				new SpacerElement(),
				//
				new HealthElement(player),
				new ManaElement(player),
				new HungerElement(player),
				//
				new SpacerElement(),
				//
				new StrengthElement(player),
				new DexterityElement(player),
				new IntelligenceElement(player),
				//
				new SpacerElement(),
				//
				new ArmorClassElement(player),
				new ProficiencyBonusElement(player),
				new VisionRadiusElement(player)
			)
		);
		elements.add( // column 2: skills
			List.of(
				new SimpleWeaponsSkillElement(player),
				new MartialWeaponsSkillElement(player),
				new FinesseWeaponsSkillElement(player),
				new RangedWeaponsSkillElement(player),
				//
				new SpacerElement(),
				//
				new ArmorTrainingSkillElement(player),
				new FortitudeSkillElement(player),
				new StealthSkillElement(player),
				new PerceptionSkillElement(player)
				// TODO add the magic skills in here oop
			)
		);
		elements.add( // column 3: current effects
			// TODO add this again
			new ArrayList<>()
		);
		elements.add( // column 4: damage modifiers
			Arrays.stream(DamageType.RESISTABLE_TYPES).map(
				type -> new DamageModifierElement(player, type)
			).collect(Collectors.toList())
		);
	}

	private final List<List<CharacterSheetElement>> elements;

	int cX = 0;
	int cY = 0;

	private CharacterSheetElement selectedElement() {
		return elements.get(cX).get(cY);
	}

	@Override
	public void displayOutput(ExtendedAsciiPanel terminal) {
		terminal.clear();

		Screen.generateBorders(terminal);

		terminal.writeCenter("== Character Sheet ==", 1);
		int[] column_start_y = {3, 3, 3, 3};
		int[] column_x = {5, 32, 59, 89};
		String[] column_headers = {"Stats", "Skills", "Status Effects", "Damage Resistances"};

		CharacterSheetElement selectedElement = selectedElement();

		for (int column_no = 0; column_no < 4; column_no++) {
			int x = column_x[column_no];
			int y = column_start_y[column_no];
			terminal.write(String.format("== %s ==", column_headers[column_no]), x, y++);

			for (CharacterSheetElement element: elements.get(column_no)) {
				String selector = element == selectedElement ? ">> " : "";
				terminal.write(selector + element.header(), x, y++);
			}
		}

//        for(Effect effect : effects) {
//        	if(effect.name() != null) {
//        		String turns = "turn";
//        		if(effect.duration() > 1) {
//        			turns = "turns";
//        		}
//        		char effectIcon = (char)30;
//        		if(effect.isNegative()) {
//        			effectIcon = (char)31;
//        		}
//        		if(effect.showInMenu()) {
//        			if(cX == 2 && cY == effects.indexOf(effect)) {
//        				terminal.write(String.format(">> %c %s: %d %s", effectIcon, effect.name(), effect.duration(), turns), x3, y3++);
//        			}else {
//        				terminal.write(String.format("%c %s: %d %s", effectIcon, effect.name(), effect.duration(), turns), x3, y3++);
//        			}
//
//        		}
//
//        	}
//		}

        terminal.write(selectedElement.details1(), column_x[0], 31);
        terminal.write(selectedElement.details2(), column_x[0], 32);
        terminal.write(selectedElement.details3(), column_x[0], 33);

        terminal.writeCenter(String.format("-- [%s]: Back --", KeybindManager.keybindText(KeybindManager.navigateMenuBack)), 38);
	}

	public boolean hasModifierFor(DamageType damageType) {
		return (
			player.isImmuneTo(damageType)
			|| player.isWeakTo(damageType)
			|| player.isResistantTo(damageType)
		);
	}

	private void moveSelectionVertical(int direction) {
		var currentColumn = elements.get(cX);
		if (currentColumn.isEmpty()) {
			throw new IllegalStateException(String.format("Empty column at cX=%d shouldn't be selected.", cX));
		}
		int initialY = cY;
		// Keep moving down until we hit a selectable element.
		// Can't loop infinitely:
		//  - We start in column 0 which has selectable elements.
		//  - moveSelectionHorizontal checks that the column it's moving into has a selectable element.
		do {
			cY = trueModulo(cY+direction, currentColumn.size());
		} while (!selectedElement().isSelectable());
	}

	private void moveSelectionHorizontal(int direction) {
		int numColumns = elements.size(); // i.e. size on the x-axis
		// Keep moving until we hit a column with selectable elements.
		// Can't loop infinitely as column 0 always has selectable elements.
		do {
			cX = trueModulo(cX+direction, numColumns);
		} while (elements.get(cX).stream().noneMatch(CharacterSheetElement::isSelectable));

		// now ensure that our y-position is within bounds
		var column = elements.get(cX);
		if (cY >= column.size()) {
			cY = column.size();
			// note that moveSelectionVertical doesn't require cY to be in-bounds
			moveSelectionVertical(-1);
		}
		if (!selectedElement().isSelectable()) {
			moveSelectionVertical(-1);
		}
	}

	private int trueModulo(int dividend, int divisor) {
		int javaModulo = dividend % divisor;
		if (javaModulo < 0) {
			return javaModulo + divisor;
		}
		else {
			return javaModulo;
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeybindManager.navigateMenuLeft:
			moveSelectionHorizontal(-1);
			return this;
		case KeybindManager.navigateMenuRight:
			moveSelectionHorizontal(1);
			return this;
		case KeybindManager.navigateMenuUp:
			moveSelectionVertical(-1);
			return this;
		case KeybindManager.navigateMenuDown:
			moveSelectionVertical(1);
			return this;
		case KeybindManager.navigateMenuBack:
			return null;
		default: return this;
		}
	}

}
