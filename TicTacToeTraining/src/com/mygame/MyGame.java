package com.mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {
    JLabel heading;
    JLabel clockLabel;
    Font font=new Font("",Font.BOLD,40);

    JPanel mainPanel;


    JButton[] btns=new JButton[9];

    int wps[][]={{0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
             };
    int winner=2;

    boolean gameOver=false;
    int gameChances[]={2,2,2,2,2,2,2,2,2};
    int activePlayer=0;
    MyGame(){
        System.out.println("Creating instance of game");
        setTitle("My Tic Tac Toe Game...");
        setSize(850,850);
        ImageIcon icon=new ImageIcon("src/img/Game.jpg");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }
    private void createGUI(){
        this.setLayout(new BorderLayout());

        this.getContentPane().setBackground(Color.decode("#2196f3"));
//        North heading...
        heading=new JLabel("Tic Tac Toe");
//        heading.setIcon(new ImageIcon("src/img/Game.jpg"));
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.WHITE);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);

        heading.setForeground(Color.WHITE);
        this.add(heading,BorderLayout.NORTH);

//        Creating object of JLabel for clock
        clockLabel=new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.WHITE);
        this.add(clockLabel,BorderLayout.SOUTH);

        Thread t=new Thread(){
        public void run() {
                try{
                    while (true) {
                        String datetime=new Date().toLocaleString();

                        clockLabel.setText(datetime);

                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();

//        Panel Section...
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));

        for(int i=1;i<=9;i++){
            JButton btn = new JButton();

            // Load image
            ImageIcon i1 = new ImageIcon("src/img/zero.png");

            // Scale the image to 40x40 (you can change size as needed)
            Image img = i1.getImage();
            Image newImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(newImg);

            // Set icon
//            btn.setIcon(scaledIcon);
            btn.setBackground(Color.decode("#90caf9"));
            btn.setFont(font);

            mainPanel.add(btn);
            btns[i-1]=btn;
            btn.addActionListener(this);
            btn.setName((i-1)+"");
        }

        this.add(mainPanel,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton=(JButton) e.getSource();
        String nameStr=currentButton.getName();
        int name=Integer.parseInt(nameStr.trim());


        if(gameOver){
            JOptionPane.showMessageDialog(this,"Game Already Over...");
            return;
        }

        if(gameChances[name]==2){
            if(activePlayer==1){
                ImageIcon i1=new ImageIcon("src/img/One.png");
                Image img = i1.getImage();
                Image newImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(newImg);
                currentButton.setIcon(scaledIcon);

                gameChances[name]=activePlayer;
                activePlayer=0;

            }else{
                ImageIcon i1=new ImageIcon("src/img/Zero.png");
                Image img=i1.getImage();
                Image newImg=img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
                ImageIcon scaledIcon=new ImageIcon(newImg);
                currentButton.setIcon(scaledIcon);
                gameChances[name]=activePlayer;
                activePlayer=1;
            }
//            find the winner
            for(int []temp:wps){
                if((gameChances[temp[0]]==gameChances[temp[1]]) && (gameChances[temp[1]]==gameChances[temp[2]]) &&
                        (gameChances[temp[2]]!=2)){
                    winner=gameChances[temp[0]];
                    gameOver=true;

                    JOptionPane.showMessageDialog(null,"Player "+winner+" has won the Game..");
                    int i=JOptionPane.showConfirmDialog(this,"do you want to play more ?");
                    if(i==0){
                        this.setVisible(false);
                        new MyGame();
                    }
                    else if(i==1){
                        System.exit(22);
                        break;
                    }
                    else{

                    }
                    System.out.println(i);
                    break;
                }
            }

//            Draw Logic
            int c=0;

            for(int x:gameChances){
                if(x==2){
                    c++;
                    break;
                }
            }

            if(c==0 && gameOver==false){
                JOptionPane.showMessageDialog(null,"Its Draw...");

                int i=JOptionPane.showConfirmDialog(this,"Play More");
                if(i==0){
                    this.setVisible(false);
                    new MyGame();
                }
                else if(i==1){
                    System.exit(1212);
                }
                else{

                }
                gameOver=true;
            }

        }
        else{
            JOptionPane.showMessageDialog(this,"Position already occupied...");
        }
    }
}
