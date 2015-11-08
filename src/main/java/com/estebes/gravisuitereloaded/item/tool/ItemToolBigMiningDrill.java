package com.estebes.gravisuitereloaded.item.tool;

import com.estebes.gravisuitereloaded.item.ItemElectricTool;
import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Locale;

public class ItemToolBigMiningDrill extends ItemElectricTool {
    protected double operationCost;

    public ItemToolBigMiningDrill(String name, int energyTier, double maxCharge, double transferLimit, boolean providesEnergy, double operationCost) {
        super(name, energyTier, maxCharge, transferLimit, providesEnergy);
        this.operationCost = operationCost;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        Block block = player.worldObj.getBlock(x, y, z);
        if (block == null || !canHarvestBlock(block, stack)) {
            return super.onBlockStartBreak(stack, x, y, z, player);
        }
        MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, false, 4.5D);
        if (mop == null) {
            return super.onBlockStartBreak(stack, x, y, z, player);
        }
        int sideHit = mop.sideHit;

        int xRange = 1;
        int yRange = 1;
        int zRange = 1;
        switch (sideHit) {
            case 0:
            case 1:
                yRange = 0;
                zRange = 1;
                break;
            case 2:
            case 3:
                xRange = 1;
                zRange = 0;
                break;
            case 4:
            case 5:
                xRange = 0;
                zRange = 1;
                break;
        }

        for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
            for (int yPos = y - yRange; yPos <= y + yRange; yPos++) {
                for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
                    if (ElectricItem.manager.canUse(stack, this.operationCost)) {
                        if (xPos == x && yPos == y && zPos == z) {
                            continue;
                        }
                        if (!super.onBlockStartBreak(stack, xPos, yPos, zPos, player)) {
                            breakExtraBlock(player.worldObj, xPos, yPos, zPos, player, x, y, z, stack);
                            ElectricItem.manager.use(stack, this.operationCost, player);
                        }
                    }
                }
            }
        }
        return super.onBlockStartBreak(stack, x, y, z, player);
    }

    public void breakExtraBlock(World world, int x, int y, int z, EntityPlayer entityPlayer, int refX, int refY, int refZ, ItemStack itemStack) {
        if (world.isAirBlock(x, y, z)) {
            return;
        }

        if(!(entityPlayer instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) entityPlayer;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (!canHarvestBlock(block, itemStack) || !(block.getBlockHardness(world, x, y, z) > 0.0F)) {
            return;
        }

        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x,y,z);
        if(event.isCanceled()) {
            return;
        }

        if (player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, x, y, z, meta, player);
            if (block.removedByPlayer(world, player, x, y, z, false)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }

            if (!world.isRemote) {
                player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            }
            return;
        }

        if (!world.isRemote) {
            block.onBlockHarvested(world, x,y,z, meta, player);
            if(block.removedByPlayer(world, player, x,y,z, true)) {
                block.onBlockDestroyedByPlayer( world, x,y,z, meta);
                block.harvestBlock(world, player, x,y,z, meta);
                block.dropXpOnBlockBreak(world, x,y,z, event.getExpToDrop());
            }
            player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
        }
        else {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            if(block.removedByPlayer(world, player, x,y,z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }
        }
    }

    public static MovingObjectPosition raytraceFromEntity (World world, Entity player, boolean par3, double range) {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
        if (!world.isRemote && player instanceof EntityPlayer) {
            d1 += 1.62D;
        }
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        if (player instanceof EntityPlayerMP) {
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return world.func_147447_a(vec3, vec31, par3, !par3, par3);
    }

    // Stuff
    @Override
    public float getDigSpeed(ItemStack itemStack, Block block, int meta) {
        if (!ElectricItem.manager.canUse(itemStack, this.operationCost)) {
            return 1.0F;
        }
        if (canHarvestBlock(block, itemStack)) {
            return 5.0F;
        }
        return 1.0F;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack itemStack) {
        return (Items.iron_pickaxe.canHarvestBlock(block, itemStack) ||
                Items.iron_pickaxe.func_150893_a(itemStack, block) > 1.0F ||
                Items.iron_shovel.canHarvestBlock(block, itemStack) ||
                Items.iron_shovel.func_150893_a(itemStack, block) > 1.0F);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        if ((toolClass.equals("pickaxe")) || (toolClass.equals("shovel"))) {
            return this.toolMaterial.getHarvestLevel();
        }
        return super.getHarvestLevel(stack, toolClass);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset) {
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack torchStack = player.inventory.mainInventory[i];
            if ((torchStack != null) && (torchStack.getUnlocalizedName().toLowerCase(Locale.ENGLISH).contains("torch"))) {
                Item item = torchStack.getItem();
                if ((item instanceof ItemBlock)) {
                    int oldMeta = torchStack.getItemDamage();
                    int oldSize = torchStack.stackSize;
                    boolean result = torchStack.tryPlaceItemIntoWorld(player, world, x, y, z, side, xOffset, yOffset, zOffset);
                    if (player.capabilities.isCreativeMode) {
                        torchStack.setItemDamage(oldMeta);
                        torchStack.stackSize = oldSize;
                    }
                    else if (torchStack.stackSize <= 0) {
                        ForgeEventFactory.onPlayerDestroyItem(player, torchStack);
                        player.inventory.mainInventory[i] = null;
                    }
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return super.onItemUse(stack, player, world, x, y, z, side, xOffset, yOffset, zOffset);
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
}
