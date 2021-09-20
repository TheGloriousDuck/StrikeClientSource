package strike.gui;

import net.minecraft.client.multiplayer.WorldClient;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMultiplayer;

public class GuiMultiplayerIngame extends GuiMultiplayer {
    public GuiMultiplayerIngame() {
        super(null);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 1 || button.id == 4) {
            this.disconnect();
        }
        super.actionPerformed(button);
    }
    
    @Override
    public void connectToSelected() {
        this.disconnect();
        super.connectToSelected();
    }
    
    private void disconnect() {
        if (this.mc.theWorld != null) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(null);
            this.parentScreen = null;
        }
    }
}
