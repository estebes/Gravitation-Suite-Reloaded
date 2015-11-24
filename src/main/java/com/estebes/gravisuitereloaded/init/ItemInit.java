package com.estebes.gravisuitereloaded.init;

import com.estebes.gravisuitereloaded.item.armor.ItemArmorAdvancedJetpack;
import com.estebes.gravisuitereloaded.item.armor.ItemArmorAdvancedNanoChestplate;
import com.estebes.gravisuitereloaded.item.armor.ItemArmorLappack;
import com.estebes.gravisuitereloaded.item.armor.ItemArmorQuantumLappack;
import com.estebes.gravisuitereloaded.item.misc.ItemMisc;
import com.estebes.gravisuitereloaded.item.tool.*;
import com.estebes.gravisuitereloaded.item.weapon.ItemWeaponQuantumSaber;
import com.estebes.gravisuitereloaded.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ItemInit {
    // Tools
    public static final ItemToolTheThingamabob itemToolTheThingamabob = new ItemToolTheThingamabob(Reference.THE_THINGAMABOB_NAME);
    public static final ItemToolBigMiningDrill itemToolBigMiningDrill = new ItemToolBigMiningDrill(Reference.BIG_MINING_DRILL_NAME,
            2, 2.7E5D, 9.0E2D, false, 5.0E1D);
    public static final ItemToolBigDiamondDrill itemToolBigDiamondDrill = new ItemToolBigDiamondDrill(Reference.BIG_DIAMOND_DRILL_NAME,
            2, 2.7E5D, 9.0E2D, false, 8.0E1D);
    public static final ItemToolBigIridiumDrill itemToolBigIridiumDrill = new ItemToolBigIridiumDrill(Reference.BIG_IRIDIUM_DRILL_NAME,
            3, 2.7E6D, 9.0E3D, false, 8.0E2D);
    public static final ItemToolTheChopper itemToolTheChopper = new ItemToolTheChopper();

    // Weapons
    public static final ItemWeaponQuantumSaber itemWeaponQuantumSaber = new ItemWeaponQuantumSaber();

    // Armor
    public static final ItemArmorLappack itemArmorLappack = new ItemArmorLappack();
    public static final ItemArmorQuantumLappack itemArmorQuantumLappack = new ItemArmorQuantumLappack();
    public static final ItemArmorAdvancedJetpack itemArmorAdvancedJetpack = new ItemArmorAdvancedJetpack();
    public static final ItemArmorAdvancedNanoChestplate itemArmorAdvancedNanoChestplate = new ItemArmorAdvancedNanoChestplate();
    //public static final ItemArmorGraviChestplate itemArmorGraviChestplate = new ItemArmorGraviChestplate();

    // Misc
    public static final ItemMisc itemMiscQuantumCircuit = new ItemMisc(Reference.QUANTUM_CIRCUIT_NAME, Reference.QUANTUM_CIRCUIT_STACKSIZE);

    public static void init() {
        // Tools
        GameRegistry.registerItem(itemToolTheThingamabob, Reference.THE_THINGAMABOB_NAME);
        GameRegistry.registerItem(itemToolBigMiningDrill, Reference.BIG_MINING_DRILL_NAME);
        GameRegistry.registerItem(itemToolBigDiamondDrill, Reference.BIG_DIAMOND_DRILL_NAME);
        GameRegistry.registerItem(itemToolBigIridiumDrill, Reference.BIG_IRIDIUM_DRILL_NAME);
        GameRegistry.registerItem(itemToolTheChopper, Reference.THE_CHOPPER_NAME);

        // Weapons
        GameRegistry.registerItem(itemWeaponQuantumSaber, Reference.QUANTUM_SABER_NAME);

        // Armor
        GameRegistry.registerItem(itemArmorLappack, Reference.LAPPACK_NAME);
        GameRegistry.registerItem(itemArmorQuantumLappack, Reference.QUANTUM_LAPPACK_NAME);
        GameRegistry.registerItem(itemArmorAdvancedJetpack, Reference.ADVANCED_JETPACK_NAME);
        GameRegistry.registerItem(itemArmorAdvancedNanoChestplate, Reference.ADVANCED_NANO_CHESTPLATE_NAME);
        //GameRegistry.registerItem(itemArmorGraviChestplate, Reference.GRAVI_CHESTPLATE_NAME);

        // Misc
        GameRegistry.registerItem(itemMiscQuantumCircuit, Reference.QUANTUM_CIRCUIT_NAME);
    }
}
