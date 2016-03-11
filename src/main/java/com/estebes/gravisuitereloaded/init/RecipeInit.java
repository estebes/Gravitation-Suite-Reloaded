package com.estebes.gravisuitereloaded.init;

import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.List;

public class RecipeInit {
    public static void init() {
        // Tools
		// Big Mining Drill
        /*Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
                "OJO", "CDC", " A ",
                'O', IC2Items.getItem("overclockerUpgrade").copy(),
                'B', IC2Items.getItem("advironblockcuttingblade").copy(),
                'C', IC2Items.getItem("electronicCircuit").copy(),
				'J', copyWithWildCard(IC2Items.getItem("electricJetpack").copy()),
                'A', copyWithWildCard(IC2Items.getItem("advBattery").copy())
        );

		// Big Diamond Drill
		Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedNanoChestplate, 1),
				"OBO", "CDC", " C ",
				'O', new ItemStack(IC2Items.getItem("overclockerUpgrade").getItem()),
				'B', new ItemStack(IC2Items.getItem("diamondblockcuttingblade").getItem()),
				'C', new ItemStack(IC2Items.getItem("advancedCircuit").getItem()),
				'D', copyWithWildCard(new ItemStack(IC2Items.getItem("diamondDrill").getItem())),
				'E', copyWithWildCard(new ItemStack(IC2Items.getItem("energyCrystal").getItem()))
		);*/

		/*// Big Iridium Drill
		Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
				"OBO", "CDC", " L ",
				'O', new ItemStack(IC2Items.getItem("overclockerUpgrade").getItem()),
				'B', new ItemStack(IC2Items.getItem("overclockerUpgrade").getItem()),
				'C', new ItemStack(IC2Items.getItem("overclockerUpgrade").getItem()),
				'D', copyWithWildCard(new ItemStack(IC2Items.getItem("iridiumDrill").getItem())),
				'L', copyWithWildCard(new ItemStack(IC2Items.getItem("lapotronCrystal").getItem()))
		);

		// Vajra



		// Misc Items
		// Iridium Block Cutting Blade
		Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
				"AIA", "IDI", "AIA",
				'A', IC2Items.getItem("advancedAlloy").copy(),
				'I', IC2Items.getItem("iridiumPlate").copy(),
				'D', new ItemStack(Items.diamond)
		);

		// Magnetron
		Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
				" C ", "CIC", "CPC",
				'C', IC2Items.getItem("coil").copy(),
				'I', new ItemStack(IC2Items.getItem("turningBlankIron").getItem(), 1, 209697),
				'P', IC2Items.getItem("plateiron").copy()
		);

		// Super Conductor
		Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
				"GGG", "CIC", "GGG",
				'G', IC2Items.getItem("glassFiberCableItem").copy(),
				'C', IC2Items.getItem("carbonPlate").copy(),
				'I', IC2Items.getItem("iridiumPlate").copy()
		);

		// Quantum Circuit
		Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
				"HHH", "GCG", "HHH",
				'H', IC2Items.getItem("glassFiberCableItem").copy(),
				'G', IC2Items.getItem("carbonPlate").copy(),
				'C', IC2Items.getItem("iridiumPlate").copy()
		);

		// Vajra Core
		Recipes.advRecipes.addRecipe(
				new ItemStack(ItemInit.itemArmorAdvancedJetpack, 1),
				"HHH", "GCG", "HHH",
				'H', IC2Items.getItem("glassFiberCableItem").copy(),
				'G', IC2Items.getItem("carbonPlate").copy(),
				'C', IC2Items.getItem("iridiumPlate").copy()
		);


		// Machine Recipes
		// Lithium Cell
		Recipes.cannerBottle.addRecipe(
				new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
				new RecipeInputItemStack(IC2Items.getItem("lithiumDust"), 1),
				new ItemStack(IC2Items.getItem("reactorLithiumCell").getItem(), 1, 1)
		);

		// Tritium Cell
		Recipes.cannerBottle.addRecipe(
				new RecipeInputItemStack(IC2Items.getItem("TritiumCell"), 1),
				new RecipeInputItemStack(new ItemStack(IC2Items.getItem("FluidCell").getItem()), 1),
				getUniversalFluidCell("ic2hotwater", 1000)
		);
		// Enriched Glowstone
		Recipes.cannerEnrich.addRecipe(
				new RecipeInputItemStack(new ItemStack(Items.glowstone_dust, 1).copy()),
				new RecipeInputItemStack(new ItemStack(IC2Items.getItem("FluidCell").getItem()), 1),
				getUniversalFluidCell("ic2coolant", 1000)
		);*/
    }


	// Utility
    public static ItemStack copyWithWildCard(ItemStack itemStack) {
        ItemStack aux = itemStack.copy();
        Items.dye.setDamage(aux, 32767);
        return aux;
    }

	public static ItemStack getUniversalFluidCell(String fluidName, int fluidAmount) {
		ItemStack aux = IC2Items.getItem("FluidCell").copy();
		((IFluidContainerItem)IC2Items.getItem("FluidCell").getItem()).fill(aux,
				FluidRegistry.getFluidStack(fluidName, fluidAmount), true);
		return aux;
	}
}
