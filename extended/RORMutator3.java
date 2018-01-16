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

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractJumpMutator;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum RORMutator3 implements MethodMutatorFactory {

  ROR_MUTATOR3;

  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new RORMethodVisitor3(this, context, methodVisitor);
  }

  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  public String getName() {
    return name();
  }

}

class RORMethodVisitor3 extends AbstractJumpMutator {

  private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<Integer, Substitution>();

  static {
    // > to <=
    MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFLE, 
        "Replaced greater by less or equal"));
    MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPLE,
        "Replaced greater by less or equal"));
    
    // >= to <=
    MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFLE, 
        "Replace greater or equal by less or equal"));
    MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPLE,
        "Replace greater or equal by less or equal"));
    
    // < to <=
    MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFLE, 
        "Replace less by less or equal"));
    MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPLE,
        "Replace less by less or equal"));
        
    // <= to <
    MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFLT, 
        "Replace less or equal by less"));
    MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPLT,
        "Replace less or equal by less"));
            
    // == to <
    MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFLT,
        "Replace equal by less"));
    MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPLT,
        "Replace equal by less"));
        
    // != to <
    MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFLT, 
        "Replace different by less"));
    MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPLT,
        "Replace different by less"));
  }

  RORMethodVisitor3(final MethodMutatorFactory factory,
      final MutationContext context, final MethodVisitor delegateMethodVisitor) {
    super(factory, context, delegateMethodVisitor);
  }

  @Override
  protected Map<Integer, Substitution> getMutations() {
    return MUTATIONS;
  }

}
