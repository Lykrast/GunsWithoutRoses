package lykrast.gunswithoutroses.gui;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BulletBagGui extends AbstractContainerScreen<BulletBagMenu> {
	//still copying botania yup https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/client/gui/bag/FlowerPouchGui.java
	private static final ResourceLocation TEXTURE = GunsWithoutRoses.rl("textures/gui/bullet_bag.png");

	public BulletBagGui(BulletBagMenu container, Inventory playerInv, Component title) {
		super(container, playerInv, title);
		imageHeight -= 33; //flower bag was +36 for a 202px high texture, and we're 133, so total -33
		// recompute, same as super
		inventoryLabelY = imageHeight - 94;
	}

	@Override
	public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(gui);
		super.render(gui, mouseX, mouseY, partialTicks);
		this.renderTooltip(gui, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
		int k = (width - imageWidth) / 2;
		int l = (height - imageHeight) / 2;
		gui.blit(TEXTURE, k, l, 0, 0, imageWidth, imageHeight);
	}

}
