import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

public class MainGame {
    public static int[][] map;
    public static int[][]walLines;

    public static JFrame window = new JFrame("Survival");
    public static World world = new World();
    public static JFrame menuFrame2, menuFrame3;

    //Create player object
    public static Player player = new Player("MT",64,138,-1,0);

    //the 2d raycaster version of camera plane
    public static double planeX = 0, planeY = 0.66,moveSpeed = 0.15,rotSpeed = 0.05;

    public static double remainTime = 60 * 5 , plantRemainTime = 0,spawnTime = 0;

    public static ArrayList<Organism> organisms = new ArrayList<Organism>();
    //Matthew did this
    //Jim did this

    //Declare Variables
    public static JButton startButton;
    public static JPanel contentPane;
    public static JFrame menuFrame;
    public static String playerName;
    public static JTextField name;

    public static boolean run = false;

    //Below starts the methods (screens)
    //Main method
    public static void main(String[] args) throws FileNotFoundException {
        menuFrame = new JFrame("Survival");

        //Set Default Font
        setUIFont(new javax.swing.plaf.FontUIResource("Courier",Font.CENTER_BASELINE,15));

        //Set up the frame work
        contentPane = new JPanel(new GridLayout(2,2));

        //Create item
        startButton = new JButton("Start");
        startButton.setFont(new Font("Courier", Font.CENTER_BASELINE, 25));
        startButton.addActionListener(new startListener());
        startButton.setBackground(Color.yellow);
        JLabel filler = new JLabel("                        ");
        filler.setFont(new Font(("Courier"), Font.BOLD, 80));
        JPanel startButtonPanel = new JPanel(new FlowLayout());
        startButtonPanel.add(filler);
        startButtonPanel.add(startButton);
        startButtonPanel.setBackground(Color.red);
        startButton.setBounds(1, 2, 20, 10);
        JPanel settingButtonPanel = new JPanel(new FlowLayout());
        JLabel instruction = new JLabel("THE BACKSTORY:");
        instruction.setFont(new Font("Courier", Font.BOLD, 16));
        settingButtonPanel.add(instruction);
        settingButtonPanel.add(new JLabel("Jan. 19th, 2038. 03:14:07"));
        settingButtonPanel.add(new JLabel("You are summoned to the world."));
        settingButtonPanel.add(new JLabel("Walkers are everywhere."));
        settingButtonPanel.add(new JLabel("Find an exit for us to"));
        settingButtonPanel.add(new JLabel("bring peace to humanity."));
        settingButtonPanel.add(new JLabel("Eat the Fruit of Strength"));
        settingButtonPanel.add(new JLabel("and kill the Walkers on your way."));
        settingButtonPanel.add(new JLabel("We've great expectations for you."));
        settingButtonPanel.add(new JLabel("Good luck!"));
        settingButtonPanel.setBackground(Color.yellow);
        JLabel title = new JLabel("  SURVIVAL");
        title.setFont(new Font("Courier", Font.BOLD, 40));

        //Add items to JPanel
        contentPane.add(title);
        contentPane.add(startButtonPanel);
        contentPane.add(settingButtonPanel);
        contentPane.add(new JLabel(new ImageIcon("backGround.png")));

        //Final settings
        ImageIcon icon = new ImageIcon("gameIcon.png");
        menuFrame.setIconImage(icon.getImage());
        menuFrame.setSize(600,600);
        menuFrame.setVisible(true);
        menuFrame.setContentPane(contentPane);
        menuFrame.getContentPane().setBackground(Color.cyan);
        //menuFrame.setVisible(true);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //********************/

        menuFrame2 = new JFrame("Survival");

        //Set up the frame work
        JPanel contentPane2 = new JPanel(new GridLayout(3, 1));

        //Create items
        JButton startButton2 = new JButton("BEGIN");
        startButton2.setBackground(Color.CYAN);
        startButton2.setFont(new Font("Courier", Font.CENTER_BASELINE, 16));
        startButton2.addActionListener(new gameListener());
        JLabel title2 = new JLabel("Insert your name to begin: ");
        title2.setFont(new Font("Courier", Font.CENTER_BASELINE, 12));
        name = new JTextField(100);

        //Add items to JPanel
        contentPane2.add(title2);
        contentPane2.add(name);
        contentPane2.add(startButton2);

        //Final settings
        menuFrame2.setSize(300,200);
        menuFrame2.setIconImage(icon.getImage());
        menuFrame2.setContentPane(contentPane2);
        menuFrame2.getContentPane().setBackground(Color.cyan);
        menuFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(1280,900);
        window.getContentPane().add(world);
        window.addKeyListener(new keyListener());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(false);
        run = false;
    }

