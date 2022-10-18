package xyz.kaleidiodev.kaleidiosguns.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;
import xyz.kaleidiodev.kaleidiosguns.item.BulletItem;

public class BulletSpriteRenderer<T extends Entity & IRendersAsItem> extends EntityRenderer<T> {
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    private final float scale;
    private boolean fullBright;

    public BulletSpriteRenderer(EntityRendererManager p_i226035_1_, net.minecraft.client.renderer.ItemRenderer p_i226035_2_, float p_i226035_3_, boolean p_i226035_4_) {
        super(p_i226035_1_);
        this.itemRenderer = p_i226035_2_;
        this.scale = p_i226035_3_;
        this.fullBright = p_i226035_4_;
    }

    public BulletSpriteRenderer(EntityRendererManager p_i50957_1_, net.minecraft.client.renderer.ItemRenderer p_i50957_2_) {
        this(p_i50957_1_, p_i50957_2_, 1.0F, false);
    }

    protected int getBlockLightLevel(T pEntity, BlockPos pPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(pEntity, pPos);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
        if (pEntity.tickCount >= 2) {
            pMatrixStack.pushPose();
            pMatrixStack.scale((float)pEntity.getBoundingBox().getXsize()*3, (float)pEntity.getBoundingBox().getYsize()*3, (float)pEntity.getBoundingBox().getZsize()*3);
            pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            ItemStack stack = pEntity.getItem();
            System.out.println(stack.getOrCreateTag().contains("shot"));
            this.fullBright = stack.getOrCreateTag().getBoolean("isPlasma");
            this.itemRenderer.renderStatic(pEntity.getItem(), ItemCameraTransforms.TransformType.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer);
            pMatrixStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}
