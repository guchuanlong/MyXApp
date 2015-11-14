package com.myunihome.myxapp.paas.search.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SearchOption implements Serializable {
	private static final long serialVersionUID = 1L;
	private SearchLogic searchLogic = SearchLogic.must;
	private SearchType searchType = SearchType.querystring;
	private DataFilter dataFilter = DataFilter.exists;

	private String queryStringPrecision = "100";

	private float boost = 1.0F;
	private boolean highlight = false;

	public SearchOption(SearchType searchType, SearchLogic searchLogic, String queryStringPrecision,
			DataFilter dataFilter, float boost, int highlight) {
		setSearchLogic(searchLogic);
		setSearchType(searchType);
		setQueryStringPrecision(queryStringPrecision);
		setDataFilter(dataFilter);
		setBoost(boost);
		setHighlight(highlight > 0);
	}

	public SearchOption() {
	}

	public DataFilter getDataFilter() {
		return this.dataFilter;
	}

	public void setDataFilter(DataFilter dataFilter) {
		this.dataFilter = dataFilter;
	}

	public boolean isHighlight() {
		return this.highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public float getBoost() {
		return this.boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public SearchLogic getSearchLogic() {
		return this.searchLogic;
	}

	public void setSearchLogic(SearchLogic searchLogic) {
		this.searchLogic = searchLogic;
	}

	public SearchType getSearchType() {
		return this.searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	public String getQueryStringPrecision() {
		return this.queryStringPrecision;
	}

	public void setQueryStringPrecision(String queryStringPrecision) {
		this.queryStringPrecision = queryStringPrecision;
	}

	public static long getSerialversionuid() {
		return 1L;
	}

	public static String formatDate(Object object) {
		if ((object instanceof Date)) {
			return formatDateFromDate((Date) object);
		}
		return formatDateFromString(object.toString());
	}

	public static boolean isDate(Object object) {
		return ((object instanceof Date)) || (Pattern.matches("[1-2][0-9][0-9][0-9]-[0-9][0-9].*", object.toString()));
	}

	public static String formatDateFromDate(Date date) {
		SimpleDateFormat dateFormat_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String result = dateFormat_hms.format(date);
			return result;
		} catch (Exception e) {
			try {
				String result = dateFormat.format(date) + "00:00:00";
				return result;
			} catch (Exception ex) {
			}
		}
		return dateFormat_hms.format(new Date());
	}

	public static String formatDateFromString(String date) {
		SimpleDateFormat dateFormat_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date value = dateFormat_hms.parse(date);
			return formatDateFromDate(value);
		} catch (Exception e) {
			try {
				Date value = dateFormat.parse(date);
				return formatDateFromDate(value);
			} catch (Exception ex) {
			}
		}
		return dateFormat_hms.format(new Date());
	}

	public static enum DataFilter {
		exists,
		notExists,
		all;
	}

	public static enum SearchLogic {
		must,
		should;
	}

	public static enum SearchType {
		querystring,
		range,
		term;
	}
}