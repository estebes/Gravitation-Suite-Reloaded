package com.estebes.gravisuitereloaded.item.armor;

import com.estebes.gravisuitereloaded.item.ItemElectricArmorJetpack;
import com.estebes.gravisuitereloaded.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemArmorAdvancedNanoChestplate extends ItemElectricArmorJetpack {
    public ItemArmorAdvancedNanoChestplate() {
        super(Reference.ADVANCED_NANO_CHESTPLATE_NAME, 3, 3.0E6D, 1.2E5D , false, 5000, 0.9D, 0.4D, ArmorMaterial.DIAMOND);
    }
}
