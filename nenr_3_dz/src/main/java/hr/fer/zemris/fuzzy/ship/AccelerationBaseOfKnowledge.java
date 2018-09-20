package hr.fer.zemris.fuzzy.ship;


import hr.fer.zemris.fuzzy.norms.ISNorm;
import hr.fer.zemris.fuzzy.norms.ITNorm;
import hr.fer.zemris.fuzzy.system.BaseOfKnowledge;

public class AccelerationBaseOfKnowledge extends BaseOfKnowledge {

	private ShipRuleFactory shipRuleFactory;
	
	public AccelerationBaseOfKnowledge(ISNorm sNorm, ITNorm tNorm) {
		LinguisticVariables3.S_NORM = sNorm;
		shipRuleFactory = new ShipRuleFactory(tNorm);
		initRules();
	}

	private void initRules() {
		initVeryCloseAcc();
		initKeepMediumSpeed();
	}

	private void initVeryCloseAcc() {
		ifThenRules.add(shipRuleFactory.get(
				"very close",
				"any distance",
				"any distance",
				"any distance",
				"any speed",
				"any orientation",
				"accelerate"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"very close",
				"any distance",
				"any distance",
				"any speed",
				"any orientation",
				"accelerate"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"very close",
				"any distance",
				"any speed",
				"any orientation",
				"accelerate"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"any distance",
				"very close",
				"any speed",
				"any orientation",
				"accelerate"));
	}
	
	private void initKeepMediumSpeed() {
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"any distance",
				"any distance",
				"medium speed OR large",
				"any orientation",
				"decelerate"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"any distance",
				"any distance",
				"small",
				"any orientation",
				"accelerate"));
	}
}