package com.estebes.gravisuitereloaded.item.tool;

import com.estebes.gravisuitereloaded.item.ItemElectricTool;
import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings("all")
public class ItemToolBigMiningDrill extends ItemElectricTool {
    protected double operationCost;
    private Set<Object> toolProperties;

    public ItemToolBigMiningDrill(String name, int energyTier, double maxCharge, double transferLimit, boolean providesEnergy, double operationCost) {
        super(name, energyTier, maxCharge, transferLimit, providesEnergy);
        this.operationCost = operationCost;
        this.toolProperties = new HashSet(Arrays.asList(new Object[] { Items.iron_pickaxe, Items.diamond_shovel }));
    }


    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        if ((toolClass.equals("pickaxe")) || (toolClass.equals("shovel"))) {
            return this.toolMaterial.getHarvestLevel();
        }
        return super.getHarvestLevel(stack, toolClass);
    }


}
