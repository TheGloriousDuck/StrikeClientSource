package strike.mods;

import strike.gui.hud.IRenderer;
import strike.gui.hud.HUDManager;
import strike.mods.impl.ModFullbright;
import strike.mods.impl.ModProjectL;
import strike.mods.impl.ModPerspective;
import strike.mods.impl.ModCPS;
import strike.mods.impl.togglesprintsneak.ModToggleSprintSneak;
import strike.mods.impl.ModKeystrokes;
import strike.mods.impl.ModXYZ;
import strike.mods.impl.ModFPS;
import strike.mods.impl.ModArmorStatus;
import strike.mods.impl.ModHelloWorld;

public class ModInstances {
    private static ModHelloWorld modHelloWorld;
    private static ModArmorStatus modArmorStatus;
    private static ModFPS modFPS;
    private static ModXYZ modXYZ;
    private static ModKeystrokes modKeystrokes;
    private static ModToggleSprintSneak modToggleSprintSneak;
    private static ModCPS modCPS;
    private static ModPerspective modPerspective;
    private static ModProjectL projectL;
    private static ModFullbright modFullbright;
    
    public static void register(final HUDManager api) {
        ModInstances.modHelloWorld = new ModHelloWorld();
        api.register(ModInstances.modHelloWorld);
        ModInstances.modArmorStatus = new ModArmorStatus();
        api.register(ModInstances.modArmorStatus);
        ModInstances.modFPS = new ModFPS();
        api.register(ModInstances.modFPS);
        ModInstances.modXYZ = new ModXYZ();
        api.register(ModInstances.modXYZ);
        ModInstances.modKeystrokes = new ModKeystrokes();
        api.register(ModInstances.modKeystrokes);
        ModInstances.modToggleSprintSneak = new ModToggleSprintSneak();
        api.register(ModInstances.modToggleSprintSneak);
        ModInstances.modCPS = new ModCPS();
        api.register(ModInstances.modCPS);
        ModInstances.modPerspective = new ModPerspective();
        api.register(ModInstances.modPerspective);
        ModInstances.projectL = new ModProjectL();
        api.register(ModInstances.projectL);
        ModInstances.modFullbright = new ModFullbright();
        api.register(ModInstances.modFullbright);
    }
    
    public static ModToggleSprintSneak getModToggleSprintSneak() {
        return ModInstances.modToggleSprintSneak;
    }
    
    public static ModPerspective getModPerspective() {
        return ModInstances.modPerspective;
    }
    
    public static ModHelloWorld getModHelloWorld() {
        return ModInstances.modHelloWorld;
    }
}
