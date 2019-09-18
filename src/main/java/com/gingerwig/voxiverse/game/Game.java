package com.gingerwig.voxiverse.game;

import com.gingerwig.voxiverse.engine.GameInterface;
import com.gingerwig.voxiverse.engine.Renderer;
import com.gingerwig.voxiverse.engine.Window;
import com.gingerwig.voxiverse.models.Mesh;


public class Game implements GameInterface
{
    private Renderer renderer;

    private Mesh mesh;



    /**
     * Sole constructor:
     * Create a new renderer object
     */
    public Game()
    {
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
                        -0.5f, 0.5f, -1.05f,
                        -0.5f, -0.5f, -1.05f,
                        0.5f, -0.5f, -1.05f,
                        0.5f, 0.5f, -1.05f
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
    }


    @Override
    public void input(Window window)
    {

    }


    @Override
    public void update(float interval)
    {

    }


    @Override
    public void render(Window window)
    {
        window.setClearColour(0, 0, 0, 0.0f);

        renderer.render(window, mesh);
    }


    @Override
    public void cleanup()
    {
        renderer.cleanup();
        mesh.cleanup();
    }
}
