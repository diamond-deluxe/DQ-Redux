package RogueLike.Main.Factories;

import RogueLike.Main.Creatures.*;
import RogueLike.Main.Dice;
import RogueLike.Main.Enums.PlayerAncestry;
import RogueLike.Main.Enums.PlayerClass;
import RogueLike.Main.ExtendedAsciiPanel;
import RogueLike.Main.FieldOfView;
import RogueLike.Main.AI.ChestAI;
import RogueLike.Main.AI.PlayerAI;
import RogueLike.Main.Creatures.Constructs.AnimatedArmor;
import RogueLike.Main.Creatures.Constructs.AnimatedWeapon;
import RogueLike.Main.Creatures.Containers.BasicChest;
import RogueLike.Main.Creatures.Fungi.BloodFungus;
import RogueLike.Main.Creatures.Fungi.Fungus;
import RogueLike.Main.Creatures.Gremlins.Gremlin;
import RogueLike.Main.Creatures.Gremlins.GremlinAlchemist;
import RogueLike.Main.Creatures.Skeletons.Skeleton;
import RogueLike.Main.Creatures.Skeletons.SkeletonCryomancer;
import RogueLike.Main.Creatures.Skeletons.SkeletonElectromancer;
import RogueLike.Main.Creatures.Skeletons.SkeletonPyromancer;
import RogueLike.Main.Creatures.Slimes.MagmaSlime;
import RogueLike.Main.Creatures.Slimes.MagmaSlimeling;
import RogueLike.Main.Creatures.Slimes.MetalSlime;
import RogueLike.Main.Creatures.Slimes.MetalSlimeling;
import RogueLike.Main.Creatures.Slimes.PinkSlime;
import RogueLike.Main.Creatures.Slimes.PinkSlimeling;
import RogueLike.Main.Creatures.Slimes.ThundercloudSlime;
import RogueLike.Main.Creatures.Slimes.ThundercloudSlimeling;
import RogueLike.Main.Damage.DamageType;
import RogueLike.Main.Items.Item;
import RogueLike.Main.Utils.NotificationHistory;
import RogueLike.Main.Utils.PlayerBuildDetails;

public class CreatureFactory {
	
public ObjectFactory objectFactory;
public ModifierFactory modifierFactory;
	
	public CreatureFactory(ObjectFactory factory) {
		this.objectFactory = factory;
		this.modifierFactory = new ModifierFactory();
	}
	
	public Creature modifyCreatureDamageDealt(Creature creature, CreatureModifier modifier) {
		creature.setName(String.format("%s %s", modifier.prefix(), creature.name()));
		creature.setUnarmedDamageType(modifier.damageType());
		return creature;
	}
	
	public Creature modifyCreatureResistsDamage(Creature creature, CreatureModifier modifier) {
		creature.setName(String.format("%s %s", creature.name(), modifier.suffix()));

		creature.addResistanceTo(modifier.damageType());

		return creature;
	}
	
