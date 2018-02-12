package dsl

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import static org.codehaus.groovy.syntax.Types.*

import java.lang.reflect.Array

/**
 * Created by Matthieu on 29/01/2018.
 */
class SensorDSL {
    private GroovyShell shell
    private CompilerConfiguration configuration
    private SensorBinding binding
    private SensorBasescript basescript

    SensorDSL() {
        binding = new SensorBinding()
        binding.setSensorModel(new SensorModel(binding));
        configuration = getDSLConfiguration()
        configuration.setScriptBaseClass("dsl.SensorBasescript")
        shell = new GroovyShell(configuration)
        binding.setVariable("mode", "autogenerated");
    }

    private static CompilerConfiguration getDSLConfiguration() {
        def secure = new SecureASTCustomizer()
        secure.with {
            //disallow closure creation
            closuresAllowed = true
            //disallow method definitions
            methodDefinitionAllowed = true
            //empty white list => forbid imports
            importsWhitelist = [
            'java.lang.*','java.util.List'
			]
            staticImportsWhitelist = []
            staticStarImportsWhitelist= []
            //language tokens disallowed
//			tokensBlacklist= []
            //language tokens allowed
            tokensWhitelist= [COMPARE_EQUAL,
                              COMPARE_NOT_EQUAL,
                              COMPARE_LESS_THAN,
                              COMPARE_LESS_THAN_EQUAL,
                              COMPARE_GREATER_THAN,
                              COMPARE_GREATER_THAN_EQUAL,
                              LEFT_SQUARE_BRACKET,
                              RIGHT_SQUARE_BRACKET,
                              MULTIPLY,
                                LOGICAL_AND,
                              LOGICAL_OPERATOR].asImmutable()
            //types allowed to be used  (including primitive types)
            constantTypesClassesWhiteList= [
                    int, Integer, Number, Integer.TYPE, String, Object, Array, Double, BigDecimal
			].asImmutable()
            //classes who are allowed to be receivers of method calls
            receiversClassesWhiteList= [
				int, Number, Integer, String, Object, Integer[], Double, BigDecimal
			].asImmutable()
        }

        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(secure)

        return configuration
    }

    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)
        binding.setScript(script)
        binding.setFileName(scriptFile.name)
        script.setBinding(binding)

        script.run()
    }
}
