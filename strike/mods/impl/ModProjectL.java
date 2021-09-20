package strike.mods.impl;

import net.minecraft.util.EnumChatFormatting;
import strike.gui.hud.ScreenPosition;
import strike.event.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import strike.event.impl.MouseEvent;
import strike.mods.ModDraggable;

public class ModProjectL extends ModDraggable {
    @Override
    public int getWidth() {
        return this.font.getStringWidth("ProjectL: Disabled");
    }
    
    @Override
    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }
    
    @EventTarget
    public void onMouse(final MouseEvent event) {
        if (this.isEnabled && (event.dx != 0 || event.dy != 0)) {
            final EntityPlayerSP entity = Minecraft.getMinecraft().thePlayer;
            if (entity != null) {
                entity.prevRenderYawOffset = entity.renderYawOffset;
                entity.prevRotationYawHead = entity.rotationYawHead;
                entity.prevRotationYaw = entity.rotationYaw;
                entity.prevRotationPitch = entity.rotationPitch;
            }
        }
    }
    
    @Override
    public void render(final ScreenPosition pos) {
        String enabledString = "";
        if (this.isEnabled()) {
            enabledString = new StringBuilder().append(EnumChatFormatting.GREEN).append("Enabled").toString();
        }
        else {
            enabledString = new StringBuilder().append(EnumChatFormatting.RED).append("Disabled").toString();
        }
        this.font.drawString(new StringBuilder("ProjectL: ").append(enabledString).toString(), pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
    }
}
