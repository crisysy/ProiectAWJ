import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException{
		
		String input = "C:\\Users\\Agripina\\Desktop\\bird.bmp";
		String output = "C:\\Users\\Agripina\\Desktop\\birdPrelucrated.bmp";
		
		GrayImage photo = new GrayImage();
		
		photo.getBMPimage(input);
		photo.convertStaticTreshold();
		photo.printBinaryPhoto(output);
		
		
	}

}
