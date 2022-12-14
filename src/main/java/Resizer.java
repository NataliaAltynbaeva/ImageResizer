import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Resizer implements Runnable {
    private File file;
        int newWidth;
        private String dstFolder;

    public Resizer(File image, int newWidth, String dstFolder){
        file = image;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
    }

    @Override
    public void run() {
        try {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    return;
                }

                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = new BufferedImage(
                        newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                );

                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("[" + Thread.currentThread().getName() +"] Finished resizing: " + file.getName());
    }
}
