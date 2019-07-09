package com.gingerwig.voxiverse.engine;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL20.*;



public class ShaderProgram
{
    private final int programID;

    private int vertexShaderID;

    private int fragmentShaderID;

    private Map<String, Integer> uniforms;



    /**
     * Sole constructor:
     * Creates a new OpenGL program object and a map to hold uniforms.
     *
     * @throws Exception    if the shader program cannot be created
     */
    public ShaderProgram() throws Exception
    {
        this.programID = glCreateProgram();

        if(this.programID == 0)
        {
            throw new Exception("Could not create shader");
        }

        uniforms = new HashMap<>();
    }



    /**
     * Creates a shader of type vertex
     *
     * @param shaderCode    the shader file
     * @throws Exception    if the shader cannot be created or compiled
     */
    public void createVertexShader(String shaderCode) throws Exception
    {
        this.vertexShaderID = createShader(shaderCode, GL_VERTEX_SHADER);
    }



    /**
     * Creates a shader of type fragment
     *
     * @param shaderCode    the shader file
     * @throws Exception    if the shader cannot be created or compiled
     */
    public void createFragmentShader(String shaderCode) throws Exception
    {
        this.fragmentShaderID = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }



    /**
     * Creates a new shader object, loads the shader file to the shader object,
     * compile's the shader object and then attaches it to the shader program.
     *
     * @param shaderCode    the shader file to be loaded
     * @param shaderType    the type of shader
     * @return              the shader ID
     * @throws Exception    if the shader cannot be created or compiled
     */
    protected int createShader(String shaderCode, int shaderType) throws Exception
    {
        int shaderID = glCreateShader(shaderType);

        if(shaderID == 0)
        {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0)
        {
            throw new Exception("Error compiling shader. Type: " + shaderType);
        }

        glAttachShader(this.programID, shaderID);

        return shaderID;
    }



    /**
     * Links the program object and creates executables from any shaders
     * that are attached to the program.
     *
     * @throws Exception    if the program object cannot be linked or validated
     */
    public void link() throws Exception
    {
        glLinkProgram(this.programID);

        if(glGetProgrami(this.programID, GL_LINK_STATUS) == 0)
        {
            throw new Exception("Error linking shader code: "
                                    + glGetProgramInfoLog(this.programID, 1024));
        }

        if(this.vertexShaderID != 0)
        {
            glDetachShader(this.programID, this.vertexShaderID);
        }

        if(this.fragmentShaderID != 0)
        {
            glDetachShader(this.programID, this.fragmentShaderID);
        }


        // For debugging, remove in final build
        glValidateProgram(this.programID);

        if(glGetProgrami(this.programID, GL_VALIDATE_STATUS) == 0)
        {
            System.err.println("Warning validating shader program: "
                                    + glGetProgramInfoLog(this.programID, 1024));
        }
    }



    /**
     * Installs the shader program object
     */
    public void bind()
    {
        glUseProgram(this.programID);
    }



    /**
     * Uninstalls the shader program object
     */
    public void unbind()
    {
        glUseProgram(0);
    }



    /**
     * Creates an OpenGL uniform location and adds it along with the uniform
     * name as a map entry.
     *
     * @param uniformName   the uniform name
     * @throws Exception    if the uniform cannot be found
     */
    public void createUniform(String uniformName) throws Exception
    {
        int uniformLocation = glGetUniformLocation(programID, uniformName);

        if(uniformLocation < 0)
        {
            throw new Exception("Could not find uniform:" + uniformName);
        }

        uniforms.put(uniformName, uniformLocation);
    }



    /**
     * Transform the matrix into a 4 x 4 floatbuffer using auto-managed
     * buffers on the stack.
     *
     * @param uniformName   the uniform name
     * @param value         the matrix to be placed in the float buffer
     */
    public void setUniform(String uniformName, Matrix4f value)
    {
        try(MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }



    /**
     * Unbinds the shader program and then deletes it.
     */
    public void cleanup()
    {
        unbind();

        if(this.programID != 0)
        {
            glDeleteProgram(this.programID);
        }
    }
}
