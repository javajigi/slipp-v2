package supports;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PagerTest {
	private Pager dut;
	
	@Test
	public void getPageNo() throws Exception {
		int pageNo = 0;
		dut = new Pager(pageNo);
		assertThat(dut.getPageNo(), is(1));
		
		pageNo = 2;
		dut = new Pager(pageNo);
		assertThat(dut.getPageNo(), is(pageNo));
	}
	
	@Test
	public void getOffset() throws Exception {
		int pageNo = 1;
		dut = new Pager(pageNo);
		assertThat(dut.getOffset(), is(0));
		
		pageNo = 2;
		dut = new Pager(pageNo);
		assertThat(dut.getOffset(), is(Pager.DEFAULT_COUNT_PER_PAGE));
	}
	
	@Test
	public void getCountPerPage() throws Exception {
		int pageNo = 1;
		int countPerPage = 5;
		dut = new Pager(pageNo, countPerPage);
		assertThat(dut.getCountPerPage(), is(5));
		
		dut = new Pager(pageNo);
		assertThat(dut.getCountPerPage(), is(Pager.DEFAULT_COUNT_PER_PAGE));
	}
	
	@Test
	public void getPreviousPageNo() throws Exception {
		int pageNo = 1;
		dut = new Pager(pageNo);
		assertThat(dut.getPreviousPageNo(), is(pageNo));
		
		pageNo = 3;
		dut = new Pager(pageNo);
		assertThat(dut.getPreviousPageNo(), is(pageNo-1));
	}
	
	@Test
	public void getNextPageNo() throws Exception {
		int pageNo = 1;
		int resultCount = 5;
		dut = new Pager(pageNo);
		assertThat(dut.getNextPageNo(resultCount), is(pageNo));
		
		resultCount = 20;
		dut = new Pager(pageNo);
		assertThat(dut.getNextPageNo(resultCount), is(pageNo+1));

	}		
}
