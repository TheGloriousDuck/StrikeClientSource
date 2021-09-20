package strike.mods.impl;

import net.minecraft.client.Minecraft;
import strike.gui.hud.ScreenPosition;
import strike.mods.ModDraggable;

public class ModFullbright extends ModDraggable {
    private ScreenPosition pos;
    
    public ModFullbright() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 10.0f;
    }
    
    @Override
    public int getWidth() {
        return 0;
    }
    
    @Override
    public int getHeight() {
        return 0;
    }
    
    @Override
    public void render(final ScreenPosition pos) {
    }
    
    @Override
    public void save(final ScreenPosition pos) {
    }
    
    @Override
    public ScreenPosition load() {
        return this.pos;
    }
}
