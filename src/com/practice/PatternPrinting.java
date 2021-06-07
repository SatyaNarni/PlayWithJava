package com.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PatternPrinting {

	public static void pattern1(int input) {
		/*
		 	1 2 3 4 
			8 7 6 5 
			9 10 11 12 
			16 15 14 13
		 */

		int c = 0;
		for (int i = 1; i <= input; i++) {
			if (i % 2 != 0) {
				for (int j = 0; j < input; j++) {
					c++;
					System.out.print(c + " ");
				}
				System.out.println();
			} else {
				c += input;
				for (int j = c; j > c - input; j--) {
					System.out.print(j + " ");
				}
				System.out.println();
			}
		}
	}

	public static void pattern2(int input) {
		/*
		 	*
			**
			***
			****
		 */

		for (int i = 1; i <= input; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print("*" + "");
			}
			System.out.println();
		}
	}

	public static void pattern3(int input) {
		/*
		  	*
			**
			***
			****
			***
			**
			*
		 */

		for (int i = 1; i <= input; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print("*" + "");
			}
			System.out.println();
		}
		for (int i = --input; i > 0; i--) {
			for (int j = 1; j <= i; j++) {
				System.out.print("*" + "");
			}
			System.out.println();
		}
	}
	
	public static void pattern4(int input) {
		/*
			****
			***
			**
			*
		 */

		for (int i = input; i > 0; i--) {
			for (int j = 1; j <= i; j++) {
				System.out.print("*" + "");
			}
			System.out.println();
		}
	}

	public static void pattern5(int input) {
		/*
		 	****
			***
			**
			*
			**
			***
			****
		 */
		
		for (int i = input; i > 0; i--) {
			for (int j = 1; j <= i; j++) {
				System.out.print("*" + "");
			}		
				System.out.println();					
		}
		for (int i = 2; i <= input; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print("*" + "");
			}
			System.out.println();
		}
		
	}
	
	public static void pattern6(int input){
		/*
			1 
			2 3 
			4 5 6 
			7 8 9 10 
		 */
		int counter=1;
		for(int i=1;i<=input;i++){
			for(int j=counter;j<=(i*(i+1)/2);j++){
				System.out.print(counter++ + " ");
			}
			System.out.println();
		}
	}

	public static void pattern7(int input){
		/*
		 	1 
			1 2 
			1 2 3 
			1 2 3 4
		 */
		for(int i=1;i<=input;i++){
			for(int j=1;j<=i;j++){
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}
	
	public static int findFactorial(int num){
		if(num>=1){
			return num*findFactorial(num-1);
		}else{
			return 1;
		}
	}
	public static int findCobination(int n,int r){
		return findFactorial(n)/(findFactorial(n-r)*findFactorial(r));
	}
	
	public static void pattern8(int input){
		/*
		 	1 
			1 1 
			1 2 1 
			1 3 3 1 
			1 4 6 4 1 
		 */
		for(int i=0;i<input;i++){
			for(int j=0;j<=i;j++){
				System.out.print(findCobination(i,j)+" ");
			}
			System.out.println();
		}
	}
	
	public static void pattern9(int input){
		/*
		 	1 2 3 4 5 
			2 3 4 5 
			3 4 5 
			4 5 
			5
		 */
		for(int i= 1;i <= input;i++){
			for(int j=i;j<=input;j++){
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}
	
	public static void pattern10(int input){
		/*
		 	5 
			4 5 
			3 4 5 
			2 3 4 5 
			1 2 3 4 5  
		 */
		for(int i= input;i >0;i--){
			for(int j=i;j<=input;j++){
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}
	
	public static void pattern11(int input){
		
		for(int i= input;i >0;i--){
			for(int j=input;j>=i;j--){
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}
	
	public static void pattern12(int input){
		/*
		 	1 
			2 1 2 
			3 2 1 2 3 
			4 3 2 1 2 3 4 
			5 4 3 2 1 2 3 4 5 
		 */
		for(int i= 1;i <= input;i++){
			for(int j=i;j>1;j--){
				System.out.print(j+" ");
			}
			for(int k=1;k<=i;k++){
				System.out.print(k+" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {

		System.out.print("Enter Input : ");
		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 1 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern1(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 2 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern2(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 3 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern3(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 4 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern4(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 5 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern5(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 6 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern6(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 7 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern7(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 8 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern8(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 9 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern9(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 10 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern10(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 11 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern11(input);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%% Pattern 12 %%%%%%%%%%%%%%%%%%%%%\n");
		pattern12(input);
	}
}
