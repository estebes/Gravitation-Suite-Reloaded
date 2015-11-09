package com.estebes.gravisuitereloaded.item;

import com.estebes.gravisuitereloaded.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.ICustomDamageItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IMetalArmor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;
import java.util.Random;

public class ItemElectricArmor extends ItemArmor implements IElectricItem, IMetalArmor, ISpecialArmor, ICustomDamageItem {
    private String name;

    // IElectricItem
    protected int energyTier;
    protected double maxCharge;
    protected double transferLimit;
    protected boolean providesEnergy;

    // ISpecialArmor
    protected int energyPerDamage;
    protected double damageAbsorptionRatio;
    protected double baseAbsorptionRatio;

    public ItemElectricArmor(String name, int energyTier, double maxCharge, double transferLimit, boolean providesEnergy,
                             int energyPerDamage, double damageAbsorptionRatio, double baseAbsorptionRatio, ArmorMaterial armorMaterial) {
        super(armorMaterial, 0, 1);

        this.name = name;
        this.energyTier = energyTier;
        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.providesEnergy = providesEnergy;
        this.energyPerDamage = energyPerDamage;
        this.damageAbsorptionRatio = damageAbsorptionRatio;
        this.baseAbsorptionRatio = baseAbsorptionRatio;

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
    public boolean getIsRepairable(ItemStack itemStack1, ItemStack itemStack2) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

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

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + this.name);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass) {
        return this.itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Reference.LOWERCASE_MOD_ID + ":textures/armor/" + this.name + ".png";
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.rare;
    }

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

    // -------------- ISpecialArmor implementation -------------- //
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.isUnblockable()) {
            return new ISpecialArmor.ArmorProperties(0, 0.0D, 0);
        }
        double absorptionRatio = this.baseAbsorptionRatio * this.damageAbsorptionRatio;

        int damageLimit = Integer.MAX_VALUE;
        if (this.energyPerDamage > 0) {
            damageLimit = (int)Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / this.energyPerDamage);
        }
        return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        if (ElectricItem.manager.getCharge(armor) >= 0) {
            return (int)Math.round(20.0D * this.baseAbsorptionRatio * this.damageAbsorptionRatio);
        }
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack itemStack, DamageSource source, int damage, int slot) {
        ElectricItem.manager.discharge(itemStack, damage * this.energyPerDamage, Integer.MAX_VALUE, true, false, false);
    }

    // -------------- IMetalArmor implementation -------------- //
    @Override
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return true;
    }

    // -------------- ICustomDamageItem implementation -------------- //
    @Override
    public int getCustomDamage(ItemStack stack) {
        return stack.getItemDamage();
    }

    @Override
    public int getMaxCustomDamage(ItemStack stack) {
        return stack.getMaxDamage();
    }

    @Override
    public void setCustomDamage(ItemStack stack, int damage) {
        stack.setItemDamage(damage);
    }

    @Override
    public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
        if (src != null) {
            stack.damageItem(damage, src);
            return true;
        }
        return stack.attemptDamageItem(damage, new Random());
    }
}
