package testing.junit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	public static void main(String[] args){
		//First Test - Board Algorithms
		Result boardTestResult = JUnitCore.runClasses(BoardTesting.class);
		
		for (Failure fail: boardTestResult.getFailures()){
			System.out.println(fail.toString());
		}
		
		System.out.println(boardTestResult.wasSuccessful());
	}
	
}
