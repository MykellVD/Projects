package project1;

import java.io.*;
import java.util.Scanner;

public class CountDownTimer {

	private int hours;
	private int minutes;
	private int seconds;

	private static boolean suspend = false;

	public CountDownTimer() {
		hours = 0;
		minutes = 0;
		seconds = 0;
	}

	public CountDownTimer(int hours, int minutes, int seconds) {
		if (hours > -1) {
			this.hours = hours;
		} else {
			throw new IllegalArgumentException("Hours cannot be negative");
		}

		if (minutes > -1) {
			if (minutes < 60) {
				this.minutes = minutes;
			}
			else {
				throw new IllegalArgumentException("Minutes cannot be greater than 59");
			}
		} else {
			throw new IllegalArgumentException("Minutes cannot be negative");
		}

		if (seconds > -1) {
			if (seconds < 60) {
				this.seconds = seconds;
			}
			else {
				throw new IllegalArgumentException("Seconds cannot be greater than 59");
			}
		} else {
			throw new IllegalArgumentException("Seconds cannot be negative");
		}
	}

	public CountDownTimer(int minutes, int seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
		hours = 0;
	}

	public CountDownTimer(int seconds) {
		this.seconds = seconds;
		hours = 0;
		minutes = 0;
	}

	public CountDownTimer(CountDownTimer other) {
		other.seconds = this.seconds;
		other.minutes = this.minutes;
		other.hours = this.hours;
	}

	public CountDownTimer(String startTime) {
		int startTimeLen = startTime.length();
		if (startTimeLen > 8)
			throw new IllegalArgumentException();
		else {
			String[] startTimeSplit = startTime.split(":");
			if (startTimeSplit.length == 1 && ) //fix
		}

		if (startTimeLen <= 2) {
			this.seconds = Integer.parseInt(startTime);
		}

		else if (startTimeLen > 3 && startTimeLen <= 5) {
			System.out.println(startTimeSplit[0] + "  " + startTimeSplit[1]);
			this.minutes = Integer.parseInt(startTimeSplit[0]);
			this.seconds = Integer.parseInt(startTimeSplit[1]);
		}

		else if (startTimeLen > 6 && startTimeLen <= 8) {
			String[] startTimeSplit = startTime.split(":");
			System.out.println(startTimeSplit[0] + "  " + startTimeSplit[1] + "  " + startTimeSplit[2]);
			this.hours = Integer.parseInt(startTimeSplit[0]);

			if (Integer.parseInt(startTimeSplit[1]) > 59)
				throw new IllegalArgumentException();
			this.minutes = Integer.parseInt(startTimeSplit[1]);

			if (Integer.parseInt(startTimeSplit[2]) > 59)
				throw new IllegalArgumentException();
			this.seconds = Integer.parseInt(startTimeSplit[2]);
		}
		else {
			this.hours = 0;
			this.minutes = 0;
			this.seconds = 0;
		}
	}

	public boolean equals(Object other) {
		boolean rtn = false;
		if (other == null){
			throw new IllegalArgumentException();
		}
		else if (other instanceof CountDownTimer){
			if(this.hours == ((CountDownTimer) other).hours &&
					this.minutes == ((CountDownTimer) other).minutes &&
					this.seconds == ((CountDownTimer) other).seconds) {
				rtn = true;
			}
			else {
				rtn = false;
			}
		}
		return rtn;
	}

	public static boolean equals (CountDownTimer t1, CountDownTimer t2) {
		if (t1.equals(t2)){
			return true;
		}
		return false;
	}

	public int compareTo(CountDownTimer other) {
		if (this.hours > other.hours && this.minutes > other.minutes && this.seconds > other.seconds) {
			return 1;
		}
		else if (this.hours < other.hours && this.minutes < other.minutes && this.seconds < other.seconds) {
			return -1;
		}
		else {
			return 0;
		}
	}

	public static int compareTo(CountDownTimer t1, CountDownTimer t2) {
		if (t1.hours > t2.hours && t1.minutes > t2.minutes && t1.seconds > t2.seconds) {
			return 1;
		}
		else if (t1.hours < t2.hours && t1.minutes < t2.minutes && t1.seconds < t2.seconds) {
			return -1;
		}
		else {
			return 0;
		}
	}

	public void sub(int seconds) {
		this.seconds -= seconds % 60;
		this.minutes -= (seconds / 60) % 60;
		this.hours -= (seconds / 60) / 60;
	}

	public void add(int seconds) {
		if(suspend == false) {
			if(seconds < 0) {
				throw new IllegalArgumentException();
			}else {
				for(int i=0;i<seconds;i++) {
					inc();
				}
			}
		}
	}

	public void inc(){
		if(suspend == false) {
			if(seconds == 59) {
				seconds = 0;
				if(minutes == 59) {
					seconds = 0;
					hours += 1;
				}else {
					minutes += 1;
				}
			}else {
				seconds += 1;
			}
		}
	}

	public void dec() {
		if(suspend == false) {
			if(seconds == 0) {
				if(minutes == 0) {
					if(hours == 0){
						hours = 0;
						minutes = 0;
						seconds = 0;
					}else{
						hours -= 1;
						minutes = 59;
						seconds = 59;
					}
				}else {
					minutes -= 1;
					seconds = 59;
				}
			}else {
				seconds -= 1;
			}
		}
	}

	public void add(CountDownTimer other) {
		if (other == null)
			throw new IllegalArgumentException();
		this.hours += other.hours;
		this.minutes += other.minutes;
		this.seconds += other.seconds;
	}

	public String toString() {
		String fmStr = this.hours + ":";
		if (this.minutes < 10) {
			fmStr += "0" + this.minutes + ":";
		}
		else {
			fmStr += this.minutes + ":";
		}
		if (this.seconds < 10) {
			fmStr += "0" + this.seconds;
		}
		else {
			fmStr += this.seconds;
		}

		return fmStr;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void save(String fileName){
		if (fileName == null)
			throw new IllegalArgumentException();
		PrintWriter out = null;
		try {
			out = new PrintWriter (new BufferedWriter(new FileWriter(fileName)));
			out.println(this.hours + "" + this.minutes + "" + this.seconds);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	public void load(String fileName){
		if (fileName == null)
			throw new NullPointerException();

		Scanner scanner = null;
		try{
			scanner = new Scanner(new File(fileName));

			this.hours = scanner.nextInt();
			this.minutes = scanner.nextInt();
			this.seconds = scanner.nextInt();
		}
		catch (FileNotFoundException e){
			throw new NullPointerException();
		}
		finally {
			scanner.close();
		}
	}

	//turns on and off all stopWatch object
	public static void setSuspend(boolean suspend) {
		CountDownTimer.suspend = suspend;
	}

	//returns suspend
	public static boolean isSuspended() {
		return suspend;
	}
}