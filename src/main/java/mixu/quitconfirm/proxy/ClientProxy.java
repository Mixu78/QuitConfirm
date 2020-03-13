package mixu.quitconfirm.proxy;

import mixu.quitconfirm.QuitConfirm;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        QuitConfirm.logger.log(Level.INFO, "We be in preInit now");
        //probably useless
        Field running = null;
        running = ReflectionHelper.findField(Minecraft.class, "field_71425_J", "running");
        running.setAccessible(true);
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
