

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;



public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int bwidth = 360;
    int bheight = 640;


Image flappyimg;
Image flappybirdbg;
Image toppipe;
Image bottompipe;

int birdx = bwidth/8;
int birdy = bheight/2;
int birdwidth = 34;
int birdheight = 24;

class Bird{
    int x = birdx;
    int y = birdy;
    int width = birdwidth;
    int height = birdheight;
    Image image;
    Bird(Image img){
        this.image = img;
    }

}
//pipes
int pipex = bwidth;
int pipey = 0;
int pipewidth = 64;
int pipeheight = 512;

class Pipe{
    int x = pipex;
    int y = pipey;
    int width = pipewidth;
    int height = pipeheight;
    Image image;
    boolean passed = false;
    
    Pipe(Image img){
        this.image = img;
    }
}


//logic
Bird bird;
Timer gameloopp;
int velocitypipe = -4;
int velocityY = -6;
int gravity =1;

ArrayList<Pipe> pipes ;
Random random = new Random();
Timer placepipesTimer;
double score=0 ;
boolean gameOver = false;







    FlappyBird(){
        setPreferredSize(new Dimension(bwidth,bheight));

        setFocusable(true);
        addKeyListener(this);

        flappyimg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        flappybirdbg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        toppipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipe = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage(); 
        
        
        bird = new Bird(flappyimg);



        pipes = new ArrayList<>();
        placepipesTimer = new Timer(1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                placepipes();
            }
        });
        placepipesTimer.start();
        gameloopp = new Timer(1000/60,this);
        gameloopp.start();

    }
    public void placepipes(){
        int randomval = (int) (pipey - pipeheight/4 - Math.random()*(pipeheight/2));
        int openspace = bheight /4;

        Pipe topPipe = new Pipe(toppipe);
        topPipe.y = randomval;

        pipes.add(topPipe);
        Pipe bottomPipe = new Pipe(bottompipe);
        bottomPipe.y = topPipe.y + topPipe.height +openspace;
        pipes.add(bottomPipe);

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
    g.drawImage(flappybirdbg, 0, 0, bwidth, bheight,null);
    g.drawImage(flappyimg, bird.x, bird.y, bird.width, bird.height, null);
    for(int i =0 ; i< pipes.size() ; i++){
        Pipe pipe = pipes.get(i);
        g.drawImage(pipe.image, pipe.x, pipe.y, pipe.width, pipe.height, null);


    }
    g.setColor(Color.white);
    g.setFont(new Font("Arial", Font.BOLD ,32));
    if(gameOver){
        g.drawString("Game Over  "+ String.valueOf((int)score),10,35);
    }
    else{
        g.drawString("Score: " + String.valueOf((int)score),10,30);
    }


}

public void move(){
    velocityY += gravity;
    bird.y += velocityY;
    bird.y = Math.max(bird.y,0);

    //pipes
    for(int i =0 ; i< pipes.size() ; i++){
        Pipe pipe = pipes.get(i);
        pipe.x += velocitypipe;

    
    if(!pipe.passed && bird.x > pipe.x + pipe.width){
        score += 0.5;
        pipe.passed = true;

    }
    if(collision(bird,pipe)){
        gameOver = true;
    }
if(bird.y > bheight){
    gameOver = true;
}
    }
}
boolean collision(Bird a, Pipe b) {
    return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
           a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
           a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
           a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
}
@Override
public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            placepipesTimer.stop();
            gameloopp.stop();

        }
    }

@Override
public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_SPACE){
        velocityY = -9;

        if(gameOver){
            bird.y = birdy;
            velocityY =0;
            pipes.clear();
            score = 0;
            gameOver = false;
            placepipesTimer.start();
            gameloopp.start();
        }
    }

}
@Override
public void keyTyped(KeyEvent e) {
}
@Override
public void keyReleased(KeyEvent e) {
    
}
}
