package com.estebes.gravisuitereloaded.item.resource;

import com.estebes.gravisuitereloaded.item.misc.ItemMisc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IBlockCuttingBlade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockCuttingBlade extends ItemMisc implements IBlockCuttingBlade {
	protected int bladeHardness;

	public ItemBlockCuttingBlade(String itemName, int bladeHardness) {
		super(itemName, 1);

		this.bladeHardness = bladeHardness;
	}

	@Override
	public int gethardness() {
		return this.bladeHardness;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean var) {
		list.add(StatCollector.translateToLocal("tooltip.item.CuttingBladeDescription") + " " + this.itemName.replace("ItemCuttingBlade", ""));
		list.add(StatCollector.translateToLocal("tooltip.item.CuttingBladeHardness") + " " + this.bladeHardness);
	}
}
