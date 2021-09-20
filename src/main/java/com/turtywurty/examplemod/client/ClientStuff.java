package com.turtywurty.examplemod.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.turtywurty.examplemod.ExampleMod;
import com.turtywurty.examplemod.client.screen.RadioScreen;
import com.turtywurty.examplemod.common.tiles.RadioTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.client.world.DimensionRenderInfo.FogType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientStuff {

	private static final ResourceLocation DIM_RENDER_INFO = new ResourceLocation(ExampleMod.MOD_ID, "example_dim");
	private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("textures/item/redstone.png");
	private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("textures/block/dirt.png");
	private static final ResourceLocation SKY_TEXTURE = new ResourceLocation(ExampleMod.MOD_ID,
			"textures/environment/sky.png");

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void clientSetup(FMLClientSetupEvent event) {
		// public net.minecraft.client.world.DimensionRenderInfo field_239208_a_ #
		// field_239208_a_
		// public net.minecraft.client.renderer.Minecraft.getInstance().worldRenderer
		// field_175012_t # skyVBO
		// public net.minecraft.client.renderer.Minecraft.getInstance().worldRenderer
		// field_175014_r # skyVertexFormat
		// public net.minecraft.client.renderer.Minecraft.getInstance().worldRenderer
		// field_175013_s # starVBO
		// public net.minecraft.client.renderer.Minecraft.getInstance().worldRenderer
		// field_175011_u # sky2VBO

		DimensionRenderInfo.field_239208_a_.put(DIM_RENDER_INFO,
				// cloudHeight, alternate sky color, fog type, render sky, diffuse lighting
				new DimensionRenderInfo(128, true, FogType.NORMAL, true, false) {

					@Override
					// adjustSkyColor
					public Vector3d func_230494_a_(Vector3d fogColor, float partialTicks) {
						return fogColor;
					}

					@Override
					// useThickFog
					public boolean func_230493_a_(int posX, int posY) {
						return false;
					}

					@Override
					public ISkyRenderHandler getSkyRenderHandler() {
						return new ISkyRenderHandler() {

							@SuppressWarnings({ "deprecation" })
							@Override
							public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world,
									Minecraft mc) {
								RenderSystem.disableTexture();
								Vector3d vector3d = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(),
										partialTicks);
								float f = (float) vector3d.x;
								float f1 = (float) vector3d.y;
								float f2 = (float) vector3d.z;
								FogRenderer.applyFog();
								BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
								RenderSystem.depthMask(false);
								RenderSystem.enableFog();
								RenderSystem.color3f(f, f1, f2);
								mc.worldRenderer.skyVBO.bindBuffer();
								mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
								mc.worldRenderer.skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
								VertexBuffer.unbindBuffer();
								mc.worldRenderer.skyVertexFormat.clearBufferState();

								Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
								RenderSystem.enableAlphaTest();
								RenderSystem.enableTexture();
								RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
										GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE,
										GlStateManager.DestFactor.ZERO);
								RenderSystem.color4f(1f, 1f, 1f, 1f);
								mc.getTextureManager().bindTexture(SKY_TEXTURE);
								bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
								bufferbuilder.pos(matrix4f1, -100, 8f, -100).tex(0.0F, 0.0F).endVertex();
								bufferbuilder.pos(matrix4f1, 100, 8f, -100).tex(1.0F, 0.0F).endVertex();
								bufferbuilder.pos(matrix4f1, 100, 8f, 100).tex(1.0F, 1.0F).endVertex();
								bufferbuilder.pos(matrix4f1, -100, 8f, 100).tex(0.0F, 1.0F).endVertex();
								bufferbuilder.finishDrawing();
								WorldVertexBufferUploader.draw(bufferbuilder);
								RenderSystem.disableTexture();

								RenderSystem.disableFog();
								RenderSystem.disableAlphaTest();
								RenderSystem.enableBlend();
								RenderSystem.defaultBlendFunc();
								float[] afloat = world.func_239132_a_().func_230492_a_(world.func_242415_f(partialTicks),
										partialTicks);
								if (afloat != null) {
									RenderSystem.disableTexture();
									RenderSystem.shadeModel(7425);
									matrixStack.push();
									matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
									float f3 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F
											: 0.0F;
									matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3));
									matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
									float f4 = afloat[0];
									float f5 = afloat[1];
									float f6 = afloat[2];
									Matrix4f matrix4f = matrixStack.getLast().getMatrix();
									bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
									bufferbuilder.pos(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();

									for (int j = 0; j <= 16; ++j) {
										float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
										float f8 = MathHelper.sin(f7);
										float f9 = MathHelper.cos(f7);
										bufferbuilder.pos(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3])
												.color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
									}

									bufferbuilder.finishDrawing();
									WorldVertexBufferUploader.draw(bufferbuilder);
									matrixStack.pop();
									RenderSystem.shadeModel(7424);
								}

								RenderSystem.enableTexture();
								RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
										GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE,
										GlStateManager.DestFactor.ZERO);
								matrixStack.push();
								float f11 = 1.0F - world.getRainStrength(partialTicks);
								RenderSystem.color4f(1.0F, 1.0F, 1.0F, f11);
								matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
								matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F));
								matrix4f1 = matrixStack.getLast().getMatrix();

								float f12 = 30.0F;
								mc.getTextureManager().bindTexture(SUN_TEXTURES);
								bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
								bufferbuilder.pos(matrix4f1, -f12, 100.0F, -f12).tex(0.0F, 0.0F).endVertex();
								bufferbuilder.pos(matrix4f1, f12, 100.0F, -f12).tex(1.0F, 0.0F).endVertex();
								bufferbuilder.pos(matrix4f1, f12, 100.0F, f12).tex(1.0F, 1.0F).endVertex();
								bufferbuilder.pos(matrix4f1, -f12, 100.0F, f12).tex(0.0F, 1.0F).endVertex();
								bufferbuilder.finishDrawing();
								WorldVertexBufferUploader.draw(bufferbuilder);

								f12 = 20.0F;
								mc.getTextureManager().bindTexture(MOON_PHASES_TEXTURES);
								int k = world.getMoonPhase();
								int l = k % 4;
								int i1 = k / 4 % 2;
								float f13 = (float) (l + 0) / 4.0F;
								float f14 = (float) (i1 + 0) / 2.0F;
								float f15 = (float) (l + 1) / 4.0F;
								float f16 = (float) (i1 + 1) / 2.0F;
								bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
								bufferbuilder.pos(matrix4f1, -f12, -100.0F, f12).tex(f15, f16).endVertex();
								bufferbuilder.pos(matrix4f1, f12, -100.0F, f12).tex(f13, f16).endVertex();
								bufferbuilder.pos(matrix4f1, f12, -100.0F, -f12).tex(f13, f14).endVertex();
								bufferbuilder.pos(matrix4f1, -f12, -100.0F, -f12).tex(f15, f14).endVertex();
								bufferbuilder.finishDrawing();

								WorldVertexBufferUploader.draw(bufferbuilder);
								RenderSystem.disableTexture();

								float f10 = world.getStarBrightness(partialTicks) * f11;
								if (f10 > 0.0F) {
									RenderSystem.color4f(f10, f10, f10, f10);
									mc.worldRenderer.starVBO.bindBuffer();
									mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
									mc.worldRenderer.starVBO.draw(matrixStack.getLast().getMatrix(), 7);
									VertexBuffer.unbindBuffer();
									mc.worldRenderer.skyVertexFormat.clearBufferState();
								}

								RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
								RenderSystem.disableBlend();
								RenderSystem.enableAlphaTest();
								RenderSystem.enableFog();
								matrixStack.pop();
								RenderSystem.disableTexture();
								RenderSystem.color3f(0.0F, 0.0F, 0.0F);
								double d0 = mc.player.getEyePosition(partialTicks).y
										- world.getWorldInfo().getVoidFogHeight();
								if (d0 < 0.0D) {
									matrixStack.push();
									matrixStack.translate(0.0D, 12.0D, 0.0D);
									mc.worldRenderer.sky2VBO.bindBuffer();
									mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
									mc.worldRenderer.sky2VBO.draw(matrixStack.getLast().getMatrix(), 7);
									VertexBuffer.unbindBuffer();
									mc.worldRenderer.skyVertexFormat.clearBufferState();
									matrixStack.pop();
								}

								if (world.func_239132_a_().func_239216_b_()) {
									RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
								} else {
									RenderSystem.color3f(f, f1, f2);
								}

								RenderSystem.enableTexture();
								RenderSystem.depthMask(true);
								RenderSystem.disableFog();
							}
						};
					}
				});
	}
	
	public static void displayRadioScreen(RadioTileEntity te) {
		Minecraft.getInstance().displayGuiScreen(new RadioScreen(te));
	}
}
