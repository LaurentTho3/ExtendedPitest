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
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

/**
 * Replaces a|b by b
 * and replaces a&amp;b by b
 */
public enum OBBNMutator3 implements MethodMutatorFactory  {

  OBBN_MUTATOR3;

  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor)  {
    return new OBBNMethodVisitor3(this, context, methodInfo, methodVisitor);
  }

  public String getGloballyUniqueId()  {
    return this.getClass().getName();
  }

  public String getName()  {
    return name();
  }
}

class OBBNMethodVisitor3 extends LocalVariablesSorter  {

  private final MethodMutatorFactory factory;
  private final MutationContext      context;
  private final MethodInfo      info;

  OBBNMethodVisitor3(final MethodMutatorFactory factory,
      final MutationContext context, final MethodInfo info, final MethodVisitor delegateMethodVisitor)  {
    super(Opcodes.ASM5, info.getAccess(), info.getMethodDescriptor(), delegateMethodVisitor);
    this.factory = factory;
    this.context = context;
    this.info = info;
  }
  
  private boolean shouldMutate(String expression) {
      if (info.isGeneratedEnumMethod()) {
          return false;
      } else {
          final MutationIdentifier newId = this.context.registerMutation(
             this.factory, "Replace " + expression + " by second member");
          return this.context.shouldMutate(newId);
      }
          
  }

  @Override
  public void visitInsn(int opcode) {
        switch (opcode) {
            case Opcodes.IAND:
            case Opcodes.IOR:
                if (this.shouldMutate(OpcodeToType.typeOfOpcode(opcode)))  {
                    int storage = this.newLocal(Type.INT_TYPE);
                    mv.visitVarInsn(Opcodes.ISTORE,storage);
                    mv.visitInsn(Opcodes.POP);
                    mv.visitVarInsn(Opcodes.ILOAD,storage);
                } else  {
                    mv.visitInsn(opcode);
                }
                break;
            case Opcodes.LAND:
            case Opcodes.LOR:
                if (this.shouldMutate(OpcodeToType.typeOfOpcode(opcode)))  {
                    int storage = this.newLocal(Type.LONG_TYPE);
                    mv.visitVarInsn(Opcodes.LSTORE,storage);
                    mv.visitInsn(Opcodes.POP2);
                    mv.visitVarInsn(Opcodes.LLOAD,storage);
                } else  {
                    mv.visitInsn(opcode);
                }
                break;
            default:
                mv.visitInsn(opcode);
                break;
        }
    }
}