    public static void newGame() throws FileNotFoundException {
        readMap();

        int count = 0;
        for(int i = 0;i<map.length;i++){
            for(int j = 0;j<map[i].length;j++){
                if(map[i][j]==0)count++;
            }
        }
        System.out.println(count);

        walLines = new int[window.getWidth()][4];
        double cameraX,rayPosX,rayPosY,rayDirX,rayDirY;
        int mapX,mapY;

        Thread checkDeath = new Thread(new CheckDeath());
        checkDeath.start();
        Thread checkCollision = new Thread(new CheckCollision());
        checkCollision.start();
        Thread walkerAI = new Thread(new WalkerAI());
        walkerAI.start();

        long oldTime = System.nanoTime();

        organisms.add(new Walker(138,61,0,0,30,1));
        organisms.add(new Plant(138,62,30));

        while(run){
            for(int x = 0;x<window.getWidth();x++){
                //calculate ray position and direction
                cameraX = 2 * x/(double)window.getWidth() - 1; //x-coordinate in camera space
                rayPosX = player.getX();
                rayPosY = player.getY();
                rayDirX = player.getDirX() + planeX*cameraX;
                rayDirY = player.getDirY() + planeY*cameraX;

                //which box of the map we're in
                mapX = (int) rayPosX;
                mapY = (int) rayPosY;

                //length of ray from current position to next x or y-side
                double sideDistX;
                double sideDistY;

                //length of ray from one x or y-side to next x or y-side
                double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
                double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
                double perpWallDist;

                //what direction to step in x or y-direction (either +1 or -1)
                int stepX;
                int stepY;

                int hit = 0; //determine wall hit
                int side = 0; //NS or a EW wall hit

                //calculate step and initial sideDist
                if (rayDirX < 0){
                    stepX = -1;
                    sideDistX = (rayPosX - mapX) * deltaDistX;
                }else{
                    stepX = 1;
                    sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
                }
                if (rayDirY < 0){
                    stepY = -1;
                    sideDistY = (rayPosY - mapY) * deltaDistY;
                }else{
                    stepY = 1;
                    sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
                }
                //DDA algorithm
                while(hit==0){
                    //jump to next map square, OR in x-direction, OR in y-direction
                    if (sideDistX < sideDistY){
                        sideDistX += deltaDistX;
                        mapX += stepX;
                        side = 0;
                    }else{
                        sideDistY += deltaDistY;
                        mapY += stepY;
                        side = 1;
                    }
                    //if ray has hit a wall, hit=1 to stop the loop
                    if (map[mapX][mapY]  != 0){
                        hit = 1;
                    }
                }

                //Calculate distance projected on camera direction
                if(side==0){
                    perpWallDist = (mapX - rayPosX + (1-stepX) / 2) / rayDirX;
                }else{
                    perpWallDist = (mapY - rayPosY + (1 - stepY) / 2) / rayDirY;
                }

                //Calculate height of line to draw on screen
                int lineHeight = (int)(window.getHeight() / perpWallDist);
                //calculate lowest and highest pixel to fill in current stripe
                int drawStart = -lineHeight / 2 + window.getHeight() / 2;
                if(drawStart < 0){
                    drawStart = 0;
                }
                int drawEnd = lineHeight / 2 + window.getHeight() / 2;
                if(drawEnd >= window.getHeight()){
                    drawEnd = window.getHeight() - 1;
                }
                walLines[x][0] = drawStart;
                walLines[x][1] = drawEnd;
                walLines[x][2] = map[mapX][mapY];
                walLines[x][3] = side;
            }
            world.repaint();
            window.repaint();

            if(deltaSecond(oldTime)==0.5){
                remainTime-=0.5;
                if(plantRemainTime>0){
                    plantRemainTime-=0.5;
                }
                if(spawnTime>0){
                    spawnTime-=0.5;
                }else{
                    spawn(20,20);
                    spawnTime = 60;
                }
                oldTime = System.nanoTime();
            }
//            System.out.println(player.getX()+" "+player.getY());
            window.setVisible(true);
            run = remainTime>0; //End when remain time <= 0
        }
        endGameScreen();
    }

