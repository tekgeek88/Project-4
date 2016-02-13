import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LetterInventory {

	// Used to store Character as the Key value and the number of times the Character occurs as the value
	public LinkedHashMap<Character, Integer> inventory;
	public int numberOfChars;

	// Default Constructor
	public LetterInventory() {
		inventory = new LinkedHashMap<Character, Integer>();
	}

	public LetterInventory(String data) {
		inventory = addData(data);
	}


	//------------------------  Methods  --------------------------//

	public LinkedHashMap<Character, Integer> addData(String newString) {
		// Put the string into lowercase letters and then convert it to a character array.
		char[] charArray = newString.toLowerCase().toCharArray();
		// Sort the letters in ascending order
		Arrays.sort(charArray);;
		// Temporary hashmap to hold each letter and the number of times each letter occurs
		LinkedHashMap<Character, Integer> tempHashMap = new LinkedHashMap<Character, Integer>();
		int charCounter = 0; // The total number of times all characters occur
		for (Character c : charArray) {
			// If the character is not a-z then skip this chanter and continue looking thru the rest of the array of characters
			if (!Character.isAlphabetic(c)) {
				continue;
			}
			// If the character exists in inventory get the number of times it has occurred and increment it by one
			if (tempHashMap.containsKey(c)){
				int currentCount = tempHashMap.get(c);
				int newCount = ++currentCount;
				tempHashMap.put(c, newCount);
			}
			// If the character has not occurred add it to the inventory and set the value of the occurrences to 1
			else {
				tempHashMap.put(c, 1);
			}
			// Add one to the total character occurrences counter
			charCounter++;
		}// End of searching through all characters in the char array
		// Update the total number of characters in the inventory
		numberOfChars = charCounter;
		// Return a LinkedHashMap of Characters and  Integers
		return tempHashMap;
	}

	public int size() {
		return numberOfChars;
	}

	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	public int get(char ch) {
		// Convert the character to a lowercase in order to search for the entry by Key
		ch = Character.toLowerCase(ch);
		// Check if key exists before trying to access it. if exists return the number of characters that key(letter) has.
		if (this.inventory.containsKey(ch) && this.inventory.get(ch) != null) {
			return this.inventory.get(ch);
		}
		// If the key doesn't exist return zero
		else {
			return 0;
		}
	}

	public void set(char letter, int value) throws IllegalArgumentException {
		// Take the letter passed in and convert it to a lowerCase
		letter = Character.toLowerCase(letter);
		int currentCount = 0;
		int newCount = 0;
		// If the letter already exists then we need to update the total characters differently
		if (inventory.containsKey(letter)) {
			currentCount = inventory.get(letter);
			newCount = value;
			// If increasing
			if (value >= currentCount) {
				numberOfChars = numberOfChars + value;
			}
			//If decreasing count
			else if (value < currentCount) {
				int difference = currentCount - value;
				numberOfChars = numberOfChars - difference;
			}
		}
		// if this is the characters first time appearing make a new entry and set the newCount to the value
		else {
			newCount = value;
			numberOfChars = numberOfChars + newCount;
		}
		// update the total count of the new char we just adjusted
		// Add it to the inventory
		if(newCount > 0) {
			inventory.put(letter, newCount);
		}
		// If something wierd happens like a charcter is set to zero we need to remove the key from the HashMap
		else if (newCount == 0){
			inventory.remove(letter);
		}
		// Then resort the collection
		inventory = sortInventory(inventory);
	}

	public LinkedHashMap<Character, Integer> sortInventory(LinkedHashMap<Character, Integer> unSortedMap) {
		// Create a new List object of the current ordered list so that we can sort it.
		List<Entry<Character, Integer>> entries;
		entries = new ArrayList<Map.Entry<Character, Integer>>((Collection<? extends Entry<Character, Integer>>) unSortedMap.entrySet());
		// call the sort method just as we did on the array and pass in the Map comparator to check the key values in the list
		Collections.sort(entries, new Comparator<Map.Entry<Character, Integer>>() {
			// Call the compare method from the List interface and create on object 'a' and object 'b' to compare the two entries to eachother
			public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b){
				return a.getKey().compareTo(b.getKey());
			}
		});
		// Then we need to put the sorted Map of entries back into our LinkedHashmap object
		LinkedHashMap<Character, Integer> sortedInventory = new LinkedHashMap<Character, Integer>();
		for (Entry<Character, Integer> entry : entries) {
			sortedInventory.put(entry.getKey(), entry.getValue());
		}
		return sortedInventory;
	}// End reorderSet()

	public LetterInventory add(LetterInventory other) {
		// Create a temporary LetterInventory to modify and return.
		LetterInventory tempLetterInventory = new LetterInventory();
		// Populate the two fields of keys and total chars of the currentLetterInventory
		tempLetterInventory.inventory.putAll(inventory);
		tempLetterInventory.numberOfChars = this.size();

		// Look through the other inventory and merge the letters with the tempLetterInventory
		for (Character ch : other.inventory.keySet()) {
			// The occurences of this letter to be added to the tempArray
			int newValue = other.inventory.get(ch);
			// If the calling LetterInventory alread has this letter then get the current value and add the new value
			if (tempLetterInventory.inventory.containsKey(ch)) {
				// get the Value in the orignal LetterInventory
				int currentValue = tempLetterInventory.inventory.get(ch);
				// Add the total prior existing occurences to the new total
				int totalOccurences = currentValue + newValue;
				tempLetterInventory.inventory.put(ch, totalOccurences);
				tempLetterInventory.numberOfChars = tempLetterInventory.numberOfChars + newValue;
			}
			else {
				tempLetterInventory.inventory.put(ch, newValue);
				tempLetterInventory.numberOfChars = tempLetterInventory.numberOfChars + newValue;
			}
		}
		tempLetterInventory.inventory = this.sortInventory(tempLetterInventory.inventory);
		return tempLetterInventory;
	}

	public LetterInventory subtract(LetterInventory other) {
		// Create a temporary LetterInventory to modify and return.
		LetterInventory tempLetterInventory = new LetterInventory();
		// Populate the two fields of keys and total chars of the currentLetterInventory
		tempLetterInventory.inventory.putAll(inventory);
		tempLetterInventory.numberOfChars = this.size();

		// Look through the other inventory and merge the letters with the tempLetterInventory
		for (Character ch : other.inventory.keySet()) {
			// The occurences of this letter to be added to the tempArray
			int amountToBeSubtracted = other.inventory.get(ch);
			// If the calling LetterInventory already has this letter then get the current value and subtract the other's value
			if (tempLetterInventory.inventory.containsKey(ch)) {
				// get the Value in the orignal LetterInventory
				int currentValue = tempLetterInventory.inventory.get(ch);
				// Subtract the total from the prior existing occurences to the new total
				int totalOccurences = currentValue - amountToBeSubtracted;
				if (totalOccurences == 0) {
					tempLetterInventory.inventory.remove(ch);
					tempLetterInventory.numberOfChars = tempLetterInventory.numberOfChars - amountToBeSubtracted;
				}
				else if (totalOccurences < 0) {
					tempLetterInventory = null;
					return tempLetterInventory;
				}
				else {
					tempLetterInventory.inventory.put(ch, totalOccurences);
					tempLetterInventory.numberOfChars = tempLetterInventory.numberOfChars - amountToBeSubtracted;
				}
			}
			// Inventory doesn't contain key being subtracted and would make a negative number so we return null
			else {
				tempLetterInventory = null;
				return tempLetterInventory;
			}
		}
		tempLetterInventory.inventory = this.sortInventory(tempLetterInventory.inventory);
		return tempLetterInventory;
	}



	@Override
	public String toString() {
		// Use the stringBuilder to create the desired output of each occurring character
		StringBuilder sb = new StringBuilder();
		String result = null;
		for (Map.Entry<Character, Integer> entry : inventory.entrySet()) {
			// For each character in the list list of characters
			Character ch = entry.getKey();
			Integer value = entry.getValue();
			// For each time a character occurs print append the character to the string
			if (value != null) {
				for (int i = 0; i < value; i++) {
					sb.append(ch);
				}
			}
		}
		// Convert the sb object to a string and then add the brackets on so it passes the test. 
		result = sb.toString();
		return "[" + result + "]";
	}

}
