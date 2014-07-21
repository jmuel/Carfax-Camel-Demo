/**
 * Created by jamesmueller on 7/20/14.
 */
class UpdateInventory {

    private String supplierId;
    private String partId;
    private String name;
    private String amount;

    public UpdateInventory(String supplierId, String partId, String name, String amount) {
        this.supplierId = supplierId;
        this.partId = partId;
        this.name = name;
        this.amount = amount;
    }

    String toString() {
        return supplierId + "," + partId + "," + name + "," + amount;
    }
}