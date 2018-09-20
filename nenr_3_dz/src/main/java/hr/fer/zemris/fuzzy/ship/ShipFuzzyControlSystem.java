package hr.fer.zemris.fuzzy.ship;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.norms.ISNorm;
import hr.fer.zemris.fuzzy.system.BaseOfKnowledge;
import hr.fer.zemris.fuzzy.system.FuzzyControlSystem;
import hr.fer.zemris.fuzzy.system.IDefuzzyfier;

public class ShipFuzzyControlSystem extends FuzzyControlSystem {

	public ShipFuzzyControlSystem(BaseOfKnowledge baseOfKnowledge, IDefuzzyfier defuzzyfier, ISNorm sNorm) {
		super(baseOfKnowledge, defuzzyfier, sNorm);
	}
	
	@Override
	public List<DomainElement> crispValuesToDomainElements(int... values) {
		List<DomainElement> domainElements = new ArrayList<>();
		
		for (int i = 0; i < values.length; i++) {
			domainElements.add(DomainElement.of(values[i]));
		}
		
		return domainElements;
	}
}
