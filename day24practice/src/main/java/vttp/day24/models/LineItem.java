package vttp.day24.models;

//shortcut to generate getter and settter, ctrl + .
public class LineItem {

    private Integer itemId;
    private String description;
    private Integer quantity;

    //This is for the testing on inserting data into the schema via the Day24Application
    public LineItem() {}
    public LineItem (String description, Integer quantity) {
        this.description = description;
        this.quantity = quantity;
    }



    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}



