
import java.io.Serializable;

public class DrinkEvent extends Event implements Serializable {
	
	DrinkEvent() {
		this.type = "Drink Event";
		this.amount = 0;
		this.time = "";
		this.date = "";
		this.desc = "";
	}
	
	DrinkEvent(String type, double amount, String time, String date, String desc) {
		this.type = type;
		this.amount = amount;
		this.time = time;
		this.date = date;
		this.desc = desc;
	}

}