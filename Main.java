import java.awt.image.BufferedImage;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // Load the input image
        BufferedImage inputImage = Steganography.loadImage("input.png");
        System.out.println("Image loaded successfully.");
        // Ask user for secret message
        System.out.print("Enter the secret message to hide: ");
        String secretMessage = sc.nextLine();
        // Encode the message into the image
        BufferedImage encodedImage = Steganography.encodeMessage(inputImage, secretMessage);
        // Save the encoded image
        Steganography.saveImage(encodedImage, "encoded.png");
        System.out.println("Message embedded and saved to 'encoded.png'.");

        sc.close();
    }
}
