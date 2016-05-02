
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class Event	implements Serializable {
	protected String type;
	protected double amount;
	protected String time;
	protected String endTime;
	protected String date;
	protected String desc;
	
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