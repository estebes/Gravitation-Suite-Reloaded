package com.estebes.gravisuitereloaded.item.armor;

import com.estebes.gravisuitereloaded.item.ItemElectricArmorJetpack;
import com.estebes.gravisuitereloaded.reference.Reference;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorAdvancedJetpack extends ItemElectricArmorJetpack {
    public ItemArmorAdvancedJetpack() {
        super(Reference.ADVANCED_JETPACK_NAME, 3, 3.0E6D, 1.2E5D, false, 0, 0.0D, 0.0D, EnumHelper.addArmorMaterial("GSR", 27, new int[]{0, 0, 0, 0}, 0));
    }
}
