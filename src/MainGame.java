import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

    //Create player object
    public static Player player = new Player("MT",65,137,-1,0);

    //the 2d raycaster version of camera plane
    public static double planeX = 0, planeY = 0.66,moveSpeed = 0.0002,rotSpeed = 0.0001;

    public static double remainTime = 60 * 0.1, plantRemainTime = 0,spawnTime = 0;
    public static long startTime=0;

    public static boolean win = false;

    public static ArrayList<Organism> organisms = new ArrayList<Organism>();
    private static Leaderboard test;

    //Declare Variables
    private static JButton startButton;
    private static JPanel contentPane;
    //public static SpecialPanel contentPane4;
    private static JFrame menuFrame, menuFrame2, menuFrame3;
    private static String playerName;
    private static JTextField name;
    private static int totalScore;

    private static Image dbImage;
    private static ImageIcon icon = new ImageIcon("gameIcon.png");

    public static boolean start = false,left,right,up,down;

    public static Clip gameMusic;

    //Below starts the methods (screens)
    //Main method
    public static void main(String[] args) throws Exception {
        menuFrame = new JFrame("Survival");

        //Import music into the game
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("ShionTownTheme.wav").getAbsoluteFile());
        gameMusic = AudioSystem.getClip();
        gameMusic.open(audioInputStream);

        //Import Background Image
        dbImage = ImageIO.read(new File("displayBackground2.jpg"));

        //Set Default Font
        setUIFont(new javax.swing.plaf.FontUIResource("Georgia",Font.CENTER_BASELINE,15));

        //Set up the frame work
        contentPane = new SpecialPanel();
        contentPane.setLayout(new GridLayout(2,2));

        //Create item
        startButton = new JButton("Start");
        startButton.setFont(new Font("Georgia", Font.CENTER_BASELINE, 25));
        startButton.addActionListener(new startListener());
        startButton.setBackground(Color.yellow);
        JLabel filler = new JLabel("                        ");
        filler.setFont(new Font(("Courier"), Font.BOLD, 80));
        JPanel startButtonPanel = new JPanel(new FlowLayout());
        startButtonPanel.add(filler);
        startButtonPanel.add(startButton);
        startButtonPanel.setBackground(Color.red);
        JPanel settingButtonPanel = new JPanel(new FlowLayout());
        JLabel instruction = new JLabel("THE BACKSTORY:");
        instruction.setFont(new Font("Georgia", Font.BOLD, 16));
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
        JLabel title = new JLabel("   SURVIVAL");
        title.setFont(new Font("Georgia", Font.BOLD, 40));

        //Add items to JPanel
        contentPane.add(title);
        contentPane.add(startButtonPanel);
        contentPane.add(settingButtonPanel);
        contentPane.add(new JLabel(new ImageIcon("backGround.png")));

        //Final settings
        menuFrame.setIconImage(icon.getImage());
        menuFrame.setSize(600,600);
        menuFrame.setContentPane(contentPane);
        menuFrame.getContentPane().setBackground(Color.cyan);
        menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
        menuFrame.setFocusable(true);

        //********************/

        menuFrame2 = new JFrame("Survival");

        //Set up the frame work
        JPanel contentPane2 = new SpecialPanel();
        contentPane2.setLayout(new GridLayout(14, 1, 10, 0));

        //Create items
        JButton startButton2 = new JButton("BEGIN");
        startButton2.setBackground(Color.CYAN);
        startButton2.setFont(new Font("Georgia", Font.CENTER_BASELINE, 16));
        startButton2.addActionListener(new gameListener());
        JLabel instruction1 = new JLabel("         main objective is to find an exit in a limited time.");
        JLabel instruction2 = new JLabel("  You'll see some blue-coloured blocks called Walkers");
        JLabel instruction3 = new JLabel("  Once you make contact with them, ");
        JLabel instruction4 = new JLabel("  your remaining time in maze will decrease, so be careful.");
        JLabel instruction5 = new JLabel("  Fortunately, some green plant objects exist here.");
        JLabel instruction6 = new JLabel("  Once you are in contact with the Plants, your life-time increases.");
        JLabel instruction7 = new JLabel("  And you will receive a Plant Buff for a limited amount of time.");
        JLabel instruction8 = new JLabel("  The Plant Buff enables you to attack the Walkers instead of ");
        JLabel instruction9 = new JLabel("  the Walkers damaging you while you are in contact with them.");
        JLabel instruction10 = new JLabel("  However there are Fake Plants in the maze that bring no");
        JLabel instruction11 = new JLabel("  benefits. They're only intended to mislead you to an unknown destination.");
        JLabel title2 = new JLabel("            Insert your name to begin: ");
        title2.setFont(new Font("Georgia", Font.CENTER_BASELINE, 12));
        name = new JTextField(100);

        //Add items to JPanel
        contentPane2.add(instruction1);
        contentPane2.add(instruction2);
        contentPane2.add(instruction3);
        contentPane2.add(instruction4);
        contentPane2.add(instruction5);
        contentPane2.add(instruction6);
        contentPane2.add(instruction7);
        contentPane2.add(instruction8);
        contentPane2.add(instruction9);
        contentPane2.add(instruction10);
        contentPane2.add(instruction11);
        contentPane2.add(title2);
        contentPane2.add(name);
        contentPane2.add(startButton2);

        //Final settings
        menuFrame2.setSize(600,500);
        menuFrame2.setIconImage(icon.getImage());
        menuFrame2.setContentPane(contentPane2);
        menuFrame2.getContentPane().setBackground(Color.cyan);
        menuFrame2.setVisible(false);
        menuFrame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*************/

        window.setIconImage(icon.getImage());
        window.setSize(1280,900);
        window.getContentPane().add(world);
        window.addKeyListener(new keyListener());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(false);
        walLines = new int[window.getWidth()][4];

        newGame();
    }

    private static void newGame() throws FileNotFoundException {
        readMap();

        int count = 0;
        for(int i = 0;i<map.length;i++){
            for(int j = 0;j<map[i].length;j++){
                if(map[i][j]==0)count++;
            }
        }
        System.out.println(count);

        double cameraX,rayPosX,rayPosY,rayDirX,rayDirY;
        int mapX,mapY;

        Thread checkDeath = new Thread(new CheckDeath());
        checkDeath.start();
        Thread checkCollision = new Thread(new CheckCollision());
        checkCollision.start();
        Thread walkerAI = new Thread(new WalkerAI());
        walkerAI.start();

        long oldTime = 0;
        boolean alive = true;

        //Initial tutorial
//        if(start){
//            organisms.add(new Walker(138,61,0,0,30,1));
//            organisms.add(new Plant(137,62,30));
//        }

        while(alive&&!win){
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
            updateMovement();
            window.repaint();

            if(start&&oldTime==0){
                startTime = System.nanoTime();
                oldTime = System.nanoTime();
            }else if(start&&deltaSecond(oldTime)==0.5){
                remainTime-=0.5;
                if(plantRemainTime>0){
                    plantRemainTime-=0.5;
                }
                if(spawnTime>0){
                    spawnTime-=0.5;
                }else{
                    int walkerNum = (int) Math.round( Math.log(deltaSecond(startTime)+10)*4000/(deltaSecond(startTime)+100) );
                    int plantNum = (int) Math.round( Math.log(deltaSecond(startTime)+10)*3500/(deltaSecond(startTime)+100) );
//                    System.out.println(deltaSecond(startTime));
                    spawn(walkerNum,plantNum);
                    System.out.println("Spawned "+walkerNum+" Walkers and "+plantNum+" plants.");
                    spawnTime = 60;
                }
                if(CheckCollision.attackTime > 0){
                    CheckCollision.attackTime-= 0.5;
                }
                if(CheckCollision.collideTime > 0){
                    CheckCollision.collideTime-= 0.5;
                }
                if(CheckCollision.walkerDiedTime > 0){
                    CheckCollision.walkerDiedTime-= 0.5;
                }
                if(CheckCollision.plantReceivedTime > 0){
                    CheckCollision.plantReceivedTime-= 0.5;
                }
                oldTime = System.nanoTime();
            }
//            System.out.println(player.getX()+" "+player.getY());
            alive = remainTime>0; //End when remain time <= 0
            win = map[(int) player.getX()][(int)player.getY()]==8;
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
//            map[y][x] = map[y][x]==0 ? 2:4;
        }
        for(int p = 0;p<plantNum;p++){
            do {
                x = (int) (Math.random() * map[0].length);
                y = (int) (Math.random() * map.length);
            }while(map[y][x]==1||map[y][x]==3||map[y][x]==4);
            organisms.add(new Plant(x,y,180));
//            map[y][x] = map[y][x]==0 ? 3:4;
        }
//        System.out.println("Spawned");
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

            for(int x = 0;x<walLines.length;x++){
//                for(int y = 0;y<2;y++){
                //g.drawImage(dbImage, 0, 0, getWidth(), getHeight(), this);
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
                }else if(walLines[x][2]==8){
                    //Exit
                    g.setColor(Color.PINK);
                    g.drawLine(x,walLines[x][0],x,walLines[x][1]);
                }
//                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Georgia", Font.BOLD, 30));
            g.drawString("Remaining Life: "+(int)remainTime+"s",0,30);
            g.setFont(new Font("Georgia", Font.CENTER_BASELINE, 20));
            if((int)plantRemainTime > 0){
                g.drawString("Remaining Buff: "+(int)plantRemainTime+"s",0,60);
            }
            if(CheckCollision.collideTime > 0){
                g.drawString("A walker attacked you.", 0, 80);
            }
            if(CheckCollision.attackTime > 0){
                g.drawString("You attacked a walker.", 0, 100);
            }
            if(CheckCollision.walkerDiedTime > 0){
                g.drawString("A walker died from your hands!", 0, 120);
            }
            if(CheckCollision.plantReceivedTime > 0){
                g.drawString("You ate a plant!", 0, 140);
            }

        }
    }

    public static class keyListener implements KeyListener {
        int factor = 10;
        @Override
        public void keyPressed(KeyEvent e) {
            //Get the key pressed
            int key = e.getKeyCode();
            if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
                right = true;
            }
            if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
                left = true;
            }
            if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
                up = true;
            }
            if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
                down = true;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
                right = false;
            }
            if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
                left = false;
            }
            if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
                up = false;
            }
            if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
                down = false;
            }
