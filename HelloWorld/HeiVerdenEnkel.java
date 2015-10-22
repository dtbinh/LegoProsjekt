package HelloWorld;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;

public class HeiVerdenEnkel {
	public static void main(String[] args)  throws Exception{
		System.out.println("Hei EV3 - du er kulest n�r du virker!");
		Thread.sleep(500);

		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		Keys keys = ev3.getKeys();

		lcd.drawString("Hello World", 4, 4);
		keys.waitForAnyPress();
	}
}

