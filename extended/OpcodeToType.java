package org.pitest.mutationtest.engine.gregor.mutators.experimental.extended;

import org.objectweb.asm.Opcodes;
import org.pitest.util.PitError;


public class OpcodeToType {

    /**
     * Returns a String description of the opcode.
     * @param opcode, an initialized opcode between 96 and 132.
     */
    public static String typeOfOpcode(int opcode) {
        // missing : comparisons and conversions, not needed.
        String type;
        switch (opcode) {
        case Opcodes.IADD : type = "integer addition";
        break;
        case Opcodes.LADD : type = "long addition";
        break;    
        case Opcodes.FADD : type = "float addition";
        break;
        case Opcodes.DADD : type = "double addition";
        break;

        case Opcodes.ISUB : type = "integer substraction";
        break;
        case Opcodes.LSUB : type = "long substraction";
        break;
        case Opcodes.FSUB : type = "float substraction";
        break;
        case Opcodes.DSUB : type = "double substraction";
        break;

        case Opcodes.IMUL : type = "integer multiplication";
        break;
        case Opcodes.LMUL : type = "long multiplication";
        break;
        case Opcodes.FMUL : type = "float multiplication";
        break;
        case Opcodes.DMUL : type = "double multiplication";
        break;

        case Opcodes.IDIV : type = "integer division";
        break;
        case Opcodes.LDIV : type = "long division";
        break;
        case Opcodes.FDIV : type = "float division";
        break;
        case Opcodes.DDIV : type = "double division";
        break;

        case Opcodes.IREM : type = "integer remainder";
        break;
        case Opcodes.LREM : type = "long remainder";
        break;
        case Opcodes.FREM : type = "float remainder";
        break;
        case Opcodes.DREM : type = "double remainder";
        break;

        case Opcodes.INEG : type = "integer negation";
        break;
        case Opcodes.LNEG : type = "long negation";
        break;
        case Opcodes.FNEG : type = "float negation";
        break;
        case Opcodes.DNEG : type = "double negation";
        break;

        case Opcodes.ISHL : type = "arithmetic integer shift left";
        break;
        case Opcodes.LSHL : type = "arithmetic long shift left";
        break;

        case Opcodes.ISHR : type = "arithmetic integer shift right";
        break;
        case Opcodes.LSHR : type = "arithmetic long shift right";
        break;    

        case Opcodes.IUSHR : type = "logical integer shift right";
        break;
        case Opcodes.LUSHR : type = "logical long shift right";
        break;

        case Opcodes.IAND : type = "integer and";
        break;
        case Opcodes.LAND : type = "long and";
        break;

        case Opcodes.IOR : type = "integer or";
        break;
        case Opcodes.LOR : type = "long or";
        break;     

        case Opcodes.IXOR : type = "integer xor";
        break;
        case Opcodes.LXOR : type = "long xor";
        break; 

        case Opcodes.IINC : type = "integer inc";
        break;

        default : throw new PitError("Unknown opcode used, value = " + opcode);
        }
        
        return type;
    }

}
