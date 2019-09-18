package com.gingerwig.voxiverse.engine;

import com.gingerwig.voxiverse.models.Mesh;
import com.gingerwig.voxiverse.utils.*;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL30.*;

public class Renderer
{
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private ShaderProgram shader;

    private Matrix4f projectionMatrix;

    private Transformation transformation;


    public Renderer()
    {
        this.transformation = new Transformation();
    }


    /**
     * Create a new program object, vertex and fragment shader and
     * link the program.
     * Create a new projection matrix, create a map entry for the uniform
     * and .
     *
     * @param window        the window object
     * @throws Exception    if the shader cannot be created or compiled or
     *                      the uniform cannot be found
     */
    public void init(Window window) throws Exception
    {
        shader = new ShaderProgram();
        shader.createVertexShader(FileLoader.loadResource("/shaders/vertex.glsl"));
        shader.createFragmentShader(FileLoader.loadResource("/shaders/fragment.glsl"));
        shader.link();

        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shader.createUniform("projectionMatrix");
        shader.createUniform("worldMatrix");
    }



    /**
     * Clear the buffers and check if window is resized.
     * Install shader program, enable VAO, draw model, disable VAO &
     * uninstall the shader program.
     *
     * @param window    the window object
     * @param gameItems Contains an array of game item objects
     */
    public void render(Window window, GameItem[] gameItems)
    {
        clear();

        if(window.isResized())
        {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shader.bind();

        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth()
                            , window.getHeight(), Z_NEAR, Z_FAR);
        shader.setUniform("projectionMatrix", projectionMatrix);


        for(GameItem gameItem : gameItems)
        {
            Matrix4f worldMatrix = transformation.getWorldMatrix(
                    gameItem.getPosition(),
                    gameItem.getRotation(),
                    gameItem.getScale());

            shader.setUniform("worldMatrix", worldMatrix);

            gameItem.getMesh().render();
        }

        shader.unbind();
    }



    /**
     * Clear the colour and depth buffers
     */
    private void clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }



    /**
     * Calls the shader program cleanup
     */
    public void cleanup()
    {
        if(shader != null)
        {
            shader.cleanup();
        }
    }
}