//            System.out.println(false);
        }

    }

    public static void updateMovement(){
        if(right){
            //both camera direction and camera plane are rotated
            double oldDirX = player.getDirX();
            player.setDirX(player.getDirX() * Math.cos(-rotSpeed) - player.getDirY() * Math.sin(-rotSpeed));
            player.setDirY(oldDirX * Math.sin(-rotSpeed) + player.getDirY() * Math.cos(-rotSpeed));
            double oldPlaneX = planeX;
            planeX = planeX * Math.cos(-rotSpeed) - planeY * Math.sin(-rotSpeed);
            planeY = oldPlaneX * Math.sin(-rotSpeed) + planeY * Math.cos(-rotSpeed);
//                System.out.println("Direction: "+player.getDirX()+","+player.getDirY());
        }
        if(left){
            double oldDirX = player.getDirX();
            player.setDirX(player.getDirX() * Math.cos(rotSpeed) - player.getDirY() * Math.sin(rotSpeed));
            player.setDirY(oldDirX * Math.sin(rotSpeed) + player.getDirY() * Math.cos(rotSpeed));
            double oldPlaneX = planeX;
            planeX = planeX * Math.cos(rotSpeed) - planeY * Math.sin(rotSpeed);
            planeY = oldPlaneX * Math.sin(rotSpeed) + planeY * Math.cos(rotSpeed);
//                System.out.println("Direction: "+player.getDirX()+","+player.getDirY());
        }
        if(up){
            if(map[(int) (player.getX() + player.getDirX() * moveSpeed)][(int) player.getY()] != 1){
                player.setX(player.getX()+(player.getDirX()*moveSpeed));
            }
            if(map[(int) player.getX()][(int) (player.getY() + player.getDirY() * moveSpeed)] != 1){
                player.setY(player.getY()+(player.getDirY()*moveSpeed));
            }
//                System.out.println("Coordinates: "+player.getX()+","+player.getY());
        }
        if(down){
            if(map[(int) (player.getX() - player.getDirX() * moveSpeed)][(int) player.getY()] != 1){
                player.setX(player.getX()-(player.getDirX()*moveSpeed));
            }
            if(map[(int) player.getX()][(int) (player.getY() - player.getDirY() * moveSpeed)] != 1){
                player.setY(player.getY()-(player.getDirY()*moveSpeed));
            }
//                System.out.println("Coordinates: "+player.getX()+","+player.getY());
        }
    }


    /******************************Additional GUI Methods***************************/

    /***************PART A: Methods of different menuFrame screens *********/

    /**editGameScreen()
     * This method  is for reconstructing the game playing screen after the user lost
     **/
    private static void endGameScreen(){

        //Save the score
        if(win){
            totalScore = (int)(1.5*deltaSecond(startTime));
        }else{
            totalScore = (int)deltaSecond(startTime);
        }

        //Create items
        test = new Leaderboard(playerName, totalScore);

        //Reset the frame work
        menuFrame3 = new JFrame("Survival");
        contentPane = new JPanel(new BorderLayout());

        //Create items
        JButton endButton = new JButton("Check your Score");
        endButton.setBackground(Color.CYAN);
        endButton.addActionListener(new endListener());

        //Add items to JPanel

        //Set Default Font
        setUIFont(new javax.swing.plaf.FontUIResource("Georgia",Font.CENTER_BASELINE,20));
        //Set up inner JPanel
        JPanel endGamePanel = new SpecialPanel();
        endGamePanel.setLayout(new GridLayout(3, 1));
        endGamePanel.add(new JLabel(" END OF THE GAME"));
        endGamePanel.add(new JLabel(" "+playerName+" has survived for "+deltaSecond(startTime)+"s."));
        if(win){
            endGamePanel.add(new JLabel(" CONGRATS FOR SAVING THE MANKIND!"));
        }else{
            endGamePanel.add(new JLabel(" Unfortunately, you lost... But you can try again"));
        }

        //Add items to outer JPanel
        JPanel innerContentPane = new JPanel(new BorderLayout());
        innerContentPane.setBackground(Color.YELLOW);
        innerContentPane.add(new JLabel("   "), BorderLayout.EAST);
        innerContentPane.add(new JLabel("   "), BorderLayout.WEST);
        innerContentPane.add(new JLabel("   "), BorderLayout.NORTH);
        innerContentPane.add(new JLabel("   "), BorderLayout.SOUTH);
        innerContentPane.add(endGamePanel, BorderLayout.CENTER);
        contentPane.add(innerContentPane, BorderLayout.CENTER);
        contentPane.add(endButton, BorderLayout.SOUTH);

        //Final JFrame settings
        menuFrame3.setIconImage(icon.getImage());
        menuFrame3.setSize(600, 300);
        menuFrame3.setContentPane(contentPane);
        menuFrame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(false);
        menuFrame3.setVisible(true);
        menuFrame3.setFocusable(true);

    }

    /**endScreen()
     * This method  is for displaying leaderboard + player score.
     **/
    private static void endScreen() {

        //Set up the frame work
        menuFrame3.remove(contentPane);
        contentPane = new JPanel(new BorderLayout());

        //Set Default Font
        setUIFont(new javax.swing.plaf.FontUIResource("Georgia", Font.CENTER_BASELINE, 16));

        ArrayList<Player> players = test.sortAndGet();
        JButton returnButton = new JButton("Exit the Game");
        returnButton.setBackground(Color.cyan);
        returnButton.addActionListener(new returnListener());
        JLabel title = new JLabel("   You have made a score of "+totalScore);
        title.setFont(new Font("Georgia", Font.CENTER_BASELINE, 19));

        //Add items to sub jpanel
        JPanel board = new SpecialPanel();
        board.setLayout(new GridLayout(11, 1, 1, 10));
        board.add(new JLabel("Leaderboard: "));
        for(int i = 0; i < 10; i++){
            board.add(new JLabel((i+1)+"."+players.get(i).getName()+": "+players.get(i).getScore()));
        }

        //Add items to JPanel
        JPanel innerContentPane = new JPanel(new BorderLayout());
        innerContentPane.setBackground(Color.CYAN);
        innerContentPane.add(title, BorderLayout.NORTH);
        innerContentPane.add(board, BorderLayout.CENTER);
        innerContentPane.add(new JLabel("    "), BorderLayout.WEST);
        innerContentPane.add(new JLabel("    "), BorderLayout.SOUTH);
        innerContentPane.add(new JLabel("    "), BorderLayout.EAST);
        contentPane.add(innerContentPane, BorderLayout.CENTER);
        contentPane.add(returnButton, BorderLayout.SOUTH);


        //Final settings
        menuFrame3.setSize(375, 500);
        menuFrame3.setContentPane(contentPane);
        menuFrame3.getContentPane().setBackground(Color.cyan);
        menuFrame3.setVisible(true);
        menuFrame3.setFocusable(true);

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
            menuFrame2.setFocusable(true);
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
            start = true;
            window.setFocusable(true);
            window.setVisible(true);
            gameMusic.start();
            gameMusic.loop(10000);
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
     * closes the JFrame
     */
    static class returnListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            menuFrame3.setVisible(false);
        }
    }

    /******************PART C: MISCELLALIOUS METHOD**************/
    /**
     * setUIFont
     * set the font for java swing
     */
    private static void setUIFont (javax.swing.plaf.FontUIResource f){
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    /******************PART D: BACKGROUND IMAGES FOR PANELS******/
    static class SpecialPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.drawImage(dbImage, 0, 0, this);
        }
    }
}
