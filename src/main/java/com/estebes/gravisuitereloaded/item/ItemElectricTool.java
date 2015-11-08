package com.estebes.gravisuitereloaded.item;

import com.estebes.gravisuitereloaded.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.HashSet;
import java.util.List;

public class ItemElectricTool extends ItemTool implements IElectricItem {
    // Vars
    protected String name;
    protected double operationCost;

    // -------------- IElectricItem vars -------------- //
    protected int energyTier;
    protected double maxCharge;
    protected double transferLimit;
    protected boolean providesEnergy;

    // -------------- Constructor -------------- //
    public ItemElectricTool(String name, int energyTier, double maxCharge, double transferLimit, boolean providesEnergy) {
        super(0.0F, ToolMaterial.EMERALD, new HashSet());

        this.name = name;
        this.energyTier = energyTier;
        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.providesEnergy = providesEnergy;

        setUnlocalizedName(this.name);
        setMaxDamage(27);
        setMaxStackSize(1);
        setNoRepair();
    }

    // -------------- Item methods -------------- //
    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack1, ItemStack itemStack2) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
        return true;
    }

    // -------------- Client item methods -------------- //
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List itemList) {
        // Charged Item
        ItemStack chargedItem = new ItemStack(this, 1);
        ElectricItem.manager.charge(chargedItem, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
        itemList.add(chargedItem);

        // Depleted Item
        ItemStack depletedItem = new ItemStack(this, 1, getMaxDamage());
        itemList.add(depletedItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + this.name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass) {
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack, int pass) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean var) {
        list.add(StatCollector.translateToLocal("tooltip.tool.PowerTier") + " " + this.energyTier);
    }

    // -------------- IElectricItem implementation -------------- //
    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return this.providesEnergy;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return this.energyTier;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }
}