    public static void spawn(int walkerNum,int plantNum){
        int x,y;
        for(int w = 0;w<walkerNum;w++){
            do {
                x = (int) (Math.random() * map[0].length);
                y = (int) (Math.random() * map.length);
            }while(map[y][x]==1||map[y][x]==2||map[y][x]==4);
            organisms.add(new Walker(x,y,-1,0, 300,(int) (Math.random()*2+1)));
            map[y][x] = map[y][x]==0 ? 2:4;
        }
        for(int p = 0;p<plantNum;p++){
            do {
                x = (int) (Math.random() * map[0].length);
                y = (int) (Math.random() * map.length);
            }while(map[y][x]==1||map[y][x]==3||map[y][x]==4);
            organisms.add(new Plant(x,y,180));
            map[y][x] = map[y][x]==0 ? 3:4;
        }
        System.out.println("finished");
    }

    public static void readMap() throws FileNotFoundException {
        Scanner mapFile = new Scanner(new File("Map.txt"));
        String line;
        int width = 0,height = 0;
        do{
            line = mapFile.nextLine();
//            System.out.println(line);
            height++;
        }while (mapFile.hasNextLine());
        width = line.length();
//        System.out.println(width+"x"+height);
        map = new int[height][width];
        mapFile = new Scanner(new File("Map.txt"));
        for(int i = 0;i<height;i++){
            line = mapFile.nextLine();
            for(int j = 0;j<width;j++){
                map[i][j] = Character.getNumericValue(line.charAt(j));
//                System.out.print(map[i][j]);
            }
//            System.out.println();
        }
    }

    public static double deltaSecond(long oldTime){
        long currentTime = System.nanoTime();
        double elapsed = (currentTime - oldTime) /1000000000.0;
        double round = Math.round(elapsed * 10);
        return round/10;
    }

    public static class World extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println(false);
            for(int x = 0;x<walLines.length;x++){
//                for(int y = 0;y<2;y++){
                if(walLines[x][3]==1){
                    g.setColor(new Color(64,64,64));
                }else{
                    g.setColor(new Color(160,160,160));
                }
                //Wall
                g.drawLine(x,walLines[x][0],x,walLines[x][1]);
                if(walLines[x][2]==2){
                    //Walker
                    g.setColor(Color.BLUE);
                    g.drawLine(x,walLines[x][0],x,walLines[x][1]);
                }else if(walLines[x][2]==3){
                    //Plant
                    g.setColor(Color.GREEN);
                    g.drawLine(x,walLines[x][0],x,walLines[x][1]);
                }else if(walLines[x][2]==4){
                    //Mixed
                    g.setColor(Color.YELLOW);
                    g.drawLine(x,walLines[x][0],x,walLines[x][1]);
                }
//                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
            g.drawString("Remaining Life: "+(int)remainTime+"s",0,30);
            g.drawString("Remaining Buff: "+(int)plantRemainTime+"s",0,60);
            //repaint();
        }
    }

    public static class keyListener implements KeyListener {
        double changeX,changeY;
        int factor = 10;
        @Override
        public void keyPressed(KeyEvent e) {
            //Get the key pressed
            System.out.println(true);
            int key = e.getKeyCode();
            if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
                //both camera direction and camera plane are rotated
                double oldDirX = player.getDirX();
                player.setDirX(player.getDirX() * Math.cos(-rotSpeed) - player.getDirY() * Math.sin(-rotSpeed));
                player.setDirY(oldDirX * Math.sin(-rotSpeed) + player.getDirY() * Math.cos(-rotSpeed));
                double oldPlaneX = planeX;
                planeX = planeX * Math.cos(-rotSpeed) - planeY * Math.sin(-rotSpeed);
                planeY = oldPlaneX * Math.sin(-rotSpeed) + planeY * Math.cos(-rotSpeed);
//                System.out.println("Direction: "+player.getDirX()+","+player.getDirY());
            }else if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
                double oldDirX = player.getDirX();
                player.setDirX(player.getDirX() * Math.cos(rotSpeed) - player.getDirY() * Math.sin(rotSpeed));
                player.setDirY(oldDirX * Math.sin(rotSpeed) + player.getDirY() * Math.cos(rotSpeed));
                double oldPlaneX = planeX;
                planeX = planeX * Math.cos(rotSpeed) - planeY * Math.sin(rotSpeed);
                planeY = oldPlaneX * Math.sin(rotSpeed) + planeY * Math.cos(rotSpeed);
//                System.out.println("Direction: "+player.getDirX()+","+player.getDirY());
            }
            if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
                if(map[(int) (player.getX() + player.getDirX() * moveSpeed)][(int) player.getY()] != 1){
                    player.setX(player.getX()+(player.getDirX()*moveSpeed));
                }
                if(map[(int) player.getX()][(int) (player.getY() + player.getDirY() * moveSpeed)] != 1){
                    player.setY(player.getY()+(player.getDirY()*moveSpeed));
                }
