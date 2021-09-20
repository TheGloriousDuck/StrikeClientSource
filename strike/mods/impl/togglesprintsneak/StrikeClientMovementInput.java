package strike.mods.impl.togglesprintsneak;

import net.minecraft.potion.Potion;
import strike.mods.ModInstances;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;

public class StrikeClientMovementInput extends MovementInput {
    private boolean sprint;
    private GameSettings gameSettings;
    private int sneakWasPressed;
    private int sprintWasPressed;
    private EntityPlayerSP player;
    private float originalFlySpeed;
    private float boostedFlySpeed;
    private Minecraft mc;
    private static final DecimalFormat df;
    
    static {
        df = new DecimalFormat("#.0");
    }
    
    public StrikeClientMovementInput(final GameSettings gameSettings) {
        this.sprint = false;
        this.sneakWasPressed = 0;
        this.sprintWasPressed = 0;
        this.originalFlySpeed = 1.0f;
        this.boostedFlySpeed = 0.0f;
        this.gameSettings = gameSettings;
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void updatePlayerMoveState() {
        this.player = this.mc.thePlayer;
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.isKeyDown()) {
            ++this.moveForward;
        }
        if (this.gameSettings.keyBindBack.isKeyDown()) {
            --this.moveForward;
        }
        if (this.gameSettings.keyBindLeft.isKeyDown()) {
            ++this.moveStrafe;
        }
        if (this.gameSettings.keyBindRight.isKeyDown()) {
            --this.moveStrafe;
        }
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (this.sneak) {
            this.moveStrafe *= (float)0.3;
            this.moveForward *= (float)0.3;
        }
        if (ModInstances.getModToggleSprintSneak().isEnabled()) {
            if (this.gameSettings.keyBindSprint.isKeyDown()) {
                if (this.sprintWasPressed == 0) {
                    if (this.sprint) {
                        this.sprintWasPressed = -1;
                    }
                    else if (this.player.capabilities.isFlying) {
                        this.sprintWasPressed = ModInstances.getModToggleSprintSneak().keyHoldTicks + 1;
                    }
                    else {
                        this.sprintWasPressed = 1;
                    }
                    this.sprint = !this.sprint;
                }
                else if (this.sprintWasPressed > 0) {
                    ++this.sprintWasPressed;
                }
            }
            else {
                if (ModInstances.getModToggleSprintSneak().keyHoldTicks > 0 && this.sprintWasPressed > ModInstances.getModToggleSprintSneak().keyHoldTicks) {
                    this.sprint = false;
                }
                this.sprintWasPressed = 0;
            }
        }
        else {
            this.sprint = false;
        }
        if (this.sprint && this.moveForward == 1.0f && this.player.onGround && !this.player.isUsingItem() && !this.player.isPotionActive(Potion.blindness)) {
            this.player.setSprinting(true);
        }
        if (ModInstances.getModToggleSprintSneak().flyBoost && this.player.capabilities.isCreativeMode && this.player.capabilities.isFlying && this.mc.getRenderViewEntity() == this.player && this.sprint) {
            if (this.originalFlySpeed < 0.0f || this.player.capabilities.getFlySpeed() != this.boostedFlySpeed) {
                this.originalFlySpeed = this.player.capabilities.getFlySpeed();
            }
            this.boostedFlySpeed = this.originalFlySpeed * ModInstances.getModToggleSprintSneak().flyBoostFactor;
            this.player.capabilities.setFlySpeed(this.boostedFlySpeed);
            if (this.sneak) {
                final EntityPlayerSP player = this.player;
                player.motionX -= 0.15 * (ModInstances.getModToggleSprintSneak().flyBoostFactor - 1.0f);
            }
            if (this.jump) {
                final EntityPlayerSP player2 = this.player;
                player2.motionX += 0.15 * (ModInstances.getModToggleSprintSneak().flyBoostFactor - 1.0f);
            }
        }
        else {
            if (this.player.capabilities.getFlySpeed() == this.boostedFlySpeed) {
                this.player.capabilities.setFlySpeed(this.originalFlySpeed);
            }
            this.originalFlySpeed = -1.0f;
        }
    }
    
    public String getDisplayText() {
        String displayText = "";
        final boolean isFlying = this.mc.thePlayer.capabilities.isFlying;
        final boolean isRiding = this.mc.thePlayer.isRiding();
        final boolean isHoldingSneak = this.gameSettings.keyBindSneak.isKeyDown();
        final boolean isHoldingSprint = this.gameSettings.keyBindSprint.isKeyDown();
        if (isFlying) {
            if (this.originalFlySpeed > 0.0f) {
                displayText = String.valueOf(displayText) + "[Flying (" + StrikeClientMovementInput.df.format((double)(this.boostedFlySpeed / this.originalFlySpeed)) + "x Boost)]  ";
            }
            else {
                displayText = String.valueOf(displayText) + "[Flying]  ";
            }
        }
        if (isRiding) {
            displayText = String.valueOf(displayText) + "[Riding]  ";
        }
        if (this.sneak) {
            if (isFlying) {
                displayText = String.valueOf(displayText) + "[Descending]  ";
            }
            else if (isRiding) {
                displayText = String.valueOf(displayText) + "[Dismounting]  ";
            }
            else if (isHoldingSneak) {
                displayText = String.valueOf(displayText) + "[Sneaking (Key Held)]  ";
            }
            else {
                displayText = String.valueOf(displayText) + "[Sneaking (Key Toggled)]  ";
            }
        }
        else if (this.sprint && !isFlying && !isRiding) {
            if (isHoldingSprint) {
                displayText = String.valueOf(displayText) + "[Sprinting (Key Held)]  ";
            }
            else {
                displayText = String.valueOf(displayText) + "[Sprinting (Key Toggled)]  ";
            }
        }
        return displayText.trim();
    }
}
