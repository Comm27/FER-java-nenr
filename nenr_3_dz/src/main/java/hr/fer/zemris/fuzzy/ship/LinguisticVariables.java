package hr.fer.zemris.fuzzy.ship;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.fuzzy.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.StandardFuzzySets;
import hr.fer.zemris.fuzzy.norms.ISNorm;

public class LinguisticVariables {

	private static Map<String, IFuzzySet> VARIABLES;
	public static ISNorm S_NORM;
	private static final String OR = "OR";
	
	static {
		VARIABLES = new HashMap<>();
		initPositioningVariables();
		initSteeringVariables();
		initSpeedVariables();
		initOrientationVaribales();
		initAccelerationVariables();
	}
	
	public static IFuzzySet get(String linguisticVariable) {
		String[] vars = linguisticVariable.split(OR);
		
		IFuzzySet resultSet = VARIABLES.get(vars[0].trim());
		for (String var : vars) {
			resultSet = S_NORM.calculate(resultSet, VARIABLES.get(var.trim()));
		}
		
		return resultSet;
	}

	private static void initPositioningVariables() {
		CalculatedFuzzySet veryClosePosition = 
				new CalculatedFuzzySet(
						ShipDomains.POSITION_DOMAIN,
						StandardFuzzySets.lFunction(
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(15)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(30))));
		
