
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SleepEvent extends Event implements Serializable {
	
	SleepEvent() {
		this.type = "Sleep Event";
		this.amount = 0;
		this.time = "";
		this.endTime = "";
		this.date = "";
		this.desc = "";
	}
	
	SleepEvent(String type, String time, String endTime, String date, String desc) {
		this.type = type;
		this.time = time;
		this.endTime = endTime;
		this.date = date;
		this.desc = desc;
	}

}