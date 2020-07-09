package com.thistestuser.farlands;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class FarLandsTransformer implements IClassTransformer
{
	private final Config config = Config.instance;
	
    @Override
    public byte[] transform(String name, String transformedName, byte[] classBytes) 
    {
    	boolean isObfuscated = !name.equals(transformedName);
        if(transformedName.equals("net.minecraft.world.gen.NoiseGeneratorOctaves") && config.isFarLands)
        	return patchClassASMOcto(name, classBytes, isObfuscated);
        else if((transformedName.equals("net.minecraft.command.CommandBase")
        		|| transformedName.equals("net.minecraft.command.CommandSetSpawnpoint")
        		|| transformedName.equals("net.minecraft.command.server.CommandSetDefaultSpawnpoint")
        		|| transformedName.equals("net.minecraft.world.ChunkCache")
        		|| transformedName.equals("net.minecraft.world.World")
        		|| transformedName.equals("net.minecraft.util.math.BlockPos")
        		|| transformedName.equals("net.minecraft.util.BlockPos")) && config.extendWB)
        	return patchClassASMCmdbase(name, classBytes, isObfuscated);
        else if((transformedName.equals("net.minecraft.network.NetHandlerPlayServer")
        		|| transformedName.equals("net.minecraft.entity.Entity")) && config.extendWB)
        	return patchClassNetHandler(name, classBytes, isObfuscated);
        else if((transformedName.equals("net.minecraft.server.MinecraftServer")
        		|| transformedName.equals("net.minecraft.world.border.WorldBorder")
        		|| transformedName.equals("net.minecraft.world.storage.WorldInfo")
        		|| transformedName.equals("net.minecraft.command.CommandWorldBorder")) && config.extendWB)
        	return patchClassASMBorder(name, classBytes, isObfuscated);
        else if(transformedName.equals("net.minecraft.entity.player.EntityPlayer") && config.extendWB)
        	return patchClassASMPlayer(name, classBytes, isObfuscated);
        else if(transformedName.equals("net.minecraft.server.management.PlayerList") && config.extendWB)
        	return patchClassASMPlayerList(name, classBytes, isObfuscated);
        else if((transformedName.equals("net.minecraft.world.gen.ChunkGeneratorOverworld")
        		|| transformedName.equals("net.minecraft.world.gen.ChunkProviderGenerate")) && config.offset)
        	return patchChunkGen(name, classBytes, isObfuscated);
		return classBytes;
    }
    
    public byte[] patchClassASMOcto(String name, byte[] classBytes, boolean obfuscated) 
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        LogManager.getLogger().info("[FarLands] Patching noise generator!");
        for(MethodNode method : classNode.methods)
        	if(method.desc.equals("([DIIIIIIDDD)[D"))
        		for(AbstractInsnNode ain : method.instructions.toArray())
        			if(ain.getOpcode() == Opcodes.LDC 
        			&& ((LdcInsnNode)ain).cst instanceof Long && (Long)((LdcInsnNode)ain).cst == 16777216L)
        				((LdcInsnNode)ain).cst = Long.MAX_VALUE;
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
    
    public byte[] patchClassASMCmdbase(String name, byte[] classBytes, boolean obfuscated) 
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        for(MethodNode method : classNode.methods)
        	for(AbstractInsnNode ain : method.instructions.toArray())
        		if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Integer && (Integer)((LdcInsnNode)ain).cst == 30000000)
        			((LdcInsnNode)ain).cst = Integer.MAX_VALUE;
        		else if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Integer && (Integer)((LdcInsnNode)ain).cst == -30000000)
        			((LdcInsnNode)ain).cst = Integer.MIN_VALUE;
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
    
    public byte[] patchClassNetHandler(String name, byte[] classBytes, boolean obfuscated) 
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        for(MethodNode method : classNode.methods)
        	for(AbstractInsnNode ain : method.instructions.toArray())
        		if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Double && (Double)((LdcInsnNode)ain).cst == 3.2E7D)
        			((LdcInsnNode)ain).cst = Double.MAX_VALUE;
        		else if(ain.getOpcode() == Opcodes.LDC 
        				&& ((LdcInsnNode)ain).cst instanceof Double && (Double)((LdcInsnNode)ain).cst == 3.0E7D)
        			((LdcInsnNode)ain).cst = 4294967294D;
        		else if(ain.getOpcode() == Opcodes.LDC 
        				&& ((LdcInsnNode)ain).cst instanceof Double && (Double)((LdcInsnNode)ain).cst == -3.0E7D)
        			((LdcInsnNode)ain).cst = -4294967294D;
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
    
    public byte[] patchClassASMBorder(String name, byte[] classBytes, boolean obfuscated) 
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        for(MethodNode method : classNode.methods)
        	for(AbstractInsnNode ain : method.instructions.toArray())
        		if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Integer && (Integer)((LdcInsnNode)ain).cst == 29999984)
        			((LdcInsnNode)ain).cst = Integer.MAX_VALUE;
        		else if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Double && (Double)((LdcInsnNode)ain).cst == 6.0E7D)
        			((LdcInsnNode)ain).cst = 4294967294D;
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
    
    public byte[] patchClassASMPlayer(String name, byte[] classBytes, boolean obfuscated)
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        for(MethodNode method : classNode.methods)
        	for(AbstractInsnNode ain : method.instructions.toArray())
        		if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Double && (Double)((LdcInsnNode)ain).cst == 2.9999999E7D)
        			((LdcInsnNode)ain).cst = 2147483647D;
        		else if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Double && (Double)((LdcInsnNode)ain).cst == -2.9999999E7D)
        			((LdcInsnNode)ain).cst = -2147483648D;
        		else if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Integer && (Integer)((LdcInsnNode)ain).cst == 29999999)
        			((LdcInsnNode)ain).cst = Integer.MAX_VALUE;
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
    
    public byte[] patchClassASMPlayerList(String name, byte[] classBytes, boolean obfuscated)
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        for(MethodNode method : classNode.methods)
        	for(AbstractInsnNode ain : method.instructions.toArray())
        		if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Integer && (Integer)((LdcInsnNode)ain).cst == -29999872)
        			((LdcInsnNode)ain).cst = Integer.MIN_VALUE;
        		else if(ain.getOpcode() == Opcodes.LDC 
        		&& ((LdcInsnNode)ain).cst instanceof Integer && (Integer)((LdcInsnNode)ain).cst == 29999872)
        			((LdcInsnNode)ain).cst = Integer.MAX_VALUE;
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
    
    public byte[] patchChunkGen(String name, byte[] classBytes, boolean obfuscated) 
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        //We cannot depend on any names, as we are trying to make this compatible
        boolean found = false;
        for(MethodNode method : classNode.methods)
        {
        	Type returnType = Type.getReturnType(method.desc);
        	for(AbstractInsnNode ain : method.instructions.toArray())
        		if(ain.getOpcode() == Opcodes.LDC && ((LdcInsnNode)ain).cst instanceof Long
        			&& (long)((LdcInsnNode)ain).cst == 132897987541L)
        		{
        			found = true;
        			InsnList list = new InsnList();
        			list.add(new VarInsnNode(Opcodes.ILOAD, 1));
        			list.add(getNumberInsn(config.offsetX));
        			list.add(new InsnNode(Opcodes.IADD));
        			list.add(new VarInsnNode(Opcodes.ISTORE, 1));
        			list.add(new VarInsnNode(Opcodes.ILOAD, 2));
        			list.add(getNumberInsn(config.offsetZ));
        			list.add(new InsnNode(Opcodes.IADD));
        			list.add(new VarInsnNode(Opcodes.ISTORE, 2));
        			method.instructions.insert(ain, list);
        		}else if(found && ain.getOpcode() == Opcodes.NEW
        			&& Type.getObjectType(((TypeInsnNode)ain).desc).equals(returnType))
        		{
        			LogManager.getLogger().info("[FarLands] Successfully applied offsets!");
        			found = true;
        			InsnList list = new InsnList();
        			list.add(new VarInsnNode(Opcodes.ILOAD, 1));
        			list.add(getNumberInsn(config.offsetX));
        			list.add(new InsnNode(Opcodes.ISUB));
        			list.add(new VarInsnNode(Opcodes.ISTORE, 1));
        			list.add(new VarInsnNode(Opcodes.ILOAD, 2));
        			list.add(getNumberInsn(config.offsetZ));
        			list.add(new InsnNode(Opcodes.ISUB));
        			list.add(new VarInsnNode(Opcodes.ISTORE, 2));
        			method.instructions.insertBefore(ain, list);
        			break;
        		}
        	if(found)
        		break;
        }
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
    
    private AbstractInsnNode getNumberInsn(int number)
    {
    	if(number >= -1 && number <= 5)
    		return new InsnNode(number + 3);
    	if(number >= -128 && number <= 127)
    		return new IntInsnNode(Opcodes.BIPUSH, number);
    	if (number >= -32768 && number <= 32767)
    		return new IntInsnNode(Opcodes.SIPUSH, number);
    	return new LdcInsnNode(number);
    }
}
