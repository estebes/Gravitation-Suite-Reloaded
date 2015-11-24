package com.estebes.gravisuitereloaded.init;

import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeInit {
    public static void init() {
        // Tools
        // Vajra
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemToolTheThingamabob, 1),
                "ICI", "CDC", "ILI",
                'I', new ItemStack(IC2Items.getItem("iridiumPlate").getItem()),
                'C', new ItemStack(ItemInit.itemMiscQuantumCircuit),
                'D', copyWithWildCard(new ItemStack(IC2Items.getItem("iridiumDrill").getItem())),
                'L', copyWithWildCard(new ItemStack(IC2Items.getItem("lapotronCrystal").getItem()))
        );

        // Big Mining Drill
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemToolBigMiningDrill, 1),
                " B ", "DTD", " B ",
                'B', copyWithWildCard(new ItemStack(IC2Items.getItem("advBattery").getItem())),
                'D', copyWithWildCard(new ItemStack(IC2Items.getItem("miningDrill").getItem())),
                'T', new ItemStack(IC2Items.getItem("lvTransformer").getItem(), 1, 4)
        );

        // Big Diamond Drill
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemToolBigDiamondDrill, 1),
                " B ", "DTD", " B ",
                'B', copyWithWildCard(new ItemStack(IC2Items.getItem("advBattery").getItem())),
                'D', copyWithWildCard(new ItemStack(IC2Items.getItem("diamondDrill").getItem())),
                'T', new ItemStack(IC2Items.getItem("lvTransformer").getItem(), 1, 4)
        );

        // Big Iridium Drill
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemToolBigIridiumDrill, 1),
                " C ", "DTD", " C ",
                'C', copyWithWildCard(new ItemStack(IC2Items.getItem("energyCrystal").getItem())),
                'D', copyWithWildCard(new ItemStack(IC2Items.getItem("iridiumDrill").getItem())),
                'T', new ItemStack(IC2Items.getItem("lvTransformer").getItem(), 1, 5)
        );

        // The Chopper
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemToolTheChopper, 1),
                " D ", "DCD", "ABA",
                'D', new ItemStack(Items.diamond),
                'C', copyWithWildCard(new ItemStack(IC2Items.getItem("energyCrystal").getItem())),
                'A', new ItemStack(IC2Items.getItem("advancedAlloy").getItem()),
                'B', copyWithWildCard(new ItemStack(IC2Items.getItem("advBattery").getItem()))
        );

        // Weapons
        // Quantum Saber
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemWeaponQuantumSaber, 1),
                "IA ", "IN ", "ILI",
                'I', new ItemStack(IC2Items.getItem("iridiumPlate").getItem()),
                'A', new ItemStack(IC2Items.getItem("advancedAlloy").getItem()),
                'N', copyWithWildCard(new ItemStack(IC2Items.getItem("nanoSaber").getItem())),
                'L', copyWithWildCard(new ItemStack(IC2Items.getItem("lapotronCrystal").getItem()))
        );

        // Armor
        // Lappack
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemArmorLappack, 1),
                "CAC", "LEL", "A A",
                'C', new ItemStack(IC2Items.getItem("advancedCircuit").getItem()),
                'A', new ItemStack(IC2Items.getItem("advancedAlloy").getItem()),
                'L', copyWithWildCard(new ItemStack(IC2Items.getItem("lapotronCrystal").getItem())),
                'E', copyWithWildCard(new ItemStack(IC2Items.getItem("energyPack").getItem()))
        );

        // Quantum Lappack
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemArmorQuantumLappack, 1),
                "ICI", "LEL", "LIL",
                'I', new ItemStack(IC2Items.getItem("iridiumPlate").getItem()),
                'C', new ItemStack(ItemInit.itemMiscQuantumCircuit),
                'L', copyWithWildCard(new ItemStack(IC2Items.getItem("lapotronCrystal").getItem())),
                'E', copyWithWildCard(new ItemStack(ItemInit.itemArmorLappack))
        );

        // Advanced Jetpack
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
                "SES", "AJA", "SPS",
                'S', new ItemStack(IC2Items.getItem("denseplatecopper").getItem(), 1, 5),
                'E', copyWithWildCard(new ItemStack(IC2Items.getItem("energyCrystal").getItem())),
                'A', new ItemStack(IC2Items.getItem("advancedCircuit").getItem()),
                'J', copyWithWildCard(new ItemStack(IC2Items.getItem("electricJetpack").getItem())),
                'P', copyWithWildCard(new ItemStack(IC2Items.getItem("energyPack").getItem()))
        );

        // Advanced Nano Chestplate
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemArmorAdvancedNanoChestplate, 1),
                "FJF", "ANA", "FJF",
                'F', new ItemStack(IC2Items.getItem("glassFiberCableItem").getItem(), 1, 9),
                'J', copyWithWildCard(new ItemStack(ItemInit.itemArmorAdvancedJetpack)),
                'A', new ItemStack(IC2Items.getItem("advancedCircuit").getItem()),
                'N', copyWithWildCard(new ItemStack(IC2Items.getItem("nanoBodyarmor").getItem()))
        );

        // Misc
        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemMiscQuantumCircuit, 1),
                "FFF", "LIL", "FFF",
                'F', new ItemStack(IC2Items.getItem("glassFiberCableItem").getItem(), 1, 9),
                'L', new ItemStack(IC2Items.getItem("lithiumDust").getItem(), 1, 14),
                'I', new ItemStack(IC2Items.getItem("iridiumPlate").getItem())
        );

        Recipes.advRecipes.addRecipe(new ItemStack(ItemInit.itemMiscQuantumCircuit, 1),
                "FLF", "FIF", "FLF",
                'F', new ItemStack(IC2Items.getItem("glassFiberCableItem").getItem(), 1, 9),
                'L', new ItemStack(IC2Items.getItem("lithiumDust").getItem(),1 , 14),
                'I', new ItemStack(IC2Items.getItem("iridiumPlate").getItem())
        );
    }

    public static ItemStack copyWithWildCard(ItemStack itemStack) {
        ItemStack aux = itemStack.copy();
        Items.dye.setDamage(aux, 32767);
        return aux;
    }
}
