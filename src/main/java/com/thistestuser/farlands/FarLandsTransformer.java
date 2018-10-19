package com.thistestuser.farlands;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class FarLandsTransformer implements IClassTransformer
{
    @Override
    public byte[] transform(String name, String transformedName, byte[] classBytes) 
    {
    	boolean isObfuscated = !name.equals(transformedName);
        if(transformedName.equals("net.minecraft.world.gen.NoiseGeneratorOctaves"))
        	return patchClassASMOcto(name, classBytes, isObfuscated);
        else if(transformedName.equals("net.minecraft.command.CommandBase")
        		|| transformedName.equals("net.minecraft.command.CommandSetSpawnpoint")
        		|| transformedName.equals("net.minecraft.command.server.CommandSetDefaultSpawnpoint")
        		|| transformedName.equals("net.minecraft.world.ChunkCache")
        		|| transformedName.equals("net.minecraft.world.World")
        		|| transformedName.equals("net.minecraft.util.math.BlockPos")
        		|| transformedName.equals("net.minecraft.util.BlockPos"))
        	return patchClassASMCmdbase(name, classBytes, isObfuscated);
        else if(transformedName.equals("net.minecraft.network.NetHandlerPlayServer")
        		|| transformedName.equals("net.minecraft.entity.Entity"))
        	return patchClassNetHandler(name, classBytes, isObfuscated);
        else if(transformedName.equals("net.minecraft.server.MinecraftServer")
        		|| transformedName.equals("net.minecraft.world.border.WorldBorder")
        		|| transformedName.equals("net.minecraft.world.storage.WorldInfo")
        		|| transformedName.equals("net.minecraft.command.CommandWorldBorder"))
        	return patchClassASMBorder(name, classBytes, isObfuscated);
        else if(transformedName.equals("net.minecraft.entity.player.EntityPlayer"))
        	return patchClassASMPlayer(name, classBytes, isObfuscated);
		return classBytes;
    }
    
    public byte[] patchClassASMOcto(String name, byte[] classBytes, boolean obfuscated) 
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        LogManager.getLogger().info("[FAR LANDS] PATCHING NOISE GENERATOR!");
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
        			((LdcInsnNode)ain).cst = Double.MAX_VALUE;
        
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
}
