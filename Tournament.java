import java.util.HashMap;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Set;
import java.io.*;

public class Tournament{

   private HashMap<String, Event> eventMap;
   private String[] nameArray;
   private HashMap<String, Player> playerMap;
   private ArrayList<Game> toDoGames;
   private PriorityQueue<Game> nextGames;
   private HashMap<Integer, Game> currGames;

   public Tournament() throws IOException{

      this.eventMap = new HashMap<String, Event>(5);
      this.playerMap = new HashMap<String, Player>(100);
      this.toDoGames = new ArrayList<Game>(200);
      this.currGames = new HashMap<Integer, Game>(7);

      //MS
      String line = null;
      int size = 0;
      Player[][] teams = new Player[64][1];
      FileInputStream MSFile = new FileInputStream("brackets/MS.txt");
      BufferedReader MSR = new BufferedReader(new InputStreamReader(MSFile));
      while ((line = MSR.readLine()) != null) {
         if (playerMap.get(line) == null){
            teams[size][0] = new Player(line, "M");
            this.playerMap.put(line, teams[size][0]);
         }
         else{
            teams[size][0] = playerMap.get(line);
         }
         size++;
	   }
      MSR.close();
      MSFile.close();
      this.eventMap.put("MS", new Event(this, "MS", true, false, size, teams));

      //WS
      line = null;
      size = 0;
      teams = new Player[16][1];
      FileInputStream WSFile = new FileInputStream("brackets/WS.txt");
      BufferedReader WSR = new BufferedReader(new InputStreamReader(WSFile));
      while ((line = WSR.readLine()) != null) {
         if (playerMap.get(line) == null){
            teams[size][0] = new Player(line, "W");
            this.playerMap.put(line, teams[size][0]);
         }
         else{
            teams[size][0] = playerMap.get(line);
         }
         size++;
	   }
      WSR.close();
      WSFile.close();
      this.eventMap.put("WS", new Event(this, "WS", true, false, size, teams));

      //MD
      line = null;
      size = 0;
      teams = new Player[64][2];
      String[] lineNames;
      FileInputStream MDFile = new FileInputStream("brackets/MD.txt");
      BufferedReader MDR = new BufferedReader(new InputStreamReader(MDFile));
      while ((line = MDR.readLine()) != null) {
         lineNames = line.split("/");
         for (int i=0; i<2; i++){
            if (playerMap.get(lineNames[i]) == null){
               teams[size][i] = new Player(lineNames[i], "M");
               this.playerMap.put(lineNames[i], teams[size][i]);
            }
            else{
               teams[size][i] = playerMap.get(lineNames[i]);
            }
         }
         size++;
	   }
      MDR.close();
      MDFile.close();
      this.eventMap.put("MD", new Event(this, "MD", true, false, size, teams));


      //WD
      line = null;
      size = 0;
      teams = new Player[32][2];
      FileInputStream WDFile = new FileInputStream("brackets/WD.txt");
      BufferedReader WDR = new BufferedReader(new InputStreamReader(WDFile));
      while ((line = WDR.readLine()) != null) {
         lineNames = line.split("/");
         for (int i=0; i<2; i++){
            if (playerMap.get(lineNames[i]) == null){
               teams[size][i] = new Player(lineNames[i], "W");
               this.playerMap.put(lineNames[i], teams[size][i]);
            }
            else{
               teams[size][i] = playerMap.get(lineNames[i]);
            }
         }
         size++;
	   }
      WDR.close();
      WDFile.close();
      this.eventMap.put("WD", new Event(this, "WD", false, true, size, teams));


      //XD
      line = null;
      size = 0;
      teams = new Player[32][2];
      FileInputStream XDFile = new FileInputStream("brackets/XD.txt");
      BufferedReader XDR = new BufferedReader(new InputStreamReader(XDFile));
      while ((line = XDR.readLine()) != null) {
         lineNames = line.split("/");
         for (int i=0; i<2; i++){
            if (playerMap.get(lineNames[i]) == null){
               if (i == 0){
                  teams[size][i] = new Player(lineNames[i], "M");
               }
               else{
                  teams[size][i] = new Player(lineNames[i], "W");
               }
               this.playerMap.put(lineNames[i], teams[size][i]);
            }
            else{
               teams[size][i] = playerMap.get(lineNames[i]);
            }
         }
         size++;
	   }
      XDR.close();
      XDFile.close();
      this.eventMap.put("XD", new Event(this, "XD", true, false, size, teams));

      //sort the players
      Set<String> tempSet = this.playerMap.keySet();
      tempSet.remove("Bye");
      this.nameArray = tempSet.toArray(new String[0]);
      Arrays.sort(this.nameArray);

      //load register
      FileInputStream regFile = new FileInputStream("load/reg.txt");
      BufferedReader regR = new BufferedReader(new InputStreamReader(regFile));
      while ((line = regR.readLine()) != null) {
         register(line);
	   }
      regR.close();
      regFile.close();

      //load lunch
      FileInputStream lunchFile = new FileInputStream("load/lunch.txt");
      BufferedReader lunchR = new BufferedReader(new InputStreamReader(lunchFile));
      while ((line = lunchR.readLine()) != null) {
         haveLunch(line, true);
	   }
      lunchR.close();
      lunchFile.close();

      //load scores
      FileInputStream SCFile = new FileInputStream("load/scores.txt");
      BufferedReader SCR = new BufferedReader(new InputStreamReader(SCFile));
      while ((line = SCR.readLine()) != null) {
         this.clearBye();
         String[] lineSplit = line.split(" ");
         String eventName = lineSplit[0];
         int gameId = Integer.parseInt(lineSplit[1]);
         Game g = eventMap.get(eventName).getGame(gameId);
         int[] score = new int[lineSplit.length-2];
         for (int i=0; i<lineSplit.length-2; i++){
            score[i] = Integer.parseInt(lineSplit[i+2]);
         }
         g.setScore(score);
	   }
      SCR.close();
      SCFile.close();
   }

