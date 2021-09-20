package strike;

import strike.event.EventTarget;
import net.minecraft.client.Minecraft;
import strike.event.impl.ClientTickEvent;
import strike.mods.ModInstances;
import strike.event.EventManager;
import strike.gui.SplashProgress;
import strike.gui.hud.HUDManager;

public class Client {
    private static final Client INSTANCE;
    private DiscordRP discordRP;
    private HUDManager hudManager;
    
    static {
        INSTANCE = new Client();
    }
    
    public Client() {
        this.discordRP = new DiscordRP();
    }
    
    public static final Client getInstance() {
        return Client.INSTANCE;
    }
    
    public void init() {
        FileManager.init();
        SplashProgress.setProgress(1, "Client - Initializing DiscordRPC");
        this.discordRP.start();
        EventManager.register(this);
    }
    
    public void start() {
        ModInstances.register(this.hudManager = HUDManager.getInstance());
    }
    
    public void shutdown() {
        this.discordRP.shutdown();
    }
    
    public DiscordRP getDiscordRP() {
        return this.discordRP;
    }
    
    @EventTarget
    public void onTick(final ClientTickEvent e) {
        if (Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
            this.hudManager.openConfigScreen();
        }
    }
}
