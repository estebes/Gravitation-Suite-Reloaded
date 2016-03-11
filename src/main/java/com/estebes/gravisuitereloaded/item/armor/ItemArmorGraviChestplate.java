package com.estebes.gravisuitereloaded.item.armor;

import com.estebes.gravisuitereloaded.item.ItemElectricArmorJetpack;
import com.estebes.gravisuitereloaded.reference.Reference;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHealth;
import net.minecraft.world.World;

import java.util.Iterator;

public class ItemArmorGraviChestplate extends ItemElectricArmorJetpack {
    public ItemArmorGraviChestplate() {
        super(Reference.GRAVI_CHESTPLATE_NAME, 4, 3.0E7D, 1.2E5D , false, 5000, 1.1D, 0.4D, ArmorMaterial.DIAMOND);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack) {
        /*if (entityPlayer.getActivePotionEffects() != null) {
            for (Iterator<PotionEffect> iterator = entityPlayer.getActivePotionEffects().iterator(); iterator.hasNext();) {
                PotionEffect potionEffect = iterator.next();
                if (PotionHealth.potionTypes.potionEffect.getPotionID() && ElectricItem.manager.canUse(itemStack, 2000.0D)) {
                    entityPlayer.removePotionEffect(potionEffect.getPotionID());
                    ElectricItem.manager.use(itemStack, 2000.0D, entityPlayer);
                }
            }
        }*/
        super.onArmorTick(world, entityPlayer, itemStack);
    }
}
