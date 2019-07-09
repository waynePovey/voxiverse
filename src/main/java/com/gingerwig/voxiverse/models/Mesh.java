package com.gingerwig.voxiverse.models;

import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;



public class Mesh
{
    private int vaoID;

    private int posVboID;

    private int inxVboID;

    private int colVboID;

    private int vertexCount;



    public Mesh(float[] positions, int[] indices, float[] colours)
    {

        FloatBuffer verticesBuffer = null;

        IntBuffer indicesBuffer = null;

        FloatBuffer coloursBuffer = null;

        try
        {
            vertexCount = indices.length;

            //Create VAO
            vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);


            //Create positions VBO
            posVboID = glGenBuffers();
            verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
            verticesBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, posVboID);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);


            //Create indices VBO
            inxVboID = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, inxVboID);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);


            //Create colours VBO
            colVboID = glGenBuffers();
            coloursBuffer = MemoryUtil.memAllocFloat(colours.length);
            coloursBuffer.put(colours).flip();
            glBindBuffer(GL_ARRAY_BUFFER, colVboID);
            glBufferData(GL_ARRAY_BUFFER, coloursBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);


            //Unbind VBOs & VAO
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
        finally
        {
            if(verticesBuffer != null)
            {
                memFree(verticesBuffer);
            }

            if(indicesBuffer != null)
            {
                memFree(indicesBuffer);
            }

            if(coloursBuffer != null)
            {
                memFree(coloursBuffer);
            }
        }
    }



    /**
     * Getter for vaoID
     *
     * @return
     */
    public int getVaoID()
    {
        return vaoID;
    }



    /**
     * Getter for vertexCount
     *
     * @return  the number of vertices.
     */
    public int getVertexCount()
    {
        return vertexCount;
    }



    /**
     *
     */
    public void cleanup()
    {
        glDisableVertexAttribArray(0);


        //Delete positions VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(posVboID);
        glDeleteBuffers(inxVboID);
        glDeleteBuffers(colVboID);

        //Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoID);
    }
}
