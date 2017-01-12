/* [Leaderboard.java]
 * A Small program for leaderboard information
 * @author Jim
 */

//Import tools from java library
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Leaderboard {

	//Declare Private Class Variable
	private ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	* default constructor
	* sets the arraylist and adds a new item
	* @param name, score
	*/
	Leaderboard(String name, int score){
		
		try{
			
			//start the scanner
			Scanner fileReader = new Scanner(new java.io.File("Leaderboard.txt"));
			String playerInfo = fileReader.nextLine();
			int i = 0;
			
			//read player information into array
			while(playerInfo != null){
				String[] info = playerInfo.split(",");
				players.add(new Player(info[0],0,0,0,0,10));
				players.get(i).setScore(Integer.parseInt(info[1]));
				i++;
				try{
					playerInfo = fileReader.nextLine();
				}catch(NoSuchElementException e){
					break;
				}
				
			}
			
			//close scanner
			fileReader.close();
			
			//Add the new player at the end of arraylist
			players.add(new Player(name,0,0,0,0,10));
			players.get(i).setScore(score);
			
			//Reprint the list storage file with one new player in the arraylist
			PrintWriter reWrite = new PrintWriter(new java.io.File("Leaderboard.txt"));
			for(int j = 0; j < i; j++){
				reWrite.println(players.get(j).getName()+","+players.get(j).getScore());
			}
			reWrite.close();
			
		//Report any exception to console
		} catch (IOException e){	
			System.out.println("Error. Cannot find file.");
		}
	}
	
	/**
	* sortAndGet
	* sort the private class variable ArrayList players
	* @return ArrayList<Protagonist> players
	*/
	public ArrayList<Player> sortAndGet(){
		
		//Selection sort
		int max = 0;
		for(int i = 0; i < players.size(); i++){
			max = i;
			for(int j = i; j < players.size(); j++){
				if(players.get(j).getScore() > players.get(max).getScore()){
					max = j;
				}
			}
			Player placeholder = players.get(i);
			players.set(i, players.get(max));
			players.set(max, placeholder);
		}
		
		//return list
		return players;
	}
	
}