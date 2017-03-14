# Extended PITest

This is a version of [PITest](http://pitest.org) with an extended set of mutation operators/individual transformations. We show in our recent papers that this extended set of mutants is more suitable than the original version of PITest and other mutation tools for scientific inquiries.

You can find more details about our work here:
 * Laurent, T., Papadakis, M., Kintis, M., Henard, C., le Traon, Y. and Ventresque, A., 2016. "Assessing and Improving the Mutation Testing Practice of PIT". International Conference on Software Testing, Verification and Validation. 
 * Coles, H., Laurent, T., Henard, C., Papadakis, M. and Ventresque, A., 2016. "PIT: a practical mutation testing tool for Java". International Symposium on Software Testing and Analysis (pp. 449-452). [Link](http://researchrepository.ucd.ie/handle/10197/7748)
 * Laurent, T., Ventresque, A., Papadakis, M., Henard, C. and Traon, Y.L., 2016. Assessing and improving the mutation testing practice of PIT. arXiv preprint arXiv:1601.02351. [Link](https://arxiv.org/abs/1601.02351)

## How to run Extended PITest?

 1. Follow the tutorial at this address: [PITest tutorial](http://pitest.org/quickstart/ant/)
 2. add the new jar (found in this GitHub repository) in the lib folder
 3. add a path element:
 <code>
 <path id="pitestPlus.path">
                <!-- must currently include the test library on the tool classpath. this will be fixed in a future version-->
                <pathelement location="lib/junit-4.9.jar" />
                <pathelement location="lib/pitest-ant-1.1.4.jar" />
                <pathelement location="lib/pitestPlus.jar" />
        </path>
 </code>
