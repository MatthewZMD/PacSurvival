import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static int[][] map;
    public static int[][]walLines;

    public static JFrame window = new JFrame("Survival");
    public static World world = new World();

    //Create player object
    public static Player player = new Player("MT",64,138,-1,0,3);

    //the 2d raycaster version of camera plane
    public static double planeX = 0, planeY = 0.66,moveSpeed = 0.2,rotSpeed = 0.05;

    public static void main(String[] args) throws FileNotFoundException {

        readMap();

        double time = 0; //time of current frame
        double oldTime = 0; //time of previous frame
        boolean run = true;

        window.setSize(1280,900);
        window.getContentPane().add(world);
        window.addKeyListener(new keyListener());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

        walLines = new int[window.getWidth()][3];
        double cameraX,rayPosX,rayPosY,rayDirX,rayDirY;
        int mapX,mapY;
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
                    if (map[mapX][mapY] > 0){
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
                walLines[x][2] = side;

            }
            oldTime = time;
//            time = System.currentTimeMillis();
//            double frameTime = (time - oldTime)/1000;
//            System.out.println(1/frameTime);
            window.repaint();
//            run = false;
        }
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

    public static class World extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int x = 0;x<walLines.length;x++){
                for(int y = 0;y<2;y++){
                    if(walLines[x][2]==1){
                        g.setColor(new Color(64,64,64));
                    }else{
                        g.setColor(new Color(160,160,160));
                    }
                    g.drawLine(x,walLines[x][0],x,walLines[x][1]);
                }
            }
        }
    }

    private static class keyListener implements KeyListener {
        double changeX,changeY;
        int factor = 10;
        @Override
        public void keyPressed(KeyEvent e) {
            //Get the key pressed
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
                if(map[(int) (player.getX() + player.getDirX() * moveSpeed)][(int) player.getY()] == 0){
                    player.setX(player.getX()+(player.getDirX()*moveSpeed));
                }
                if(map[(int) player.getX()][(int) (player.getY() + player.getDirY() * moveSpeed)] == 0){
                    player.setY(player.getY()+(player.getDirY()*moveSpeed));
                }
//                System.out.println("Coordinates: "+player.getX()+","+player.getY());
            }else if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
                if(map[(int) (player.getX() - player.getDirX() * moveSpeed)][(int) player.getY()] == 0){
                    player.setX(player.getX()-(player.getDirX()*moveSpeed));
                }
                if(map[(int) player.getX()][(int) (player.getY() - player.getDirY() * moveSpeed)] == 0){
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

}
