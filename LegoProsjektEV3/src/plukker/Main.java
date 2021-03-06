package plukker;

//import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.utility.Delay;

class Main {
    public static void main(String[] arg) throws Exception {
	float searchRadius = 60f;
	//float sideOfSquare = (float) ((searchRadius * 2) * Math.sqrt(2));
	float ballSize = 50f;
	int ballTarget = 4;
	int ballsFound = 0;
	Sensor sensor = new Sensor();
	Pickup pickUp = new Pickup();
	Mover motor = new Mover(sensor, pickUp);
	BTConnectEV3 nxt = new BTConnectEV3();
	Thread btThread = new Thread(nxt);
	btThread.start();
	// motor.testRotate();
	Radar radar = new Radar(sensor, motor, searchRadius, ballSize);
	// radar.findPoints();
	// LCD.drawString("Press enter to start", 0, 4);
	// Button.ENTER.waitForPressAndRelease();
	// radar.navigate();

	// motor.start();
	// EV3 ev3 = (EV3) BrickFinder.getLocal();
	boolean fortsett = true;
	while (fortsett) {
	    if (radar.getRemaining() == 0) {
		if (motor.goToCentre(searchRadius)){
		    radar.findPoints();
		}else{
		    radar.addPoint(motor.getPointAt(sensor.getDistance(), motor.getHeading()));
		}
		
	    }else if (motor.fetchBall(radar.getClosestPoint(true))) {
		ballsFound++;
		motor.goHome();
		nxt.setReady();
		if (ballsFound >= ballTarget) {
		    fortsett = false;
		    break;
		}
		motor.resetHome();
	    }
	    
	    if (Button.ESCAPE.isDown())
		fortsett = false;
	    if (Button.ENTER.isDown()) {
		motor.setPose0();
		sensor.resetGyro();
		radar.findPoints();
		radar.navigate();
	    }
	    Thread.sleep(50);
	}
	//motor.terminate();
	Delay.msDelay(5000);
	nxt.setDone();
	//motor.join();
	btThread.join();
    }
}