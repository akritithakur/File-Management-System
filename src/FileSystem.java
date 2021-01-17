/*
 * OPERATING SYSTEMS PROJECT 
 * 
 * 201501430 - Prajeet Thakur
 * 201501431 - Akriti  Thakur
 * 
*/

import java.io.*;
import java.util.*;
public class FileSystem {
	
   	static BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
   	static Node home = new Node("home.dir");
	static int fdCounter = 1;
	static char MEM[] = new char[20*1024];
	static ArrayList<MemNode> memBus = new ArrayList<MemNode>();

	public static void main(String[] args) {
    	_init();
    	while(true){
    		if(handleInput()==-5)
    			break;
    		}
    	
    }
    
    
    public static int handleInput(){
    	
    	try{
    		
	    		String[] inp = BR.readLine().split(" ");
	    	switch(inp[0].toLowerCase()) {
	    		case "debug" :
		    		{
	    				for (MemNode temp : memBus) {
	    					
		    				System.out.println(temp);
	    				}
		    			break ;
		    		}
	    		case "make" :
	    			{
	    				home.addFF(inp[1],fdCounter);
	    				int existing = -1;

	    				for(int i = 0; i<memBus.size();i++){
	    					if(memBus.get(i).fd == fdCounter){
	    						existing = i;
	    					}
	    				}

	    				if(existing == -1){
	    					memBus.add(new MemNode("",fdCounter));
	    				}
	    				fdCounter++;
	    				break;
	    			}
	    		case "rm" :
	    			{
		    			int toInspect = home.getFD(inp[1]);
		    			int existing = -1;
		    			for(int i = 0; i<memBus.size();i++){
	    					if(memBus.get(i).fd == toInspect){
	    						existing = i;
	    					}
	    				}
	    				if(existing != -1){
	    					memBus.remove(existing);
	    				}
		    			home.deleteFF(inp[1]);
		    			break;
		    		}	
	    		case "rn" :
		    		{
		    			home.renameFF(inp[1],inp[2]);
		    			break;
		    		}
	    		case "ls" :
	    		{
	    			home.printLayout(0,"home");
	    			break;
	    		}
	    		case "open" :
	    		{
	    			home.openFile(inp[1]);
	    			break;
	    		}
	    		case "close" :
	    		{
	    			home.closeFile(inp[1]);
	    			break;
	    		}
	    		case "rd" :
	    		{
	    			home.readDirectory(inp[1]);
	    			break;
	    		}
	    		case "rf" :
	    		{
	    			home.readFile(inp[1]);
	    			break;
	    		}
	    		case "write" :
	    		{
	    			String toWrite = "";
	    			for(int i = 2; i<inp.length;i++){
	    				toWrite += inp[i] + " ";
	    			}
	    			home.writeFile(inp[1], toWrite);
	    			int toInspect = home.getFD(inp[1]);
	    			int existing = -1;
    				for(int i = 0; i<memBus.size();i++){
    					if(memBus.get(i).fd == toInspect){
    						existing = i;
    					}
    				}
    				if(existing != -1){
    					memBus.get(existing).data = toWrite;
    					memBus.get(existing).setSize();
    				}
	    			break;
	    		}
	    		case "meta" :
	    		{
	    			home.readMeta(inp[1]);
	    			break;
	    		}
	    		case "exit" :
	    		{
	    			return -5;
	    		}
	    		case "" :
	    		{
	    			break;
	    		}
	    		default :
	    		{
    				System.out.println("Invalid command");
    				break;
    			}
    		}
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
    	return 0;
    }

    public static void _init(){
    	for(int i=0;i<MEM.length;i++){
    		MEM[i]= '0';
    	}
    	System.out.println("This file system uses tree-structured directory approach and contiguous memory allocation.");
    	System.out.println("------------------------------------------------------------------------------------------");
    	System.out.println("List of commands : ");
    	System.out.println();
    	System.out.println();
    	System.out.println("{filepath} example : home/folder1/folder2/file1.file (All files and folders should be inside home.)");
    	System.out.println();
    	System.out.println("1) Create: make {filepath} ");
    	System.out.println("2) Delete: rm {filepath} ");
    	System.out.println("3) Rename: rn {filepath} {newName}");
    	System.out.println("4) Print: ls");
		System.out.println("5) Open: open {filepath}");
		System.out.println("6) Close: close {filepath}");
		System.out.println("7) Read Dir: rd {DirectoryPath}");
		System.out.println("8) Read File: rf {filePath}");
		System.out.println("9) Write File: write {filePath} {data}");
		System.out.println("10) Read Metadata: meta {filePath}");
		System.out.println("11) Exit Filesystem: exit");


    }
}