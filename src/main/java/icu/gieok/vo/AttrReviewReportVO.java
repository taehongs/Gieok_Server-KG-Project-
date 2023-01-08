package icu.gieok.vo;

public class AttrReviewReportVO {
	
	private int report_no;
	private int user_code;
	private int bad_member;
	private int rev_code;
	private String rev_report;
	private String report_type;
	
	private String user_id;

	private AttrReviewVO bad_review;
	
	
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
	public int getRev_code() {
		return rev_code;
	}
	public void setRev_code(int rev_code) {
		this.rev_code = rev_code;
	}
	public String getRev_report() {
		return rev_report;
	}
	public void setRev_report(String rev_report) {
		this.rev_report = rev_report;
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
	public AttrReviewVO getBad_review() {
		return bad_review;
	}
	public void setBad_review(AttrReviewVO bad_review) {
		this.bad_review = bad_review;
	}

	
	
	
	
	
	

}
