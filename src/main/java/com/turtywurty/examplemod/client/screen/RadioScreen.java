package com.turtywurty.examplemod.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.turtywurty.examplemod.ExampleMod;
import com.turtywurty.examplemod.common.tiles.RadioTileEntity;

import de.sfuhrm.radiobrowser4j.Station;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.gui.ScrollPanel;

public class RadioScreen extends Screen {

	private StationPanel stationPanel;
	protected static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MOD_ID, "textures/gui/radio.png");

	private int texWidth = 246, texHeight = 166;

	private final RadioTileEntity tileEntity;

	public RadioScreen(RadioTileEntity radioTile) {
		super(new TranslationTextComponent("screen." + ExampleMod.MOD_ID + ".radio"));
		this.width = 246;
		this.height = 166;
		this.tileEntity = radioTile;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackgroundElements(matrixStack);
		this.renderForegroundElements(matrixStack, mouseX, mouseY, partialTicks);
	}

	@SuppressWarnings("deprecation")
	private void renderBackgroundElements(MatrixStack matrixStack) {
		this.renderBackground(matrixStack);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		matrixStack.translate((this.width / 2) - (this.texWidth / 2), (this.height / 2) - (this.texHeight / 2), 0);
		blit(matrixStack, 0, 0, 0, 0, this.texWidth, this.texHeight);
	}

	private void renderForegroundElements(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderComponentHoverEffect(matrixStack, Style.EMPTY, mouseX, mouseY);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		this.stationPanel = new StationPanel(this.tileEntity, minecraft, 50, 100);
	}

	public static class StationPanel extends ScrollPanel {
		private List<StationButton> buttons = new ArrayList<>();

		private final RadioTileEntity tileEntity;

		public StationPanel(RadioTileEntity tileEntity, Minecraft client, int top, int left) {
			super(client, 40, 100, top, left);
			this.tileEntity = tileEntity;
			int buttonIndex = 0;
			for (Station s : this.tileEntity.radioStations) {
				buttons.add(new StationButton(left, top + (buttonIndex++ * this.height), this.width, this.height,
						new StringTextComponent(s.getName()), null));
				// System.out.println(buttons.get(buttonIndex++).getMessage().getString());
			}
		}

		@Override
		protected int getContentHeight() {
			return 40;
		}

		@Override
		protected void drawPanel(MatrixStack mStack, int entryRight, int relativeY, Tessellator tess, int mouseX,
				int mouseY) {
			this.render(mStack, mouseX, mouseY, mouseY);
			for (StationButton btn : this.buttons) {
				btn.render(mStack, mouseX, mouseY, mouseY);
			}
		}
	}

	protected static class StationButton extends Button {
		public StationButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction) {
			super(x, y, width, height, title, pressedAction);
		}
	}
}
