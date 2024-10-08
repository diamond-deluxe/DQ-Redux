package RogueLike.Main;

import java.awt.Color;

public enum Tile {
	FLOOR((char)250, /*AsciiPanel.yellow*/ new Color(173, 146, 125), "A rocky cave floor."),
	MERCHANT_FLOOR((char)250, /*AsciiPanel.yellow*/ new Color(173, 146, 125), "A rocky cave floor."),
	WALL((char)177, /*AsciiPanel.yellow*/ new Color(173, 146, 125), "A rocky cave wall. Looks breakable."),
	BOUNDS('x', ExtendedAsciiPanel.brightBlack, "Beyond the edge of the world."),
	STAIRS_UP((char)30, ExtendedAsciiPanel.white, "A rough stone staircase that goes up."),
	STAIRS_DOWN((char)31, ExtendedAsciiPanel.white, "A rough stone staircase that goes down."),
	UNKNOWN(' ', ExtendedAsciiPanel.white, "You don't know what is there."),
	STAIRS_EXIT((char)30, ExtendedAsciiPanel.orange, "A rough stone staircase that leads out of the caves."),
	BARS_VERTICAL((char)186, ExtendedAsciiPanel.white, "A cage made of sturdy iron bars."),
	BARS_HORIZONTAL((char)205, ExtendedAsciiPanel.white, "A cage made of sturdy iron bars."),
	BARS_CROSS((char)206, ExtendedAsciiPanel.white, "A cage made of sturdy iron bars."),
	BARS_DOOR((char)205, ExtendedAsciiPanel.brown, "A tough wooden door with a heavy lock."),
	BARS_NW((char)201, ExtendedAsciiPanel.white, "A cage made of sturdy iron bars."),
	BARS_SW((char)200, ExtendedAsciiPanel.white, "A cage made of sturdy iron bars."),
	BARS_NE((char)187, ExtendedAsciiPanel.white, "A cage made of sturdy iron bars."),
	BARS_SE((char)188, ExtendedAsciiPanel.white, "A cage made of sturdy iron bars."),
	GRASS_TALL((char)176, ExtendedAsciiPanel.green, "Tall, thick grass blocks your view."),
	FIRE((char)(167+256), ExtendedAsciiPanel.orange, "A small fire is burning here."),
	SMOKE((char)176, ExtendedAsciiPanel.smoke, "A dense cloud of smoke and ash is swirling here."),
	PIT((char)220, ExtendedAsciiPanel.invisible, "A deep chasm opens here. You can't see the bottom.."),
	ACID_GAS((char)176, ExtendedAsciiPanel.apple, "A cloud of caustic gas is swirling here."),
	PARALYZE_GAS((char)176, ExtendedAsciiPanel.paralyzed, "A cloud of paralytic gas is swirling here."),
	CONFUSE_GAS((char)176, ExtendedAsciiPanel.pink, "A cloud of confusion gas is swirling here."),
	MIXED_GAS((char)176, ExtendedAsciiPanel.orange, "A cloud of alchemical murk is swirling here."),
	FUNGUS_WALL((char)177, ExtendedAsciiPanel.green, "A wall of tangled cave fungus. Looks like you could chop through it."),
	
	
	
	
	
	
	;

	
	private char glyph;
	public char glyph() {
		return glyph;
	}
	
	private Color color;
	public Color color() {
		return color;
	}
	
	private String details;
	public String details() {
		return details;
	}
	
	Tile(char glyph, Color color, String details){
		this.glyph = glyph;
		this.color = color;
		this.details = details;
		
	}
	
	public void update() {
		
	}
	
	public boolean isPit() {
		return this == Tile.PIT;
	}
	
	public boolean canHaveSubtiles() {
		return this == Tile.FLOOR || this == Tile.GRASS_TALL;
	}
	
	public boolean canHaveGas() {
		return this != Tile.WALL && this != Tile.BOUNDS;
	}
	
	public boolean displayGas() {
		return this == Tile.FLOOR || this == Tile.PIT;
	}
	
	public boolean isGas() {
		return this == Tile.ACID_GAS || this == Tile.PARALYZE_GAS || this == Tile.CONFUSE_GAS || this == Tile.MIXED_GAS || this == Tile.SMOKE;
	}
	
	public boolean isFire() {
		return this == Tile.FIRE;
	}
	
	public boolean isDiggable() {
		return this == Tile.WALL || this == Tile.FUNGUS_WALL;
	}
	
	public boolean isWall() {
		return this == Tile.WALL || this == Tile.FUNGUS_WALL;
	}
	
	public boolean isDecaying() {
		return this == Tile.FIRE || this == Tile.SMOKE || this == Tile.ACID_GAS || this == Tile.PARALYZE_GAS || this == Tile.CONFUSE_GAS || this == Tile.MIXED_GAS;
	}
	
	public boolean isObscuring() {
		return this == Tile.GRASS_TALL || this == Tile.SMOKE || this == Tile.MIXED_GAS;
	}
	
	public boolean noItems() {
		return this == WALL || this == FIRE || this == GRASS_TALL || this == BOUNDS || this.isBars() || this == PIT || this == STAIRS_UP || this == STAIRS_DOWN || this == STAIRS_EXIT ;
	}
	
	public boolean noWall() {
		return this == FIRE || this == GRASS_TALL || this == BOUNDS || this.isBars() || this == PIT || this == STAIRS_UP || this == STAIRS_DOWN || this == STAIRS_EXIT;
	}
	
	public boolean isGround() {
		return this != WALL && this != BOUNDS && this != PIT;
	}
	
	public boolean isOpen() {
		return this != WALL && this != BOUNDS;
	}
	
	public boolean isBars() {
		return this == BARS_VERTICAL || this == BARS_HORIZONTAL || this == BARS_NW || this == BARS_SW || this == BARS_NE || this == BARS_SE || this == BARS_DOOR || this == BARS_CROSS;
	}
	
	public boolean isBarsDoor() {
		return this == BARS_DOOR;
	}
	
	public boolean isGrass() {
		return this == GRASS_TALL;
	}
	
	public boolean isStairs() {
		return this == STAIRS_UP || this == STAIRS_DOWN || this == STAIRS_EXIT;
	}
	
	public boolean isStairsExit() {
		return this == STAIRS_EXIT;
	}

	public boolean blocksRangedAttacks() {
		return this.isBars() || this.isWall();
	}

}
