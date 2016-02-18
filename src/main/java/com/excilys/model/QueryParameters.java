package com.excilys.model;

/**
 * Class that represents all the parameters that we need to make a query
 * 
 * @author B. Herbaut
 */
public class QueryParameters {

	int limit;
	int pageSize;
	int pageNumber;

	public QueryParameters() {
		this(1,10);
	}

	/**
	 * Create a queryParameter object with the given parameters. If you don't
	 * want to set some parameters just put -1 or null.
	 * 
	 * @param pageNumber
	 * @param pageSize
	 */
	public QueryParameters(int pageNumber, int pageSize) {
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.limit = (pageNumber -1)* pageSize;
	}

	public int getLimit() {
		return limit;
	}

	public int getPageSize(){
		return pageSize;
	}
	
	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
		this.limit = (pageNumber -1)* pageSize;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		this.limit = (pageNumber -1)* pageSize;
	}

}
