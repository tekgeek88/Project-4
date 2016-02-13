import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class CypherEncoder {

	public static void main(String[] args) {

		Scanner input = null;
		try {
			input = new Scanner(new File("cypherKey.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("You must copy cypherKey.txt to this directory" +
					" before running the testing program.");
			System.exit(1);
		}
		
		LinkedHashMap<Character, Character> characterMap = new LinkedHashMap<Character, Character>();
		
		
		
		for (int i = 0; i < 27; i++) {
			String line = input.nextLine();
			char letter = line.charAt(0);
			char key = line.charAt(2);
			characterMap.put(letter, key);
		}
		System.out.println("Encryption keys generated...");
		System.out.println(characterMap);

		StringBuilder sb = new StringBuilder();
		String unEncryptedStr = "I would really like to go to the store because I have alot of fun at the store.";
		unEncryptedStr = unEncryptedStr.toLowerCase();
		String encryptedString;
		for (int i = 0; i < unEncryptedStr.length(); i++) {
			char currentChar = unEncryptedStr.charAt(i);
			System.out.print(currentChar);
			sb.append(characterMap.get(currentChar));
		}
		System.out.println();
		
		encryptedString = sb.toString();
		
		System.out.println(encryptedString);
		
	}
		
	


}