   public void clearBye(){
      boolean flag = true;
      while (flag){
         flag = false;
         for (Game g: toDoGames){
            if (g.getTeam1()[0].getName().equals("Bye") && g.getTeam2() != null){
               int[] score = {0, 21};
               g.setScore(score);
               flag = true;
               break;
            }
            else if (g.getTeam2()[0].getName().equals("Bye") && g.getTeam1() != null){
               int[] score = {21, 0};
               g.setScore(score);
               flag = true;
               break;
            }
         }
      }
   }

   public String[] getNameArray(){
      return nameArray;
   }

   public ArrayList<Game> getToDoGames(){
      return toDoGames;
   }

   public void addToDoGame(Game game){
      this.toDoGames.add(game);
   }

   public void removeToDoGame(Game game){
      this.toDoGames.remove(game);
   }

   public String register(String name){
      Player p = playerMap.get(name);
      if (p.getReg()){
         return ("Sorry! " + name + " has already registered.");
      }
      else{
         p.register();
         //record reg
         try{
            PrintWriter regFile = new PrintWriter(new BufferedWriter(new FileWriter("record/reg.txt", true)));
            regFile.println(name);
            regFile.close();
         }
         catch(IOException ex){
         }
      }
      return "";
   }

   public String haveLunch(String name, boolean reset){
      Player p = playerMap.get(name);
      if (p.getLunch()){
         return ("Sorry! " + name + " has already had lunch.");
      }
      else{
         p.haveLunch(reset);
         //record reg
         try{
            PrintWriter lunchFile = new PrintWriter(new BufferedWriter(new FileWriter("record/lunch.txt", true)));
            lunchFile.println(name);
            lunchFile.close();
         }
         catch(IOException ex){
         }
      }
      return "";
   }

   public static Comparator<Game> gameComparator = new Comparator<Game>(){
        public int compare(Game g1, Game g2) {
            return (int) (g2.getValue() - g1.getValue());
        }
   };

   public HashMap<Integer, Game> getCurrGames(){
      return currGames;
   }

