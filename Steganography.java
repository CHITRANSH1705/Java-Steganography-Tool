import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Steganography {
    public static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }
    public static void saveImage(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
    }
    private static String messageToBinary(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        binary.append("00000000"); // null terminator
        return binary.toString();
    }
    private static String binaryToMessage(String binary) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i + 8 <= binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            if (byteStr.equals("00000000")) break;
            message.append((char) Integer.parseInt(byteStr, 2));
        }
        return message.toString();}
    public static BufferedImage encodeMessage(BufferedImage original, String message) {
        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Copy original pixels to new image
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                image.setRGB(x, y, original.getRGB(x, y));
            }}
        String binary = messageToBinary(message);
        int bitIndex = 0;
        outer:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (bitIndex >= binary.length()) break outer;
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                if (bitIndex < binary.length()) {
                    r = (r & 0xFE) | (binary.charAt(bitIndex++) - '0');
                }
                if (bitIndex < binary.length()) {
                    g = (g & 0xFE) | (binary.charAt(bitIndex++) - '0');
                }
                if (bitIndex < binary.length()) {
                    b = (b & 0xFE) | (binary.charAt(bitIndex++) - '0');
                }
                int newRGB = (r << 16) | (g << 8) | b;
                image.setRGB(x, y, newRGB);
            }}
        return image;}
    public static String decodeMessage(BufferedImage image) {
        StringBuilder binary = new StringBuilder();
        outer:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                binary.append(r & 1);
                binary.append(g & 1);
                binary.append(b & 1);
                if (binary.length() >= 8 &&
                        binary.substring(binary.length() - 8).equals("00000000")) {
                    break outer;
                }}}
        return binaryToMessage(binary.toString());
    }}