	//Player
	public Player newPlayer(FieldOfView fov, NotificationHistory notificationsHandle, PlayerBuildDetails details) {
		//world, name, glyph, color, maxHP 20, maxMana, base armorclass, strength, dexterity, intelligence, visionRadius, inventorySize) {
		Player player = new Player(objectFactory.world, "Player", '@', ExtendedAsciiPanel.brightWhite, 20, 20, 10, 10, 10, 10, 8, 20);
		player.setID(0);
		new PlayerAI(player, notificationsHandle, fov, objectFactory, objectFactory.world);
		
		player.setPlayerName(details.name);
		player.setPlayerAncestry(details.ancestry);
		player.creatureTypes.add(details.ancestry.toString());
		
		player.setStrength(details.startingStats.get(0));
		player.setDexterity(details.startingStats.get(1));
		player.setIntelligence(details.startingStats.get(2));
		
		player.setSkills(details.startingSkills);

		//Max HP
		player.setMaxHP((player.baseStrength()*2)-5);

		//Max Mana
		if(details.ancestry == PlayerAncestry.ELF) {
			int amount = (int) Math.ceil((((player.baseIntelligence()*2)-5)*1.25));
			player.setMaxMana(amount);
		}else {
			player.setMaxMana((player.baseIntelligence()*2)-5);
		}
		
		//Ancestry Traits
		if(details.ancestry == PlayerAncestry.DWARF) {
			player.modifyBaseArmorClass(1);
			player.addResistanceTo(DamageType.POISON);
		}
		if(details.ancestry == PlayerAncestry.DRAGONBORN) {
			player.addResistanceTo(DamageType.FIRE);
		}
		
		//Class Equipment
		player.setPlayerClass(details.playerClass);
		switch (details.playerClass) {
			case WARRIOR:
				Item startWeaponWarrior = objectFactory.itemFactory.newShortsword(0, false, false, false, false);
				player.learnNameQuiet(startWeaponWarrior);
				player.inventory().add(startWeaponWarrior);
				player.equip(startWeaponWarrior, true);
				Item startArmorWarrior = objectFactory.itemFactory.newChainmailArmor(0, false, false, false, false);
				player.learnNameQuiet(startArmorWarrior);
				player.inventory().add(startArmorWarrior);
				player.equip(startArmorWarrior, true);
				Item startShieldWarrior = objectFactory.itemFactory.newRoundShield(0, false, false, false, false);
				player.learnNameQuiet(startShieldWarrior);
				player.inventory().add(startShieldWarrior);
				player.equip(startShieldWarrior, true);

				player.setHPScaleAmount(player.hpScaleHigh());
				player.setManaScaleAmount(player.manaScaleLow());
				break;

			case ROGUE:
				Item startWeaponRogue = objectFactory.itemFactory.newDagger(0, false, false, false, false);
				player.learnNameQuiet(startWeaponRogue);
				player.inventory().add(startWeaponRogue);
				player.equip(startWeaponRogue, true);
				player.equipToQuickslot(startWeaponRogue, 1);
				Item startArmorRogue = objectFactory.itemFactory.newPaddedClothArmor(0, false, false, false, false);
				player.learnNameQuiet(startArmorRogue);
				player.inventory().add(startArmorRogue);
				player.equip(startArmorRogue, true);
				Item startItemRogue = objectFactory.itemFactory.newPotionOfInvisibility(0, false);
				player.learnNameQuiet(startItemRogue);
				player.inventory().add(startItemRogue);

				player.setHPScaleAmount(player.hpScaleMedium());
				player.setManaScaleAmount(player.manaScaleMedium());
				break;

			case MAGE:
				Item startArmorWizard = objectFactory.itemFactory.newPaddedClothArmor(0, false, false, false, false);
				player.learnNameQuiet(startArmorWizard);
				player.inventory().add(startArmorWizard);
				player.equip(startArmorWizard, true);
				Item startWandMage = objectFactory.itemFactory.newMagicMissileWand(0, player, false);
				player.learnNameQuiet(startWandMage);
				player.inventory().add(startWandMage);
				player.equipToQuickslot(startWandMage, 1);
				startWandMage = objectFactory.itemFactory.newFireboltWand(0, player, false);
				player.learnNameQuiet(startWandMage);
				player.inventory().add(startWandMage);
				player.equipToQuickslot(startWandMage, 1);

				player.setHPScaleAmount(player.hpScaleLow());
				player.setManaScaleAmount(player.manaScaleHigh());
				break;

			case RANGER:
				Item startBowRanger = objectFactory.itemFactory.newShortbow(0, false, false, false, false);
				player.learnNameQuiet(startBowRanger);
				player.inventory().add(startBowRanger);
				player.equip(startBowRanger, true);
				Item startWeaponRanger = objectFactory.itemFactory.newClub(0, false, false, false, false);
				player.learnNameQuiet(startWeaponRanger);
				player.inventory().add(startWeaponRanger);
				Item startArmorRanger = objectFactory.itemFactory.newPaddedClothArmor(0, false, false, false, false);
				player.learnNameQuiet(startArmorRanger);
				player.inventory().add(startArmorRanger);
				player.equip(startArmorRanger, true);
				Item startItemRanger = objectFactory.itemFactory.newArrows(0, 0);
				startItemRanger.setStackAmount(20);
				player.learnNameQuiet(startItemRanger);
				player.inventory().add(startItemRanger);
				player.equip(startItemRanger, true);

				player.setHPScaleAmount(player.hpScaleMedium());
				player.setManaScaleAmount(player.manaScaleMedium());
				break;
				
			case WITCH:
				Item startWeaponWitch = objectFactory.itemFactory.newDagger(0, false, false, false, false);
				player.learnNameQuiet(startWeaponWitch);
				player.inventory().add(startWeaponWitch);
				player.equip(startWeaponWitch, true);
				player.equipToQuickslot(startWeaponWitch, 1);
				Item startArmorWitch = objectFactory.itemFactory.newPaddedClothArmor(0, false, false, false, false);
				player.learnNameQuiet(startArmorWitch);
				player.inventory().add(startArmorWitch);
				player.equip(startArmorWitch, true);
				Item startWandWitch_2 = objectFactory.itemFactory.newAcidBlastWand(0, player, false);
				player.learnNameQuiet(startWandWitch_2);
				player.inventory().add(startWandWitch_2);
				player.equipToQuickslot(startWandWitch_2, 2);

				player.setHPScaleAmount(player.hpScaleLow());
				player.setManaScaleAmount(player.manaScaleHigh());
				break;
				
			case PALADIN:
				Item startWeaponPaladin = objectFactory.itemFactory.newShortsword(0, false, false, false, false);
				player.learnNameQuiet(startWeaponPaladin);
				player.inventory().add(startWeaponPaladin);
				player.equip(startWeaponPaladin, true);
				Item startArmorPaladin = objectFactory.itemFactory.newChainmailArmor(0, false, false, false, false);
				player.learnNameQuiet(startArmorPaladin);
				player.inventory().add(startArmorPaladin);
				player.equip(startArmorPaladin, true);
				Item startWandPaladin = objectFactory.itemFactory.newBladsWardWand(0, player, false);
				player.learnNameQuiet(startWandPaladin);
				player.inventory().add(startWandPaladin);
				player.equipToQuickslot(startWandPaladin, 1);

				player.setHPScaleAmount(player.hpScaleHigh());
				player.setManaScaleAmount(player.manaScaleLow());
				break;
				
			case MONK:
				Item startWeaponMonk = objectFactory.itemFactory.newClub(0, false, false, false, false);
				player.learnNameQuiet(startWeaponMonk);
				player.inventory().add(startWeaponMonk);
				player.equip(startWeaponMonk, true);
				Item startArmorMonk = objectFactory.itemFactory.newPaddedClothArmor(0, false, false, false, false);
				player.learnNameQuiet(startArmorMonk);
				player.inventory().add(startArmorMonk);
				player.equip(startArmorMonk, true);
				Item startItemMonk = objectFactory.itemFactory.newPotionOfMindVision(0, false);
				player.learnNameQuiet(startItemMonk);
				player.inventory().add(startItemMonk);

				player.setHPScaleAmount(player.hpScaleMedium());
				player.setManaScaleAmount(player.manaScaleMedium());
				break;

			default:
				throw new IllegalArgumentException(details.playerClass.toString());
		}
	
		//Starting Food
		player.inventory().add(objectFactory.itemFactory.newRations(0, 0));

		//Ancestry Items
		if(details.ancestry == PlayerAncestry.DRAGONBORN) {
			Item startWandDragonborn = objectFactory.itemFactory.newDragonsBreathWand(0, player, false);
			player.learnNameQuiet(startWandDragonborn);
			player.inventory().add(startWandDragonborn);
			if(details.playerClass == PlayerClass.MAGE || details.playerClass == PlayerClass.PALADIN) {
				player.equipToQuickslot(startWandDragonborn, 2);
			}else if(details.playerClass == PlayerClass.WITCH) {
				player.equipToQuickslot(startWandDragonborn, 3);
			}else{
				player.equipToQuickslot(startWandDragonborn, 1);
			}
		}

		//Test Items
		//Item startWandTest = objectFactory.itemFactory.newScrollOfMagicMapping(0, player, false);
		//player.learnNameQuiet(startWandTest);
		//player.inventory().add(startWandTest);
		player.inventory().add(objectFactory.itemFactory.newDragonsBreathWand(0, player, false));
		player.inventory().add(objectFactory.itemFactory.newIceKnifeWand(0, player, false));
		//player.modifyGold(10000);
		
		//
		player.stackItems();
		return player;
	}
	
