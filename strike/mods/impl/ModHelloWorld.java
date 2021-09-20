package strike.mods.impl;

import strike.gui.hud.ScreenPosition;
import strike.mods.ModDraggable;

public class ModHelloWorld extends ModDraggable {
    @Override
    public int getWidth() {
        return this.font.getStringWidth("Strike Client Alpha");
    }
    
    @Override
    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }
    
    @Override
    public void render(final ScreenPosition pos) {
        this.font.drawString("Strike Client Alpha", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, -1);
    }
    
    @Override
    public void renderDummy(final ScreenPosition pos) {
        this.font.drawString("Strike Client Alpha", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, -16711936);
    }
}
