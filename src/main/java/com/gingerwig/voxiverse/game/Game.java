package com.gingerwig.voxiverse.game;

import com.gingerwig.voxiverse.engine.GameInterface;
import com.gingerwig.voxiverse.engine.GameItem;
import com.gingerwig.voxiverse.engine.Renderer;
import com.gingerwig.voxiverse.engine.Window;
import com.gingerwig.voxiverse.models.Mesh;

import static org.lwjgl.glfw.GLFW.*;


public class Game implements GameInterface
{
    private Renderer renderer;

    private GameItem gameItems[];

    private Mesh mesh;

    private GameItem gameItem1;
    private GameItem gameItem2;
    private GameItem gameItem3;

    private float translateX = 2f;
    private float translateY = 0;

    private float scale = 1;



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
                        -0.5f, 0.5f, 0.5f,
                        -0.5f, -0.5f, 0.5f,
                        0.5f, -0.5f, 0.5f,
                        0.5f, 0.5f, 0.5f,
                        -0.5f, 0.5f, -0.5f,
                        0.5f, 0.5f, -0.5f,
                        -0.5f, -0.5f, -0.5f,
                        0.5f, -0.5f, -0.5f
                };

        int[] indices = new int[]
                {
                        //Front
                        0, 1, 3, 3, 1, 2,

                        //Top
                        4, 0, 5, 5, 0, 3,

                        //Right
                        3, 2, 5, 5, 2, 7,

                        //Left
                        4, 6, 0, 0, 6, 1,

                        //Bottom
                        1, 6, 2, 2, 6, 7,

                        //Back
                        6, 4, 7, 7, 4, 5
                };


        float[] colours = new float[]
                {
                        1.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 1.0f,
                        0.5f, 0.5f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 1.0f,
                        0.5f, 0.5f, 0.0f
                };

        mesh = new Mesh(vertices, indices, colours);

        gameItem1 = new GameItem(mesh);
        gameItem2 = new GameItem(mesh);
        gameItem3 = new GameItem(mesh);


        gameItem1.setPosition(0, 0, -4);
        gameItem2.setPosition(translateX,0 , -4);
        gameItem3.setPosition(-2f,0 , -4);


        gameItems[0] = gameItem1;
        gameItems[1] = gameItem2;
        gameItems[2] = gameItem3;
    }


    @Override
    public void input(Window window)
    {
        if(window.isKeyPressed(GLFW_KEY_RIGHT))
        {
            translateX += 0.01f;
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT))
        {
            translateX -= 0.01f;
        }


        if(window.isKeyPressed(GLFW_KEY_UP))
        {
            translateY += 0.01f;
        }

        if(window.isKeyPressed(GLFW_KEY_DOWN))
        {
            translateY -= 0.01f;
        }


        if(window.isKeyPressed(GLFW_KEY_EQUAL))
        {
            if(scale < 1.5f)
            {
                scale += 0.01f;
            }
        }

        if(window.isKeyPressed(GLFW_KEY_MINUS))
        {
            if(scale > 0.25f)
            {
                scale -= 0.01f;
            }
        }
    }


    @Override
    public void update(float interval)
    {
        float rotation = gameItem1.getRotation().x + 1.5f;

        if(rotation > 360)
        {
            rotation = 0;
        }

        gameItem1.setRotation(rotation, rotation, rotation);

        gameItem2.setPosition(translateX, translateY, -4f);

        gameItem3.setScale(scale);
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
