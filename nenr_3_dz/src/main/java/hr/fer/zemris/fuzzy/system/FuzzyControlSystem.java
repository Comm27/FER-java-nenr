package hr.fer.zemris.fuzzy.system;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.norms.ISNorm;

public abstract class FuzzyControlSystem implements IFuzzyControlSystem {

	private IBaseOfKnowledge baseOfKnowledge;
	private IDefuzzyfier defuzzyfier;
	
	private ISNorm sNorm;
	
	public FuzzyControlSystem(IBaseOfKnowledge baseOfKnowledge, IDefuzzyfier defuzzyfier, ISNorm sNorm) {
		this.baseOfKnowledge = baseOfKnowledge;
		this.defuzzyfier = defuzzyfier;
		
		this.sNorm = sNorm;
	}

	@Override
	public int getOutput(int... values) {
		return defuzzyfier.defuzzyfie(conclude(baseOfKnowledge.getRules(), crispValuesToDomainElements(values)));
	}
	
	private IFuzzySet conclude(List<IIfThenRule> ifThenRules, List<DomainElement> domainElements) {
		List<IFuzzySet> rulesResults = new ArrayList<>();
		
		for (IIfThenRule ifThenRule : ifThenRules) {
			rulesResults.add(ifThenRule.getRuleConclusion(domainElements));
		}
		
		IFuzzySet resultSet = rulesResults.get(0);
		for (IFuzzySet iFuzzySet : rulesResults) {
			resultSet = sNorm.calculate(resultSet, iFuzzySet);
		}
		
		return resultSet;
	}
	
	public abstract List<DomainElement> crispValuesToDomainElements(int ... values);
}
