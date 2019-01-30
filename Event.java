import java.util.HashMap;

public class Event{

   private Tournament tournament;
   private String name;
   private boolean consolation, roundRobin;
   private int size;
   private HashMap<Integer, Game> gameMap;

   public Event(Tournament t, String name, boolean consolation, boolean roundRobin, int size, Player[][] teams){

      this.tournament = t;
      this.name = name;
      this.consolation = consolation;
      this.roundRobin = roundRobin;
      this.size = size;

      //set event games
      if (roundRobin){
         this.gameMap = new HashMap<Integer, Game> (size*(size-1)/2);
         int id = 0;
         for (int i=0; i<size; i++){
            for (Player p: teams[i]){
               if (name == "MS" || name == "WS"){
                  p.setRRS(size - 1);
               }
               else if (name == "MD" || name == "WD"){
                  p.setRRD(size - 1);
               }
               else{
                  p.setRRXD(size - 1);
               }
            }
            for (int j = i+1; j<size; j++){
               Game game = new Game(this, id, true, false);
               game.setPlayer(0, teams[i], true);
               game.setPlayer(1, teams[j], true);
               this.gameMap.put(id, game);
               id++;
            }
         }
      }
      else{
         if (consolation){
            this.gameMap = new HashMap<Integer, Game> (size+size/2);
         }
         else{
            this.gameMap = new HashMap<Integer, Game> (size);
         }
         for (int i=0; i<size/2; i++){
            int id = size/2+i;
            Game game = new Game(this, id, true, false);
            game.setPlayer(0, teams[i*2], false);
            game.setPlayer(1, teams[i*2+1], false);
            this.gameMap.put(id, game);
         }
      }
   }

   public Tournament getTournament(){
      return tournament;
   }

   public String getName(){
      return name;
   }

   public int getSize(){
      return size;
   }
   
   public boolean getCons(){
      return consolation;
   }
   
   public boolean getRR(){
      return roundRobin;
   }
   
   public HashMap<Integer, Game> getGameMap(){
      return gameMap;
   }

   public Game getGame(int id){
      return gameMap.get(id);
   }

   public void setGame(int id, Game game){
      this.gameMap.put(id, game);
   }

