/*
 * This process refers to each individual TrainA and 
 * the function from TrackTack.java it should call.
 * It will only allow new "runs" to execute if there is space.
 */
package assignment1;
/**
 * @author csf16roc - Conor Roarty B00657634
 */
class TrainAProcess extends Thread {
    String trainName;
    TrainTrack theTrack;

    //initialise (constructor)   
    public TrainAProcess(String trainName, TrainTrack theTrack) {
        this.trainName = trainName;
        this.theTrack = theTrack;
    }

    @Override
    public void run() {   // start train Process
        // wait for clearance before moving on to the track
        theTrack.trainA_MoveOnToTrack(trainName); // move on to track A
        theTrack.trainA_MoveAroundSectionOne(trainName);
        theTrack.MoveAcrossJunctionOne(trainName);
        theTrack.trainA_MoveAroundSectionTwo(trainName);
        theTrack.MoveAcrossJunctionTwo(trainName);
        theTrack.trainA_MoveAroundSectionThree(trainName);
        theTrack.trainA_MoveOffTrack(trainName); // move off the track at 5
    } // end run  

} // end trainAProcess
