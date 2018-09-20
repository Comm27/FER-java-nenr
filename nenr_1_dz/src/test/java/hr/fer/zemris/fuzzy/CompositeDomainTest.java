package hr.fer.zemris.fuzzy;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class CompositeDomainTest {

	private CompositeDomain cd;
	
	@Before
	public void initialize() {
		SimpleDomain sd1 = new SimpleDomain(-2, 2);
		SimpleDomain sd2 = new SimpleDomain(0, 3);
		cd = new CompositeDomain(new SimpleDomain[]{ sd1, sd2 });
	}
	
	@Test
	public void cardinalityComponentsTest() {
		assertEquals(cd.getCardinality(), 12);
		assertEquals(cd.getNumberOfComponents(), 2);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void wrongIndexTest1() {
		cd.elementForIndex(-1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void wrongIndexTest2() {
		cd.elementForIndex(12);
	}
	
	@Test
	public void elementForIndexTest1() {
		assertEquals(cd.elementForIndex(0), DomainElement.of(new int[]{ -2, 0 }));
	}
	
	@Test
	public void elementForIndexTest2() {
		assertEquals(cd.elementForIndex(6), DomainElement.of(new int[]{ 0, 0 }));
	}
	
	@Test
	public void elementForIndexTest3() {
		assertEquals(cd.elementForIndex(11), DomainElement.of(new int[]{ 1, 2 }));
	}
	
	@Test
	public void elementForIndex4() {
		SimpleDomain sd3 = new SimpleDomain(0, 3);
		CompositeDomain cd2 = (CompositeDomain) Domain.combine(sd3, sd3);
		cd2 = (CompositeDomain) Domain.combine(cd2, sd3);
		
		assertEquals(DomainElement.of(new int[]{ 0, 0, 0 }), cd2.elementForIndex(0));
		assertEquals(DomainElement.of(new int[]{ 0, 0, 2 }), cd2.elementForIndex(2));
		assertEquals(DomainElement.of(new int[]{ 0, 1, 1 }), cd2.elementForIndex(4));
		assertEquals(DomainElement.of(new int[]{ 0, 1, 2 }), cd2.elementForIndex(5));
		assertEquals(DomainElement.of(new int[]{ 0, 2, 0 }), cd2.elementForIndex(6));
		assertEquals(DomainElement.of(new int[]{ 0, 2, 2 }), cd2.elementForIndex(8));
		assertEquals(DomainElement.of(new int[]{ 1, 0, 0 }), cd2.elementForIndex(9));
		assertEquals(DomainElement.of(new int[]{ 1, 1, 0 }), cd2.elementForIndex(12));
		assertEquals(DomainElement.of(new int[]{ 2, 0, 0 }), cd2.elementForIndex(18));
		assertEquals(DomainElement.of(new int[]{ 2, 1, 0 }), cd2.elementForIndex(21));
		assertEquals(DomainElement.of(new int[]{ 2, 1, 2 }), cd2.elementForIndex(23));
		assertEquals(DomainElement.of(new int[]{ 2, 2, 2 }), cd2.elementForIndex(26));
	}
	
	@Test
	public void indexOfElemet1() {
		assertEquals(cd.indexOfElement(DomainElement.of(new int[]{ -2, 0 })), 0);
	}
	
	@Test
	public void indexOfElemet2() {
		assertEquals(cd.indexOfElement(DomainElement.of(new int[]{ 0, 0 })), 6);
	}
	
	@Test
	public void indexOfElemet3() {
		assertEquals(cd.indexOfElement(DomainElement.of(new int[]{ 1, 2 })), 11);
	}
	
	@Test
	public void indexOfElemet4() {
		SimpleDomain sd3 = new SimpleDomain(0, 3);
		CompositeDomain cd2 = (CompositeDomain) Domain.combine(sd3, sd3);
		cd2 = (CompositeDomain) Domain.combine(cd2, sd3);
		
		assertEquals(0, cd2.indexOfElement(DomainElement.of(new int[]{ 0, 0, 0 })));
		assertEquals(2, cd2.indexOfElement(DomainElement.of(new int[]{ 0, 0, 2 })));
		assertEquals(4, cd2.indexOfElement(DomainElement.of(new int[]{ 0, 1, 1 })));
		assertEquals(5, cd2.indexOfElement(DomainElement.of(new int[]{ 0, 1, 2 })));
		assertEquals(6, cd2.indexOfElement(DomainElement.of(new int[]{ 0, 2, 0 })));
		assertEquals(8, cd2.indexOfElement(DomainElement.of(new int[]{ 0, 2, 2 })));
		assertEquals(9, cd2.indexOfElement(DomainElement.of(new int[]{ 1, 0, 0 })));
		assertEquals(12, cd2.indexOfElement(DomainElement.of(new int[]{ 1, 1, 0 })));
		assertEquals(18, cd2.indexOfElement(DomainElement.of(new int[]{ 2, 0, 0 })));
		assertEquals(21, cd2.indexOfElement(DomainElement.of(new int[]{ 2, 1, 0 })));
		assertEquals(23, cd2.indexOfElement(DomainElement.of(new int[]{ 2, 1, 2 })));
		assertEquals(26, cd2.indexOfElement(DomainElement.of(new int[]{ 2, 2, 2 })));
	}
	
	@Test(expected=NoSuchElementException.class)
	public void indexOfElemet5() {
		SimpleDomain sd3 = new SimpleDomain(0, 3);
		CompositeDomain cd2 = (CompositeDomain) Domain.combine(sd3, sd3);
		cd2 = (CompositeDomain) Domain.combine(cd2, sd3);
		
		cd2.indexOfElement(DomainElement.of(3, 2, 0));
	}
}
