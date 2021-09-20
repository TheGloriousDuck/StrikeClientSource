package strike.mods.impl.togglesprintsneak;

import strike.gui.hud.ScreenPosition;
import strike.mods.ModDraggable;

public class ModToggleSprintSneak extends ModDraggable {
    public boolean flyBoost;
    public float flyBoostFactor;
    public int keyHoldTicks;
    private String textToRender;
    
    public ModToggleSprintSneak() {
        this.flyBoost = false;
        this.flyBoostFactor = 4.0f;
        this.keyHoldTicks = 7;
        this.textToRender = "";
    }
    
    @Override
    public int getWidth() {
        return this.font.getStringWidth(this.textToRender);
    }
    
    @Override
    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }
    
    @Override
    public void render(final ScreenPosition pos) {
        this.textToRender = this.mc.thePlayer.movementInput.getDisplayText();
        this.font.drawString(this.textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
    
    @Override
    public void renderDummy(final ScreenPosition pos) {
        this.textToRender = "[Sprinting (Key Toggled)]";
        this.font.drawString(this.textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
