package hr.fer.zemris.fuzzy.ship;

import hr.fer.zemris.fuzzy.norms.ISNorm;
import hr.fer.zemris.fuzzy.norms.ITNorm;
import hr.fer.zemris.fuzzy.system.BaseOfKnowledge;

public class SteeringBaseOfKnowledge extends BaseOfKnowledge {

	private ShipRuleFactory shipRuleFactory;
	
	public SteeringBaseOfKnowledge(ISNorm sNorm, ITNorm tNorm) {
		LinguisticVariables3.S_NORM = sNorm;
		shipRuleFactory = new ShipRuleFactory(tNorm);
		initRules();
	}

	private void initRules() {
		initStrongLeftRules();
		initStrongRightRules();
		initLeftRules();
		initRightRules();
		initOrientationRules();
	}

	private void initStrongLeftRules() {
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"very close",
				"any distance",
				"any distance",
				"any speed",
				"any orientation",
				"strong left"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"any distance",
				"very close",
				"any speed",
				"any orientation",
				"strong left"));
	}

	private void initStrongRightRules() {
		ifThenRules.add(shipRuleFactory.get(
				"very close",
				"any distance",
				"any distance",
				"any distance",
				"any speed",
				"any orientation",
				"strong right"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"very close",
				"any distance",
				"any speed",
				"any orientation",
				"strong right"));
	}

	private void initLeftRules() {
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"close",
				"any distance",
				"any distance",
				"any speed",
				"any orientation",
				"left"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"any distance",
				"close",
				"any speed",
				"any orientation",
				"left"));
	}

	private void initRightRules() {
		ifThenRules.add(shipRuleFactory.get(
				"close",
				"any distance",
				"any distance",
				"any distance",
				"any speed",
				"any orientation",
				"right"));
		
		ifThenRules.add(shipRuleFactory.get(
				"any distance",
				"any distance",
				"close",
				"any distance",
				"any speed",
				"any orientation",
				"right"));
	}
	
	private void initOrientationRules() {
		ifThenRules.add(shipRuleFactory.get(
				"far OR very far",
				"any distance",
				"any distance",
				"any distance",
				"any speed",
				"wrong",
				"strong left"));
		
		ifThenRules.add(shipRuleFactory.get(
				"very close OR close OR medium distance",
				"far OR very far",
				"any distance",
				"any distance",
				"any speed",
				"wrong",
				"strong right"));
	}
}
