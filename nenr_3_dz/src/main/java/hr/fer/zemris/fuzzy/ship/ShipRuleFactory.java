package hr.fer.zemris.fuzzy.ship;

import hr.fer.zemris.fuzzy.norms.ITNorm;
import hr.fer.zemris.fuzzy.system.IIfThenRule;
import hr.fer.zemris.fuzzy.system.IIfThenRuleFactory;
import hr.fer.zemris.fuzzy.system.IfThenRuleFactory;

public class ShipRuleFactory {

	private IIfThenRuleFactory ifThenRuleFactory;
	
	public ShipRuleFactory(ITNorm tNorm) {
		ifThenRuleFactory = new IfThenRuleFactory(tNorm);
	}
	
	public IIfThenRule get(
			String left,
			String right,
			String leftAngle,
			String rightAngle,
			String speed,
			String orientation,
			String action) {
		
		return ifThenRuleFactory.createRule(
									LinguisticVariables3.get(action),
									LinguisticVariables3.get(left),
									LinguisticVariables3.get(right),
									LinguisticVariables3.get(leftAngle),
									LinguisticVariables3.get(rightAngle),
									LinguisticVariables3.get(speed),
									LinguisticVariables3.get(orientation));
	}
}
