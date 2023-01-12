package vttp.day24workshop.model;

public class OrderDetails {

    //order_id is foreign key here, but dont need to create variable here
    //orderDetailsId is auto_increment, as such dont have to put it here
    
    //private Integer orderDetailsId;
    private String product;
    private Float unit_price;
    private Float discount;
    private Integer quantity;

    //This is for inserting in the Controller
    public OrderDetails() {}
    public OrderDetails (String product, Float unit_price, Float discount, Integer quantity) {
        this.product = product;
        this.unit_price = unit_price;
        this.discount = discount;
        this.quantity = quantity;
    }

    // public Integer getorderDetailsId() {
    //     return orderDetailsId;
    // }
    // public void setorderDetailsId(Integer orderDetailsId) {
    //     this.orderDetailsId = orderDetailsId;
    // }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public Float getUnit_price() {
        return unit_price;
    }
    public void setUnit_price(Float unit_price) {
        this.unit_price = unit_price;
    }
    public Float getDiscount() {
        return discount;
    }
    public void setDiscount(Float discount) {
        this.discount = discount;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString () {
        return "OrderDetails: [product = " + product + ", unit_price = " + unit_price + ", discount = " + discount 
        + ", quantity = " + quantity + "]";
    }




    
}


// create table order_details (
//     id int auto_increment not null,
//     product varchar(64) not null,
//     unit_price decimal (3,2) not null,
//     discount decimal (3,2) default 1.00,
//     quantity int not null,

//     -- foreign key
//     order_id int not null,

//     primary key (id),

//     constraint fk_order_id
//         foreign key(order_id) references orders(order_id)
// );