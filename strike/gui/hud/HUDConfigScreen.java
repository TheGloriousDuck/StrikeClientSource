package strike.gui.hud;

import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import java.util.Iterator;
import java.util.Collection;
import java.util.Optional;
import java.util.HashMap;
import net.minecraft.client.gui.GuiScreen;

public class HUDConfigScreen extends GuiScreen {
    private final HashMap<IRenderer, ScreenPosition> renderers;
    private Optional<IRenderer> selectedRenderer;
    private int prevX;
    private int prevY;
    
    public HUDConfigScreen(final HUDManager api) {
        this.renderers = (HashMap<IRenderer, ScreenPosition>)new HashMap();
        this.selectedRenderer = (Optional<IRenderer>)Optional.empty();
        final Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();
        for (final IRenderer ren : registeredRenderers) {
            if (!ren.isEnabled()) {
                continue;
            }
            ScreenPosition pos = ren.load();
            if (pos == null) {
                pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
            }
            this.adjustBounds(ren, pos);
            this.renderers.put(ren, pos);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawDefaultBackground();
        final float zBackup = this.zLevel;
        this.zLevel = 200.0f;
        this.drawHollowRect(0, 0, this.width - 1, this.height - 1, -65536);
        for (final IRenderer renderer : this.renderers.keySet()) {
            final ScreenPosition pos = (ScreenPosition)this.renderers.get(renderer);
            renderer.renderDummy(pos);
            this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), -16711681);
        }
        this.zLevel = zBackup;
    }
    
    private void drawHollowRect(final int x, final int y, final int w, final int h, final int color) {
        this.drawHorizontalLine(x, x + w, y, color);
        this.drawHorizontalLine(x, x + w, y + h, color);
        this.drawVerticalLine(x, y + h, y, color);
        this.drawVerticalLine(x + w, y + h, y, color);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.renderers.entrySet().forEach(entry -> ((IRenderer)entry.getKey()).save((ScreenPosition)entry.getValue()));
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    protected void mouseClickMove(final int x, final int y, final int button, final long time) {
        if (this.selectedRenderer.isPresent()) {
            this.moveSelectedRenderBy(x - this.prevX, y - this.prevY);
        }
        this.prevX = x;
        this.prevY = y;
    }
    
    private void moveSelectedRenderBy(final int offsetX, final int offsetY) {
        final IRenderer renderer = (IRenderer)this.selectedRenderer.get();
        final ScreenPosition pos = (ScreenPosition)this.renderers.get(renderer);
        pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);
        this.adjustBounds(renderer, pos);
    }
    
    @Override
    public void onGuiClosed() {
        for (final IRenderer renderer : this.renderers.keySet()) {
            renderer.save((ScreenPosition)this.renderers.get(renderer));
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    private void adjustBounds(final IRenderer renderer, final ScreenPosition pos) {
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        final int screenWidth = res.getScaledWidth();
        final int screenHeight = res.getScaledHeight();
        final int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        final int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));
        pos.setAbsolute(absoluteX, absoluteY);
    }
    
    @Override
    protected void mouseClicked(final int x, final int y, final int button) throws IOException {
        this.loadMouseOver(this.prevX = x, this.prevY = y);
    }
    
    private void loadMouseOver(final int x, final int y) {
        this.selectedRenderer = (Optional<IRenderer>)this.renderers.keySet().stream().filter((Predicate)new MouseOverFinder(x, y)).findFirst();
    }
    
    private class MouseOverFinder implements Predicate<IRenderer> {
        private int mouseX;
        private int mouseY;
        
        public MouseOverFinder(final int x, final int y) {
            this.mouseX = x;
            this.mouseY = y;
        }
        
        public boolean test(final IRenderer renderer) {
            final ScreenPosition pos = (ScreenPosition)HUDConfigScreen.this.renderers.get(renderer);
            final int absoluteX = pos.getAbsoluteX();
            final int absoluteY = pos.getAbsoluteY();
            return this.mouseX >= absoluteX && this.mouseX <= absoluteX + renderer.getWidth() && this.mouseY >= absoluteY && this.mouseY <= absoluteY + renderer.getHeight();
        }
    }
}
