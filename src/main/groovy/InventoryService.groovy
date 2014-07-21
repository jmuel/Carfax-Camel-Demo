/**
 * Created by jamesmueller on 7/20/14.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryService.class);


    public UpdateInventory csvToObject(String csv) {
        String[] lines = csv.split(",");
        if (lines == null || lines.length != 4) {
            throw new IllegalArgumentException("CSV line is not valid: " + csv);
        }

        String supplierId = lines[0];
        String partId = lines[1];
        String name = lines[2];
        String amount = lines[3];

        return new UpdateInventory(supplierId, partId, name, amount);
    }


    public void updateInventory(UpdateInventory update) throws Exception {
        Thread.sleep(100);

        LOG.info("Inventory " + update.partId + " updated");
    }

}
