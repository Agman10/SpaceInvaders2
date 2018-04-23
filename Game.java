import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Game extends JPanel{
    JFrame frame;
    static Invader[] invaders = new Invader[50];
    static final int WIDTH = 210;
    static final int HEIGHT = 230;
    static final int SCALE = 3;
    static BufferedImage invader;
    public static Set<Integer> keysDown = new HashSet<>();





    static int invaderSpacing = 16;
    static int invadersPerRow = 10;
    static int invadersWidth = invaderSpacing * invadersPerRow;
    static boolean movingLeft = true;
    static int invadersX = (WIDTH / 2) - ((invadersPerRow * invaderSpacing) / 2);
    static int invadersY = 0;

    static int margin = 10;
    static int minX = margin ;
    static int maxX = (WIDTH - margin);


    public static void main(String[] args) throws IOException {
        invader = ImageIO.read(new File("textures/invader.png"));
        populateInvaders();
        Game game = new Game();

    }

    public static void populateInvaders(){
            //int invaderSpacing = (invader.getWidth() + 4) * SCALE;
        for(int i = 0; i < invaders.length; i++) {


                int x = (i  % invadersPerRow);
                int y = ((i - x) / invadersPerRow);

                invaders[i] = new Invader((x * invaderSpacing), y * invaderSpacing);

                System.out.println("New invaders: " + x * invaderSpacing + " - " + y * invaderSpacing);
            }
    }

    double invaderScale = .5;

    public Game() throws IOException {
        this.frame = new JFrame("space invaders");
        this.frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
        this.frame.add(this);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.decode("#111111"));
        //this.frame.setIconImage(img.getImage());
        this.addKeyListener(new Listener());
        this.setFocusable(true);
        this.frame.setVisible(true);
        //this.frame.setLayout(new BorderLayout());
        //this.frame.pack();
        // Import test

    }

    int lastUpdate = 0;

    @Override
    public void paintComponent(Graphics g) {

        if(g == null){
            System.out.println("Null");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            return;
        }



        /* Clear background */
        g.setColor(Color.decode("#111111"));
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
        lastUpdate++;
        if(lastUpdate > 2){
            int xSpeed = 1;
            int ySpeed = 5;

            lastUpdate = 0;
            if(movingLeft) {
                invadersX-=xSpeed;
            } else {
                invadersX+=xSpeed;
            }
            System.out.println(invadersX + " : " + (maxX));
            if(invadersX > maxX - invadersWidth){
                movingLeft = true;
                invadersY+=ySpeed;
            }
            if(invadersX < minX){
                movingLeft = false;
                invadersY+=ySpeed;
            }

        }




        for(int i = 0; i < invaders.length; i++){
            if(invaders[i].alive) drawImage(invader, invaders[i].x + invadersX, invaders[i].y + invadersY, g);
        }


        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        repaint();
    }

    public void drawImage(BufferedImage img, int x, int y, Graphics g){
        try{
            g.drawImage(img, x * SCALE, y * SCALE, img.getWidth() * SCALE, img.getHeight() * SCALE, this);
        } catch(NullPointerException e){
            System.out.println("Problem drawing image. " + e.getMessage());
        }

    }

    public class Listener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if(!keysDown.contains(e.getKeyCode())) keysDown.add(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keysDown.remove(e.getKeyCode());
        }
    }

}
