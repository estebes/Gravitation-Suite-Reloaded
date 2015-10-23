package com.estebes.gravisuitereloaded.item.tool;

import com.estebes.gravisuitereloaded.item.ItemElectricTool;
import com.estebes.gravisuitereloaded.util.Util;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class ItemToolTheThingamabob extends ItemElectricTool {
    private double operationCost;

    public ItemToolTheThingamabob(String name/*, int energyTier, double maxCharge, double transferLimit, boolean providesEnergy*/) {
        super(name, 4, 1.0E7D, 1.2E4D, false);
        this.operationCost = 2.0E3D;
    }

    // Item methods
    public int getHarvestLevel(ItemStack itemStack, String toolClass) {
        return this.toolMaterial.getHarvestLevel();
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);
        int metaData = world.getBlockMetadata(x, y, z);
        if ((block.getBlockHardness(world, x, y, z) >= 0.0D)) {
            if (ElectricItem.manager.canUse(itemStack, this.operationCost)) {
                if (!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                    if (block.canSilkHarvest(world, entityPlayer, x, y, z, metaData)) {
                        ArrayList<ItemStack> items = new ArrayList();
                        if(Item.getItemFromBlock(block) != null) {
                            ItemStack drop = new ItemStack(getItemFromBlock(block), 1, metaData);
                            items.add(drop);
                        }
                        ForgeEventFactory.fireBlockHarvesting(items, world, block, x, y, z, metaData, 0, 1.0F, true, entityPlayer);
                        for (ItemStack drop : items) {
                            Random rand = new Random();
                            float dX = rand.nextFloat() * 0.8F + 0.1F;
                            float dY = rand.nextFloat() * 0.8F + 0.1F;
                            float dZ = rand.nextFloat() * 0.8F + 0.1F;
                            EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, drop.copy());
                            float factor = 0.05F;
                            entityItem.motionX = rand.nextGaussian() * factor;
                            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                            entityItem.motionZ = rand.nextGaussian() * factor;
                            world.spawnEntityInWorld(entityItem);
                        }
                        world.setBlockToAir(x, y, z);
                        ElectricItem.manager.use(itemStack, this.operationCost, entityPlayer);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        if ((block.getBlockHardness(world, x, y, z) != 0.0D) && (entity instanceof EntityPlayer)) {
            ElectricItem.manager.use(itemStack, this.operationCost, entity);
        }
        else {
            ElectricItem.manager.discharge(itemStack, this.operationCost, this.getTier(itemStack), true, false, false);
        }
        return true;
    }

    @Override
    public float getDigSpeed(ItemStack itemStack, Block block, int meta) {
        if (!ElectricItem.manager.canUse(itemStack, this.operationCost)) {
            return 0.0F;
        }
        if (canHarvestBlock(block, itemStack)) {
            return 20000.0F;
        }
        return 1.0F;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase player) {
        if (ElectricItem.manager.use(itemstack, this.operationCost, player)) {
            entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player), 15.0F);
        }
        else {
            entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 1.0F);
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List itemList) {
        Map<Integer, Integer> enchantmentMap = new HashMap();
        enchantmentMap.put(Integer.valueOf(Enchantment.fortune.effectId), Integer.valueOf(3));

        // Charged Item
        ItemStack chargedItem = new ItemStack(this, 1);
        ElectricItem.manager.charge(chargedItem, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
        EnchantmentHelper.setEnchantments(enchantmentMap, chargedItem);
        NBTTagCompound nbtDataCharged = Util.getOrCreateNbtData(chargedItem);
        updateAttributes(nbtDataCharged);
        itemList.add(chargedItem);

        // Depleted Item
        ItemStack depletedItem = new ItemStack(this, 1);
        ElectricItem.manager.charge(depletedItem, 0.0D, Integer.MAX_VALUE, true, false);
        EnchantmentHelper.setEnchantments(enchantmentMap, depletedItem);
        NBTTagCompound nbtDataDepleted = Util.getOrCreateNbtData(chargedItem);
        updateAttributes(nbtDataDepleted);
        itemList.add(depletedItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean var) {
        list.add(StatCollector.translateToLocal("tooltip.tool.PowerTier") + " " + 4);
        list.add(StatCollector.translateToLocal("tooltip.tool.VajraDescription"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack, int pass) {
        return false;
    }

    // ????
    private static void updateAttributes(NBTTagCompound nbtData) {
        NBTTagCompound entry = new NBTTagCompound();

        entry.setLong("UUIDMost", field_111210_e.getMostSignificantBits());
        entry.setLong("UUIDLeast", field_111210_e.getLeastSignificantBits());
        entry.setString("Name", "Tool modifier");
        entry.setInteger("Operation", 0);

        NBTTagList list = new NBTTagList();
        list.appendTag(entry);

        nbtData.setTag("AttributeModifiers", list);
    }
}
