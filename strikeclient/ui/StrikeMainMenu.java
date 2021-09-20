package strikeclient.ui;

import java.io.IOException;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import strike.Client;
import net.minecraft.client.gui.GuiScreen;

public class StrikeMainMenu extends GuiScreen {
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Client.getInstance().getDiscordRP().update("Idle", "Main Menu");
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Tutorial/main_menu.jpeg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(1, 10, this.height / 2 - 40, "Singleplayer"));
        this.buttonList.add(new GuiButton(2, 10, this.height / 2 - 15, "Multiplayer"));
        this.buttonList.add(new GuiButton(3, 10, this.height / 2 + 10, "Options"));
        this.buttonList.add(new GuiButton(4, 10, this.height / 2 + 35, "Quit"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 1) {
            Client.getInstance().getDiscordRP().update("Idle", "Singleplayer Menu");
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 2) {
            Client.getInstance().getDiscordRP().update("Idle", "Multiplayer Menu");
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 3) {
            Client.getInstance().getDiscordRP().update("Idle", "Settings Menu");
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 4) {
            Client.getInstance().shutdown();
            this.mc.shutdown();
        }
        super.actionPerformed(button);
    }
}
