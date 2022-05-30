import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

/**
 * This class contains the dynamically allocated array and it's processing
 * Student Name: Isaac Ribeiro
 * Student Number: 040957075
 * Course: CST8130 - Data Structures
 * CET-CS-Level 3
 * @author/Professor James Mwangi PhD. 
 * 
 */
public class Numbers {
	
/* Member variables */
	
	/**
	 * Stores float values.
	 */
	private float[] numbers;
	
	/**
	 * Stores the size of the array.
	 */
	private int numElements;
	
	/**
	 * Stores the number of items currently in the array.
	 */
	private int numValues;
	

/* Constructors */

	/**
	 * Default Constructor
	 */
	public Numbers() {
		
		this(3); // initialize a "default" array since this is the default constructor
	}

	/**
	 * Constructor that initializes the numbers array.
	 * @param size - Max size of the numbers array
	 */
	public Numbers(int size) {
		
		this.numValues = 0;				// reset number of values to zero
		this.numElements = size;		// set number of elements to specified size
		this.numbers = new float[size]; // initialize the numbers array of max 'size'
	}
	
	
/* Methods */
	
	/**
	 * Adds a value in the array
	 * @param input - Scanner object to use for input
	 * @param isFile - whether the input is from a file or not
	 */
	public void addValue(Scanner input, boolean isFile) {
		
		if (this.numValues == this.numElements) {		// early out if array is full
			
			System.out.println("Array is full.");
			return;
		}
		
		
		// accept a float value
		
		float value = 0.f;
		boolean inputValid = false;
		
		do {	// until user input is valid
			
			try {
				
				if (!isFile) System.out.print("Enter a value: ");		// prompt value input
					
				value = input.nextFloat();
				inputValid = true;
				
			} catch (InputMismatchException e) {							// warn and flush scanner on input mismatch
				
				if (!isFile)
					System.out.println("Invalid input. Please enter a floating-point number.");
				else
					System.out.println("Error - invalid value.");

				input.nextLine();
				
			} catch (NoSuchElementException e) {							// warn on value is missing from file
				
				System.out.println("Error - missing value.");
			}
			
		} while (inputValid == false && isFile == false);	// retry if user input is invalid
		
		
		if (inputValid) {													// add value to array if input is valid
			
			this.numbers[this.numValues] = value;
			this.numValues++;
		}
	}
	
	/**
	 * Calculates the average of all the values in the numbers array.
	 * @return float value that represents the average
	 */
	public float calcAverage() {
		
		// sum entire numbers array
		float sum = 0.f;
		
		for (int i = 0; i < this.numValues; ++i) sum += this.numbers[i];
		
		return sum / (float) this.numValues;									// compute and return average
	}
	
	/**
	 * Finds the minimum and maximum values in the numbers array.
	 * @return Formatted string containing minimum, maximum, and max mod min values
	 */
	public String findMinMax() {
		
		String format = "Average is: %.1f, Minimum value is %.1f, Maximum value is %.1f, max mod min is %.1f";
		
		float avg = 0.f;
		float max = 0.f;
		float min = 0.f;
		
		// calculate average
		avg = this.calcAverage();
		
		// find min/max values
		for (int i = 0; i < this.numValues; ++i) {
			
			float value = this.numbers[i];
			
			if (i == 0) min = max = value;	// set min/max values to array values on first iteration
			
			if (value < min) min = value;	// compare found min with each value in array and keep smallest value as min
			
			if (value > max) max = value;	// compare found max with each value in array and keep largest value as max
		}
		
		// calculate max mod min
		float mod = (min != 0.f) ? max % min : 0.f;	// 0.0 if NaN
		
		// format and return result string
		return String.format(format, avg, min, max, mod);
	}

	/**
	 * Method to create a string representation of the values in the Numbers array.
	 * @return the String representation of this object.
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < numValues; ++i) {
		
			sb.append(this.numbers[i]);		// 1.0
			sb.append('\n');				// 
		}
		
		return sb.toString();
	}
	
	/**
	 * Method to add multiple values to the Numbers array.
	 * @param input - user/file input stream
	 * @param isFile - whether the input is from a file or not
	 */
	public void addValues(Scanner input, boolean isFile) {

		// input value count and values
		
		int count;
		boolean countValid = false;
		int numRemaining = this.numElements - this.numValues;
		
		do {
			
			count = -1;												// set count to invalid value to start
			
			try {
				
				if (!isFile) System.out.print("How many values do you wish to add? ");
				count = input.nextInt();							// input value count
				
				// check if count is valid
				countValid = count >= 0 && count <= numRemaining;		// count must be positive and fit in the remaining array
				
			} catch (InputMismatchException e) {						// warn user on input mismatch
				
				if (input.hasNextLine() && !isFile)
					input.nextLine();										// flush scanner if necessary
				
			} catch (NoSuchElementException e) {						// warn if count not found
				
				System.out.println("End of file reached before count found. Aborting.");
			}
			
			
			// print warnings messages when count is invalid
			if (!countValid) {				
				
				if (count > numRemaining)								// warn if count doesn't fit in the array
					System.out.printf("No room in array to add all values - %d available.\n", numRemaining);
				
				if (count < 0) {										// warn if count is 0, negative, or invalid
					
					if (!isFile) System.out.println("Enter a positive integer value.");
					
					else System.out.println("File invalid.");
				}
			}
			
		} while (countValid == false && isFile == false);	// retry if user input is invalid
		
		
		// input values and add to array
		if (countValid)
			for (int i = 0; i < count; ++i) this.addValue(input, isFile);
	}
	
	/**
	 * Method to read in values from a file on disk and add them to the Numbers array.
	 * @param input - user input stream
	 */
	public void readFromDisk(Scanner input) {
		
		// input path and open scanner 
		
		boolean pathValid = false;
		Scanner fileScanner = null;
		
		// input path and open a scanner
		do { // until the file scanner opens successfully			
			
			try {
				
				System.out.println("Name of the file to read from: ");
				String path = input.nextLine();						// input path
				
				File file = new File(path);							// create File
				
				fileScanner = new Scanner(file);					// open file
				pathValid = true;										// file is valid if exception hasn't occurred
				
				this.addValues(fileScanner, true);					// add values 
				
				fileScanner.close();								// close file scanner
				
			} catch (FileNotFoundException e) {							// notify user on file not found 
				
				System.out.println("File not found. Try again...");
			}
			
		} while (pathValid == false);
	}
	
	/**
	 * Method to write the contents of the Numbers array to disk.
	 * @param input - user input stream
	 */
	public void writeToDisk(Scanner input) {
		
		// early out if there are no values in the array
		if (this.numValues == 0) {
			
			System.out.println("No values to write.");
			return;
		}
		
		// open and write to file
		try {
			System.out.println("Name of the file to save to: ");
			String path = input.nextLine();							// input the path
			
			FileWriter writer = new FileWriter(path);				// open writer
			
			writer.write(Integer.toString(this.numValues) + '\n');		// print num values in array
			
			writer.write(this.toString());								// print all array values
			
			
			writer.close();											// close writer
			
		} catch (IOException e) {										// print message on IOException
			
			System.out.println(e);
		}
	}
}
