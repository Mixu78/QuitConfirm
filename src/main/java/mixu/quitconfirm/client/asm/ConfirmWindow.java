package mixu.quitconfirm.client.asm;

import mixu.quitconfirm.QuitConfirm;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.Level;

import javax.swing.*;

import static javax.swing.JOptionPane.*;

public class ConfirmWindow {
    public static void doExit() {
        JFrame frame = new JFrame();

        frame.setAlwaysOnTop(true);

        JDialog confirmDialog = new JDialog(frame, "Quit Confirm", false);

        confirmDialog.setAlwaysOnTop(true);

        JOptionPane optionPane = new JOptionPane("Do you want to quit the game?", QUESTION_MESSAGE, YES_NO_OPTION);
        confirmDialog.getContentPane().add(optionPane);
        confirmDialog.pack();
        confirmDialog.setLocationRelativeTo(null);
        frame.setLocationRelativeTo(null);
        confirmDialog.setVisible(true);
        optionPane.addPropertyChangeListener(
                event -> {
                    switch (event.getNewValue().toString()) {
                        case "0":
                            ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), false, "running", "field_71425_J");
                            try {
                                frame.dispose();
                            } catch (Exception e) {}
                            break;

                        case "1":
                            ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), true, "running", "field_71425_J");
                            try {
                                frame.dispose();
                            } catch (Exception e) {}
                            break;

                        default:
                            ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), true, "running", "field_71425_J");
                            try {
                                frame.dispose();
                            } catch (Exception e) {}
                            break;
                    }
                }
        );
    }
}
