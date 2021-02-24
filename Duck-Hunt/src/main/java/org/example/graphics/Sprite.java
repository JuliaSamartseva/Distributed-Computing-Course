package org.example.graphics;

public class Sprite {
  public static Sprite background = new Sprite("src/main/resources/background.jpg");
  public static Sprite shooter = new Sprite("src/main/resources/shooter.png");
  public static Sprite bullet = new Sprite("src/main/resources/bullet.png");
  public static Sprite shot = new Sprite("src/main/resources/shot.png");

  private int width, height;
  private int[] pixels;

  public Sprite(String fileName) {
    create(Texture.load(fileName));
  }

  public Sprite(Texture texture) {
    create(texture);
  }

  public static Sprite[] getLeftDucks() {
    Sprite[] leftDuck = new Sprite[2];
    leftDuck[0] = new Sprite(Texture.loadImage(SpriteSheet.ducks.image.getSubimage(141, 0, 32, 32)));
    leftDuck[1] = new Sprite(Texture.loadImage(SpriteSheet.ducks.image.getSubimage(279, 0, 32, 32)));
    return leftDuck;
  }

  public static Sprite[] getRightDucks() {
    Sprite[] rightDuck = new Sprite[2];
    rightDuck[0] = new Sprite(Texture.loadImage(SpriteSheet.ducks.image.getSubimage(190,0,32,32)));
    rightDuck[1] = new Sprite(Texture.loadImage(SpriteSheet.ducks.image.getSubimage(234,0,32,32)));
    return rightDuck;
  }

  private void create(Texture texture) {
    width = texture.getWidth();
    height = texture.getHeight();
    pixels = texture.getPixels(Texture.FORMAT_RGBA);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int[] getPixels() {
    return pixels;
  }
}
