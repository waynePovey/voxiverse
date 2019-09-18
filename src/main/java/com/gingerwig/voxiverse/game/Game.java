package com.gingerwig.voxiverse.game;

import com.gingerwig.voxiverse.engine.GameInterface;
import com.gingerwig.voxiverse.engine.GameItem;
import com.gingerwig.voxiverse.engine.Renderer;
import com.gingerwig.voxiverse.engine.Window;
import com.gingerwig.voxiverse.models.Mesh;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;


public class Game implements GameInterface
{
    private Renderer renderer;

    private GameItem gameItems[];

    private Mesh mesh;

    private float rotY = 0;



    /**
     * Sole constructor:
     * Create a new renderer object
     */
    public Game()

    {
        gameItems = new GameItem[3];
        renderer = new Renderer();
    }



    /**
     *
     * @param window
     * @throws Exception
     */
    @Override
    public void init(Window window) throws Exception
    {
        renderer.init(window);


        float[] vertices = new float[]
                {
                        -0.5f, 0.5f, -2f,
                        -0.5f, -0.5f, -2f,
                        0.5f, -0.5f, -2f,
                        0.5f, 0.5f, -2f
                };

        int[] indices = new int[]
                {
                        0, 1, 3, 3, 1, 2
                };


        float[] colours = new float[]
                {
                        1.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 1.0f,
                        0.5f, 0.5f, 0.0f
                };

        mesh = new Mesh(vertices, indices, colours);

        GameItem gameItem1 = new GameItem(mesh);
        GameItem gameItem2 = new GameItem(mesh);
        GameItem gameItem3 = new GameItem(mesh);

        gameItems[0] = gameItem1;
        gameItems[1] = gameItem2;
        gameItems[2] = gameItem3;

        gameItems[0].setPosition(0,rotY , 0);
        gameItems[0].setRotation(rotY, rotY, rotY);

        gameItems[1].setPosition(0.5f,-0.5f , 0);
        gameItems[1].setRotation(0, rotY, 0);

        gameItems[2].setPosition(-0.5f,-0.5f , 0);
        gameItems[2].setRotation(0, rotY, 0);
    }


    @Override
    public void input(Window window)
    {
        if(window.isKeyPressed(GLFW_KEY_UP))
        {
            rotY += 1;
        }
    }


    @Override
    public void update(float interval)
    {

    }


    @Override
    public void render(Window window)
    {
        window.setClearColour(0, 0, 0, 0.0f);

        renderer.render(window, gameItems);
    }


    @Override
    public void cleanup()
    {
        renderer.cleanup();
        mesh.cleanup();
    }
}
