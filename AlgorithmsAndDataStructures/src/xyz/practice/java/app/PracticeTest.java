package xyz.practice.java.app;

public class PracticeTest {
	
	
	public static void main(String[] args) {
		System.out.println(containsDuplicates("anil"));
		System.out.println(arePermutations("anil", "lina"));
		System.out.println(compressString("aaavvvbbbbeeeedddaa"));
	}
	
	static class CharCount {
		public char c;
		public int count;
		public CharCount() {}
	}
	// aaavvvbbbbeeeedddaa -> a3v3b4e4d3a2
	public static String compressString(String input) {
		
		CharCount[] ccArr = new CharCount[input.length()];
		boolean noRepeat = true;
		char[] chars = input.toCharArray();
		char last = chars[0];
		CharCount cc = new CharCount();
		cc.c = chars[0];
		cc.count = 1;
		int cc_index = 0;
		ccArr[cc_index] = cc;
		for(int index = 1; index < chars.length; index++) {
			if (last == chars[index]) {
				ccArr[cc_index].count++;
				noRepeat = false;
			} else {
				cc_index++;
				CharCount cc_curr = new CharCount();
				last = chars[index];
				cc_curr.c = chars[index];
				cc_curr.count = 1;
				ccArr[cc_index] = cc_curr;
			}
		}
		if (noRepeat) {
			return input;
		} else {
			StringBuilder builder = new StringBuilder();
			for (CharCount cccurr : ccArr) {
				if (cccurr != null) {
					builder.append(cccurr.c);
					builder.append(cccurr.count);
				}
			}
			return builder.toString();
		}
	}
	
	// assumption: only ascii chars
	public static boolean containsDuplicates(String input) {
		if (input == null) return false;
		char[] allChars = input.toCharArray();
		boolean[] bits = new boolean[256];
		for (char c : allChars) {
			if (bits[c]) {
				return true;
			} else {
				bits[c] = true;
			}
		}
		return false;
	}
	
	// assumption: only ascii chars
	public static boolean arePermutations(String input1, String input2) {
		if (input1 == null || input2 == null) return false;
		int[] bits1 = getBitArray(input1);
		int[] bits2 = getBitArray(input2);
		int i = 0;
		while (i < 256) {
			if (bits1[i] != bits2[i]) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	private static int[] getBitArray(String input) {
		if (input == null || input.length() == 0) return null;
		char[] allChars = input.toCharArray();
		int[] bits = new int[256];
		for (char c : allChars) {
			bits[c]++;
		}
		return bits;
	}
}
