package com.practice;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HashMapSortBasedOnkeysAndValues {
	

	public static void main(String[] args) {
		HashMap<String,Integer> hp = new HashMap<>();
		hp.put("Satya", 100);
		hp.put("Abhi", 30);
		hp.put("Pavani", 60);
		hp.put("Sathish", 27);
		hp.put("Kanna", 10);
		hp.put("Rahul", 20);
		
		System.out.println("\n%%%%%%%%%%%%%%%%%% HashMap : Before Sorting %%%%%%%%%%%%%%%%%%\n");

		for(Map.Entry<String, Integer> i: hp.entrySet()){
			System.out.println(i.getKey() +" : "+i.getValue());
		}
		
		TreeMap<String,Integer> tm = new TreeMap<>(hp);
		
		System.out.println("\n%%%%%%%%%%%%% HashMap : After Sorting(Key based) %%%%%%%%%%%%%%\n");
		for(Map.Entry<String, Integer> i: tm.entrySet()){
			System.out.println(i.getKey() +" : "+i.getValue());
		}
		
				
		List<Map.Entry<String, Integer>> li = new LinkedList<>(hp.entrySet());
		Collections.sort(li, new Comparator<Map.Entry<String, Integer>>(){
			public int compare(Map.Entry<String, Integer> ob1,Map.Entry<String, Integer> ob2){	
				return ob1.getValue().compareTo(ob2.getValue());
			}
			
		});
		
		LinkedHashMap<String,Integer> temp = new LinkedHashMap<>();
		for(Map.Entry<String, Integer> i : li){
			temp.put(i.getKey(), i.getValue());
		}
		
		System.out.println("\n%%%%%%%%%%%%% HashMap : After Sorting(Value based) %%%%%%%%%%%%\n");
		for(Map.Entry<String, Integer> i: temp.entrySet()){
			System.out.println(i.getKey() +" : "+i.getValue());
		}
		
		System.out.println("\n%%%%%%%%%%%%% HashMap : All Keys %%%%%%%%%%%%%%\n");
		
		for(String s : temp.keySet()){
			System.out.println(s);
		}
		
		System.out.println("\n%%%%%%%%%%%%% HashMap : AAll Values %%%%%%%%%%%%%%\n");
		
		for(Integer s : temp.values()){
			System.out.println(s);
		}
	}

}
