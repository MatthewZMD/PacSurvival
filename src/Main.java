import javax.swing.*;

public class Main {
    public static int[][] map =
    {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,2,2,2,2,2,0,0,0,0,3,0,3,0,3,0,0,0,1},
        {1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,3,0,0,0,3,0,0,0,1},
        {1,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,2,2,0,2,2,0,0,0,0,3,0,3,0,3,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,4,0,4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,4,0,0,0,0,5,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,4,0,4,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,4,0,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
    public static JFrame window = new JFrame("Survival");

    public static void main(String[] args) {
        //Create user object
        Player player = new Player(map.length/2,map[0].length/2,-1,0);

        //the 2d raycaster version of camera plane
        double planeX = 0, planeY = 0.66;

        double time = 0; //time of current frame
        double oldTime = 0; //time of previous frame
        boolean run = true;

        window.setSize(400,300);

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

            }
        }
    }
}
