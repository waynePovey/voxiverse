package com.gingerwig.voxiverse.engine;

import com.gingerwig.voxiverse.utils.*;

public class GameEngine implements Runnable
{
    private static final int TARGET_FPS = 120;
    private static final int TARGET_UPS = 30;

    private final Thread gameLoopThread;
    private Window window;
    private GameInterface game;
    private Timer timer;


    public GameEngine(String windowTitle, int width, int height, boolean vSync
                        , GameInterface aGame)
    {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        game = aGame;
        timer = new Timer();
    }


    public void start()
    {
        String osName = System.getProperty("os.name");

        if(osName.contains("mac"))
        {
            gameLoopThread.run();
        }
        else
        {
            gameLoopThread.start();
        }
    }


    @Override
    public void run()
    {
        try
        {
            init();
            gameLoop();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            cleanup();
        }
    }


    protected void gameLoop()
    {
        boolean running = true;

        float elapsedTime;
        float accumulatedTime = 0f;
        float updateInterval = 1f / TARGET_UPS;

        while(running && !window.windowShouldClose())
        {
            elapsedTime = timer.getElapsedTime();
            accumulatedTime += elapsedTime;

            input();

            while(accumulatedTime >= updateInterval)
            {
                update(updateInterval);
                accumulatedTime -= updateInterval;
            }

            render();
            sync();
        }
    }


    private void sync()
    {
        double loopLength = 1 / TARGET_FPS;
        double loopStartTime = timer.getLastLoopTime();

        while(timer.getTime() < loopStartTime + loopLength)
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }


    protected void init() throws Exception
    {
        window.init();
        game.init(window);
        timer.init();
    }


    protected void input()
    {
        game.input(window);
    }


    protected void update(float interval)
    {
        game.update(interval);
    }


    protected void render()
    {
        game.render(window);
        window.update();
    }


    protected void cleanup()
    {
        game.cleanup();
    }
}
