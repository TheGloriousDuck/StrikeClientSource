package strike.gui.hud;

import strike.event.EventTarget;
import java.util.Iterator;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import strike.event.impl.RenderEvent;
import net.minecraft.client.gui.GuiScreen;
import java.util.Collection;
import strike.event.EventManager;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import java.util.Set;

public class HUDManager {
    private static HUDManager instance;
    private Set<IRenderer> registeredRenderers;
    private Minecraft mc;
    
    static {
        HUDManager.instance = null;
    }
    
    private HUDManager() {
        this.registeredRenderers = (Set<IRenderer>)Sets.newHashSet();
        this.mc = Minecraft.getMinecraft();
    }
    
    public static HUDManager getInstance() {
        if (HUDManager.instance != null) {
            return HUDManager.instance;
        }
        EventManager.register(HUDManager.instance = new HUDManager());
        return HUDManager.instance;
    }
    
    public void register(final IRenderer... renderers) {
        for (final IRenderer render : renderers) {
            this.registeredRenderers.add(render);
        }
    }
    
    public void unregister(final IRenderer... renderers) {
        for (final IRenderer render : renderers) {
            this.registeredRenderers.remove(render);
        }
    }
    
    public Collection<IRenderer> getRegisteredRenderers() {
        return (Collection<IRenderer>)Sets.newHashSet((Iterable)this.registeredRenderers);
    }
    
    public void openConfigScreen() {
        this.mc.displayGuiScreen(new HUDConfigScreen(this));
    }
    
    @EventTarget
    public void onRender(final RenderEvent e) {
        if (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiContainer || this.mc.currentScreen instanceof GuiChat) {
            for (final IRenderer renderer : this.registeredRenderers) {
                this.callRenderer(renderer);
            }
        }
    }
    
    private void callRenderer(final IRenderer renderer) {
        if (!renderer.isEnabled()) {
            return;
        }
        ScreenPosition pos = renderer.load();
        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }
        renderer.render(pos);
    }
}
