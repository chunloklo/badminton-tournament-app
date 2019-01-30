import java.time.LocalTime;

public class Player{

   private String name, sex;
   private Game nextS, nextD, nextXD;
   private int RRS, RRD, RRXD; 
   private boolean registered, isPlaying, lunch;
   private LocalTime breakTime;

   public Player(String name, String sex){
      this.name = name;
      this.sex = sex;
      this.RRS = 0;
      this.RRD = 0; 
      this.RRXD = 0;
      this.registered = false;
      this.isPlaying = false;
      this.lunch = false;
      this.breakTime = LocalTime.now();
   }

   public String getName(){
      return name;
   }

   public boolean getReg(){
      return registered;
   }

   public void register(){
      this.registered = true;
   }

   public boolean getPlay(){
      return isPlaying;
   }

   public void setIsPlaying(boolean isPlaying){
      this.isPlaying = isPlaying;
   }

   public boolean getLunch(){
      return lunch;
   }

   public void haveLunch(boolean reset){
      this.lunch = true;
      if (!reset){
         this.takeBreak(45);
      }
   }

   public void takeBreak(int minutes){
      this.breakTime = LocalTime.now().plusMinutes(minutes);
   }

   public boolean isBreak(){
      return this.breakTime.isAfter(LocalTime.now());
   }
   
   public int getRRS(){
      return this.RRS;
   }
   
   public int getRRD(){
      return this.RRD;
   }
   
      public int getRRXD(){
      return this.RRXD;
   }
   
   public void setRRS(int gameRemain){
      if (gameRemain == -1){
         this.RRS--;
      }
      else{
         this.RRS = gameRemain;
      }
   }
   
   public void setRRD(int gameRemain){
      if (gameRemain == -1){
         this.RRD--;
      }
      else{
         this.RRD = gameRemain;
      }
   }

   public void setRRXD(int gameRemain){
      if (gameRemain == -1){
         this.RRXD--;
      }
      else{
         this.RRXD = gameRemain;
      }
   }
   
   public Game getS(){
      return nextS;
   }

   public void setS(Game game){
      this.nextS = game;
   }

   public Game getD(){
      return nextD;
   }

   public void setD(Game game){
      this.nextD = game;
   }

   public Game getXD(){
      return nextXD;
   }

   public void setXD(Game game){
      this.nextXD = game;
   }

   public int remainS(){
      if (nextS == null){
         return RRS;
      }
      else{
         return nextS.getRound()+1;
      }
   }

   public int remainD(){
      if (nextD == null){
         return RRD;
      }
      else{
         return nextD.getRound()+1;
      }
   }

   public int remainXD(){
      if (nextXD == null){
         return RRXD;
      }
      else{
         return nextXD.getRound()+1;
      }
   }

   public void startGame(){
      this.isPlaying = true;
   }

   //public void endGame(){
   //   this.isPlaying = false;
   //   this.takeBreak(15);
   //}

   public String toString(){
      String s = "<td>" + name + "</td>";
      s = s + "<td align=\"center\">" + sex  + "</td>";
      s = s + "<td align=\"center\">" + Boolean.toString(registered) + "</td>";
      s = s + "<td>" + breakTime.toString() + "</td>";
      s = s + "<td align=\"center\">" + Boolean.toString(isPlaying) + "</td>";
      s = s + "<td align=\"center\">" + Boolean.toString(lunch) + "</td>";
      if (nextS == null){
         s = s + "<td align=\"center\">null</td>";
      }
      else{
         s = s + "<td align=\"center\">" + Integer.toString(nextS.getId()) + "</td>";
      }
      if (nextD == null){
         s = s + "<td align=\"center\">null</td>";
      }
      else{
         s = s + "<td align=\"center\">" + Integer.toString(nextD.getId()) + "</td>";
      }
      if (nextXD == null){
         s = s + "<td align=\"center\">null</td>";
      }
      else{
         s = s + "<td align=\"center\">" + Integer.toString(nextXD.getId()) + "</td>";
      }
      s = s + "</tr>";
      return s;
   }
}