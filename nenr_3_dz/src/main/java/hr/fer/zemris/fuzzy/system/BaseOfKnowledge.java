package hr.fer.zemris.fuzzy.system;

import java.util.ArrayList;
import java.util.List;

public class BaseOfKnowledge implements IBaseOfKnowledge {

	protected List<IIfThenRule> ifThenRules;
	
	public BaseOfKnowledge() {
		ifThenRules = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return ifThenRules.size();
	}

	@Override
	public List<IIfThenRule> getRules() {
		return ifThenRules;
	}

	@Override
	public IIfThenRule getRule(int index) {
		return ifThenRules.get(index);
	}
}
