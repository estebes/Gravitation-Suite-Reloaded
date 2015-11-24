package com.estebes.gravisuitereloaded.item;

import com.estebes.gravisuitereloaded.util.Util;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import ic2.api.item.ElectricItem;
import ic2.api.util.Keys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;

public class ItemElectricArmorJetpack extends ItemElectricArmor {
    public ItemElectricArmorJetpack(String name, int energyTier, double maxCharge, double transferLimit, boolean providesEnergy,
                                    int energyPerDamage, double damageAbsorptionRatio, double baseAbsorptionRatio, ArmorMaterial armorMaterial) {
        super(name, energyTier, maxCharge, transferLimit, providesEnergy, energyPerDamage, damageAbsorptionRatio, baseAbsorptionRatio, armorMaterial);
    }

    public double getCharge(ItemStack itemStack) {
        return ElectricItem.manager.getCharge(itemStack);
    }

    public void useEnergy(ItemStack itemStack, double energyAmount) {
        ElectricItem.manager.discharge(itemStack, energyAmount, this.energyTier, true, false, false);
    }

    public boolean useJetpack(EntityPlayer entityPlayer, boolean hoverMode) {
        ItemStack jetpack = entityPlayer.inventory.armorInventory[2];
        if (getCharge(jetpack) <= 0.0D) {
            return false;
        }

        float power = 1.0F;
        float dropPercentage = 0.001F;
        if (getCharge(jetpack) / getMaxCharge(jetpack) <= dropPercentage) {
            power = (float) (power * (getCharge(jetpack) / (getMaxCharge(jetpack) * dropPercentage)));
        }
        if (Keys.instance.isForwardKeyDown(entityPlayer)) {
            float retruster = 0.3F;
            if (hoverMode) {
                retruster = 0.65F;
            }
            float forwardpower = power * retruster * 2.0F;

            float boostSpeed = 0.0F;
            if ((Keys.instance.isBoostKeyDown(entityPlayer)) && ((getCharge(jetpack)) > 12 * 5)) {
                boostSpeed = 0.09F;
                if (hoverMode) {
                    boostSpeed = 0.07F;
                }
            }
            if (forwardpower > 0.0F) {
                entityPlayer.moveFlying(0.0F, 0.4F * forwardpower, 0.02F + boostSpeed);
                if ((boostSpeed > 0.0F) && (!entityPlayer.worldObj.isRemote)) {
                    useEnergy(jetpack, 12 * 5);
                }
            }
        }

        int maxFlightHeight = Util.getWorldHeight(entityPlayer.worldObj);

        double y = entityPlayer.posY;
        if (y > maxFlightHeight) {
            if (y > maxFlightHeight) {
                y = maxFlightHeight;
            }
            power = (float) (power * ((maxFlightHeight - y) / 25.0D));
        }
        double prevmotion = entityPlayer.motionY;
        entityPlayer.motionY = Math.min(entityPlayer.motionY + power * 0.2F, 0.6000000238418579D);
        if (hoverMode) {
            float maxHoverY = 0.0F;
            if (Keys.instance.isJumpKeyDown(entityPlayer)) {
                maxHoverY = 0.2F;
            }
            if (Keys.instance.isSneakKeyDown(entityPlayer)) {
                maxHoverY = -0.2F;
            }
            if (((getCharge(jetpack)) > 12 * 5) && Keys.instance.isBoostKeyDown(entityPlayer) &&
                    (Keys.instance.isSneakKeyDown(entityPlayer) || Keys.instance.isJumpKeyDown(entityPlayer))) {
                maxHoverY *= 2.0D;
                useEnergy(jetpack, 12 * 5);
            }

            if (entityPlayer.motionY > maxHoverY) {
                entityPlayer.motionY = maxHoverY;
                if (prevmotion > entityPlayer.motionY) {
                    entityPlayer.motionY = prevmotion;
                }
            }
        }

        if (!entityPlayer.onGround) {
            useEnergy(jetpack, 12);
        }

        entityPlayer.fallDistance = 0.0F;
        entityPlayer.distanceWalkedModified = 0.0F;

        resetPlayerInAirTime(entityPlayer);

        return true;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.inventory.armorInventory[2] != itemStack) {
            return;
        }
        NBTTagCompound nbtData = Util.getOrCreateNbtData(itemStack);
        boolean hoverMode = nbtData.getBoolean("hoverMode");
        byte toggleTimer = nbtData.getByte("toggleTimer");
        boolean jetpackUsed = false;
        if ((Keys.instance.isJumpKeyDown(player)) && (Keys.instance.isModeSwitchKeyDown(player)) && (toggleTimer == 0)) {
            toggleTimer = 10;
            hoverMode = !hoverMode;
            if (!world.isRemote) {
                nbtData.setBoolean("hoverMode", hoverMode);
            }
        }
        if ((Keys.instance.isJumpKeyDown(player)) || (hoverMode)) {
            jetpackUsed = useJetpack(player, hoverMode);
        }
        if ((!world.isRemote) && (toggleTimer > 0)) {
            toggleTimer = (byte)(toggleTimer - 1);

            nbtData.setByte("toggleTimer", toggleTimer);
        }

        if (jetpackUsed) {
            player.inventoryContainer.detectAndSendChanges();
        }
    }

    public void resetPlayerInAirTime(EntityPlayer player) {
        if (!(player instanceof EntityPlayerMP)) {
            return;
        }
        ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, ((EntityPlayerMP) player).playerNetServerHandler,
                Integer.valueOf(0), new String[]{"field_147365_f", "floatingTickCount"});
    }
}
