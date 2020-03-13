package mixu.quitconfirm;

import mixu.quitconfirm.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.lang.reflect.Field;

@Mod(
        modid = QuitConfirm.MODID,
        name = QuitConfirm.MODNAME,
        version = QuitConfirm.VERSION,
        clientSideOnly = true
)
public class QuitConfirm {
    public static final String MODID = "quitconfirm";
    public static final String MODNAME = "Quit Confirm";
    public static final String VERSION = "1.0.0";

    @SidedProxy(clientSide = "mixu.quitconfirm.proxy.ClientProxy", serverSide = "mixu.quitconfirm.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static QuitConfirm instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
