package splitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Split {

	public static void main(String[] args) {

		try
		{
			BufferedReader input = new BufferedReader(new FileReader("input_full.csv"));//Buffered Reader object instance with FileReader
			BufferedWriter localInput = new BufferedWriter(new FileWriter("input_local.csv", false));
			BufferedWriter waggaInput = new BufferedWriter(new FileWriter("input_wagga.csv", false));

			String fileRead = input.readLine(); // Headers
			fileRead = input.readLine(); //first real line
			

			while (fileRead != null)
			{
				// split input line on commas, except those between quotes ("")
				String[] tokenize = fileRead.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				
				
				//Fixing lines that are incorrectly ended prematurely due to line break in fields like 'notes'
				// System.out.print("line size = "+tokenize.length+"   ");
				while(tokenize.length<56){
					fileRead = fileRead + input.readLine();
					tokenize = fileRead.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					System.out.println("line size = "+tokenize.length);
				}
				
				String orderID = tokenize[0];							//OrderID
				String shippingMethod = tokenize[14];					//shipping method that was used
				
				if (shippingMethod.toLowerCase().contains("wagga")){ //shipping method is wagga
					while(tokenize[0].equals(orderID)){
						waggaInput.write(fileRead); //write line to wagga file
						fileRead = input.readLine();
					}
				} else {
					while(tokenize[0].equals(orderID)){
						localInput.write(fileRead); //write line to local file
						fileRead = input.readLine();
					}
				}
				
				
			}

			input.close();
			localInput.close();
			waggaInput.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("error: file not found!");
			System.exit(1);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(1);
		}

	}

}
