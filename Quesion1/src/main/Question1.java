package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Question1 {
	
	public void doesFileExist(String path) {
		
		File file = new File(path); 
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
		
			String inputLine;
			String[] tokens;
		    
			while ((inputLine = in.readLine()) != null) {
				
				tokens = inputLine.split("- |– |, |\n");
				
				for(int i = 0; i < tokens.length; i++) {
					System.out.println(tokens[i]);
				}
			}
		    
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Question1 obj = new Question1();
		obj.doesFileExist("dict.txt");
	}
}