   public String toString(){
      Game g;
      String s = "<html><head><title>" + name + "</title></head><body><table>";
      if (roundRobin){
         for (int i=0; i<size*(size-1)/2; i++){
            g = getGame(i);
            s = s + "<tr><td>";
            if (g.getTeam1() != null){
               for (Player p: g.getTeam1()){
                  s = s + "-" + p.getName();
               }
            }
            else{
               s = s + "*waiting*";
            }
            s = s + "</td><td>|</td></tr>";
            s = s + "<tr><td></td><td>|</td><td>";
            if (g.getScore() != null){
               int set = g.getScore().length/2;
               int w = 0;
               for (int j=0; j<set; j++){
                  if (g.getScore()[j*2]>g.getScore()[j*2+1]){
                     w++;
                  }
               }
               Player[] winner;
               if (w>set/2){
                  winner = g.getTeam1();
               }
               else{
                  winner = g.getTeam2();
               }
               for (Player p: winner){
                  s = s + "-" + p.getName();
               }
            }
            s = s + "</td></tr>";
            s = s + "<tr><td>";
            if (g.getTeam2() != null){
               for (Player p: g.getTeam2()){
                  s = s + "-" + p.getName();
               }
            }
            else{
               s = s + "*waiting*";
            }
            s = s + "</td><td>|</td></tr>";
            s = s + "<tr><td><br><br></td></tr>";
         }
         s = s + "</table></body></html>";
         return s;
      }
      int maxRound = (int)(Math.log(getSize())/Math.log(2));
      for (int i=1; i < getSize(); i++){
         for(int j=0; j<maxRound; j++){
            if ((i/Math.pow(2,j))%2 != 0){
               g = getGame((int)Math.pow(2, maxRound-1-j)+(i/(int)Math.pow(2,j+1)));
               s = s + "<tr>";
               for (int k = 0; k < maxRound; k++){
                  if (j==k){
                     s = s + "<td>";
                     if (g.getTeam1() != null){
                        for (Player p: g.getTeam1()){
                           s = s + "-" + p.getName();
                        }
                     }
                     else{
                        s = s + "*waiting*";
                     }
                     s = s + "</td><td>|</td>";
                  }
                  else if ((i-(int)Math.pow(2, k)>=0)&&(((i-(int)Math.pow(2, k))/(int)Math.pow(2, k+1))%2 == 0 && k<maxRound-1)){
                     s = s + "<td></td><td>|</td>";
                  }
                  else{
                     s = s + "<td></td><td></td>";
                  }
               }
               s = s + "<td>";
               if (i-2 == getSize()/2){
                  Game g0 = getGame(0);
                  if (g0.getTeam1() != null){
                     for (Player p: g0.getTeam1()){
                        s = s + "-" + p.getName();
                     }
                  }
                  else{
                     s = s + "*waiting*";
                  }
                  s = s + "</td><td>|</td>";
               }
               else{
                  s = s + "</tr>";
               }
               s = s + "<tr>";
               for (int k = 0; k < maxRound; k++){
                  if (j==k){
                     s = s + "<td>";
                     if (g.getTeam2() != null){
                        for (Player p: g.getTeam2()){
                           s = s + "-" + p.getName();
                        }
                     }
                     else{
                        s = s + "*waiting*";
                     }
                     s = s + "</td><td>|</td>";
                  }
                  else if ((i-(int)Math.pow(2, k)>=0) && (((i-(int)Math.pow(2, k))/(int)Math.pow(2, k+1))%2 == 0) && k<maxRound-1){
                     s = s + "<td></td><td>|</td>";
                  }
                  else{
                     s = s + "<td></td><td></td>";
                  }
               }
               s = s + "<td>";
               if (i-2 == getSize()/2){
                  Game g0 = getGame(0);
                  if (g0.getTeam2() != null){
                     for (Player p: g0.getTeam2()){
                        s = s + "-" + p.getName();
                     }
                  }
                  else{
                     s = s + "*waiting*";
                  }
                  s = s + "</td><td>|</td>";
               }
               else{
                  s = s + "</td><td></td>";
               }
               s = s + "</tr>";
               break;
            }
         }
      }

      if (consolation){
         s = s + "<tr><td><br><br><br>**************</td></tr><tr><td>Division B</td></tr><tr><td>**************<br></td></tr>";
         maxRound = maxRound-1;
         for (int i=1; i < getSize()/2; i++){
            for(int j=0; j<maxRound; j++){
               if ((i/Math.pow(2,j))%2 != 0){
                  g = getGame((int)Math.pow(2, maxRound-1-j)+(i/(int)Math.pow(2,j+1))+getSize());
                  s = s + "<tr>";
                  for (int k = 0; k < maxRound; k++){
                     if (j==k){
                        s = s + "<td>";
                        if (g.getTeam1() != null){
                           for (Player p: g.getTeam1()){
                              s = s + "-" + p.getName();
                           }
                        }
                        else{
                           s = s + "*waiting*";
                        }
                        s = s + "</td><td>|</td>";
                     }
                     else if ((i-(int)Math.pow(2, k)>=0)&&(((i-(int)Math.pow(2, k))/(int)Math.pow(2, k+1))%2 == 0 && k<maxRound-1)){
                        s = s + "<td></td><td>|</td>";
                     }
                     else{
                        s = s + "<td></td><td></td>";
                     }
                  }
                  s = s + "<td>";
                  if (i-2 == getSize()/4){
                     Game g0 = getGame(getSize());
                     if (g0.getTeam1() != null){
                        for (Player p: g0.getTeam1()){
                           s = s + "-" + p.getName();
                        }
                     }
                     else{
                        s = s + "*waiting*";
                     }
                     s = s + "</td><td>|</td>";
                  }
                  else{
                     s = s + "</tr>";
                  }
                  s = s + "<tr>";
                  for (int k = 0; k < maxRound; k++){
                     if (j==k){
                        s = s + "<td>";
                        if (g.getTeam2() != null){
                           for (Player p: g.getTeam2()){
                              s = s + "-" + p.getName();
                           }
                        }
                        else{
                           s = s + "*waiting*";
                        }
                        s = s + "</td><td>|</td>";
                     }
                     else if ((i-(int)Math.pow(2, k)>=0) && (((i-(int)Math.pow(2, k))/(int)Math.pow(2, k+1))%2 == 0) && k<maxRound-1){
                        s = s + "<td></td><td>|</td>";
                     }
                     else{
                        s = s + "<td></td><td></td>";
                     }
                  }
                  s = s + "<td>";
                  if (i-2 == getSize()/4){
                     Game g0 = getGame(getSize());
                     if (g0.getTeam2() != null){
                        for (Player p: g0.getTeam2()){
                           s = s + "-" + p.getName();
                        }
                     }
                     else{
                        s = s + "*waiting*";
                     }
                     s = s + "</td><td>|</td>";
                  }
                  else{
                     s = s + "</td><td></td>";
                  }
                  s = s + "</tr>";
                  break;
               }
            }
         }
      }
      s = s + "</table></body></html>";
      return s;
   }
}