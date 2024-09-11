package RogueLike.Main.Screens;

import java.awt.event.KeyEvent;
import java.util.Map;

import RogueLike.Main.Enums.PlayerAncestry;
import RogueLike.Main.Enums.PlayerClass;
import RogueLike.Main.ExtendedAsciiPanel;
import RogueLike.Main.Skill;
import RogueLike.Main.TextUtils;

public class ChooseClassScreen implements Screen{
	
	public int check = 0;

	public void setCheck(int value) {
		check = value;
	}
	
	public ChooseClassScreen(PlayerAncestry playerAncestry) {
		this.playerAncestry = playerAncestry;
	}
	
	private final PlayerAncestry playerAncestry;

	public char borderVertical = 186;
	public char borderHorizontal = 205;
	public char borderCorner = 206;
	public char borderCornerNW = 201;
	public char borderCornerNE = 187;
	public char borderCornerSW = 200;
	public char borderCornerSE = 188;

	public PlayerClass selectedClass() {
		return PlayerClass.values()[check];
	}

	public void displayOutput(ExtendedAsciiPanel terminal) {
		terminal.clear();
		Screen.generateBorders(terminal);
		terminal.writeCenter("== Select your Class ==", 1);	
		int y = 5;
		
		terminal.write(String.format("%c%s%c", borderCornerNW, String.valueOf(borderHorizontal).repeat(16), borderCornerNE), 5, y++);
		for (PlayerClass playerClass: PlayerClass.values()) {
			char cursorLeft;
			char cursorRight;
			if (playerClass == selectedClass()) {
				cursorLeft = '>';
				cursorRight = '<';
			} else {
				cursorLeft = ' ';
				cursorRight = ' ';
			}

			terminal.write(String.format("%c %c %-7s %c    %c", borderVertical, cursorLeft, playerClass, cursorRight, borderVertical), 5, y++);
			terminal.write(String.format("%c                %c", borderVertical, borderVertical), 5, y++);
		}
		terminal.write(String.format("%c%s%c", borderCornerSW, String.valueOf(borderHorizontal).repeat(16), borderCornerSE), 5, --y);

		y = 3;

		terminal.write(String.format("+||+ %s +||+", selectedClass()), 26, y+=4);
		y++;
		terminal.writeMultiline(TextUtils.wordWrap(selectedClass().description, 52, 0), 26, y+=1);
		y = terminal.getCursorY() + 1;  // we don't know a priori how many lines the description takes up
		terminal.write("+||+ Starting Skills +||+", 26, y+=1);
		y++;
		for (Skill skill: selectedClass().startingSkills) {
			terminal.write(String.format("+ %s I", skill), 31, y+=1);
			String formattedDescription = "- " + TextUtils.formatWithDict(skill.descriptionLevel1, Map.of("proficiencyBonus", 2));
			terminal.writeMultiline(TextUtils.wordWrap(formattedDescription, 60, 4), 31, y+1);
			y = terminal.getCursorY() + 1;  // we don't know a priori how many lines the description takes up
		}
		terminal.write("+ 2x Level I skills of your choice", 31, y+=1);
		y++;
		terminal.write("+||+ Starting Equipment +||+",26,  y+=1);
		y++;

		// TODO(diamond) - maybe put this info on PlayerClass somehow?
		switch (selectedClass()) {
			case WARRIOR:
				terminal.write("+ Shortsword", 31, y+=1);
				terminal.write("+ Chainmail Tunic", 31, y+=1);
				terminal.write("+ Round Shield", 31, y+=1);
				terminal.write("+ Ration of Food", 31, y+=1);
				break;
			case ROGUE:
				terminal.write("+ Dagger", 31, y+=1);
				terminal.write("+ Padded Armor", 31, y+=1);
				terminal.write("+ Potion of Invisibility", 31, y+=1);
				terminal.write("+ Ration of Food", 31, y+=1);
				break;
			case MAGE:
				terminal.write("+ Padded Armor", 31, y+=1);
				terminal.write("+ Wand of Magic Missile", 31, y+=1);
				terminal.write("+ Wand of Firebolt", 31, y+=1);
				terminal.write("+ Ration of Food", 31, y+=1);
				break;
			case RANGER:
				terminal.write("+ Club", 31, y+=1);
				terminal.write("+ Padded Armor", 31, y+=1);
				terminal.write("+ Shortbow", 31, y+=1);
				terminal.write("+ 20x Arrows", 31, y+=1);
				terminal.write("+ Ration of Food", 31, y+=1);
				break;
			case WITCH:
				terminal.write("+ Dagger", 31, y+=1);
				terminal.write("+ Padded Armor", 31, y+=1);
				terminal.write("+ Wand of Acid Blast", 31, y+=1);
				terminal.write("+ Ration of Food", 31, y+=1);
				break;
			case PALADIN:
				terminal.write("+ Shortsword", 31, y+=1);
				terminal.write("+ Chainmail Tunic", 31, y+=1);
				terminal.write("+ Wand of Blad's Ward", 31, y+=1);
				terminal.write("+ Ration of Food", 31, y+=1);
				break;
			case MONK:
				terminal.write("+ Club", 31, y+=1);
				terminal.write("+ Padded Armor", 31, y+=1);
				terminal.write("+ Potion of Mind Vision", 31, y+=1);
				terminal.write("+ Ration of Food", 31, y+=1);
				break;
		}


		terminal.writeCenter("-- [UP / DOWN]: Move Selection | [ENTER]: Confirm and Continue --", 40);
		terminal.writeCenter("-- [ESCAPE]: Return to Main Menu --", 42);
	}

	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(check == 0) {
				check = 6;
			}else {
				check--;
			}
			return this;
			
		case KeyEvent.VK_DOWN:
			if(check == 6) {
				check = 0;
			}else {
				check++;
			}
			return this;
			
		case KeyEvent.VK_ENTER:
			if(check == 0) {
				return new ChooseAbilityScreen(PlayerClass.WARRIOR, playerAncestry);
			}else if(check == 1) {
				return new ChooseAbilityScreen(PlayerClass.ROGUE, playerAncestry);
			}else if(check == 2) {
				return new ChooseAbilityScreen(PlayerClass.MAGE, playerAncestry);
			}else if(check == 3) {
				return new ChooseAbilityScreen(PlayerClass.RANGER, playerAncestry);
			}else if(check == 4) {
				return new ChooseAbilityScreen(PlayerClass.WITCH, playerAncestry);
			}else if(check == 5) {
				return new ChooseAbilityScreen(PlayerClass.PALADIN, playerAncestry);
			}else if(check == 6) {
				return new ChooseAbilityScreen(PlayerClass.MONK, playerAncestry);
			}
		case KeyEvent.VK_ESCAPE: return new ChooseAncestryScreen();
		}
		
		return this;
		
		
	}
}
