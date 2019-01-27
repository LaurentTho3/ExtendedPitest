# Extended PITest

This is a version of [PITest](http://pitest.org) with an extended set of mutation operators/individual transformations. We show in our recent papers that this extended set of mutants is more suitable than the original version of PITest and other mutation tools for scientific inquiries.

You can find more details about our work here:
 * Laurent, T., Papadakis, M., Kintis, M., Henard, C., le Traon, Y. and Ventresque, A., 2017. "Assessing and Improving the Mutation Testing Practice of PIT". International Conference on Software Testing, Verification and Validation. 
 * Coles, H., Laurent, T., Henard, C., Papadakis, M. and Ventresque, A., 2016. "PIT: a practical mutation testing tool for Java". International Symposium on Software Testing and Analysis (pp. 449-452). [Link](http://researchrepository.ucd.ie/handle/10197/7748)
 * Laurent, T., Ventresque, A., Papadakis, M., Henard, C. and Traon, Y.L., 2016. Assessing and improving the mutation testing practice of PIT. arXiv preprint arXiv:1601.02351. [Link](https://arxiv.org/abs/1601.02351)

## How to run Extended PITest?

The extended set of mutants is now available in PIT's main code at https://github.com/hcoles/pitest/

~~We make Extended PITest available as a JAR file. Thus, it can be run Extended Pitest through Ant or the command line, just as the normal PITest.
Instructions for running PITest through Ant and the command line are available at http://pitest.org/quickstart/ . pitestPlus.jar then replaces the usual pitest.jar file.~~

~~As the current build of Extended PITest is based on an older version of PITest, we also provide the corresponding pitest-ant.jar and pitest-command-line.jar files. Also note that this version does not need the pitest-entry.jar file mentioned in the instructions.~~

The additional mutants can be used by passing their name to the mutators argument and are as follows:

    ABS: replaces a variable with its additive inverse
    AOD: replaces an arithmetic expression with each of its operands
    AOR: replaces an arithmetic operator with every other one
    CRCR: replaces a constant a with its additive inverse, or with 1, 0, a+1, a-1
    OBBN: replaces the operators & with | and vice versa
    ROR: replaces the a relational operator with every other one
    UOI: Replaces a variable with a unary operator or removes an instance of an unary operator

## Source code
The source code for the additional mutators is provided in the extended folder.
To add these mutators to your version of pit, place this folder in org/pitest/mutationtest/engine/gregor/mutators/experimental/ and register the mutators in org.pitest.mutationtest.engine.gregor.config.MUTATORS.
In order to use the AODMutator2 mutator, a getAccess() method should be added to org.pitest.mutationtes.engine.gregor.MethodInfo.
