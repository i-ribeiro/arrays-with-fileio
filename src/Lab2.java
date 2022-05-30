import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * This class contains the dynamically allocated array and it's processing
 * Student Name: Isaac Ribeiro
 * Student Number: 040957075
 * Course: CST8130 - Data Structures
 * CET-CS-Level 3
 * @author/Professor James Mwangi PhD. 
 * 
 */
public class Lab2 {
	
/* Menu options */
	
	/**
	 * The menu option value to initialize a default Numbers array. 
	 */
	private static final int MENU_INIT		= 1;
	
	/**
	 * The menu option value to specify the size of the Numbers array.
	 */
	private static final int MENU_SIZE		= 2;
	
	/**
	 * The menu option value to add a value to the Numbers array. 
	 */
	private static final int MENU_ADD		= 3;
	
	/**
	 * The menu option value to display the values in the Numbers array. 
	 */
	private static final int MENU_DISPLAY	= 4;
	
	/**
	 * The menu option value to display the minimum, maximum, and min mod max values of the Numbers array. 
	 */
	private static final int MENU_MINMAX	= 5;
	
	/**
	 * The menu option value to add multiple values to the Numbers array.
	 */
	private static final int MENU_MULTI		= 6;
	
	/**
	 * The menu option value to read values into the Numbers array from a file on disk.
	 */
	private static final int MENU_READFILE	= 7;
	
	/**
	 * The menu option value to save values from the Numbers array to a file on disk.
	 */
	private static final int MENU_WRITEFILE	= 8;
	
	/**
	 * The menu option value to exit. 
	 */
	private static final int MENU_EXIT		= 9;
	
	
/* Methods */

	/** Main method used to test the Numbers class. 
	 * @param args - unused
	 */
	public static void main(String[] args) {
		
		Numbers numbers = new Numbers();			// start with default Numbers array size
		
		Scanner keyboard = new Scanner(System.in);	// user input stream from keyboard
		int menuChoice = 0;							// user menu option selection
		
		boolean exitFlag = false;					// breaks top menu loop when true
		
		
		// top menu loop
		do {
			
			displayMainMenu();
			
			menuChoice = inputMenuChoice(keyboard);					// input menu selection
			
			switch (menuChoice) {									// switch on selection input
				
			case MENU_INIT:												// initialize default Numbers array
				numbers = new Numbers();
				break;
				
			case MENU_SIZE:
				numbers = new Numbers(inputSize(keyboard));				// initialize Numbers array of user-specified size
				break;
				
			case MENU_ADD:
				numbers.addValue(keyboard, false);						// add a value to the Numbers array
				break;
				
			case MENU_DISPLAY:
				System.out.printf("Numbers are: \n%s \n\n",				// display the values in the Numbers array
						numbers.toString());
				break;
				
			case MENU_MINMAX:
				System.out.printf("%s \n\n", numbers.findMinMax());		// display the min, max, and min % max values of the Numbers array
				break;
				
			case MENU_MULTI:
				numbers.addValues(keyboard, false);						// add multiple values to the Numbers array
				break;
				
			case MENU_READFILE:											// read values in from a file
				numbers.readFromDisk(keyboard);
				break;
				
			case MENU_WRITEFILE:										// write values to a file
				numbers.writeToDisk(keyboard);
				break;
				
			case MENU_EXIT:												// exit the menu loop
				exitFlag = true;
				System.out.println("Exiting...");
				break;
				
			default:
				System.out.print("Invalid selection. Try again. \n\n");
				break;
			}
			
		} while(exitFlag == false);
		
		keyboard.close();
	}
	
	
	/**
	 * Method to prompt the user to input a menu option selection.
	 * @param input - user input stream
	 * @return the menu option selected by the user.
	 */
	private static int inputMenuChoice(Scanner input) {

		int menuChoice = -1;
		
		// accept an integer value
		
		try {
			
			menuChoice = input.nextInt();
			
		} catch (InputMismatchException e) {
			
			input.next();
		}
		
		if (input.hasNextLine()) input.nextLine();
		
		return menuChoice;
	}

	/**
	 * Method to prompt the user to input the new Numbers array size.
	 * @param input - user input stream
	 * @return the user input for the new Numbers array size.
	 */
	private static int inputSize(Scanner input) {

		int size = 0;
		boolean inputValid = false;
		
		// accept a positive integer value
		do {
			
			try {
				
				System.out.print("Enter new size of array: ");	// prompt size input
				size = input.nextInt();
				
				if (size > 0) inputValid = true;
				
			} catch (InputMismatchException e) {
				
				input.next();
			}
			
			// re-prompt if input is invalid
			if (inputValid == false) System.out.print("Invalid input. Please enter a positive integer value. \n\n");
			
		} while (inputValid == false);
		
		return size;
	}


	/**
	 * Method to print the main menu. 
	 */
	private static void displayMainMenu() {
		
		System.out.println("Please select one of the following:");
		System.out.println("1: Initialize a default array");
		System.out.println("2: To specify the max size of the array");
		System.out.println("3: Add value to the array");
		System.out.println("4: Display values in the array");
		System.out.println("5: Display average of the values, minimum value, maximum value, max mod min");
		System.out.println("6: Enter multiple values");
		System.out.println("7: Read values from file");
		System.out.println("8: Save values to file");
		System.out.println("9: To Exit");
		System.out.print  ("> ");
	}
}