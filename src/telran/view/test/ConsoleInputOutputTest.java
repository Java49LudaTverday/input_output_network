package telran.view.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.view.console.ConsoleInputOutput;

class ConsoleInputOutputTest {
	ConsoleInputOutput conInOut;

	@BeforeEach
	void setUp() throws Exception {
		conInOut = new ConsoleInputOutput();
	}

	@Test
	void readStringTest() {
		String hello = conInOut.readString("Say HELLO");
		assertEquals("hello", hello);
	}
	@Test
	void writeTest() {
		conInOut.write("Hello");
	
	}
	@Test
	void writeLineTest() {
		conInOut.writeLine("Say hello world!");
	}
	@Test
	void readObjectTest() {
		//TODO
	}
	@Test
	void readIntTest() {
		//TODO
	}@Test
	void readLongTest() {
		//TODO
	}@Test
	void readStringPredTest() {
		//TODO
	}@Test
	void readStringOptionsTest() {
		//TODO
	}@Test
	void readDateTest() {
		//TODO
	}@Test
	void readDoubleTest() {
		//TODO
	}

}
