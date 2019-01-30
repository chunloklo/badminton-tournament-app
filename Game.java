import java.util.Arrays;
import java.io.*;

public class Game{

   private Event event;
   private boolean first, consolation, waited;
   private int id, round, court;
   private Player[] team1, team2;
   private int[] score;
   private Game nextW, nextL;

   public Game(Event event, int id, boolean first, boolean consolation){

      this.event = event;
      this.id = id;
      this.first = first;
      this.consolation = consolation;
      this.waited = false;

      if (event.getRR()){
         this.round = -1; 
      }
      else{
         if (consolation){
            if (id-event.getSize() == 0){
               this.round = 0;
            }
            else{
               this.round = (int)(Math.log(id-event.getSize())/Math.log(2));
            }
         }
         else{
            if (id == 0){
               this.round = 0;
            }
            else{
               this.round = (int)(Math.log(id)/Math.log(2));
            }
         }
      }

      //set next
      int nextId;
      if (round <= 0){
         return;
      }
      else{
         //consolation
         if (consolation){
            nextId = event.getSize() + (id - event.getSize())/2;
            if (event.getGame(nextId) == null){
               this.nextW = new Game(event, nextId, false, true);
               event.setGame(nextId, nextW);
            }
            else{
               this.nextW = event.getGame(nextId);
            }
            //34final
            if (round == 1){
               nextId = event.getSize();
               if (event.getGame(nextId) == null){
                  this.nextL = new Game(event, nextId, false, true);
                  event.setGame(nextId, nextL);
               }
               else{
                  this.nextL = event.getGame(nextId);
               }
            }
         }
         else{
            nextId = id/2;
            if (event.getGame(nextId) == null){
               this.nextW = new Game(event, nextId, false, false);
               event.setGame(nextId, nextW);
            }
            else{
               this.nextW = event.getGame(nextId);
            }
            //first round
            if (first && event.getCons()){
               nextId = event.getSize() + id/2;
               if (event.getGame(nextId) == null){
                  this.nextL = new Game(event, nextId, false, true);
                  event.setGame(nextId, nextL);
               }
               else{
                  this.nextL = event.getGame(nextId);
               }
            }
            //34final
            if (round == 1){
               nextId = 0;
               if (event.getGame(nextId) == null){
                  this.nextL = new Game(event, nextId, false, false);
                  event.setGame(nextId, nextL);
               }
               else{
                  this.nextL = event.getGame(nextId);
               }
            }
         }
      }
   }

   public Event getEvent(){
      return event;
   }

   public int getId(){
      return id;
   }

   public Player[] getTeam1(){
      return team1;
   }
   public Player[] getTeam2(){
      return team2;
   }

   public void setWaited(){
      this.waited = true;
   }

   public void setPlayer(int teamNum, Player[] team, boolean RR){
      //update players
      if (! RR){
         for (Player p: team){
            if (event.getName() == "MS" || event.getName() == "WS"){
               p.setS(this);
            }
            else if (event.getName() == "MD" || event.getName() == "WD"){
               p.setD(this);
            }
            else{
               p.setXD(this);
            }
         }
      }
      //set game
      boolean ready = false;
      if (teamNum == 0){
         this.team1 = team;
         if (team2 != null){
            ready = true;
         }
      }
      else{
         this.team2 = team;
         if (team1 != null){
            ready = true;
         }
      }
      if (ready){
         if (!event.getTournament().getToDoGames().contains(this)){
            event.getTournament().addToDoGame(this);
         }
      }
      else if (!first && id != 0){
         if (consolation){
            if (event.getGame(id*2-event.getSize()+(1-teamNum)) != null){
               event.getGame(id*2-event.getSize()+(1-teamNum)).setWaited();
            }
         }
         else{
            event.getGame(id*2+(1-teamNum)).setWaited();
         }
      }
   }

