package mixu.quitconfirm.client.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("QuitConfirmPlugin")
public class shutdownConfirmTransformer implements IClassTransformer {

    private static final boolean isBadMethod = false;
    private static final String classToTransform = "net.minecraft.client.Minecraft";
    @Override
    public byte[] transform(String name, String transformedName, byte[] classBeingTransformed) {
        boolean isObfuscated = !name.equals(transformedName);
        if (transformedName.equals(classToTransform)) { return transform(transformedName, classBeingTransformed, isObfuscated); } else return classBeingTransformed;
    }

    private static byte[] transform(String className, byte[] classBeingTransformed, boolean isObfuscated) {
        System.out.println("Transforming: " + className);
        boolean failed = false;
        try
        {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBeingTransformed);
            classReader.accept(classNode, 0);


            if (className.equals(classToTransform)) {
                transformMinecraft(classNode, isObfuscated);
            } else System.out.println("Something went wrong, " + className + " never got transformed");
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        catch (Exception e)
        {
            System.out.println("Failed to transform shutdown function");
            e.printStackTrace();
            failed = true;
        }
        finally {
            if(!failed) {
                System.out.println("Transformed shutdown function successfully!");
            }
        }
        return classBeingTransformed;
    }

    private static void transformMinecraft(ClassNode MinecraftClass, boolean isObfuscated) {
        final String SHUTDOWN_FUNCTION = isObfuscated ? "n" : "shutdown";
        System.out.println("Transforming the shutdown function now");
        for (MethodNode method : MinecraftClass.methods)
        {
            if (method.name.equals(SHUTDOWN_FUNCTION))
            {
                AbstractInsnNode targetNode = null;
                AbstractInsnNode endNode = null;
                for (AbstractInsnNode instruction : method.instructions.toArray())
                {
                    if (instruction.getOpcode() == ALOAD)
                    {
                        if (instruction.getNext().getOpcode() == ICONST_0 && instruction.getNext().getNext().getOpcode() == PUTFIELD)
                        {
                            System.out.println("Found the necessary stuff for transforming");
                            targetNode = instruction;
                            endNode = instruction.getNext().getNext();
                            break;
                        }
                    }
                }
                if (targetNode != null && isBadMethod)
                {
                    /*
                    REMOVING:
                    methodVisitor.visitFieldInsn(PUTFIELD, "net/minecraft/client/Minecraft", "running", "Z");
                     */
                        System.err.println("\n\n" + TextFormatting.DARK_RED + "WARNING: DELETING SHUTDOWN FUNCTION FUNCTIONALITY, THIS MAKES MINECRAFT UNQUITTABLE UNLESS TERMINATED\n");
                        method.instructions.remove(endNode);

                    //Wrapping this in a conditional
                }
                else if (targetNode != null)
                {
                    /*
                    Replacing:
                        minecraft.running = false

                    With:
                        ConfirmWindow.doExit()

                    BYTECODE
                    Replacing:
                        methodVisitor.visitFieldInsn(PUTFIELD, "net/minecraft/client/Minecraft", "running", "Z");
                    With:
                        INVOKEVIRTUAL ConfirmWindow.doExit()
                     */

                    System.out.println("Starting to transform");
                    //AbstractInsnNode popNode = targetNode.getPrevious();
                    //LabelNode newLabelNode = new LabelNode();
                    //InsnList toInsert = new InsnList();

                    //toInsert.add(new VarInsnNode(ALOAD, 0));
                    //toInsert.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ConfirmWindow.class), "doExit", "()V", false));
                    //toInsert.add(new JumpInsnNode(IFEQ, newLabelNode));

                    try {
                        method.instructions.insert(endNode, new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ConfirmWindow.class), "doExit", "()V", false));
                        method.instructions.remove(endNode);
                   // method.instructions.insert(endNode, newLabelNode);
                    } catch (Exception e) {System.out.println("Something went wrong!"); e.printStackTrace(); return;}

                    System.out.println("Finished transforming");
                }
                else
                {
                    System.out.println("Something went wrong transforming BlockCactus!");
                }
            }
        }
    }
}
