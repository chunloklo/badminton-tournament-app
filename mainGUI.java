import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.io.*;

public class mainGUI{

   private static Tournament t;

   public static void main (String[] args) throws IOException{

      t = new Tournament();
      t.clearBye();

      JFrame frame = new JFrame("GT 2016 Spring Badminton Tournament");
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

      int cursor = 0;
      int buffer = 75;
      int boundY = 100;
      int boundX = 500;
      int gap = 50;
      cursor += buffer;

      JPanel gamePanel = new JPanel();
      gamePanel.setPreferredSize(new Dimension(700,1000));
      gamePanel.setLayout(null);

      JButton regButton = new JButton("Register");
      regButton.setBounds(100,cursor,boundX,boundY);
      regButton.setFont(new Font("Arial", Font.PLAIN, 40));
      regButton.addActionListener(new regListener());
      gamePanel.add(regButton);

      cursor += (boundY + gap);

      JButton lunchButton = new JButton("Have Lunch");
      lunchButton.setBounds(100,cursor,boundX,boundY);
      lunchButton.setFont(new Font("Arial", Font.PLAIN, 40));
      lunchButton.addActionListener(new lunchListener());
      gamePanel.add(lunchButton);

      cursor += (boundY + gap);

      JButton nextButton = new JButton("Search for Games");
      nextButton.setBounds(100,cursor,boundX,boundY);
      nextButton.setFont(new Font("Arial", Font.PLAIN, 40));
      nextButton.addActionListener(new nextListener());
      gamePanel.add(nextButton);

      cursor += (boundY + gap);

      JButton startButton = new JButton("Start a Game");
      startButton.setBounds(100,cursor,boundX,boundY);
      startButton.setFont(new Font("Arial", Font.PLAIN, 40));
      startButton.addActionListener(new startListener());
      gamePanel.add(startButton);

      cursor += (boundY + gap);

      JButton endButton = new JButton("End a Game");
      endButton.setBounds(100,cursor,boundX,boundY);
      endButton.setFont(new Font("Arial", Font.PLAIN, 40));
      endButton.addActionListener(new endListener());
      gamePanel.add(endButton);

      cursor += (boundY + gap);

      // JButton updateButton = new JButton("Update Output");
      // updateButton.setBounds(150,1100,700,150);
      // updateButton.setFont(new Font("Arial", Font.PLAIN, 40));
      // updateButton.addActionListener(new updateListener());
      // gamePanel.add(updateButton);

      JButton updateButton = new JButton("Update Output");
      updateButton.setBounds(100,cursor,boundX,boundY);
      updateButton.setFont(new Font("Arial", Font.PLAIN, 40));
      updateButton.addActionListener(new updateListener());
      gamePanel.add(updateButton);

      frame.getContentPane().add(gamePanel);
      frame.pack();
      frame.setVisible(true);
   }

   //added code


   //Reg Frame
   private static class regListener implements ActionListener{

      private static JComboBox regPBox;
      private static JLabel regPLabel;

      public void actionPerformed (ActionEvent event){

         JFrame regFrame = new JFrame("Register");
         regFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         JPanel regPanel = new JPanel();
         regPanel.setPreferredSize(new Dimension(1050,275));
         regPanel.setLayout(null);

         this.regPBox = new JComboBox(t.getNameArray());
         this.regPBox.setBounds(75,50,600,100);
         this.regPBox.setFont(new Font("Arial", Font.PLAIN, 40));
         regPanel.add(regPBox);

         JButton regPButton = new JButton("Register");
         regPButton.setBounds(725,50,250,100);
         regPButton.setFont(new Font("Arial", Font.PLAIN, 40));
         regPButton.addActionListener(new regPListener());
         regPanel.add(regPButton);

         this.regPLabel = new JLabel("", SwingConstants.CENTER);
         this.regPLabel.setBounds(15,175,1000,50);
         this.regPLabel.setFont(new Font("Arial", Font.PLAIN, 40));
         regPanel.add(this.regPLabel);

         regFrame.getContentPane().add(regPanel);
         regFrame.pack();
         regFrame.setVisible(true);
      }

      private static class regPListener implements ActionListener{
         public void actionPerformed (ActionEvent event){
            String player = (String) regPBox.getSelectedItem();
            String s = t.register(player);
            if (s.length() == 0){
               regPLabel.setText(player + " has been registered successfully!");
            }
            else{
               regPLabel.setText(s);
            }
         }
      }
   }

   //Lunch Frame
   private static class lunchListener implements ActionListener{

      private static JComboBox lunchPBox;
      private static JLabel lunchPLabel;

