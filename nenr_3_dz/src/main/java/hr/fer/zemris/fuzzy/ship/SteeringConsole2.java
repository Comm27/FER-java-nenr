package hr.fer.zemris.fuzzy.ship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.norms.ISNorm;
import hr.fer.zemris.fuzzy.norms.MaxSNorm;
import hr.fer.zemris.fuzzy.norms.MinTNorm;
import hr.fer.zemris.fuzzy.system.IBaseOfKnowledge;
import hr.fer.zemris.fuzzy.system.IDefuzzyfier;
import hr.fer.zemris.fuzzy.system.IIfThenRule;

public class SteeringConsole2 {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			
			IBaseOfKnowledge bok = new SteeringBaseOfKnowledge(new MaxSNorm(), new MinTNorm());
			IDefuzzyfier def = new COADefuzzyfier();
			
			while (true) {
				try {
					System.out.println("Zadajte: L, D, LK, DK, V, S. Razdvojite ih razmacima.");
					String[] params = br.readLine().split("\\s+");
					
					int l = Integer.parseInt(params[0]);
					int d = Integer.parseInt(params[1]);
					int lk = Integer.parseInt(params[2]);
					int dk = Integer.parseInt(params[3]);
					int v = Integer.parseInt(params[4]);
					int s = Integer.parseInt(params[5]);
					
					
					
					print(conclude(bok.getRules(), crispValuesToDomainElements(l, d, lk, dk, v, s)), "Rezultat sviju pravila:");
					System.out.println(def.defuzzyfie(conclude(bok.getRules(), crispValuesToDomainElements(l, d, lk, dk, v, s))));
					
				} catch (Exception e) {
					System.out.println("Pogreška pri unosu parametara, pokušajte ponovo.");
					continue;
				}
			}
		} catch (IOException e) {
			System.out.println("Ne mogu čitati.");
			return;
		}
	}
	
	public static List<DomainElement> crispValuesToDomainElements(int... values) {
		List<DomainElement> domainElements = new ArrayList<>();
		
		for (int i = 0; i < values.length; i++) {
			domainElements.add(DomainElement.of(values[i]));
		}
		
		return domainElements;
	}
	
	public static void print(IFuzzySet set, String header) {
		if (header != null) {
			System.out.println(header);
		}
		
		for (DomainElement domainElement : set.getDomain()) {
			System.out.println("d(" + domainElement + ")=" + set.getValueAt(domainElement));
		}
		System.out.println("");
	}
	
	public static IFuzzySet conclude(List<IIfThenRule> ifThenRules, List<DomainElement> domainElements) {
		List<IFuzzySet> rulesResults = new ArrayList<>();
		ISNorm sNorm = new MaxSNorm();
		
		for (IIfThenRule ifThenRule : ifThenRules) {
			rulesResults.add(ifThenRule.getRuleConclusion(domainElements));
		}
		
		IFuzzySet resultSet = rulesResults.get(0);
		for (IFuzzySet iFuzzySet : rulesResults) {
			resultSet = sNorm.calculate(resultSet, iFuzzySet);
		}
		
		return resultSet;
	}
}
