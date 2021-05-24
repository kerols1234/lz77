/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIC
 */
public class Lz77 {

    static File tags_file = new File("tags_file.txt");
	static String tag = "";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            
            compresionLZ77("lz77.txt");
            decompresionLZ77("tags_file.txt");
            
        } catch (IOException ex) {
            Logger.getLogger(Lz77.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void decompresionLZ77(String fileName) throws FileNotFoundException, IOException {
        ArrayList <Character> arr=new ArrayList <>(); 
        File file=new File(fileName);
        Scanner reader=new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            Character x=data.charAt(1);
            if(x.equals('0'))
            {
                arr.add(data.charAt(5));
            }
            else
            {
               int a=Character.getNumericValue(x);  
               int b=Character.getNumericValue(data.charAt(3)); 
               while(b>0)
               {
                  arr.add(arr.get(arr.size()-a));
                  b--;
                 // a++;

               }
                arr.add(data.charAt(5));
            }
        }
        reader.close();
        file.delete();
        FileWriter writer = new FileWriter("lz77.txt");

        for(int i=0;i<arr.size();i++)
        {
            writer.write(arr.get(i));
        }
        writer.close();
    }

    public static void compresionLZ77(String fileName) throws IOException {
		
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String str,d = "";
		
		while((str = br.readLine())!=null){
	         d +=  str;
		}
		
		char[] data = d.toCharArray();
		
		br.close();
		fr.close();
		
		for (int i = 0; i < data.length; i++) {
			i = tags(data, i);	
		}
		
		FileWriter fileWriter = new FileWriter(tags_file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		bufferedWriter.write(tag);
		
		bufferedWriter.close();
		fileWriter.close();

	}
	
    public static int tags(char[] data, int index){
		
		int p = index - 1, length = 0, best_length = 0, best_postion = 0, best_index = index, step = 1, old_index = index;
		boolean b = false, n = true;
		while(p != -1) {
			int old_postion = p;
			index = old_index;
			while(true) {
				if(index < data.length && data[p] == data[index]) {
					index++;
					p++;
					length++;
					b = true;
				}else {
					break;
				}
			}
						
			if(((step + length) < (best_length + best_postion)) || (b && n)) {
				best_length = length;
				best_postion = step;
				best_index = index;
				n = false;
			}
			
			p = old_postion - 1;
			step++;
		}
				
		tag += "<"+ best_postion + "," + best_length + "," + data[best_index] + ">" + "\n";
		
		return best_index;
	}
	
}