      public void actionPerformed (ActionEvent event){

         JFrame lunchFrame = new JFrame("Lunch");
         lunchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         JPanel lunchPanel = new JPanel();
         lunchPanel.setPreferredSize(new Dimension(1050,275));
         lunchPanel.setLayout(null);

         this.lunchPBox = new JComboBox(t.getNameArray());
         this.lunchPBox.setBounds(75,50,600,100);
         this.lunchPBox.setFont(new Font("Arial", Font.PLAIN, 40));
         lunchPanel.add(lunchPBox);

         JButton lunchPButton = new JButton("Have Lunch");
         lunchPButton.setBounds(725,50,250,100);
         lunchPButton.setFont(new Font("Arial", Font.PLAIN, 40));
         lunchPButton.addActionListener(new lunchPListener());
         lunchPanel.add(lunchPButton);

         this.lunchPLabel = new JLabel("", SwingConstants.CENTER);
         this.lunchPLabel.setBounds(15,175,1000,50);
         this.lunchPLabel.setFont(new Font("Arial", Font.PLAIN, 40));
         lunchPanel.add(this.lunchPLabel);

         lunchFrame.getContentPane().add(lunchPanel);
         lunchFrame.pack();
         lunchFrame.setVisible(true);
      }

      private static class lunchPListener implements ActionListener{
         public void actionPerformed (ActionEvent event){
            String player = (String) lunchPBox.getSelectedItem();
            String s = t.haveLunch(player, false);
            if (s.length() == 0){
               lunchPLabel.setText(player + " is going to enjoy the lunch!");
            }
            else{
               lunchPLabel.setText(s);
            }
         }
      }
   }

   //Next Frame
   private static class nextListener implements ActionListener{
      public void actionPerformed (ActionEvent event){

         t.setNextGames();

         JFrame nextFrame = new JFrame("Next Games");
         nextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         JPanel nextPanel = new JPanel();
         nextPanel.setPreferredSize(new Dimension(1300,900));
         nextPanel.setLayout(null);

         JLabel nextLabel = new JLabel("", SwingConstants.CENTER);
         nextLabel.setBounds(-50,-50,1300,900);
         nextLabel.setFont(new Font("Arial", Font.PLAIN, 20));
         nextPanel.add(nextLabel);

         String s = "<html><tabel><tr><td></td><td></td><td></td><td></td><td>Next Games</td><td></td><td></td><td></td><td></td></tr>";
         s = s + "<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
         s = s + "<tr><td></td><td>Game</td><td>Round</td><td>Team 1</td><td>Team 2</td><td></td><td>W next</td><td>L next</td><td>Value</td><td></td></tr>";
         Game g;
         int counter = 0;
         while ((g = t.getNextGames().poll()) != null && counter < 10){
            s = s + "<tr><td></td>" + g.toString()+ "</tr>";
            counter++;
         }
         s = s + "<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
         s = s + "<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
         s = s + "<tr><td></td><td></td><td></td><td></td><td>Current Games</td><td></td><td></td><td></td><td></td></tr>";
         s = s + "<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
         s = s + "<tr><td></td><td>Game</td><td>Round</td><td>Team 1</td><td>Team 2</td><td></td><td>W next</td><td>L next</td><td>Value</td><td></td></tr>";
         for (int i = 1; i<7; i++){
            s = s + "<tr><td>" + Integer.toString(i) + "</td>";
            g = t.getCurrGames().get(i);
            if (g == null){
               s = s + "<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>";
            }
            else{
               s = s + g.toString() + "</tr>";
            }
         }

         s = s + "</tabel></html>";
         nextLabel.setText(s);

         nextFrame.getContentPane().add(nextPanel);
         nextFrame.pack();
         nextFrame.setVisible(true);
      }
   }

   //Start Frame
   private static class startListener implements ActionListener{

      private static JTextField idTF;
      private static JComboBox eventBox, courtBox;
      private static JLabel startPLabel;

      public void actionPerformed (ActionEvent event){

         JFrame startFrame = new JFrame("Start a Game");
         startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         JPanel startPanel = new JPanel();
         startPanel.setPreferredSize(new Dimension(850,400));
         startPanel.setLayout(null);

         JLabel eventLB = new JLabel("Event:");
         eventLB.setBounds(100,50,150,100);
         eventLB.setFont(new Font("Arial", Font.PLAIN, 40));
         startPanel.add(eventLB);

         String[] eventList = {"MS","WS","MD","WD","XD"};
         this.eventBox = new JComboBox(eventList);
         this.eventBox.setBounds(270,50,150,100);
         this.eventBox.setFont(new Font("Arial", Font.PLAIN, 40));
         startPanel.add(eventBox);

         JLabel idLB = new JLabel("ID:");
         idLB.setBounds(500,50,100,100);
         idLB.setFont(new Font("Arial", Font.PLAIN, 40));
         startPanel.add(idLB);

         this.idTF = new JTextField(2);
         this.idTF.setBounds(600,50,150,100);
         this.idTF.setFont(new Font("Arial", Font.PLAIN, 40));
         startPanel.add(idTF);

         JLabel courtLB = new JLabel("Court:");
         courtLB.setBounds(100,175,150,100);
         courtLB.setFont(new Font("Arial", Font.PLAIN, 40));
         startPanel.add(courtLB);

         String[] courtList = {"1","2","3","4","5","6","7"};
         this.courtBox = new JComboBox(courtList);
         this.courtBox.setBounds(270,175,150,100);
         this.courtBox.setFont(new Font("Arial", Font.PLAIN, 40));
         startPanel.add(courtBox);

         JButton startPButton = new JButton("Start");
         startPButton.setBounds(500,175,250,100);
         startPButton.setFont(new Font("Arial", Font.PLAIN, 40));
         startPButton.addActionListener(new startPListener());
         startPanel.add(startPButton);

         this.startPLabel = new JLabel("", SwingConstants.CENTER);
         this.startPLabel.setBounds(15,300,825,50);
         this.startPLabel.setFont(new Font("Arial", Font.PLAIN, 40));
         startPanel.add(this.startPLabel);

         startFrame.getContentPane().add(startPanel);
         startFrame.pack();
         startFrame.setVisible(true);
      }

