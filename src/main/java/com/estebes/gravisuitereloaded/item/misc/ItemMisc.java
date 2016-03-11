package com.estebes.gravisuitereloaded.item.misc;

import com.estebes.gravisuitereloaded.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMisc extends Item {
    protected String itemName;

    public ItemMisc(String itemName, int maxStackSize) {
        this.itemName = itemName;

        this.maxStackSize = maxStackSize;
        this.setUnlocalizedName(this.itemName);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Reference.LOWERCASE_MOD_ID + ":" + this.itemName);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass) {
        return this.itemIcon;
    }
}
