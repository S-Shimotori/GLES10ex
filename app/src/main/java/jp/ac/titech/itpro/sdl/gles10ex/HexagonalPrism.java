package jp.ac.titech.itpro.sdl.gles10ex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by S-Shimotori on 5/27/16.
 */
public class HexagonalPrism implements SimpleRenderer.Obj {

    private FloatBuffer vbuf;
    private float x, y, z;
    private static float ROOT3 = (float) Math.sqrt(3);

    public HexagonalPrism(float s, float x, float y, float z) {

        float[] vertices = {
                /*
                   1
                2／＼3  3_1  1_2
                |   |  |_|  |_|
                5＼／4  4 6  6 5
                  6
                 */
                // top
                s,      ROOT3 * s,  s,  //1
                0,      0,          s,  //2
                2 * s,  0,          s,  //3

                // bottom
                0,      0,          -s, //5
                2 * s,  0,          -s, //4
                s,      ROOT3 * s,  -s, //6

                //side
                2 * s,  0,          s,  //3
                s,      ROOT3 * s,  s,  //1
                2 * s,  0,          -s, //4
                s,      ROOT3 * s,  -s, //6

                s,      ROOT3 * s,  s,  //1
                0,      0,          s,  //2
                s,      ROOT3 * s,  -s, //6
                0,      0,          -s, //5
        };

        vbuf = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vbuf.put(vertices);
        vbuf.position(0);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vbuf);

        //top
        gl.glNormal3f(0, 0, 1);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);

        //bottom
        gl.glNormal3f(0, 0, -1);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 3, 3);

        //side
        gl.glNormal3f(0, -1, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 1, 4);
        gl.glNormal3f(ROOT3, 1, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 6, 4);
        gl.glNormal3f(-ROOT3, 1, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 10, 4);
    }
    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }
}