	//Fungi
	public Creature newFungus(int depth, boolean addToWorld) {
		Creature fungus = new Fungus(objectFactory, "Fungus", 'f', ExtendedAsciiPanel.green, 100, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(fungus, depth);
		}
		return fungus;
	}
	public Creature newBloodFungus(int depth, Creature player, boolean addToWorld) {
		Creature fungus = new BloodFungus(objectFactory, player, "Blood Fungus", 'f', ExtendedAsciiPanel.red, 101, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(fungus, depth);
		}
		return fungus;
	}
	
	//Bats
	public Creature newBat(int depth, boolean addToWorld) {
		Creature bat = new Bat(objectFactory, "Bat", 'b', ExtendedAsciiPanel.magenta, 200, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(bat, depth);
		}
		return bat;
		
	}
	
	//Skeletons
	public Creature newSkeleton(int depth, Creature player, boolean addToWorld) {
		Creature skeleton = new Skeleton(objectFactory, player, "Skeleton", 'k', ExtendedAsciiPanel.white, 300, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(skeleton, depth);
		}
		return skeleton;
	}
	
	public Creature newSkeletonCryomancer(int depth, Creature player, boolean addToWorld) {
		Creature skeleton = new SkeletonCryomancer(objectFactory, player, "Skeleton Cryomancer", 'k', ExtendedAsciiPanel.water, 301, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(skeleton, depth);
		}
		return skeleton;
	}
	
