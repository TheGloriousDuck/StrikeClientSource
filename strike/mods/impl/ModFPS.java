package strike.mods.impl;

import net.minecraft.client.Minecraft;
import strike.gui.hud.ScreenPosition;
import strike.mods.ModDraggable;

public class ModFPS extends ModDraggable {
    @Override
    public int getWidth() {
        return 50;
    }
    
    @Override
    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }
    
    @Override
    public void render(final ScreenPosition pos) {
        this.font.drawString(new StringBuilder("FPS: ").append(Minecraft.getDebugFPS()).toString(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
