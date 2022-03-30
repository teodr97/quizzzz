package client.scenes;



import javax.inject.Inject;

public class WaitingRunnable implements Runnable{
    private WaitingRoom controller;
    @Inject
    public WaitingRunnable(WaitingRoom controller){
        this.controller = controller;

    }

    /**
     * Runs this thing
     */
    public void run(){

    }

    /**
     * @return the controller of this runnnable
     */
    public WaitingRoom getController(){
        return this.controller;
    }



}