	public Creature newSkeletonPyromancer(int depth, Creature player, boolean addToWorld) {
		Creature skeleton = new SkeletonPyromancer(objectFactory, player, "Skeleton Pyromancer", 'k', ExtendedAsciiPanel.orange, 302, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(skeleton, depth);
		}
		return skeleton;
	}
	
	public Creature newSkeletonElectromancer(int depth, Creature player, boolean addToWorld) {
		Creature skeleton = new SkeletonElectromancer(objectFactory, player, "Skeleton Electromancer", 'k', ExtendedAsciiPanel.paralyzed, 303, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(skeleton, depth);
		}
		return skeleton;
	}
	
	//Slimes
	public Creature newPinkSlime(int depth, Creature player, boolean addToWorld) {
		Creature slime = new PinkSlime(objectFactory, player, "Pink Slime", 'S', ExtendedAsciiPanel.brightPink, 400, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slime, depth);
		}
		return slime;		
	}
	
	public Creature newMetalSlime(int depth, Creature player, boolean addToWorld) {
		Creature slime = new MetalSlime(objectFactory, player, "Metal Slime", 'S', ExtendedAsciiPanel.white, 401, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slime, depth);
		}
		return slime;		
	}
	
	public Creature newMagmaSlime(int depth, Creature player, boolean addToWorld) {
		Creature slime = new MagmaSlime(objectFactory, player, "Magma Slime", 'S', ExtendedAsciiPanel.orange, 402, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slime, depth);
		}
		return slime;	
	}
	
	public Creature newThundercloudSlime(int depth, Creature player, boolean addToWorld) {
		Creature slime = new ThundercloudSlime(objectFactory, player, "Thundercloud Slime", 'S', ExtendedAsciiPanel.paralyzed, 403, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slime, depth);
		}
		return slime;	
	}
	
	//Slimelings
	public Creature newPinkSlimeling(int depth, Creature player, boolean addToWorld) {
		Creature slimeling = new PinkSlimeling(objectFactory, player, "Pink Slimeling", 's', ExtendedAsciiPanel.brightPink, 500, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slimeling, depth);
		}
		return slimeling;
	}
	
	public Creature newMetalSlimeling(int depth, Creature player, boolean addToWorld) {
		Creature slimeling = new MetalSlimeling(objectFactory, player, "Metal Slimeling", 's', ExtendedAsciiPanel.white, 501, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slimeling, depth);
		}
		return slimeling;
	}
	
	public Creature newMagmaSlimeling(int depth, Creature player, boolean addToWorld) {
		Creature slimeling = new MagmaSlimeling(objectFactory, player, "Magma Slimeling", 's', ExtendedAsciiPanel.orange, 502, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slimeling, depth);
		}
		return slimeling;
	}
	
	public Creature newThundercloudSlimeling(int depth, Creature player, boolean addToWorld) {
		Creature slimeling = new ThundercloudSlimeling(objectFactory, player, "Thundercloud Slimeling", 's', ExtendedAsciiPanel.paralyzed, 503, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(slimeling, depth);
		}
		return slimeling;
	}
	
	//Ogres
	public Creature newOgre(int depth, Creature player, boolean addToWorld) {
		Creature ogre = new Ogre(objectFactory, player, "Ogre", 'O', ExtendedAsciiPanel.brightGreen, 600, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(ogre, depth);
		}
		return ogre;
	}
	
	//Gremlins
	public Creature newGremlin(int depth, Creature player, boolean addToWorld) {
		Creature gremlin = new Gremlin(objectFactory, player, "Gremlin", 'g', ExtendedAsciiPanel.cobalt, 700, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(gremlin, depth);
		}
		return gremlin;
	}
	
	public Creature newGremlinSkirmisher(int depth, Creature player, boolean addToWorld) {
		Creature gremlin = new Gremlin(objectFactory, player, "Gremlin Skirmisher", 'g', ExtendedAsciiPanel.red, 701, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(gremlin, depth);
		}
		return gremlin;
	}
	
	public Creature newGremlinAlchemist(int depth, Creature player, boolean addToWorld) {
		Creature alchemist = new GremlinAlchemist(objectFactory, player, "Gremlin Alchemist", 'g', ExtendedAsciiPanel.orange, 702, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(alchemist, depth);
		}
		return alchemist;
	}
	
	//Cloaker
	public Creature newCloaker(int depth, Creature player, boolean addToWorld) {
		Creature cloaker = new Cloaker(objectFactory, player, "Cloaker", 'c', ExtendedAsciiPanel.brightBlack, 800, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(cloaker, depth);
		}
		return cloaker;	
	}
	
	//Constructs
	public Creature newAnimatedArmor(int depth, Creature player, boolean addToWorld) {
		Creature animatedArmor = new AnimatedArmor(objectFactory, player, "Animated Armor", (char)203, ExtendedAsciiPanel.lilac, 900, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(animatedArmor, depth);
		}
		return animatedArmor;
	}
	
	public Creature newAnimatedWeapon(int depth, Creature player, boolean addToWorld) {
		Creature animatedWeapon = new AnimatedWeapon(objectFactory, player, "Animated Weapon", ')', ExtendedAsciiPanel.lilac, 901, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(animatedWeapon, depth);
		}
		return animatedWeapon;
	}
	
	//Mimic
	public Creature newMimic(int depth, Creature player, boolean addToWorld) {
		Creature mimic = new Mimic(objectFactory, player, "Mimic", (char)127, ExtendedAsciiPanel.apple, 1000, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(mimic, depth);
		}
		return mimic;
	}
	
	//Merchant
	public Creature newMerchant(int depth, Creature player, boolean addToWorld) {
		Creature merchant = new Merchant(objectFactory, player, "Merchant", (char)234, ExtendedAsciiPanel.lilac, 1100, depth);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(merchant, depth);
		}
		return merchant;
	}
	
	//Chest
	public Creature newChest(int depth, Creature player, boolean addToWorld) {
		Creature chest = new BasicChest(objectFactory, player, "Chest", (char)127, ExtendedAsciiPanel.brown, depth, Dice.d12.roll());
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(chest, depth);
		}
		return chest;
	}
	
	//Spell Creatures
	public Creature newIceWall(int depth, Creature player, boolean addToWorld) {
		Creature icewall = new Creature(objectFactory.world, "Ice Wall", (char)177, ExtendedAsciiPanel.water, 15, 10, 16, 16, 1, 1, 1, 20);
		icewall.setID(1100);
		new ChestAI(icewall, objectFactory, objectFactory.world);
		icewall.addImmunityTo(DamageType.FROST);
		icewall.setHasNoCorpse(true);
		icewall.setHasNoXP(true);
		icewall.creatureTypes.add("Elemental");
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(icewall, depth);
		}
		return icewall;
	}
	
	//Technical Creatures
	public Creature newTileSpell(int depth, Creature player, boolean addToWorld) {
		Creature tileSpell = new Creature(objectFactory.world, " ", 'T', ExtendedAsciiPanel.brown, 1, 1, 10, 1, 1, 1, 1, 1);
		new ChestAI(tileSpell, objectFactory, objectFactory.world);
		tileSpell.setIsFlying(true);
		tileSpell.modifyIsTileSpell(true);
		tileSpell.setHasNoCorpse(true);
		tileSpell.setHasNoXP(true);
		if(addToWorld) {
			objectFactory.world.addAtEmptyLocation(tileSpell, depth);
		}
		return tileSpell;
	}
	
	
	

}
