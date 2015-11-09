package com.estebes.gravisuitereloaded.item.armor;

import com.estebes.gravisuitereloaded.item.ItemElectricArmor;
import com.estebes.gravisuitereloaded.reference.Reference;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorQuantumLappack extends ItemElectricArmor {
    public ItemArmorQuantumLappack() {
        super(Reference.QUANTUM_LAPPACK_NAME, 4, 6.0E7D, 1.2E5D , true, 0, 0.0D, 0.0D, EnumHelper.addArmorMaterial("GSR", 27, new int[]{0, 0, 0, 0}, 0));
    }
}
