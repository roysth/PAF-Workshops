package vttp2022.paf.assessment.eshop.models;

public class OrderStatusCount {
    //For task 6

    /*
    Required Json:
        {
            "name": <name>
            "dispatched": <no. of dispatched orders>
            "pending": <no. of pending orders>
        }
    */

    private String name;
    private Integer dispatched;
    private Integer pending;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getDispatched() {
        return dispatched;
    }
    public void setDispatched(Integer dispatched) {
        this.dispatched = dispatched;
    }
    public Integer getPending() {
        return pending;
    }
    public void setPending(Integer pending) {
        this.pending = pending;
    }

    @Override
	public String toString() {
		return "OrderStatusCount: {name=%s, dispatched=%d, pending=%d}".formatted(name, dispatched, pending);
	}
}

