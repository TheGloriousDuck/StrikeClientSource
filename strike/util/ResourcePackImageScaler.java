package strike.util;

import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ResourcePackImageScaler {
    public static final int SIZE = 64;
    
    public static BufferedImage scalePackImage(final BufferedImage image) throws IOException {
        if (image == null) {
            return null;
        }
        System.out.println(new StringBuilder("Scaling resource pack icon from ").append(image.getWidth()).append(" to ").append(64).toString());
        final BufferedImage smallImage = new BufferedImage(64, 64, 2);
        final Graphics graphics = smallImage.getGraphics();
        graphics.drawImage((Image)image, 0, 0, 64, 64, (ImageObserver)null);
        graphics.dispose();
        return smallImage;
    }
}
