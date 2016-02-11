import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class LetterInventory {

	public LinkedHashMap<Character, Integer> inventory;
	public int numberOfChars;
	
	// Default Constructor
	public LetterInventory() {
	}

	public LetterInventory(String data) {
		this.inventory = addString(data);
	}

	
	//------------------------  Methods  --------------------------//
	
	public LinkedHashMap<Character, Integer> addString(String newString) {
		// Put the string into a character array
		char[] charArray = newString.toLowerCase().toCharArray();
		Arrays.sort(charArray);;
		
		// Temporary hashmap to hold each letter and the number of times each letter occurs
		LinkedHashMap<Character, Integer> tempHashMap = new LinkedHashMap<Character, Integer>();
		int charCounter = 0;
		for (Character c : charArray) {
			if (!Character.isAlphabetic(c)) {
				continue;
			}
			c = Character.toLowerCase(c);
			if (tempHashMap.containsKey(c)){
				int oldCount = tempHashMap.get(c);
				int newCount = ++oldCount;
				tempHashMap.put(c, newCount);
			}
			else {
				tempHashMap.put(c, 1);
			}
			charCounter++;
		}
		this.numberOfChars = charCounter;
		return tempHashMap;
	}
	
	
	public int size() {
		return numberOfChars;
	}
	
	public boolean isEmpty() {
		return inventory.isEmpty();
	}
	
	public int get(char ch) {
		// Convert the character to a lowercase to match the Key entry
		ch = Character.toLowerCase(ch);
		// Check if key exists before trying to access it. if exists return the number of characters that key(letter) has.
		if (this.inventory.containsKey(ch)) {
			return this.inventory.get(ch);
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		// Use the stringBuilder to create the desired output of each occurring character
		StringBuilder sb = new StringBuilder();
		String result = null;
		
		for (Map.Entry<Character, Integer> entry : inventory.entrySet()) {
		    Character ch = entry.getKey();
		    Integer value = entry.getValue();
		    for (int i = 0; i < value; i++) {
				sb.append(ch);
			}
		}
		result = sb.toString();
		return "[" + result + "]";
	}
	
	public void set(char letter, int value) throws IllegalArgumentException {
		letter = Character.toLowerCase(letter);
		int oldCount = 0;
		int newCount = 0;
		if (inventory.containsKey(letter)) {
			oldCount = inventory.get(letter);
			newCount = oldCount + value;
			this.numberOfChars = newCount;
		}
		else {
			newCount = value;
		}
		inventory.put(letter, value);
		this.numberOfChars = value;
	}
	
}
