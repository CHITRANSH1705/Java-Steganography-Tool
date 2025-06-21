import java.awt.image.BufferedImage;
public class MainDecode {
    public static void main(String[] args) {
        try {
            BufferedImage image = Steganography.loadImage("encoded.png");
            System.out.println(" Encoded image loaded successfully.");
            String message = Steganography.decodeMessage(image);
            System.out.println(" Decoded message: " + message);
        } catch (Exception e) {
            System.out.println(" Error decoding image: " + e.getMessage());
        }}}
