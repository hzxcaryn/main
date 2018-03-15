package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code OutletDetailsPanel} of the UI
 */
public class OutletDetailsPanelHandle extends NodeHandle<Node> {

    public static final String OUTLET_ID = "#outletDetailsPanelPlaceholder";

    public OutletDetailsPanelHandle(Node outletDetailsNode) {
        super(outletDetailsNode);
    }

}
