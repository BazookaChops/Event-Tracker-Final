
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Event	implements Serializable {
	private String type;
	private double amount;
	private String time;
	private String endTime;
	private String date;
	private String desc;
	
	Event() {
		this.type = "";
		this.amount = 0;
		this.time = "";
		this.endTime = "";
		this.date = "";
		this.desc = "";
	}
	
	Event(String type, String time, String endTime, String date, String desc) {
		this.type = type;
		this.time = time;
		this.endTime = endTime;
		this.date = date;
		this.desc = desc;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}	
	public void setType(String type) {
		this.type = type;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public double getAmount() {
		return amount;
	}
	public String getTime() {
		return time;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getType() {
		return type;
	}
	public String getDesc() {
		return desc;
	}
}
