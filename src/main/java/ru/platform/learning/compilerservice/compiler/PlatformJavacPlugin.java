package ru.platform.learning.compilerservice.compiler;


import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.util.Context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class PlatformJavacPlugin implements Plugin {

    public static final String NAME = "MyPlugin";

    private static Set<String> TARGET_TYPES = new HashSet<>(Arrays.asList(
        // Use only primitive types for simplicity
        byte.class.getName(), short.class.getName(), char.class.getName(), 
        int.class.getName(), long.class.getName(), float.class.getName(), double.class.getName()));

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void init(JavacTask task, String... args) {
        Context context = ((BasicJavacTask) task).getContext();
        task.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {
            }

            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() != TaskEvent.Kind.PARSE) {

                }
//                e.getCompilationUnit()
//                    .accept(new TreeScanner<Void, Void>() {
//                        @Override
//                        public Void visitMethod(MethodTree method, Void v) {
//                            List<VariableTree> parametersToInstrument = method.getParameters()
//                                .stream()
//                                .filter(PlatformJavacPlugin.this::shouldInstrument)
//                                .collect(Collectors.toList());
//                            if (!parametersToInstrument.isEmpty()) {
//                                // There is a possible case that more than one argument is marked by @Positive,
//                                // as the checks are added to the method's body beginning, we process parameters RTL
//                                // to ensure correct order.
//                                Collections.reverse(parametersToInstrument);
//                                parametersToInstrument.forEach(p -> addCheck(method, p, context));
//                            }
//                            // There is a possible case that there is a nested class declared in a method's body,
//                            // hence, we want to proceed with method body AST as well.
//                            return super.visitMethod(method, v);
//                        }
//                    }, null);
            }
        });
    }

//    private boolean shouldInstrument(VariableTree parameter) {
//        return TARGET_TYPES.contains(parameter.getType().toString())
//          && parameter.getModifiers().getAnnotations()
//            .stream()
//            .anyMatch(a -> Positive.class.getSimpleName().equals(a.getAnnotationType().toString()));
//    }
//
//    private void addCheck(MethodTree method, VariableTree parameter, Context context) {
//        JCTree.JCIf check = createCheck(parameter, context);
//        JCTree.JCBlock body = (JCTree.JCBlock) method.getBody();
//        body.stats = body.stats.prepend(check);
//    }
//
//    private static JCTree.JCIf createCheck(VariableTree parameter, Context context) {
//        TreeMaker factory = TreeMaker.instance(context);
//        Names symbolsTable = Names.instance(context);
//
//        return factory.at(((JCTree) parameter).pos)
//          .If(factory.Parens(createIfCondition(factory, symbolsTable, parameter)),
//            createIfBlock(factory, symbolsTable, parameter),
//            null);
//    }
//
//    private static JCTree.JCBinary createIfCondition(TreeMaker factory, Names symbolsTable, VariableTree parameter) {
//        Name parameterId = symbolsTable.fromString(parameter.getName().toString());
//        return factory.Binary(JCTree.Tag.LE,
//          factory.Ident(parameterId),
//          factory.Literal(TypeTag.INT, 0));
//    }
//
//    private static JCTree.JCBlock createIfBlock(TreeMaker factory, Names symbolsTable, VariableTree parameter) {
//        String parameterName = parameter.getName().toString();
//        Name parameterId = symbolsTable.fromString(parameterName);
//
//        String errorMessagePrefix = String.format("Argument '%s' of type %s is marked by @%s but got '",
//          parameterName, parameter.getType(), Positive.class.getSimpleName());
//        String errorMessageSuffix = "' for it";
//
//        return factory.Block(0, com.sun.tools.javac.util.List.of(
//          factory.Throw(
//            factory.NewClass(null, nil(),
//              factory.Ident(symbolsTable.fromString(IllegalArgumentException.class.getSimpleName())),
//                com.sun.tools.javac.util.List.of(factory.Binary(JCTree.Tag.PLUS,
//                  factory.Binary(JCTree.Tag.PLUS, factory.Literal(TypeTag.CLASS, errorMessagePrefix),
//                    factory.Ident(parameterId)),
//                    factory.Literal(TypeTag.CLASS, errorMessageSuffix))), null))));
//    }

}
