package hr.fer.zemris.fuzzy;

public class Operations {

	public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction unaryFunction) {
		MutableFuzzySet mfSet = new MutableFuzzySet(set.getDomain());
		
		for (DomainElement domainElement : set.getDomain()) {
			mfSet.set(domainElement, unaryFunction.valueAt(set.getValueAt(domainElement)));
		}
		
		return mfSet;
	}
	
	public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction binaryFunction) {
		MutableFuzzySet mfSet = new MutableFuzzySet(set1.getDomain());
		
		for (DomainElement domainElement : set1.getDomain()) {
			mfSet.set(domainElement, binaryFunction.valueAt(set1.getValueAt(domainElement), set2.getValueAt(domainElement)));
		}
		
		return mfSet;
	}
	
	public static IUnaryFunction zadehNot() {
		return value -> 1 - value;
	}
	
	public static IBinaryFunction zadehAnd() {
		return (value1, value2) -> value1 > value2 ? value2 : value1;
	}
	
	public static IBinaryFunction zadehOr() {
		return (value1, value2) -> value1 > value2 ? value1 : value2;
	}
	
	public static IBinaryFunction hamacherTNorm(double value) {
		return (value1, value2) -> {
			return (value1 * value2) / (value + (1 - value) * (value1 + value2 - value1 * value2));
		};
	}
	
	public static IBinaryFunction hamacherSNorm(double value) {
		return (value1, value2) -> {
			return (value1 + value2 - (2 - value) * value1 * value2) / (1 - (1 - value) * value1 * value2);
		};
	}
}
