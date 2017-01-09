package pimco.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import pimco.test.IObjectTest;

public class FilteringIterator<T> implements Iterator<T> {

	Iterator<T> m_iter;
	IObjectTest m_tester;
	T m_nextObj;
	
	public FilteringIterator(Iterator<T> myIterator, IObjectTest myTest){
		m_iter = myIterator;
		m_tester = myTest;
	}
	
	@Override
	public boolean hasNext() {
		while (m_iter.hasNext()) {
			T nextObj = m_iter.next();
			if (m_tester == null || m_tester.test(nextObj)) {
				m_nextObj = nextObj;
				return true;
			}
		}
		return false;
	}

	@Override
	public T next() {
		T nextObj = null;
		if (m_nextObj == null) {
			if (hasNext() == false) {
				throw new NoSuchElementException("Reached end of list");
			} else {
				nextObj = m_nextObj;
				m_nextObj = null;
			}
		} else {
			nextObj = m_nextObj;
		}
		return nextObj;
	}

}
