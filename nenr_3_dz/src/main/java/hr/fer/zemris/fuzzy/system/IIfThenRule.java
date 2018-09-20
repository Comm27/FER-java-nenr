package hr.fer.zemris.fuzzy.system;

import java.util.List;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;

public interface IIfThenRule {

	public IFuzzySet getRuleConclusion(List<DomainElement> domainElements);
}
