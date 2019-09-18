package com.gingerwig.voxiverse.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation
{
    private Matrix4f projectionMatrix;

    private Matrix4f worldMatrix;

    public Transformation()
    {
        this.projectionMatrix = new Matrix4f();
        this.worldMatrix = new Matrix4f();
    }


    public final Matrix4f getProjectionMatrix(float fov, float width, float height,
                                                float zNear, float zFar)
    {
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);

        return projectionMatrix;
    }


    public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale)
    {
        worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.y)).
                scale(scale);

        return worldMatrix;
    }
}
