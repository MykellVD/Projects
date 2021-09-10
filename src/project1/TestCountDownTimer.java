package project1;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestCountDownTimer {

	@Test
	public void testDefaultConstructor() {
		CountDownTimer s = new CountDownTimer();
		assertTrue(s.getHours() == 0);
		assertTrue(s.getMinutes() == 0);
		assertTrue(s.getSeconds() == 0);
	}

	@Test
	public void testConstructor3Parameters() {
		CountDownTimer s = new CountDownTimer(0, 0, 0);
		assertTrue(s.getHours() == 0);
		assertTrue(s.getMinutes() == 0);
		assertTrue(s.getSeconds() == 0);

		s = new CountDownTimer(2, 3, 4);
		assertTrue(s.getHours() == 2);
		assertTrue(s.getMinutes() == 3);
		assertTrue(s.getSeconds() == 4);
	}

	// Testing for an exception; can only test for 1 at a time;
	// no lines of code after "new CountDownTimer(-2, 3, 4);" will be run
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor3ParametersNegHour() {
		new CountDownTimer(-2, 3, 4);
	}

	// Testing for an exception; can only test for 1 at a time;
	// no lines of code after "new CountDownTimer(2, -3, 4);" will be run
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor3ParametersNegMinute() {
		new CountDownTimer(2, -3, 4);
	}

	// Testing for an exception; can only test for 1 at a time;
	// no lines of code after "new CountDownTimer(2, 3, -4);" will be run
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor3ParametersNegSecond() {
		new CountDownTimer(2, 3, -4);
	}

	// Testing for exceptions; testing all 3 at the same time
	@Test
	public void testConstructor3ParametersNegInput() {
		try {
			new CountDownTimer(-2, 3, 4);
		}
		catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}

		try {
			new CountDownTimer(2, -3, 4);
		}
		catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}

		try {
			new CountDownTimer(2, 3, -4);
		}
		catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}

	// Testing for an exception; no lines of code after
	// "new CountDownTimer(12,67,14);" will be run
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor3LargeMinute() {
		new CountDownTimer(12, 60, 14);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testConstructor3LargeSecond() {
		new CountDownTimer(12, 59, 60);
	}

	// Testing for an exception; no lines of code after
	// "new CountDownTimer("a");" will be run
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorString1ParameterAlpha() {
		new CountDownTimer("a");
	}

	@Test (expected = IllegalArgumentException.class)
	public void testConstructorStringLarge() {
		new CountDownTimer("1:23:45:678");
	}

	@Test
	public void testAdd1() {
		CountDownTimer s = new CountDownTimer();

		s.add(1);
		assertEquals(s.getHours(), 0);
		assertEquals(s.getMinutes(), 0);
		assertEquals(s.getSeconds(), 1);
	}

	@Test
	public void testAdd10() {
		CountDownTimer s = new CountDownTimer();

		s.add(10);
		assertEquals(s.getHours(), 0);
		assertEquals(s.getMinutes(), 0);
		assertEquals(s.getSeconds(), 10);
	}

	@Test
	public void testAdd59() {
		CountDownTimer s = new CountDownTimer();

		s.add(59);
		assertEquals(s.getHours(), 0);
		assertEquals(s.getMinutes(), 0);
		assertEquals(s.getSeconds(), 59);
	}

	@Test
	public void testAdd60() {
		CountDownTimer s = new CountDownTimer();

		// inc to 1 min
		s.add(60);
		assertEquals(s.getHours(), 0);
		assertEquals(s.getMinutes(), 1);
		assertEquals(s.getSeconds(), 0);
	}


	@Test
	public void testDec1Second() {
		CountDownTimer s = new CountDownTimer(1, 59, 59);

		// dec 1
		s.dec();
		assertEquals(s.getHours(), 1);
		assertEquals(s.getMinutes(), 59);
		assertEquals(s.getSeconds(), 58);
	}

	@Test
	public void testEquals() {
		CountDownTimer s1 = new CountDownTimer(5, 59, 30);
		CountDownTimer s2 = new CountDownTimer(6, 01, 20);
		CountDownTimer s3 = new CountDownTimer(5, 59, 30);
		CountDownTimer s4 = new CountDownTimer(5, 59, 20);
		CountDownTimer s5 = new CountDownTimer(5, 40, 30);
		CountDownTimer s6 = new CountDownTimer(4, 59, 30);
		CountDownTimer s7 = new CountDownTimer(5, 40, 20);
		CountDownTimer s8 = new CountDownTimer(4, 59, 20);
		CountDownTimer s9 = new CountDownTimer(4, 40, 30);

		assertFalse(s1.equals(s2));
		assertTrue(s1.equals(s3));
		assertEquals(s3, s1);
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
		assertFalse(s1.equals(s8));
		assertFalse(s1.equals(s9));
	}

	@Test (expected = IllegalArgumentException.class)
	public void testEqualsNull() {
		CountDownTimer s = new CountDownTimer();
		s.equals(null);
	}

	@Test //JU
	public void testToString(){
		CountDownTimer c = new CountDownTimer("5:10:30");
		assertEquals(c.toString(),"5:10:30");

		c = new CountDownTimer("20:10:8");
		assertEquals(c.toString(),"20:10:08");

		c = new CountDownTimer("20:8");
		assertEquals(c.toString(),"0:20:08");

		c = new CountDownTimer("8");
		assertEquals(c.toString(),"0:00:08");
	}

	@Test (expected = NumberFormatException.class) //JU
	public void testToStringBlankHour(){
		CountDownTimer c = new CountDownTimer(":15:30");
	}

	@Test (expected = NumberFormatException.class) //JU
	public void testToStringBlankMin(){
		CountDownTimer c = new CountDownTimer("6::30");
	}

	@Test (expected = NumberFormatException.class) //JU
	public void testToStringBlankSec(){
		CountDownTimer c = new CountDownTimer("6:15:");
	}

	@Test //JU
	public void testAddInt(){
		CountDownTimer c = new CountDownTimer(5,59,45);
		c.add(20);
		assertEquals(c.toString(),"6:00:05");
	}

	@Test (expected = IllegalArgumentException.class) //JU
	public void testAddIntNeg(){
		CountDownTimer c = new CountDownTimer(5,59,45);
		c.add(-56);
	}

	@Test //JU
	public void testStopWatchEquals() {
		CountDownTimer c1 = new CountDownTimer(1,21,31);
		CountDownTimer c2 = new CountDownTimer(1,21,31);
		CountDownTimer c3 = new CountDownTimer(2,32,52);

		assertTrue(CountDownTimer.equals(c1, c2));
		assertFalse(CountDownTimer.equals(c1, c3));
	}

	@Test (expected = NullPointerException.class) //JU
	public void testLoadInvalidFile() {
		CountDownTimer c = new CountDownTimer();
		c.load("FakeFile");

	}

	@Test (expected = NullPointerException.class) //JU
	public void testLoadNullFile() {
		CountDownTimer c = new CountDownTimer();
		c.load(null);

	}

	@Test (expected = IllegalArgumentException.class) //JU
	public void testSaveNullFile() {
		CountDownTimer c = new CountDownTimer();
		c.save(null);
	}

	@Test (expected = IllegalArgumentException.class) //JU
	public void testAdd2() {
		CountDownTimer c = new CountDownTimer();
		c.add(null);
	}

	@Test (expected = IllegalArgumentException.class) //JU
	public void testConstructorStringLargeMinutes() {
		new CountDownTimer("1:72:32");
	}
	@Test (expected = IllegalArgumentException.class) //MVD
	public void testConstructorStringLargeSeconds() {
		new CountDownTimer("1:30:82");
	}

	@Test (expected = IllegalArgumentException.class) //JU
	public void testConstructorStringLongSeconds() {
		new CountDownTimer("1:32:1234");
	}

	@Test (expected = IllegalArgumentException.class) //MVD
	public void testConstructorStringLongMinutes() {
		new CountDownTimer("1:3849:12");
	}

}