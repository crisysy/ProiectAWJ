import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GrayImage {
	
	private int width;
	private int height;
	private RGBpixel [][] pixels;
	private RGBpixel [][] binaryPhoto;
	private int tThreshold;
	private int imagePadding;
	
	public void getBMPimage(String fileName) throws IOException{
	
		FileInputStream photoFile = new FileInputStream(fileName);
		
		
		photoFile.skip(18);
		this.width = getIntFromFile(photoFile);
		this.height = getIntFromFile(photoFile);
		
		System.out.println(width);
		System.out.println();
		System.out.println(height);
		
		photoFile.skip(28);
		pixels = new RGBpixel[height][width];
		this.imagePadding = (4 - (width * 3 % 4)) % 4;
		
		for (int row = 1; row <= height; row++){
			for(int col = 0; col < width; col++){
				pixels[height - row][col] = new RGBpixel();
				pixels[height - row][col].setBlue(photoFile.read());
				pixels[height - row][col].setGreen(photoFile.read());
				pixels[height - row][col].setRed(photoFile.read());
			}
			photoFile.skip(imagePadding);
		}
		photoFile.close();
	}
	
	int getIntFromFile(FileInputStream file) throws IOException{
		int returnValue = 0;
		for(int i = 0; i < 4; i++){
			returnValue = returnValue + (file.read() << 8 * i );
		}
		return returnValue;	
	}
	
	public void convertStaticTreshold() throws IOException{
		this.tThreshold = 112; // poti sa schimbi
		binaryPhoto = new RGBpixel[height][width];
		
		
		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
				binaryPhoto[row][col] = new RGBpixel();
				if(pixels[row][col].getGreen() < tThreshold){
					binaryPhoto[row][col].setBlue(0);
					binaryPhoto[row][col].setGreen(0);
					binaryPhoto[row][col].setRed(0);
				}
				else{
					binaryPhoto[row][col].setBlue(255);
					binaryPhoto[row][col].setGreen(255);
					binaryPhoto[row][col].setRed(255);
				}
			}
		}	
	}
	

	public void printBinaryPhoto( String outputFile) throws IOException {
		FileOutputStream outPhotoFile = new FileOutputStream(outputFile);
		
		int size = width*height*3+54;
		int reserved = 0;
		int offset = 54;
		int header = 40;
		int dataSize = width*height*3;//total amount of pixels
		int[] headerInts = {size,reserved,offset,header,width,height,0,dataSize,72,72,0,0};
		byte[] nonInts = {66,77,1,0,8,0};
		//             {B, M, 1,0,8,0}
		outPhotoFile.write(nonInts[0]);
		outPhotoFile.write(nonInts[1]);
		for(int i = 0;i<=5;i++){
			for(int j = 0;j <= 3;j++){
				outPhotoFile.write((byte)(headerInts[i] >> 8*j));
			}
		}
		
		for(int i=2;i<=5;i++){
			outPhotoFile.write(nonInts[i]);
		}
		
		for(int i=6;i<12;i++){
			for(int j = 0;j <= 3;j++){
				outPhotoFile.write((byte)(headerInts[i] >> 8*j));
			}
		}
		
		for(int row = 1; row <= height; row++){
			for(int col = 0; col < width; col++){
				
				outPhotoFile.write(binaryPhoto[height - row][col].getBlue());
				outPhotoFile.write(binaryPhoto[height - row][col].getGreen());
				outPhotoFile.write(binaryPhoto[height - row][col].getRed());				
			}
			for(int inc = 0; inc < imagePadding; inc++){
				outPhotoFile.write(0);
			}
		}
		outPhotoFile.close();
	}
}
		
	
	

	
	
	


