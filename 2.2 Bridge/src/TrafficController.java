public class TrafficController {
    private volatile boolean car_on_bridge = false;
    private Object control = new Object();


    // Red car enter from left
    public void enterLeft() {
        crossing();
    }


    // Blue car enter from right
    public void enterRight() {
        crossing();
    }


    // Blue car leave on the left
    // boolean value is set false
    public  void leaveLeft() {
        setBool(false);

    }


    // Red car leave on the right
    // boolean value is set false
    public  void leaveRight() {
        setBool(false);
    }


    // method returns the most updated boolean value of car_on_bridge
    // synchronize that way only one thread can retrieve this value
    public boolean getBool() {
        synchronized (control) {
            return car_on_bridge;
        }
    }


    // method sets the boolean value to be true or false
    // synchronize to let only one thread access at a time
    public void setBool(boolean f){
        synchronized (control) {
            this.car_on_bridge = f;
        }
    }


    // Synchronize crossing so that only one car can cross
    public synchronized void crossing(){
        while(car_on_bridge){
            car_on_bridge = getBool();
        }
        car_on_bridge = true;
    }

}