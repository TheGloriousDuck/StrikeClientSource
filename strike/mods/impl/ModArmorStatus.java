package strike.mods.impl;

import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import strike.gui.hud.ScreenPosition;
import strike.mods.ModDraggable;

public class ModArmorStatus extends ModDraggable {
    @Override
    public int getWidth() {
        return 64;
    }
    
    @Override
    public int getHeight() {
        return 64;
    }
    
    @Override
    public void render(final ScreenPosition pos) {
        for (int i = 0; i < this.mc.thePlayer.inventory.armorInventory.length; ++i) {
            final ItemStack itemStack = this.mc.thePlayer.inventory.armorInventory[i];
            this.renderItemStack(pos, i, itemStack);
        }
    }
    
    @Override
    public void renderDummy(final ScreenPosition pos) {
        this.renderItemStack(pos, 3, new ItemStack(Items.diamond_helmet));
        this.renderItemStack(pos, 2, new ItemStack(Items.diamond_chestplate));
        this.renderItemStack(pos, 1, new ItemStack(Items.diamond_leggings));
        this.renderItemStack(pos, 0, new ItemStack(Items.diamond_boots));
    }
    
    private void renderItemStack(final ScreenPosition pos, final int i, final ItemStack is) {
        if (is == null) {
            return;
        }
        GL11.glPushMatrix();
        final int yAdd = -16 * i + 48;
        if (is.getItem().isDamageable()) {
            final double damage = (is.getMaxDamage() - is.getItemDamage()) / (double)is.getMaxDamage() * 100.0;
            this.font.drawString(String.format("%.2f%%", new Object[] { damage }), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5, -1);
        }
        RenderHelper.enableGUIStandardItemLighting();
        this.mc.getRenderItem().renderItemAndEffectIntoGUI(is, pos.getAbsoluteX(), pos.getAbsoluteY() + yAdd);
        GL11.glPopMatrix();
    }
}
