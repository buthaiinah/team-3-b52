package edu.pearlmart;
import java.math.BigDecimal;
import java.time.LocalDate;
import static edu.pearlmart.Domain.*;
public final class SampleData {
    private SampleData() {}
    public static void load(InventoryService inventory) {
        inventory.addNew(new Item("P001","100001","Basmati Rice 1 kg",new BigDecimal("12.50"),20,"Pearl Foods","Qatar",LocalDate.now().plusYears(1),"Demo data"));
        inventory.addNew(new Item("P002","100002","Olive Oil 500 ml",new BigDecimal("24.00"),10,"Green Valley","Spain",LocalDate.now().plusYears(1),"Demo data"));
        inventory.addNew(new Item("P003","100003","Paper Towels",new BigDecimal("8.00"),30,"Home Essentials","Qatar",null,"Demo data"));
    }
}
