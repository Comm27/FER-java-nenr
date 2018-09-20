package hr.fer.zemris.fuzzy.system;

import java.util.List;

public interface IBaseOfKnowledge {

	public int getSize();
	public List<IIfThenRule> getRules();
	public IIfThenRule getRule(int index);
}
