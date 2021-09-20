package strike.mods.impl;

import org.lwjgl.input.Mouse;
import strike.gui.hud.ScreenPosition;
import java.util.ArrayList;
import java.util.List;
import strike.mods.ModDraggable;

public class ModCPS extends ModDraggable {
    List<Long> clicks;
    private boolean wasPressed;
    private long lastPressed;
    
    public ModCPS() {
        this.clicks = (List<Long>)new ArrayList();
    }
    
    @Override
    public int getWidth() {
        return this.font.getStringWidth("CPS: 00");
    }
    
    @Override
    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }
    
    @Override
    public void render(final ScreenPosition pos) {
        final boolean pressed = Mouse.isButtonDown(0);
        if (pressed != this.wasPressed) {
            this.lastPressed = System.currentTimeMillis();
            if (this.wasPressed = pressed) {
                this.clicks.add(this.lastPressed);
            }
        }
        this.font.drawString(new StringBuilder("CPS: ").append(this.getCPS()).toString(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
    
    private int getCPS() {
        final long time = System.currentTimeMillis();
        this.clicks.removeIf(aLong -> aLong + 1000L < time);
        return this.clicks.size();
    }
}
