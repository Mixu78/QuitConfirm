package mixu.quitconfirm.client.asm;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import java.util.Arrays;

public class shutdownConfirmModContainer extends DummyModContainer {
    public shutdownConfirmModContainer() {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "shutdownconfirm";
        meta.name = "Shutdown confirmation";
        meta.description = "Adds exit confirmation to when you press the big red x";
        meta.version = "1.12.2-1.0.0";
        meta.authorList = Arrays.asList("Mixu_78");
    }

        public boolean registerBus(EventBus bus, LoadController controller) {
            bus.register(this);
            return true;
        }

}
