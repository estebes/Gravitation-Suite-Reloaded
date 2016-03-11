package com.estebes.gravisuitereloaded.init;

import com.estebes.gravisuitereloaded.item.armor.ItemArmorAdvancedJetpack;
import com.estebes.gravisuitereloaded.item.armor.ItemArmorAdvancedNanoChestplate;
import com.estebes.gravisuitereloaded.item.armor.ItemArmorLappack;
import com.estebes.gravisuitereloaded.item.armor.ItemArmorQuantumLappack;
import com.estebes.gravisuitereloaded.item.misc.ItemMisc;
import com.estebes.gravisuitereloaded.item.resource.ItemBlockCuttingBlade;
import com.estebes.gravisuitereloaded.item.tool.*;
import com.estebes.gravisuitereloaded.item.weapon.ItemWeaponQuantumSaber;
import com.estebes.gravisuitereloaded.reference.Reference;
import com.estebes.xtbxlib.ic2.item.resource.ItemResourceCuttingBlade;
import com.estebes.xtbxlib.ic2.item.tool.ItemElectricToolDrillArea;
import com.estebes.xtbxlib.ic2.item.tool.ItemElectricToolType;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ItemInit {
    // Tools
    /*public static final ItemToolTheThingamabob itemToolTheThingamabob = new ItemToolTheThingamabob(Reference.THE_THINGAMABOB_NAME);
    public static final ItemToolBigMiningDrill itemToolBigMiningDrill = new ItemToolBigMiningDrill(Reference.BIG_MINING_DRILL_NAME,
            2, 2.7E5D, 9.0E2D, false, 5.0E1D);
    public static final ItemToolBigDiamondDrill itemToolBigDiamondDrill = new ItemToolBigDiamondDrill(Reference.BIG_DIAMOND_DRILL_NAME,
            2, 2.7E5D, 9.0E2D, false, 8.0E1D);
    public static final ItemToolBigIridiumDrill itemToolBigIridiumDrill = new ItemToolBigIridiumDrill(Reference.BIG_IRIDIUM_DRILL_NAME,
            3, 2.7E6D, 9.0E3D, false, 8.0E2D);
    public static ItemToolTheChopper itemToolTheChopper;
	public static ItemResourceCuttingBlade itemResourceCuttingBlade = new ItemResourceCuttingBlade("Iridium", 12);
	public static ItemElectricToolDrillArea itemElectricToolDrillArea = new ItemElectricToolDrillArea(Reference.BIG_MINING_DRILL_NAME,
			Reference.LOWERCASE_MOD_ID, ItemElectricToolType.TOOL_DRILL_DIAMOND, false, 1000000.0D, 3, 12.0D, 10.0F);
*/
    // Weapons
    public static final ItemWeaponQuantumSaber itemWeaponQuantumSaber = new ItemWeaponQuantumSaber();

    // Armor
    //public static final ItemArmorLappack itemArmorLappack = new ItemArmorLappack();
    //public static final ItemArmorQuantumLappack itemArmorQuantumLappack = new ItemArmorQuantumLappack();
    public static ItemArmorAdvancedJetpack itemArmorAdvancedJetpack;
    public static ItemArmorAdvancedNanoChestplate itemArmorAdvancedNanoChestplate;
    //public static final ItemArmorGraviChestplate itemArmorGraviChestplate = new ItemArmorGraviChestplate();

    // Resource
	//public static final ItemBlockCuttingBlade itemCuttingBladeIridium = new ItemBlockCuttingBlade(Reference.CUTTING_BLADE_IRIDIUM_NAME, 12);

    // Misc
    //public static final ItemMisc itemMiscQuantumCircuit = new ItemMisc(Reference.QUANTUM_CIRCUIT_NAME, 64);

    public static void init() {
        itemArmorAdvancedJetpack = new ItemArmorAdvancedJetpack();
        itemArmorAdvancedNanoChestplate = new ItemArmorAdvancedNanoChestplate();
		/*itemToolTheChopper = new ItemToolTheChopper();
        // Tools
        GameRegistry.registerItem(itemToolTheThingamabob, Reference.THE_THINGAMABOB_NAME);
        GameRegistry.registerItem(itemToolBigMiningDrill, Reference.BIG_MINING_DRILL_NAME);
        GameRegistry.registerItem(itemToolBigDiamondDrill, Reference.BIG_DIAMOND_DRILL_NAME);
        GameRegistry.registerItem(itemToolBigIridiumDrill, Reference.BIG_IRIDIUM_DRILL_NAME);
        GameRegistry.registerItem(itemToolTheChopper, Reference.THE_CHOPPER_NAME);
		GameRegistry.registerItem(itemElectricToolDrillArea, "DrillAreaTest");
*/
        // Weapons
        GameRegistry.registerItem(itemWeaponQuantumSaber, Reference.QUANTUM_SABER_NAME);

        // Armor
        //GameRegistry.registerItem(itemArmorLappack, Reference.LAPPACK_NAME);
        //GameRegistry.registerItem(itemArmorQuantumLappack, Reference.QUANTUM_LAPPACK_NAME);
        GameRegistry.registerItem(itemArmorAdvancedJetpack, Reference.ADVANCED_JETPACK_NAME);
        GameRegistry.registerItem(itemArmorAdvancedNanoChestplate, Reference.ADVANCED_NANO_CHESTPLATE_NAME);
        //GameRegistry.registerItem(itemArmorGraviChestplate, Reference.GRAVI_CHESTPLATE_NAME);

		// Resource
		//GameRegistry.registerItem(itemResourceCuttingBlade, Reference.CUTTING_BLADE_IRIDIUM_NAME);

        // Misc
        //GameRegistry.registerItem(itemMiscQuantumCircuit, Reference.QUANTUM_CIRCUIT_NAME);
    }
}
