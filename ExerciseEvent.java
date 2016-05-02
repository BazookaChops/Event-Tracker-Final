
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ExerciseEvent extends Event implements Serializable {
	
	ExerciseEvent() {
		this.type = "Exercise Event";
		this.amount = 0;
		this.time = "";
		this.endTime = "";
		this.date = "";
		this.desc = "";
	}
	
	ExerciseEvent(String type, String time, String endTime, String date, String desc) {
		this.type = type;
		this.time = time;
		this.endTime = endTime;
		this.date = date;
		this.desc = desc;
	}

}