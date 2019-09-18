package com.gingerwig.voxiverse.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Class for implementing the GLFW window.
 */

public class Window
{
    private long windowHandle;
    private int width;
    private int height;
    private final String title;
    private boolean resized;
    private boolean vSync;


    public Window(String title, int aWidth, int aHeight, boolean vSyncOn)
    {
        this.title = title;
        this.width = aWidth;
        this.height = aHeight;
        this.vSync = vSyncOn;
        this.resized = false;
    }

    public void init()
    {
        //Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();


        //Initialise GLFW
        if(!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }


        //Window settings
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);


        //Create window
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if(windowHandle == NULL)
        {
            throw new RuntimeException("Failed to create the GLFW window");
        }


        //Setup resize callback
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) ->
        {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });


        //Setup key callback

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) ->
        {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            {
                glfwSetWindowShouldClose(window, true);
            }
        });


        //Get resolution of monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());


        //Center the window
        glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );


        //Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);

        if(isVSync())
        {
            glfwSwapInterval(1);
        }


        //Make the window visible
        glfwShowWindow(windowHandle);

        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glEnable(GL_DEPTH_TEST);

    }


    public void update()
    {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    //Check for key presses
    public boolean isKeyPressed(int keyCode)
    {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    //Set clear colour
    public void setClearColour(float r, float g, float b, float alpha)
    {
        glClearColor(r, g, b, alpha);
    }


    //Test whether window should close
    public boolean windowShouldClose()
    {
        return glfwWindowShouldClose(windowHandle);
    }


    //GETTERS AND SETTERS

    //resized
    public void setResized(boolean resized)
    {
        this.resized = resized;
    }

    public boolean isResized()
    {
        return this.resized;
    }


    //vSync
    public void setvSync(boolean vSync)
    {
        this.resized = vSync;
    }

    public boolean isVSync()
    {
        return vSync;
    }


    //width
    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getWidth()
    {
        return this.width;
    }


    //height
    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getHeight()
    {
        return this.height;
    }


    //title
    public String getTitle()
    {
        return this.title;
    }
}
