package org.example.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {
  public static SpriteSheet ducks = new SpriteSheet("src/main/resources/duckSpriteSheet.png");
  public String path;
  public BufferedImage image;

  public SpriteSheet(String path) {
    this.path = path;
    try {
      image = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
