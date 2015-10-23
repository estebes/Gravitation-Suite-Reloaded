package com.estebes.gravisuitereloaded.item.weapon;

import com.estebes.gravisuitereloaded.reference.Reference;
import com.estebes.gravisuitereloaded.util.Util;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ItemWeaponQuantumSaber extends ItemTool implements IElectricItem {

    // IElectricItem
    private int tier;
    private double maxCharge;
    private double transferLimit;
    private double operationCost;
    private double idleCost;

    // Texture
    private IIcon[] textures;

    public ItemWeaponQuantumSaber() {
        super(0.0F, ToolMaterial.EMERALD, new HashSet());
        this.tier = 4;
        this.maxCharge = 1.0E7D;
        this.transferLimit = 1.2E4D;
        this.operationCost = 3.0E3D;
        this.idleCost = 2.0E2D;

        setUnlocalizedName(Reference.QUANTUM_SABER_NAME);
        setMaxDamage(27);
        setMaxStackSize(1);
        setNoRepair();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[2];
        this.textures[0] = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + Reference.QUANTUM_SABER_NAME + "Off");
        this.textures[1] = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + Reference.QUANTUM_SABER_NAME + "On");
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass) {
        NBTTagCompound nbtData = Util.getOrCreateNbtData(itemStack);
        if (nbtData.getBoolean("active")) {
            return this.textures[1];
        }
        return this.textures[0];
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
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
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        int damage = 6;
        if (ElectricItem.manager.canUse(stack, this.idleCost)) {
            NBTTagCompound nbtData = Util.getOrCreateNbtData(stack);
            if (nbtData.getBoolean("active")) {
                damage = 40;
            }
        }

        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", damage, 0));
        return multimap;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase source) {
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity) {
        NBTTagCompound nbtData = Util.getOrCreateNbtData(itemStack);
        if(!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            if(entity instanceof EntityLivingBase) {
                if(!nbtData.getBoolean("active")) {
                    ((EntityLivingBase) entity).attackEntityFrom(DamageSource.causePlayerDamage(player), 6);
                    return true;
                }
                else {
                    if (!ElectricItem.manager.use(itemStack, this.operationCost, player)) {
                        nbtData.setBoolean("active", false);
                        Map<Integer, Integer> enchantmentMap = new HashMap();
                        EnchantmentHelper.setEnchantments(enchantmentMap, itemStack);
                        updateAttributes(nbtData);
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 6);
                        return true;
                    }
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 40);
                    //Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation("gravisuitereloaded:johncena")));
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            return itemStack;
        }

        NBTTagCompound nbtData = Util.getOrCreateNbtData(itemStack);
        Map<Integer, Integer> enchantmentMap = new HashMap();

        if (nbtData.getBoolean("active")) {
            nbtData.setBoolean("active", false);
            EnchantmentHelper.setEnchantments(enchantmentMap, itemStack);
            updateAttributes(nbtData);
        }
        else if (ElectricItem.manager.canUse(itemStack, this.idleCost)) {
            nbtData.setBoolean("active", true);
            enchantmentMap.put(Integer.valueOf(Enchantment.looting.effectId), Integer.valueOf(3));
            EnchantmentHelper.setEnchantments(enchantmentMap, itemStack);
            updateAttributes(nbtData);
        }
        return super.onItemRightClick(itemStack, world, entityplayer);
    }

    private static void updateAttributes(NBTTagCompound nbtData) {
        boolean active = nbtData.getBoolean("active");
        int damage = active ? 40 : 6;

        NBTTagCompound entry = new NBTTagCompound();

        entry.setString("AttributeName", SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        entry.setLong("UUIDMost", field_111210_e.getMostSignificantBits());
        entry.setLong("UUIDLeast", field_111210_e.getLeastSignificantBits());
        entry.setString("Name", "Tool modifier");
        entry.setDouble("Amount", damage);
        entry.setInteger("Operation", 0);

        NBTTagList list = new NBTTagList();
        list.appendTag(entry);

        nbtData.setTag("AttributeModifiers", list);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List itemList) {

        // Charged Item
        ItemStack chargedItem = new ItemStack(this, 1);
        ElectricItem.manager.charge(chargedItem, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
        itemList.add(chargedItem);

        // Depleted Item
        ItemStack depletedItem = new ItemStack(this, 1);
        ElectricItem.manager.charge(depletedItem, 0.0D, Integer.MAX_VALUE, true, false);
        itemList.add(depletedItem);
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    public boolean isBookEnchantable(ItemStack item, ItemStack book) {
        return false;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return block == Blocks.web ? 6.0F : 0.0F;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack itemStack) {
        return block == Blocks.web;
    }

    @Override
    public boolean getIsRepairable(ItemStack item1, ItemStack item2) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean var) {
        list.add(StatCollector.translateToLocal("tooltip.tool.PowerTier") + " " + this.tier);
        list.add(StatCollector.translateToLocal("tooltip.tool.QuantumSaberDescription"));
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean par5) {
        NBTTagCompound nbtData = Util.getOrCreateNbtData(itemStack);
        if (!nbtData.getBoolean("active")) {
            return;
        }
        if (entity instanceof EntityPlayerMP) {
            if(!ElectricItem.manager.use(itemStack, this.idleCost/20, (EntityPlayer)entity)) {
                nbtData.setBoolean("active", false);
                Map<Integer, Integer> enchantmentMap = new HashMap();
                EnchantmentHelper.setEnchantments(enchantmentMap, itemStack);
                updateAttributes(nbtData);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack, int pass) {
        return false;
    }
}