		CalculatedFuzzySet closePosition = 
				new CalculatedFuzzySet(
						ShipDomains.POSITION_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(20)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(30)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(40))));
		
		CalculatedFuzzySet midPosition = 
				new CalculatedFuzzySet(
						ShipDomains.POSITION_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(30)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(40)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(50))));
		
		CalculatedFuzzySet farPosition = 
				new CalculatedFuzzySet(
						ShipDomains.POSITION_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(40)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(60)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(80))));
		
		CalculatedFuzzySet veryFarPosition = 
				new CalculatedFuzzySet(
						ShipDomains.POSITION_DOMAIN,
						StandardFuzzySets.gammaFunction(
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(70)),
								ShipDomains.POSITION_DOMAIN.indexOfElement(DomainElement.of(90))));
		
		MutableFuzzySet anyDistance = new MutableFuzzySet(ShipDomains.POSITION_DOMAIN);
		for (DomainElement element : ShipDomains.POSITION_DOMAIN) {
			anyDistance.set(element, 1d);
		}
		
		VARIABLES.put("very close", veryClosePosition);
		VARIABLES.put("close", closePosition);
		VARIABLES.put("medium distance", midPosition);
		VARIABLES.put("far", farPosition);
		VARIABLES.put("very far", veryFarPosition);
		VARIABLES.put("any distance", anyDistance);
	}
	
	private static void initSteeringVariables() {
		CalculatedFuzzySet strongRightSteer = 
				new CalculatedFuzzySet(
						ShipDomains.STEERING_DOMAIN,
						StandardFuzzySets.lFunction(
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-90)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-70))));
		
		CalculatedFuzzySet rightSteer = 
				new CalculatedFuzzySet(
						ShipDomains.STEERING_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-75)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-50)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-25))));
		
		CalculatedFuzzySet lightRightSteer = 
				new CalculatedFuzzySet(
						ShipDomains.STEERING_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-30)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-20)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-10))));
		
		CalculatedFuzzySet forwardSteer = 
				new CalculatedFuzzySet(
						ShipDomains.STEERING_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(-15)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(0)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(15))));
		
		CalculatedFuzzySet lightLeftSteer = 
				new CalculatedFuzzySet(
						ShipDomains.STEERING_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(10)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(20)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(30))));
		
		CalculatedFuzzySet leftSteer = 
				new CalculatedFuzzySet(
						ShipDomains.STEERING_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(25)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(70)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(75))));
		
		CalculatedFuzzySet strongLeftSteer = 
				new CalculatedFuzzySet(
						ShipDomains.STEERING_DOMAIN,
						StandardFuzzySets.gammaFunction(
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(70)),
								ShipDomains.STEERING_DOMAIN.indexOfElement(DomainElement.of(90))));
		
		VARIABLES.put("strong right", strongRightSteer);
		VARIABLES.put("right", rightSteer);
		VARIABLES.put("light right", lightRightSteer);
		VARIABLES.put("forward", forwardSteer);
		VARIABLES.put("light left", lightLeftSteer);
		VARIABLES.put("left", leftSteer);
		VARIABLES.put("strong left", strongLeftSteer);	
	}
	
	private static void initSpeedVariables() {
		CalculatedFuzzySet verySmall = 
				new CalculatedFuzzySet(
						ShipDomains.SPEED_DOMAIN,
						StandardFuzzySets.lFunction(
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(7)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(15))));
		
		CalculatedFuzzySet small = 
				new CalculatedFuzzySet(
						ShipDomains.SPEED_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(10)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(20)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(28))));
		
		CalculatedFuzzySet medium = 
				new CalculatedFuzzySet(
						ShipDomains.SPEED_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(22)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(35)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(45))));
		
		CalculatedFuzzySet large = 
				new CalculatedFuzzySet(
						ShipDomains.SPEED_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(40)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(50)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(60))));
		
		CalculatedFuzzySet veryLarge = 
				new CalculatedFuzzySet(
						ShipDomains.SPEED_DOMAIN,
						StandardFuzzySets.gammaFunction(
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(55)),
								ShipDomains.SPEED_DOMAIN.indexOfElement(DomainElement.of(70))));
		
		MutableFuzzySet dontCare = new MutableFuzzySet(ShipDomains.SPEED_DOMAIN);
		for (DomainElement domainElement : ShipDomains.SPEED_DOMAIN) {
			dontCare.set(domainElement, 1d);
		}
		
		VARIABLES.put("very small", verySmall);
		VARIABLES.put("small", small);
		VARIABLES.put("medium speed", medium);
		VARIABLES.put("large", large);
		VARIABLES.put("very large", veryLarge);
		VARIABLES.put("any speed", dontCare);
	}
	
	private static void initOrientationVaribales() {
		MutableFuzzySet wrongOrientation = new MutableFuzzySet(ShipDomains.ORIENTATION_DOMAIN);
		wrongOrientation.set(DomainElement.of(0), 1d);
		wrongOrientation.set(DomainElement.of(1), 0d);
		
		MutableFuzzySet correctOrientation = new MutableFuzzySet(ShipDomains.ORIENTATION_DOMAIN);
		correctOrientation.set(DomainElement.of(0), 0d);
		correctOrientation.set(DomainElement.of(1), 1d);
		
		MutableFuzzySet anyOrientation = new MutableFuzzySet(ShipDomains.ORIENTATION_DOMAIN);
		anyOrientation.set(DomainElement.of(0), 1d);
		anyOrientation.set(DomainElement.of(1), 1d);
		
		VARIABLES.put("wrong", wrongOrientation);
		VARIABLES.put("correct", correctOrientation);
		VARIABLES.put("any orientation", anyOrientation);
	}
	
	private static void initAccelerationVariables() {
		CalculatedFuzzySet strongDecelerate = 
				new CalculatedFuzzySet(
						ShipDomains.ACCELERATION_DOMAIN,
						StandardFuzzySets.lFunction(
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-49)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-35))));
		
		CalculatedFuzzySet decelerate = 
				new CalculatedFuzzySet(
						ShipDomains.ACCELERATION_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-37)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-25)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-15))));
		
		CalculatedFuzzySet stay = 
				new CalculatedFuzzySet(
						ShipDomains.ACCELERATION_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-20)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(0)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(20))));
		
		CalculatedFuzzySet accelerate = 
				new CalculatedFuzzySet(
						ShipDomains.ACCELERATION_DOMAIN,
						StandardFuzzySets.lambdaFunction(
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(15)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(25)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(37))));
		
		CalculatedFuzzySet strongAccelerate = 
				new CalculatedFuzzySet(
						ShipDomains.ACCELERATION_DOMAIN,
						StandardFuzzySets.gammaFunction(
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(35)),
								ShipDomains.ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(49))));
		
		VARIABLES.put("strong decelerate", strongDecelerate);
		VARIABLES.put("decelerate", decelerate);
		VARIABLES.put("stay", stay);
		VARIABLES.put("accelerate", accelerate);
		VARIABLES.put("strong accelerate", strongAccelerate);
	}
}