package com.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringSplitAndReverseWithoutSplitFunction {

	public static void main(String[] args) {
		String givenString = "Let this be the given String";
		String delimiiter = " ";
		List<String> temp = new ArrayList<>();

		String str = givenString.concat(delimiiter);// Add one more Delimiter at the  end

		int i = 0;
		for (int j = str.indexOf(delimiiter); j != -1; j = str.indexOf(delimiiter, i)) {
			temp.add(str.substring(i, j));
			i = j + 1;
		}
		Collections.reverse(temp);// To reverse the list
		
		for (String st : temp) {
			System.out.println(st);
		}

	}

}
