<<<<<<< HEAD
package de.tu_darmstadt.gdi1.gorillas.test.students.suites;

import de.tu_darmstadt.gdi1.gorillas.test.students.testcases.ThrowTestGravity;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GorillasTestsuiteExtended3 {

	public static Test suite() {

		TestSuite suite = new TestSuite("Student tests for Gorillas - Extended 3");
		suite.addTest(new JUnit4TestAdapter(ThrowTestGravity.class));
		return suite;
	}
}
=======
package de.tu_darmstadt.gdi1.gorillas.test.students.suites;

import de.tu_darmstadt.gdi1.gorillas.test.students.testcases.ThrowTestGravity;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GorillasTestsuiteExtended3 {

	public static Test suite() {

		TestSuite suite = new TestSuite("Student tests for Gorillas - Extended 3");
		suite.addTest(new JUnit4TestAdapter(ThrowTestGravity.class));
		return suite;
	}
}
>>>>>>> 7f21ad53f21070392f5e9970a5505351eb72a6bf
