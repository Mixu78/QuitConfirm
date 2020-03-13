package mixu.quitconfirm.proxy;

import mixu.quitconfirm.QuitConfirm;
import org.apache.logging.log4j.Level;

public class ServerProxy extends CommonProxy {
    @Override
    public void preInit() {
        QuitConfirm.logger.log(Level.INFO, "We be in preInit now");
    }

    @Override
    public void init() {
        QuitConfirm.logger.log(Level.INFO, "We be in init now");
    }

    @Override
    public void postInit() {
        QuitConfirm.logger.log(Level.INFO, "We be in postInit now");
    }
}
