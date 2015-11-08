package com.estebes.gravisuitereloaded.item.tool;

import com.estebes.gravisuitereloaded.util.Util;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemToolBigIridiumDrill extends ItemToolBigMiningDrill {
    public ItemToolBigIridiumDrill(String name, int energyTier, double maxCharge, double transferLimit, boolean providesEnergy, double operationCost) {
        super(name, energyTier, maxCharge, transferLimit, providesEnergy, operationCost);
    }

    @Override
    public float getDigSpeed(ItemStack itemStack, Block block, int meta) {
        if (!ElectricItem.manager.canUse(itemStack, this.operationCost)) {
            return 1.0F;
        }
        if (canHarvestBlock(block, itemStack)) {
            return 15.0F;
        }
        return 1.0F;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return (Items.diamond_pickaxe.canHarvestBlock(block, stack) ||
                Items.diamond_pickaxe.func_150893_a(stack, block) > 1.0F ||
                Items.diamond_shovel.canHarvestBlock(block, stack) ||
                Items.diamond_shovel.func_150893_a(stack, block) > 1.0F);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            return itemStack;
        }

        Map<Integer, Integer> enchantmentMap = new HashMap();

        if(EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) == 0) {
            enchantmentMap.put(Integer.valueOf(Enchantment.silkTouch.effectId), Integer.valueOf(1));
        }
        else {
            enchantmentMap.put(Integer.valueOf(Enchantment.fortune.effectId), Integer.valueOf(3));
        }
        EnchantmentHelper.setEnchantments(enchantmentMap, itemStack);
        return super.onItemRightClick(itemStack, world, entityplayer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List itemList) {
        Map<Integer, Integer> enchantmentMap = new HashMap<Integer, Integer>();
        enchantmentMap.put(Integer.valueOf(Enchantment.fortune.effectId), Integer.valueOf(3));

        // Charged Item
        ItemStack chargedItem = new ItemStack(this, 1);
        ElectricItem.manager.charge(chargedItem, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
        EnchantmentHelper.setEnchantments(enchantmentMap, chargedItem);
        NBTTagCompound nbtDataCharged = Util.getOrCreateNbtData(chargedItem);
        itemList.add(chargedItem);

        // Depleted Item
        ItemStack depletedItem = new ItemStack(this, 1);
        ElectricItem.manager.charge(depletedItem, 0.0D, Integer.MAX_VALUE, true, false);
        EnchantmentHelper.setEnchantments(enchantmentMap, depletedItem);
        NBTTagCompound nbtDataDepleted = Util.getOrCreateNbtData(chargedItem);
        itemList.add(depletedItem);
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }
}