   public int getValue(){
      int team1Value, team2Value, waitValue;
      if (event.getName() == "MS" || event.getName() == "WS"){
         team1Value = team1[0].remainS() + team1[0].remainD() + team1[0].remainXD();
         team2Value = team2[0].remainS() + team2[0].remainD() + team2[0].remainXD();
      }
      else {
         team1Value = team1[0].remainD() + Math.max(team1[0].remainXD(), team1[1].remainXD());
         team2Value = team2[0].remainD() + Math.max(team2[0].remainXD(), team2[1].remainXD());
      }

      waitValue = 0;
      if (waited){
         waitValue = 5;
      }
      return team1Value + team2Value + waitValue;
   }

   public int[] getScore(){
      return score;
   }

   public void setScore(int[] score){
      this.score = score;
      //calculate winner
      int set = score.length/2;
      int w = 0;
      for (int i=0; i<set; i++){
         if (score[i*2]>score[i*2+1]){
            w++;
         }
      }
      Player[] winner, loser;
      if (w>set/2){
         winner = team1;
         loser = team2;
      }
      else{
         winner = team2;
         loser = team1;
      }
      //set next games
      if (nextW != null){
         this.nextW.setPlayer(id%2, winner, false);
      }
      else{
         //update players
         for (Player p: winner){
            if (event.getName() == "MS" || event.getName() == "WS"){
               p.setS(null);
            }
            else if (event.getName() == "MD" || event.getName() == "WD"){
               p.setD(null);
            }
            else{
               p.setXD(null);
            }
         }
      }
      if (nextL != null){
         this.nextL.setPlayer(id%2, loser, false);
      }
      else{
         //update players
         for (Player p: loser){
            if (event.getName() == "MS" || event.getName() == "WS"){
               p.setS(null);
            }
            else if (event.getName() == "MD" || event.getName() == "WD"){
               p.setD(null);
            }
            else{
               p.setXD(null);
            }
         }
      }
      //remove from toDoGame
      event.getTournament().removeToDoGame(this);
      //record scores
      if ((!team1[0].getName().equals("Bye"))&& (!team2[0].getName().equals("Bye"))){
         try{
            PrintWriter scoreFile = new PrintWriter(new BufferedWriter(new FileWriter("record/scores.txt", true)));
            scoreFile.print(event.getName());
            scoreFile.print(" ");
            scoreFile.print(id);
            for (int i = 0; i<score.length; i++){
               scoreFile.print(" ");
               scoreFile.print(score[i]);
            }
            scoreFile.println();
            scoreFile.close();
         }
         catch(IOException ex){
         }
      }
   }

   public int getRound(){
      return round;
   }

   public String toString(){
      String s = "<td>"+event.getName()+" "+Integer.toString(id)+"</td><td align=\"center\">";
      if (event.getRR()){
         s = s + "*";
      }
      else{
         if (consolation){
            s = s + "B";
         }
         s = s + Integer.toString((int)(Math.log(event.getSize())/Math.log(2))-round);
      }
      s = s + "</td><td>";
      if (team1 != null){
         for (Player p: team1){
            s = s + " -" + p.getName();
         }
      }
      s = s + "</td><td>";
      if (team2 != null){
         for (Player p: team2){
            s = s + " -" + p.getName();
         }
      }
      s = s + "</td><td align=\"center\">";
      if (score != null){
         for (int i = 0; i<score.length/2; i++){
            s = s + Integer.toString(score[i*2]) + ":" + Integer.toString(score[i*2+1])+" ";
         }
      }
      s = s + "</td><td align=\"center\">";
      if (nextW == null){
         s = s + "null";
      }
      else{
         s = s + Integer.toString(nextW.id);
      }
      s = s + "</td><td align=\"center\">";
      if (nextL == null){
         s = s + "null";
      }
      else{
         s = s + Integer.toString(nextL.id);
      }
      s = s + "</td><td align=\"center\">";
      if(team1 != null && team2 != null){
         s = s + Integer.toString(getValue());
      }
      s = s + "</td><td>";
      if (waited){
         s = s + "waited";
      }
      s = s + "</td>";
      return s;
   }
}