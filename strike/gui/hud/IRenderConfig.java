package strike.gui.hud;

public interface IRenderConfig {
    void save(final ScreenPosition screenPosition);
    
    ScreenPosition load();
}
