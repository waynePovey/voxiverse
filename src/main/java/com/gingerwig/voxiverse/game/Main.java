package com.gingerwig.voxiverse.game;

import com.gingerwig.voxiverse.engine.GameEngine;
import com.gingerwig.voxiverse.engine.GameInterface;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            boolean vSync = true;
            GameInterface game = new Game();
            GameEngine engine = new GameEngine("First game", 1600, 900, vSync, game);
            engine.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
