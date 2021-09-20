package strike.event.impl;

import org.lwjgl.input.Mouse;
import strike.event.EventCancelable;

public class MouseEvent extends EventCancelable {
    public final int x;
    public final int y;
    public final int dx;
    public final int dy;
    public final int dwheel;
    public final boolean buttonstate;
    public final long nanoseconds;
    public final int button;
    
    public MouseEvent() {
        this.x = Mouse.getEventX();
        this.y = Mouse.getEventY();
        this.dx = Mouse.getEventDX();
        this.dy = Mouse.getEventDY();
        this.dwheel = Mouse.getEventDWheel();
        this.button = Mouse.getEventButton();
        this.buttonstate = Mouse.getEventButtonState();
        this.nanoseconds = Mouse.getEventNanoseconds();
    }
}