//                System.out.println("Coordinates: "+player.getX()+","+player.getY());
            }else if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
                if(map[(int) (player.getX() - player.getDirX() * moveSpeed)][(int) player.getY()] != 1){
                    player.setX(player.getX()-(player.getDirX()*moveSpeed));
                }
                if(map[(int) player.getX()][(int) (player.getY() - player.getDirY() * moveSpeed)] != 1){
                    player.setY(player.getY()-(player.getDirY()*moveSpeed));
                }
//                System.out.println("Coordinates: "+player.getX()+","+player.getY());
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
//            System.out.println(false);
        }

    }

    /***************PART A: Methods of different menuFrame screens *********/

    /**editGameScreen()
     * This method  is for reconstructing the game playing screen after the user lost
     **/
    public static void endGameScreen(){

        //Reset the frame work
        menuFrame3 = new JFrame("Survival");
        contentPane = new JPanel(new BorderLayout());

        //Create items
        ImageIcon image = new ImageIcon("backGround.jpg");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        JButton endButton = new JButton("Check your Score");
        endButton.addActionListener(new endListener());

        //Add items to JPanel

        JPanel endGamePanel = new JPanel(new GridLayout(4, 1));
        endGamePanel.add(new JLabel("Game Over"));
        endGamePanel.add(new JLabel("Player has killed this many walkers"));
        endGamePanel.add(new JLabel("Player has survived for this amount of time."));

        contentPane.add(endGamePanel, BorderLayout.CENTER);
        contentPane.add(endButton, BorderLayout.SOUTH);

        //Final settings
        menuFrame3.setSize(600, 600);
        menuFrame3.setContentPane(contentPane);
        menuFrame3.getContentPane().setBackground(Color.cyan);
        window.setVisible(false);
        menuFrame3.setVisible(true);

    }

    /**endScreen()
     * This method  is for displaying leaderboard + player score.
     **/
    public static void endScreen(){

        //Set up the frame work
        menuFrame3.remove(contentPane);
        contentPane = new JPanel(new BorderLayout());

        //Set Default Font
        setUIFont(new javax.swing.plaf.FontUIResource("Courier",Font.CENTER_BASELINE,16));

        //Create items
        Leaderboard test = new Leaderboard(playerName, 5);
        ArrayList<Player> players = test.sortAndGet();
        JButton returnButton = new JButton("Return to Start Menu");
        returnButton.setBackground(Color.cyan);
        returnButton.addActionListener(new returnListener());
        JLabel title = new JLabel("You have made a score of "+players.get(players.size()-1).getScore());
        title.setFont(new Font("Courier", Font.CENTER_BASELINE, 19));

        //Add items to sub jpanel
        JPanel board = new JPanel(new GridLayout(players.size()+1, 1));
        board.add(new JLabel("Leaderboard: "));
        for(int i = 0; i < players.size(); i++){
            board.add(new JLabel((i+1)+"."+players.get(i).getName()+": "+players.get(i).getScore()));
        }

        //Add items to JPanel
        contentPane.add(title, BorderLayout.NORTH);
        contentPane.add(board, BorderLayout.CENTER);
        contentPane.add(returnButton, BorderLayout.SOUTH);
        contentPane.add(new JLabel("    "), BorderLayout.WEST);

        //Final settings
        menuFrame.setContentPane(contentPane);
        menuFrame.getContentPane().setBackground(Color.cyan);
        menuFrame.setVisible(true);

    }

    /*******************PART B: ACTION LISTERNERS *************/

    /**
     * startListener
     * remakes the JFrame
     */
    static class startListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            menuFrame.setVisible(false);
            menuFrame2.setVisible(true);
        }
    }

    /**
     * gameListener
     * switches the JFrame to game frame in MainGame.java
     */
    static class gameListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            menuFrame2.setVisible(false);
            //Get the name of the player
            playerName = name.getText();
            //Temporarily open a new game menuFrame, run the game.
            run = true;
            window.setVisible(true);
            try{
                newGame();
            }catch(FileNotFoundException e){

            }

        }
    }

    /**
     * endListener
     * remakes the JFrame
     */
    static class endListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            endScreen();
        }
    }

    /**
     * returnListener
     * remakes the JFrame
     */
    static class returnListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            menuFrame3.setVisible(false);
            menuFrame.setVisible(true);
        }
    }


    /******************PART C: MISCELLALIOUS METHODS**************/
    /**
     * setUIFont
     * set the font for java swing
     */
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }


}