   public PriorityQueue<Game> getNextGames(){
      return nextGames;
   }

//THIS IS THE SPOT THAT SETS THE NEXT GAME
   public void setNextGames(){
      this.clearBye();
      this.nextGames = new PriorityQueue<Game>(100, gameComparator);
      for(Game g: toDoGames){
         boolean goodGame = true;
         for (Player p: g.getTeam1()){
            if (!p.getReg() || p.getPlay() || p.isBreak()){
               goodGame = false;
            }
            if ((g.getEvent().getName() == "MD" || g.getEvent().getName() == "WD" || g.getEvent().getName() == "XD") && (p.getS() != null || p.getRRS() > 0)){
               goodGame = false;
            }
            // if (g.getEvent().getName() == "XD" && p.getD() != null){
            //    goodGame = false;
            // }
         }
         for (Player p: g.getTeam2()){
            if (!p.getReg() || p.getPlay() || p.isBreak()){
               goodGame = false;
            }
            if ((g.getEvent().getName() == "MD" || g.getEvent().getName() == "WD" || g.getEvent().getName() == "XD") && (p.getS() != null || p.getRRS() > 0)){
                goodGame = false;
            }
            // if (g.getEvent().getName() == "XD" && p.getD() != null){
            //    goodGame = false;
            // }
         }
         if (goodGame){
            this.nextGames.add(g);
         }
      }
   }

   public String startGame(String e, int id, int court){
      this.clearBye();
      Game game;
      try{
          game = eventMap.get(e).getGame(id);
      }
      catch (NullPointerException ex){
         return "Sorry! Can't find this game in the system.";
      }
      if (game == null){
         return "Sorry! Can't find this game in the system.";
      }
      //exception
      if(game.getTeam1() == null || game.getTeam2() == null){
         return "Sorry! Missing player for this game.";
      }
      if (currGames.get(court) != null) {
         return "Sorry! Court is not available.";
      }

      //check if game is already playing;
      for (Player p: game.getTeam1()){
         if (p.getPlay()) {
            return "Sorry! Player(s) is on court";
         }
      }
      for (Player p: game.getTeam2()){
         if (p.getPlay()) {
            return "Sorry! Player(s) is on court";
         }
      }

      //update players
      for (Player p: game.getTeam1()){
         p.setIsPlaying(true);
      }
      for (Player p: game.getTeam2()){
         p.setIsPlaying(true);
      }
      //update lists
      this.removeToDoGame(game);
      this.currGames.put(court, game);
      return "";
   }

   public String endGame(int court, int[] score){
      Game game = currGames.get(court);
      //exception
      if (game == null){
         return "Sorry! No game is playing on this court.";
      }
      //update players
      for (Player p: game.getTeam1()){
         p.setIsPlaying(false);
         if (court != 7 && score.length != 0){
            p.takeBreak(10);
            if (game.getEvent().getRR()){
               if (game.getEvent().getName() == "MS" || game.getEvent().getName() == "WS"){
                  p.setRRS(-1);
               }
               else if (game.getEvent().getName() == "MD" || game.getEvent().getName() == "WD"){
                  p.setRRD(-1);
               }
               else{
                  p.setRRXD(-1);
               }
            }
         }
      }
      for (Player p: game.getTeam2()){
         p.setIsPlaying(false);
         if (court != 7 && score.length != 0){
            p.takeBreak(10);
            if (game.getEvent().getRR()){
               if (game.getEvent().getName() == "MS" || game.getEvent().getName() == "WS"){
                  p.setRRS(-1);
               }
               else if (game.getEvent().getName() == "MD" || game.getEvent().getName() == "WD"){
                  p.setRRD(-1);
               }
               else{
                  p.setRRXD(-1);
               }
            }
         }
      }
      //update lists
      currGames.remove(court);
      //if score is empty
      if (score.length == 0){
         this.addToDoGame(game);
         return "";
      }
      //update game
      else{
         game.setScore(score);
         return "";
      }
   }

