package com.thistestuser.farlands;

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
        	return patchClassASM(name, classBytes, isObfuscated);
		return classBytes;
    }
    
    public byte[] patchClassASM(String name, byte[] classBytes, boolean obfuscated) 
    {
    	ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);
        
        System.out.println("[FAR LANDS] PATCHING NOISE GENERATOR!");
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
}
