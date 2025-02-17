package entities;
 
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
 
public class Camera {
     
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
     
    public Camera(){}
    
    public Camera(Vector3f pos){
    	this.position = pos;
    }
    
    /**
     * Movement of the camera
     */
    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){ //UP
            position.y+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_MINUS)){ //DOWN
            position.y-=0.2f;
        }
        //ROTATE
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)){ //LEFT
            yaw-=0.4f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){ //RIGHT
        	yaw+=0.4f;
        }
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
     
     
 
}
