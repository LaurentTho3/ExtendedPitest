/*
 * Copyright 2010 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.mutators.experimental.extended;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

/**
 * Mutation operator realizing (a) to (++a)
 */
public enum UOIMutator3 implements MethodMutatorFactory {

    UOI_MUTATOR3;

    public MethodVisitor create(final MutationContext context, final MethodInfo methodInfo,
            final MethodVisitor methodVisitor) {
        return new UOIMethodVisitor3(this, context, methodInfo, methodVisitor);
    }

    public String getGloballyUniqueId() {
        return this.getClass().getName();
    }

    public String getName() {
        return name();
    }
}

class UOIMethodVisitor3 extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;
    private final MethodInfo info;

    UOIMethodVisitor3(final MethodMutatorFactory factory, final MutationContext context, final MethodInfo info,
            final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM5, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
        this.info = info;
    }

    private boolean shouldMutate(String description) {
        if (context.getClassInfo().isEnum()) {
            return false;
        } else {
            final MutationIdentifier newId = this.context.registerMutation(this.factory, description);
            return this.context.shouldMutate(newId);
        }
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        switch (opcode) {
        case Opcodes.ILOAD:
            if (this.shouldMutate("Incremented (++a) integer local variable number " + var)) {
                mv.visitIincInsn(var, 1);
            }
            mv.visitVarInsn(opcode, var);
            break;
        case Opcodes.FLOAD:
            if (this.shouldMutate("Incremented (++a) float local variable number " + var)) {
                mv.visitVarInsn(opcode, var);
                mv.visitInsn(Opcodes.FCONST_1);
                mv.visitInsn(Opcodes.FADD);
                mv.visitVarInsn(Opcodes.FSTORE, var);
            }
            mv.visitVarInsn(opcode, var);
            break;
        case Opcodes.LLOAD:
            if (this.shouldMutate("Incremented (++a) long local variable number " + var)) {
                mv.visitVarInsn(opcode, var);
                mv.visitInsn(Opcodes.LCONST_1);
                mv.visitInsn(Opcodes.LADD);
                mv.visitVarInsn(Opcodes.LSTORE, var);
            }
            mv.visitVarInsn(opcode, var);
            break;
        case Opcodes.DLOAD:
            if (this.shouldMutate("Incremented (++a) double local variable number " + var)) {
                mv.visitVarInsn(opcode, var);
                mv.visitInsn(Opcodes.DCONST_1);
                mv.visitInsn(Opcodes.DADD);
                mv.visitVarInsn(Opcodes.DSTORE, var);
            }
            mv.visitVarInsn(opcode, var);
            break;
        default:
            mv.visitVarInsn(opcode, var);
            break;
        }
    }

    // ARRAYS
    @Override
    public void visitInsn(final int opcode) {
        // I F L D + BS
        switch (opcode) {
        case Opcodes.IALOAD:
            if (this.shouldMutate("Incremented (++a) integer array field")) {
                // stack = [array] [index]
                
                mv.visitInsn(Opcodes.DUP2);
                // stack = [array] [index] [array] [index]
                
                mv.visitInsn(opcode);
                // stack = [array] [index] [array(index)]
                
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.IADD);
                // stack = [array] [index] [array(index)+1]
                
                mv.visitInsn(Opcodes.DUP_X2);
                // stack = [array(index)+1] [array] [index] [array(index)+1]
                
                mv.visitInsn(Opcodes.IASTORE);
            } else {
                mv.visitInsn(opcode);
            }
            break;
        case Opcodes.FALOAD:
            if (this.shouldMutate("Incremented (++a) float array field")) {
                mv.visitInsn(Opcodes.DUP2);
                mv.visitInsn(opcode);
                mv.visitInsn(Opcodes.FCONST_1);
                mv.visitInsn(Opcodes.FADD);
                mv.visitInsn(Opcodes.DUP_X2);
                mv.visitInsn(Opcodes.FASTORE);
            } else {
                mv.visitInsn(opcode);
            }
            break;
        case Opcodes.LALOAD:
            if (this.shouldMutate("Incremented (++a) long array field")) {
                mv.visitInsn(Opcodes.DUP2);
                mv.visitInsn(opcode);
                mv.visitInsn(Opcodes.LCONST_1);
                mv.visitInsn(Opcodes.LADD);
                mv.visitInsn(Opcodes.DUP2_X2);
                mv.visitInsn(Opcodes.LASTORE);
            } else {
                mv.visitInsn(opcode);
            }
            break;
        case Opcodes.DALOAD:
            if (this.shouldMutate("Incremented (++a) double array field")) {
                mv.visitInsn(Opcodes.DUP2);
                mv.visitInsn(opcode);
                mv.visitInsn(Opcodes.DCONST_1);
                mv.visitInsn(Opcodes.DADD);
                mv.visitInsn(Opcodes.DUP2_X2);
                mv.visitInsn(Opcodes.DASTORE);
            } else {
                mv.visitInsn(opcode);
            }
            break;

        case Opcodes.BALOAD:
            if (this.shouldMutate("Incremented (++a) byte array field")) {
                mv.visitInsn(Opcodes.DUP2);
                mv.visitInsn(opcode);
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.IADD);
                mv.visitInsn(Opcodes.I2B);
                mv.visitInsn(Opcodes.DUP_X2);
                mv.visitInsn(Opcodes.BASTORE);
            } else {
                mv.visitInsn(opcode);
            }
            break;

        case Opcodes.SALOAD:
            if (this.shouldMutate("Incremented (++a) short array field")) {
                mv.visitInsn(Opcodes.DUP2);
                mv.visitInsn(opcode);
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.IADD);
                mv.visitInsn(Opcodes.I2S);
                mv.visitInsn(Opcodes.DUP_X2);
                mv.visitInsn(Opcodes.SASTORE);
            } else {
                mv.visitInsn(opcode);
            }
            break;

        default:
            mv.visitInsn(opcode);
            break;
        }
    }

    // PARAMETERS, static or not.
    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        // GETFIELD I,F,L,D + B,S
        if ((opcode == Opcodes.GETFIELD)) {
            if (desc.equals("I")) {
                if (this.shouldMutate("Incremented (++a) integer field " + name)) {
                    // stack = [this]
                    
                    mv.visitInsn(Opcodes.DUP);
                    // stack = [this] [this]
                    
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    // stack = [this] [this.field]
                    
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.IADD);
                    // stack = [this] [this.field +1]

                    mv.visitInsn(Opcodes.DUP_X1);
                    // stack = [this.field +1] [this] [this.field +1]

                    mv.visitFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("F")) {
                if (this.shouldMutate("Incremented (++a) float field " + name)) {
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.FCONST_1);
                    mv.visitInsn(Opcodes.FADD);
                    mv.visitInsn(Opcodes.DUP_X1);
                    mv.visitFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("J")) {
                if (this.shouldMutate("Incremented (++a) long field " + name)) {
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.LCONST_1);
                    mv.visitInsn(Opcodes.LADD);
                    mv.visitInsn(Opcodes.DUP2_X1);
                    mv.visitFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("D")) {
                if (this.shouldMutate("Incremented (++a) double field " + name)) {
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.DCONST_1);
                    mv.visitInsn(Opcodes.DADD);
                    mv.visitInsn(Opcodes.DUP2_X1);
                    mv.visitFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("B")) {
                if (this.shouldMutate("Incremented (++a) byte field " + name)) {
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.IADD);
                    mv.visitInsn(Opcodes.I2B);
                    mv.visitInsn(Opcodes.DUP_X1);
                    mv.visitFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("S")) {
                if (this.shouldMutate("Incremented (++a) short field " + name)) {
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.IADD);
                    mv.visitInsn(Opcodes.I2S);
                    mv.visitInsn(Opcodes.DUP_X1);
                    mv.visitFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
                    return;
                }
            }
        }

        // GETSTATIC I,F,L,D + B,S
        if (opcode == Opcodes.GETSTATIC) {
            if (desc.equals("I")) {
                if (this.shouldMutate("Incremented (++a) static integer field " + name)) {
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.IADD);
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("F")) {
                if (this.shouldMutate("Incremented (++a) static float field " + name)) {
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.FCONST_1);
                    mv.visitInsn(Opcodes.FADD);
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("J")) {
                if (this.shouldMutate("Incremented (++a) static long field " + name)) {
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.LCONST_1);
                    mv.visitInsn(Opcodes.LADD);
                    mv.visitInsn(Opcodes.DUP2);
                    mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("D")) {
                if (this.shouldMutate("Incremented (++a) static double field " + name)) {
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.DCONST_1);
                    mv.visitInsn(Opcodes.DADD);
                    mv.visitInsn(Opcodes.DUP2);
                    mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("B")) {
                if (this.shouldMutate("Incremented (++a) static byte field " + name)) {
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.IADD);
                    mv.visitInsn(Opcodes.I2B);
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, name, desc);
                    return;
                }
            }
            if (desc.equals("S")) {
                if (this.shouldMutate("Incremented (++a) static short field " + name)) {
                    mv.visitFieldInsn(opcode, owner, name, desc);
                    mv.visitInsn(Opcodes.ICONST_1);
                    mv.visitInsn(Opcodes.IADD);
                    mv.visitInsn(Opcodes.I2S);
                    mv.visitInsn(Opcodes.DUP);
                    mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, name, desc);
                    return;
                }
            }
        }
        mv.visitFieldInsn(opcode, owner, name, desc);
    }

}
