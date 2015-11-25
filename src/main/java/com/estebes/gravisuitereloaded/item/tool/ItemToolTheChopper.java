package com.estebes.gravisuitereloaded.item.tool;

import com.estebes.gravisuitereloaded.item.ItemElectricTool;
import com.estebes.gravisuitereloaded.reference.Reference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;

public class ItemToolTheChopper extends ItemElectricTool {

    public ItemToolTheChopper() {
        super(Reference.THE_CHOPPER_NAME, 2, 100000.0D, 128.0D, false);
        this.operationCost = 100.0D;
        MinecraftForge.EVENT_BUS.register(this);
    }


    @Override
    public boolean onBlockStartBreak (ItemStack itemStack, int x, int y, int z, EntityPlayer entityPlayer) {
        if (entityPlayer.isSneaking() || !ElectricItem.manager.canUse(itemStack, this.operationCost) ||
                entityPlayer.worldObj.getBlock(x, y, z) == null) {
            return super.onBlockStartBreak(itemStack, x, y, z, entityPlayer);
        }

        if (entityPlayer.worldObj.getBlock(x, y, z).isWood(entityPlayer.worldObj, x, y, z) ||
                entityPlayer.worldObj.getBlock(x, y, z).getMaterial() == Material.sponge)
            if(detectTree(entityPlayer.worldObj, x,y,z, entityPlayer.worldObj.getBlock(x, y, z))) {
                breakTree(entityPlayer.worldObj, x, y, z, x, y, z, itemStack, entityPlayer.worldObj.getBlock(x, y, z),
                        entityPlayer.worldObj.getBlockMetadata(x, y, z), entityPlayer);
                return true;
            }

        return super.onBlockStartBreak(itemStack, x, y, z, entityPlayer);
    }

    private boolean detectTree(World world, int x, int y, int z, Block wood) {
        int height = y;
        boolean foundTop = false;
        do {
            height++;
            Block block = world.getBlock(x, height, z);
            if (block != wood) {
                height--;
                foundTop = true;
            }
        } while (!foundTop);

        int numLeaves = 0;
        if (height - y < 50) {
            for (int xPos = x - 1; xPos <= x + 1; xPos++) {
                for (int yPos = height - 1; yPos <= height + 1; yPos++) {
                    for (int zPos = z - 1; zPos <= z + 1; zPos++) {
                        Block leaves = world.getBlock(xPos, yPos, zPos);
                        if (leaves != null && leaves.isLeaves(world, xPos, yPos, zPos))
                            numLeaves++;
                    }
                }
            }
        }

        return numLeaves > 3;
    }

    private void breakTree (World world, int x, int y, int z, int xStart, int yStart, int zStart, ItemStack stack, Block bID, int meta, EntityPlayer player)
    {
        for (int xPos = x - 1; xPos <= x + 1; xPos++) {
            for (int yPos = y; yPos <= y + 1; yPos++) {
                for (int zPos = z - 1; zPos <= z + 1; zPos++) {
                    if (ElectricItem.manager.canUse(stack, this.operationCost)) {
                        Block localBlock = world.getBlock(xPos, yPos, zPos);
                        if (bID == localBlock) {
                            int localMeta = world.getBlockMetadata(xPos, yPos, zPos);
                            float localHardness = localBlock == null ? Float.MAX_VALUE : localBlock.getBlockHardness(world, xPos, yPos, zPos);

                            if (!(localHardness < 0)) {
                                BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(x, y, z, world, localBlock, localMeta, player);
                                MinecraftForge.EVENT_BUS.post(event);

                                int xDist = xPos - xStart;
                                int yDist = yPos - yStart;
                                int zDist = zPos - zStart;

                                if (9*xDist*xDist + yDist*yDist + 9*zDist*zDist < 2500 ) {
                                    if (localMeta % 4 == meta % 4) {
                                        if (!player.capabilities.isCreativeMode) {
                                            localBlock.harvestBlock(world, player, x,y,z, localMeta);
                                            onBlockDestroyed(stack, world, localBlock, xPos, yPos, zPos, player);
                                        }

                                        world.setBlockToAir(xPos, yPos, zPos);
                                        if (!world.isRemote) {
                                            breakTree(world, xPos, yPos, zPos, xStart, yStart, zStart, stack, bID, meta, player);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving) {
        if (block.getBlockHardness(world, x, y, z) != 0.0D) {
            if (entityLiving != null) {
                ElectricItem.manager.use(itemStack, this.operationCost, entityLiving);
            }
            else {
                ElectricItem.manager.discharge(itemStack, this.operationCost, this.getTier(itemStack), true, false, false);
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            return;
        }
        Entity entity = event.target;
        EntityPlayer player = event.entityPlayer;
        ItemStack itemstack = player.inventory.getStackInSlot(player.inventory.currentItem);
        if ((itemstack != null) && (itemstack.getItem() == this) && ((entity instanceof IShearable)) && (ElectricItem.manager.use(itemstack, this.operationCost, player)))
        {
            IShearable target = (IShearable)entity;
            if (target.isShearable(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ))
            {
                ArrayList<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
                for (ItemStack stack : drops)
                {
                    EntityItem ent = entity.entityDropItem(stack, 1.0F);

                    ent.motionY += itemRand.nextFloat() * 0.05F;
                    ent.motionX += (itemRand.nextFloat() - itemRand.nextFloat()) * 0.1F;
                    ent.motionZ += (itemRand.nextFloat() - itemRand.nextFloat()) * 0.1F;
                }
            }
        }
    }


    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
    {
        ElectricItem.manager.use(itemstack, this.operationCost, attacker);
        return true;
    }

    @Override
    public float getDigSpeed(ItemStack itemStack, Block block, int meta) {
        if (!ElectricItem.manager.canUse(itemStack, this.operationCost)) {
            return 1.0F;
        }
        if (canHarvestBlock(block, itemStack)) {
            return 8.0F;
        }
        return 1.0F;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack itemStack) {
        return (Items.diamond_axe.canHarvestBlock(block, itemStack) ||
                Items.diamond_axe.func_150893_a(itemStack, block) > 1.0F ||
                Items.shears.canHarvestBlock(block, itemStack) ||
                Items.shears.func_150893_a(itemStack, block) > 1.0F);
    }


    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        Multimap<String, AttributeModifier> ret;
        if (ElectricItem.manager.canUse(stack, this.operationCost)) {
            ret = HashMultimap.create();
            ret.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", 12.0D, 0));
        }
        else {
            ret = super.getAttributeModifiers(stack);
        }
        return ret;
    }
}
