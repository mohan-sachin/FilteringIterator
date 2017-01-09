package pimco.iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import pimco.test.IObjectTest;

public class FilteringIteratorTest {
	static List<Integer> s_data = Arrays.asList(1,2,3,4,5,6,7,8,9);
	private static HashMap<String, IObjectTest> s_testObjMap = new HashMap<String, IObjectTest>();
	
    static
    {
        s_testObjMap.put("nullTestObj", null);
        
        s_testObjMap.put("allTrueObj", new IObjectTest() {
			@Override
			public boolean test(Object o) 
			{ return true;}});

        s_testObjMap.put("allFalseObj", new IObjectTest() {
			@Override
			public boolean test(Object o) 
			{ return false;}});
        
        s_testObjMap.put("evenTestObj", new IObjectTest() {
			@Override
			public boolean test(Object o) {
				Integer v = (Integer) o;
				return ((v%2) == 0);
			}
		});
    }
	
	@Test
	public void testIterator(){
		Iterator<Integer> myIter = s_data.iterator();
		IObjectTest myTest = s_testObjMap.get("evenTestObj");
		Iterator<Integer> filteringIterator = new FilteringIterator<Integer>(myIter, myTest);
		
		List<Integer> expected = Arrays.asList(2,4,6,8);
		List<Integer> actual = new ArrayList<Integer>();
		while (filteringIterator.hasNext()){
			actual.add(filteringIterator.next());
		}
		Assert.assertArrayEquals(actual.toArray(), expected.toArray());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testEndOfList() {
		Iterator<Integer> myIter = s_data.iterator();
		IObjectTest myTest = s_testObjMap.get("evenTestObj");
		Iterator<Integer> filteringIterator = new FilteringIterator<Integer>(myIter, myTest);
		while (true) {
			filteringIterator.next();
		}
	}

	@Test
	public void testNullTestObj() {
		Iterator<Integer> myIter = s_data.iterator();
		IObjectTest myTest = s_testObjMap.get("nullTestObj");
		Iterator<Integer> filteringIterator = new FilteringIterator<Integer>(myIter, myTest);
		List<Integer> actual = new ArrayList<Integer>();
		while (filteringIterator.hasNext()){
			actual.add(filteringIterator.next());
		}
		Assert.assertArrayEquals(actual.toArray(), s_data.toArray());
	}

	@Test
	public void testAllTrueObj() {
		Iterator<Integer> myIter = s_data.iterator();
		IObjectTest myTest = s_testObjMap.get("allTrueObj");
		Iterator<Integer> filteringIterator = new FilteringIterator<Integer>(myIter, myTest);
		List<Integer> actual = new ArrayList<Integer>();
		while (filteringIterator.hasNext()){
			actual.add(filteringIterator.next());
		}
		Assert.assertArrayEquals(actual.toArray(), s_data.toArray());
	}

	@Test
	public void testAllFalseObj() {
		Iterator<Integer> myIter = s_data.iterator();
		IObjectTest myTest = s_testObjMap.get("allFalseObj");
		Iterator<Integer> filteringIterator = new FilteringIterator<Integer>(myIter, myTest);
		List<Integer> actual = new ArrayList<Integer>();
		while (filteringIterator.hasNext()){
			actual.add(filteringIterator.next());
		}
		Assert.assertEquals(actual.size(), 0);
	}
}
