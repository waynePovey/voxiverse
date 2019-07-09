package com.gingerwig.voxiverse.utils;

import java.io.InputStream;
import java.util.Scanner;

public class FileLoader
{
    public static String loadResource(String filename)
    {
        String result = "";

        try(InputStream in = Class.forName(FileLoader.class.getName()).getResourceAsStream(filename);
            Scanner sc = new Scanner(in, "UTF-8"))
        {
            result = sc.useDelimiter("\\A").next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }
}
