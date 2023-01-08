package icu.gieok.vo;

public class BoardLikeReportVO {
    
    private int report_no;
	private int user_code;
	private int bad_member;
    private int board_no;
    private String board_like;
    private String board_report;
    private String report_type;
    
    private String user_id;
    
    private BoardVO bad_board;
    
    

	public int getReport_no() {
		return report_no;
	}

	public void setReport_no(int report_no) {
		this.report_no = report_no;
	}

	public int getUser_code() {
		return user_code;
	}

	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}

	public int getBad_member() {
		return bad_member;
	}

	public void setBad_member(int bad_member) {
		this.bad_member = bad_member;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public String getBoard_like() {
		return board_like;
	}

	public void setBoard_like(String board_like) {
		this.board_like = board_like;
	}

	public String getBoard_report() {
		return board_report;
	}

	public void setBoard_report(String board_report) {
		this.board_report = board_report;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public BoardVO getBad_board() {
		return bad_board;
	}

	public void setBad_board(BoardVO bad_board) {
		this.bad_board = bad_board;
	}
    
    
	
 
    
    
    
    
    
    
 }
