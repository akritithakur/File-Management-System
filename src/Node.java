import java.util.*;
import java.io.*;

public class Node {
	boolean isDir, isOpen;
	ArrayList<Node> childs = new ArrayList<Node>();
	String data;
	int size;
	int seekP;
	String name;
	String temp = null;
	int FD;

	public String toString(){
		System.out.println(name);
		return name;
	}

	public Node(String filePath){
		this.seekP = 0;
		this.size = 0;
		this.name = filePath;
		this.seekP = 0;
		try{
			if(filePath.split("\\.")[1].equals("file")){
				this.isDir = false;
			}else{
				this.isOpen = true;
				this.isDir = true;
			}
		}catch(Exception e){
			this.isOpen = true;
			this.isDir = true;
		}
	}
	public Node(String filePath, int fd){
		this.seekP = 0;
		this.FD = fd;
		this.size = 0;
		this.name = filePath;
		this.seekP = 0;
		try{
			if(filePath.split("\\.")[1].equals("file")){
				this.isDir = false;
			}else{
				this.isOpen = true;
				this.isDir = true;
			}
		}catch(Exception e){
			this.isOpen = true;
			this.isDir = true;
		}
	}
	
	public void addFF(String filepath,int fd){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			this.childs.add(new Node(remainingPath[1],fd));
		}
		else{
			int existing = -1;

			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing == -1){
				Node toAdd = new Node(remainingPath[1]);
				this.childs.add(toAdd);
				Node nextN = childs.get(childs.indexOf(toAdd));
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.addFF(pathToGive, fd);
			}
			else{
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.addFF(pathToGive, fd);
			}
		}
	}

	
	public void deleteFF(String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
					break;
				}
			}

			if(existingChe == -1){
				System.out.println("ERROR: File path not existing.");
			}
			else{
				childs.remove(existingChe);
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.deleteFF(pathToGive);
			}
			else{
				System.out.println("ERROR: File path not existing.");
			}
		}
	}
	
	public void printLayout( int increment, String path) {
		for (int i = 0; i < increment; i++) {
            System.out.print(" ");
        }
		System.out.println(path);

        for( Node n: childs){
        	
 
        	if(n.isDir == false){
              for (int i = 0; i < increment+2; i++) {
                System.out.print(" ");
       	        }
        		System.out.println(path + "/" + n.name);
        	}
        }
        for( Node n: childs){
        	if(n.isDir == true){
        		if(n.isOpen){
        			n.printLayout(increment+2, path+"/" + n.name);
        		}
        		else{
        			System.out.println(n.name + " directory is closed. Its content cant be read.");        		}
        	}
        }

    }
	
	public void openFile(String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: file path not existing.");
			}
			else{
					childs.get(existingChe).isOpen = true ;	
					System.out.println("Opened: " + filepath);
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.openFile(pathToGive);
			}
			else{
				System.out.println("ERROR: file path not existing.");
			}
		}
	}
	public void closeFile(String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: file path not existing.");
			}
			else{
				if(childs.get(existingChe).isOpen ){
					childs.get(existingChe).isOpen = false ;					
					System.out.println("Closed: " + filepath);
				}
				else{
					System.out.println("File is not open");//Closed: " + filepath);

				}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.closeFile(pathToGive);
			}
			else{
				System.out.println("ERROR: file path not existing.");
			}
		}
	}
	public void renameFF(String filepath, String newName){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: file path not existing.");
			}
			else{
				childs.get(existingChe).name = newName;
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.renameFF(pathToGive, newName);
			}
			else{
				System.out.println("ERROR: file path not existing.");
			}
		}

	}

	public void readDirectory( String filepath){

		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 1 && remainingPath[0].equals("home")){
					System.out.println("home Directory Contents:");
			for(Node n: childs){
				System.out.println(n.name);
			}
		}
	
		
		else if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: Directory not existing.");
			}
			else{
					
					for(int i = 0; i<childs.size();i++){
						if(childs.get(i).name.equals(remainingPath[1])){
							existingChe = i;
						}
					}
					Node yolo = childs.get(existingChe);
					System.out.println(yolo.name + " Directory Contents:");
					for(Node n: yolo.childs){
						System.out.println(n.name);
					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.readDirectory(pathToGive);
			}
			else{
				System.out.println("ERROR: Directory not existing.");
			}
		}
		
	}
	public void readFile( String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{
					if(childs.get(existingChe).isOpen){
						System.out.println(childs.get(existingChe).data);
					}else{
						System.out.println("File is Closed. Open it to read/write.");
					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.readFile(pathToGive);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		
	}
	public void writeFile( String filepath, String junk){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{
					if(childs.get(existingChe).isOpen){
						
						if(childs.get(existingChe).seekP==0)
						{
							childs.get(existingChe).data = junk;
							temp = junk.trim();
							childs.get(existingChe).seekP = (junk.length()-1);
							childs.get(existingChe).size = (junk.length()-1);
						}
						else
						{
							temp=temp + junk;
							childs.get(existingChe).data = temp.trim();
							childs.get(existingChe).seekP = (temp.length()-1);
							childs.get(existingChe).size = (temp.length()-1);
						}
						
						
						//childs.get(existingChe).size = junk.length();
					}else{
						System.out.println("File is Closed. Open it to read/write.");
					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.writeFile(pathToGive, junk);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		
	}
	public void readMeta( String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{

						System.out.println("File Attributes: ");
						System.out.println("File Name: " + childs.get(existingChe).name);
						System.out.println("IsOpen: " + childs.get(existingChe).isOpen);
						System.out.println("Size: " + childs.get(existingChe).size);
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.readMeta(pathToGive);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		
	}
	public int getFD(String filepath){
		String[] remainingPath = filepath.split("/");
		int toReturn = -1;
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{
					if(childs.get(existingChe).isOpen){
						toReturn = childs.get(existingChe).FD;
					}else{

					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				toReturn = nextN.getFD(pathToGive);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		return toReturn;
	}
}
