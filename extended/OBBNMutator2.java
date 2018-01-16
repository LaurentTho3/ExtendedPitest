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
 * Replaces a&amp;b by a
 * and replaces a|b by a
 */
public enum OBBNMutator2 implements MethodMutatorFactory  {

  OBBN_MUTATOR2;

  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor)  {
    return new OBBNMethodVisitor2(this, context, methodInfo, methodVisitor);
  }

  public String getGloballyUniqueId()  {
    return this.getClass().getName();
  }

  public String getName()  {
    return name();
  }
}

class OBBNMethodVisitor2 extends MethodVisitor  {

  private final MethodMutatorFactory factory;
  private final MutationContext      context;
  private final MethodInfo      info;

  OBBNMethodVisitor2(final MethodMutatorFactory factory,
      final MutationContext context, final MethodInfo info, final MethodVisitor delegateMethodVisitor)  {
    super(Opcodes.ASM5, delegateMethodVisitor);
    this.factory = factory;
    this.context = context;
    this.info = info;
  }
  
  private boolean shouldMutate(String expression) {
      if (info.isGeneratedEnumMethod()) {
          return false;
      } else {
          final MutationIdentifier newId = this.context.registerMutation(
             this.factory, "Replaced " + expression + " by first member");
          return this.context.shouldMutate(newId);
      }
          
  }

  @Override
  public void visitInsn(int opcode) {
        switch (opcode) {
            case Opcodes.IAND:
            case Opcodes.IOR:
                if (this.shouldMutate(OpcodeToType.typeOfOpcode(opcode)))  {
                    mv.visitInsn(Opcodes.POP);
                } else  {
                    mv.visitInsn(opcode);
                }
                break;
            case Opcodes.LAND:
            case Opcodes.LOR:
                if (this.shouldMutate(OpcodeToType.typeOfOpcode(opcode)))  {
                    mv.visitInsn(Opcodes.POP2);
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