   public void updateOutput(){

      this.clearBye();

      //Update Player
      String s = "<html><head><title>Players</title></head><body><table>";
      s = s + "<tr><td>Name</td><td>Sex</td><td>Registered</td><td align=\"center\">Break</td><td>Playing</td><td>Lunch</td><td align=\"center\">S</td><td align=\"center\">D</td><td align=\"center\">XD</td></tr>";
      for (String name: this.nameArray){
         s = s + playerMap.get(name).toString();
      }
      s = s + "</table></body></html>";

      File nameListOld=new File("output/Players.html");
      File nameListOldWeb=new File("output/badminton/Players.html");
      nameListOld.delete();
      nameListOldWeb.delete();
      File nameListNew=new File("output/Players.html");
      File nameListNewWeb=new File("output/badminton/Players.html");
      try{
          FileWriter nameListW = new FileWriter(nameListNew, false);
          FileWriter nameListWWeb = new FileWriter(nameListNewWeb, false);
          nameListW.write(s);
          nameListW.close();
          nameListWWeb.write(s);
          nameListWWeb.close();
      }
      catch (IOException ex){
      }

      //Update Game Webpage
      s = "<html><head><title>Games</title></head><body><table>";
      s = s + "<tr><td>Game</td><td>Round</td><td>Team 1</td><td>Team 2</td><td>Score</td><td>W next</td><td>L next</td><td>Value</td><td></td></tr>";
      for (Event e: this.eventMap.values()){
         for (Game g: e.getGameMap().values()){
            s = s + "<tr>" + g.toString() + "</tr>";
         }
      }
      s = s + "</table></body></html>";

      File gameListOld=new File("output/Game List.html");
      File gameListOldWeb=new File("output/badminton/Game List.html");
      gameListOld.delete();
      gameListOldWeb.delete();
      File gameListNew=new File("output/Game List.html");
      File gameListNewWeb=new File("output/badminton/Game List.html");
      try{
          FileWriter gameListW = new FileWriter(gameListNew, false);
          FileWriter gameListWWeb = new FileWriter(gameListNewWeb, false);
          gameListW.write(s);
          gameListW.close();
          gameListWWeb.write(s);
          gameListWWeb.close();
      }
      catch (IOException ex){
      }

      //Update to Play Webpage
      s = "<html><head><title>Games to Play</title></head><body><table>";
      s = s + "<tr><td>Game</td><td>Round</td><td>Team 1</td><td>Team 2</td><td></td><td>W next</td><td>L next</td><td>Value</td><td></td></tr>";
      PriorityQueue<Game> toDoQ = new PriorityQueue<Game>(100, gameComparator);
      for (Game g: toDoGames){
         toDoQ.add(g);
      }
      Game g;
      while ((g = toDoQ.poll()) != null){
         s = s + "<tr>" + g.toString()+ "</tr>";
      }
      s = s + "</table></body></html>";

      File toPlayListOld=new File("output/Games to Play.html");
      File toPlayListOldWeb=new File("output/badminton/Games to Play.html");
      toPlayListOld.delete();
      toPlayListOldWeb.delete();
      File toPlayListNew=new File("output/Games to Play.html");
      File toPlayListNewWeb=new File("output/badminton/Games to Play.html");
      try{
          FileWriter toPlayListW = new FileWriter(toPlayListNew, false);
          FileWriter toPlayListWWeb = new FileWriter(toPlayListNewWeb, false);
          toPlayListW.write(s);
          toPlayListWWeb.write(s);
          toPlayListWWeb.close();
          toPlayListW.close();
      }
      catch (IOException ex){
      }

      //Update Brackets
      for (String eventS: eventMap.keySet()){
         s = eventMap.get(eventS).toString();
         File fOld=new File("output/" + eventS + ".html");
         File fOldWeb=new File("output/badminton/" + eventS + ".html");
         fOld.delete();
         fOldWeb.delete();
         File fNew=new File("output/" + eventS + ".html");
         File fNewWeb=new File("output/badminton/" + eventS + ".html");
         try{
             FileWriter fW = new FileWriter(fNew, false);
             FileWriter fWWeb = new FileWriter(fNewWeb, false);
             fW.write(s);
             fWWeb.write(s);
             fW.close();
             fWWeb.close();
         }
         catch (IOException ex){
         }


      }

      //UpdateGithub
      try {
         GithubUpdater.update();
      }
      catch (Exception e) {
      }
   }
}