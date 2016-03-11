package com.estebes.gravisuitereloaded.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ToolItemRenderer implements IItemRenderer {
	private static RenderItem renderItem = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemStack, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		renderItem.renderIcon(0, 0, itemStack.getIconIndex(), 16, 16);

		GL11.glScalef(0.5F, 0.5F, 0.5F);
		int chargePercentage = (int)itemStack.getItem().getDurabilityForDisplay(itemStack);
		int chargeColor = chargePercentage >= 50 ? 0x00FF00 : chargePercentage >= 15 ? 0xFFFF00 : 0xFF0000;
		Minecraft.getMinecraft().fontRenderer.drawString(Integer.toString(chargePercentage), 1, 1, chargeColor);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
	}
}