      private static class startPListener implements ActionListener{
         public void actionPerformed (ActionEvent event){
            String eventS = (String) eventBox.getSelectedItem();
            int id;
            try{
               id = Integer.parseInt(idTF.getText());
            }
            catch(NumberFormatException ex){
               startPLabel.setText("Sorry! Can't find this game in the system.");
               return;
            }
            int court = Integer.parseInt((String)courtBox.getSelectedItem());
            String s = t.startGame(eventS, id, court);
            if (s.length() == 0){
               startPLabel.setText(eventS + " " + Integer.toString(id) + " will be played on court " + Integer.toString(court));
            }
            else{
               startPLabel.setText(s);
            }
         }
      }
   }

   //End Frame
   private static class endListener implements ActionListener{

      private static JTextField[] scoreTF;
      private static JComboBox courtBox;
      private static JLabel endPLabel;

      public void actionPerformed (ActionEvent event){

         JFrame endFrame = new JFrame("End a Game");
         endFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         JPanel endPanel = new JPanel();
         endPanel.setPreferredSize(new Dimension(850,400));
         endPanel.setLayout(null);

         JLabel scoreLB = new JLabel("Score:");
         scoreLB.setBounds(50,50,200,100);
         scoreLB.setFont(new Font("Arial", Font.PLAIN, 40));
         endPanel.add(scoreLB);

         JLabel[] colons = new JLabel[3];
         for (int i=0; i<3; i++){
            colons[i] = new JLabel(":");
            colons[i].setBounds(285+i*210,50,20,100);
            colons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            endPanel.add(colons[i]);
         }

         this.scoreTF = new JTextField[6];
         for (int i=0; i<6; i++){
            this.scoreTF[i] = new JTextField(2);
            this.scoreTF[i].setBounds(195+i*105, 50, 80, 100);
            this.scoreTF[i].setFont(new Font("Arial", Font.PLAIN, 40));
            endPanel.add(scoreTF[i]);
         }

         JLabel courtLB = new JLabel("Court:");
         courtLB.setBounds(100,175,150,100);
         courtLB.setFont(new Font("Arial", Font.PLAIN, 40));
         endPanel.add(courtLB);

         String[] courtList = {"1","2","3","4","5","6","7"};
         this.courtBox = new JComboBox(courtList);
         this.courtBox.setBounds(270,175,150,100);
         this.courtBox.setFont(new Font("Arial", Font.PLAIN, 40));
         endPanel.add(courtBox);

         JButton endPButton = new JButton("End");
         endPButton.setBounds(500,175,250,100);
         endPButton.setFont(new Font("Arial", Font.PLAIN, 40));
         endPButton.addActionListener(new endPListener());
         endPanel.add(endPButton);

         this.endPLabel = new JLabel("", SwingConstants.CENTER);
         this.endPLabel.setBounds(15,300,825,50);
         this.endPLabel.setFont(new Font("Arial", Font.PLAIN, 40));
         endPanel.add(this.endPLabel);

         endFrame.getContentPane().add(endPanel);
         endFrame.pack();
         endFrame.setVisible(true);
      }

      private static class endPListener implements ActionListener{
         public void actionPerformed (ActionEvent event){

            int court = Integer.parseInt((String)courtBox.getSelectedItem());

            int score;
            ArrayList<Integer> scoreList = new ArrayList<Integer>(6);
            for (int i=0; i<6; i++){
               String s = scoreTF[i].getText();
               if (s.equals("")){
                  break;
               }
               try{
                  score = Integer.parseInt(s);
               }
               catch(NumberFormatException ex){
                  endPLabel.setText("Sorry! Can't read the score.");
                  return;
               }
               scoreList.add(score);
            }
            if (scoreList.size()%2 != 0){
               endPLabel.setText("Sorry! Can't read the score.");
               return;
            }
            int[] scores = new int[scoreList.size()];
            for (int i=0; i<scoreList.size(); i++){
               scores[i] = (int)scoreList.get(i);
            }

            String s = t.endGame(court, scores);
            if (s.length() == 0){
               endPLabel.setText("The game on court " + Integer.toString(court) + " is finished.");
            }
            else{
               endPLabel.setText(s);
            }
         }
      }
   }

   //Update
   private static class updateListener implements ActionListener{
      public void actionPerformed (ActionEvent event){
         t.updateOutput();
      }
   }

}