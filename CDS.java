/*
 * Used to set an interval between threads and prevent collisions in the backend
 */
package assignment1;
/**
 * @author csf16roc - Conor Roarty B00657634
 */
public class CDS {
    public static void idle (int millisecs) 
    {
        Thread mainThread = Thread.currentThread();
        System.out.println(mainThread.getName() + ": About to sleep");
        try { 
             Thread.sleep(millisecs);
        } catch (InterruptedException e) { }
        System.out.println (mainThread.getName() + ": Woken up");
    }
}
