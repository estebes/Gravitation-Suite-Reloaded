package com.estebes.gravisuitereloaded.item.armor;

import com.estebes.gravisuitereloaded.item.ItemElectricArmor;
import com.estebes.gravisuitereloaded.reference.Reference;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorLappack extends ItemElectricArmor {
    public ItemArmorLappack() {
        super(Reference.LAPPACK_NAME, 4, 2.0E7D, 1.2E5D , true, EnumHelper.addArmorMaterial("GSR", 27, new int[]{0, 0, 0, 0}, 0));
    }
}
