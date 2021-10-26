//Bryant Le

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class PickUpTrash extends Application
{
    int x = 0;
    int y = 0;
    int[][] grid = new int[20][20];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pick Up Trash");
        Group root = new Group();
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Grid
        for(int i = 0; i < 20; i++)
            for(int j = 0; j < 20; j++) {
                int ran = (int) Math.round(Math.random());
                if (ran == 0)
                    grid[i][j] = 0;
                else
                    grid[i][j] = 1;
            }

        canvas.setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event){
                x = (int)event.getX();
                y = (int)event.getY();
                draw(canvas.getGraphicsContext2D());
             }
        });

        draw(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void draw(GraphicsContext gc)
    {
        //Trash Bins
        for(int i = 0; i < 20; i++)
            for(int j = 0; j < 20; j++)
                gc.strokeRect(15*j+55,15*i+55,10,10);

            //Trash
        for(int i = 0; i < 20; i++)
            for(int j = 0; j < 20; j++) {
                    if (grid[i][j] == 0)
                        gc.setFill(Color.WHITE);
                    else
                        gc.setFill(Color.BLACK);
                gc.fillRect(15 * j + 53, 15 * i + 53, 10, 10);
            }
        int f = (x - 53) / 5;
        int g = (y - 53) / 5;
        if(x >= 53 && x <= 353 && y >= 53 && y <= 353 && (f+1) % 3 != 0 && (g+1) % 3 != 0) {
            pickUpTrash(g/3,f/3);
            x = y = 0;
            draw(gc);
        }

        //All Cleared
        boolean clear = true;
        for(int i = 0; i < 20; i++)
            for(int j = 0; j < 20; j++)
                if(grid[i][j] != 0)
                    clear = false;
        if(clear)
            gc.strokeText("You cleared all the trash", 135,35);
    }
    public void pickUpTrash(int c, int r){
        if(grid[c][r] == 1) {
            grid[c][r] = 0;
            if(c > 0)
                pickUpTrash(c - 1,r);
            if(c < 19)
                pickUpTrash(c+1,r);
            if(r>0)
                pickUpTrash(c,r-1);
            if(r<19)
                pickUpTrash(c,r+1);
        }

    }
}