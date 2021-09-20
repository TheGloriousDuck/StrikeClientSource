package strike.mods.impl;

import strike.gui.hud.ScreenPosition;
import strike.mods.ModDraggable;

public class ModXYZ extends ModDraggable {
    @Override
    public int getWidth() {
        return this.font.getStringWidth(this.getXYZString());
    }
    
    private String getXYZString() {
        return String.format("XYZ: %.3f / %.3f / %.3f", new Object[] { this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ });
    }
    
    @Override
    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }
    
    @Override
    public void render(final ScreenPosition pos) {
        this.font.drawString(this.getXYZString(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
