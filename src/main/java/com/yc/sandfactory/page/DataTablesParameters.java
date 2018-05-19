package com.yc.sandfactory.page;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 	
 * datatables和action交互的json封装<br>
 * datatables分页查询传入的参数<br>
 * @author 马征
 * 
 * Project Name：pms-oms-web
 * ClassName：DataTablesParameters
 * Description：分页采用LitePaging
 * @modify 何帅斌
 * @date: 2015-1-27 上午9:50:58
 * note:
 *
 */
public class DataTablesParameters {
	/**
	 * 显示的起始索引
	 */
	private int start;
	
	/**
	 * 显示的行数
	 */
	private int length;
	
	/**
	 * 排序列的下标
	 */
	private int orderCol;
	
	/**
	 * 排序方向 asc  desc
	 */
	private String orderDir;
	
	/**
	 * 表格绘制次数，无需设置，返回页面的时候原封返回
	 */
	private int draw;
	
	/**
	 * 搜索的关键字
	 */
	private String keyword;
	
	/**
	 * 从spring中获取request并调用DataTablesParameters(HttpServletRequest request)构造方法
	 * 推荐使用 DataTablesParameters(HttpServletRequest request) 
	 */
	public DataTablesParameters() {
		this(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
	}
	
	/**
	 * 实例化DataTablesParameters
	 * @Description：
	 * @author: 马德成
	 * @date: 2014-10-10 上午9:28:27
	 * @return
	 */
	public static DataTablesParameters newInstance(){
		return new DataTablesParameters();
	}
	
	/**
	 * 实例化DataTablesParameters
	 * @Description：
	 * @author: 马德成
	 * @date: 2014-10-10 上午9:28:27
	 * @return
	 */
	public static DataTablesParameters newInstance(HttpServletRequest request){
		return new DataTablesParameters(request);
	}
	
	private HttpServletRequest request;
	
	/**
	 * 根据request自动取出datatable传入的参数，请确认传入request包含必要的参数
	 * @param request
	 */
	public DataTablesParameters(HttpServletRequest request) {
		this.request = request;
		
		start = Integer.parseInt(request.getParameter("start"));
		length = Integer.parseInt(request.getParameter("length"));
		String orderColumn = request.getParameter("order[0][column]");
		
		if(StringUtils.isNotBlank(orderColumn)){
			orderCol = Integer.parseInt(orderColumn);
			orderCol = orderCol < 0 ? 0 : orderCol; 
		} else {
			orderCol = 9999999;
		}
		
		String dir = request.getParameter("order[0][dir]");
		if(StringUtils.isNotBlank(dir)){
			orderDir = dir.trim().toUpperCase();
			if (!orderDir.equals("ASC") && !orderDir.equals("DESC")) {
				orderDir = "ASC";
			}
		} else {
			orderDir = "ASC";
		}
		
		draw = Integer.parseInt(request.getParameter("draw"));
		
		if (StringUtils.isNotBlank(request.getParameter("search[value]"))) {
			keyword = request.getParameter("search[value]");
		}
	}

	/**
	 * 获取DataTables分页数据
	 * @Description：
	 * @author: 马德成
	 * @date: 2014-10-10 上午9:43:29
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataTablesReply<?> getDataTablesReply(LitePaging<?> pagination) {
		return new DataTablesReply(pagination, this.getDraw());
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	/**
	 * 获取datatables排序的列序号（从1开始）
	 * @return
	 */
	public int getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(int orderCol) {
		this.orderCol = orderCol;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}
	
	/**
	 * 获取排序列的下标（从0开始，如果小于0则为0）
	 * @return
	 */
	public int getOrderColIndex() {
		int orderColIndex = getOrderCol() - 1;
		if (orderColIndex < 0) {
			orderColIndex = 0;
		}
		return orderColIndex;
	}

	/**
	 * 获取排序所在的字段名称
	 */
	public String getOrderColData(HttpServletRequest request) {
		int colIndex = this.getOrderCol();
		return request.getParameter("columns[" + colIndex + "][data]");
	}

	/**
	 * @return 分页的页码
	 */
	public int getPage() {
		return (start / length) + 1;
	}
	
	/**
	 * 从给定的列表中字段获取需要排序的列名
	 * @Description：
	 * @author: 马德成
	 * @date: 2014-10-8 下午3:00:53
	 * @param orderColumns 给定的排序列表
	 * @return
	 */
	public String getOrderColName(String[] orderColumns){
		return orderCol < orderColumns.length ? orderColumns[orderCol] : null;
	}
	
	/**
	 * 获取排序所在的字段名称(页面字段)
	 */
	public String getOrderColName() {
		return request.getParameter("columns[" + orderCol + "][data]");
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
