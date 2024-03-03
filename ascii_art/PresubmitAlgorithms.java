package ascii_art;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;
import java.util.Arrays;
public class PresubmitAlgorithms implements Testing{

    private static final int NAME_INDEX = 0;
    private static final int RETURN_TYPE_INDEX = 1;
    private static final int PARAMETER_TYPES_INDEX = 2;
    private static final String[][] PUBLIC_METHODS = {{"findDuplicate", "int", "[int[] arg0]"},
            {"uniqueMorseRepresentations", "int", "[java.lang.String[] arg0]"}};

    public void test(){
        verifyExecutables();
    }

    private void verifyExecutables(){
        boolean found;
        int mod;
        String returnType;
        String parameterTypes;

        Executable[] methods = Algorithms.class.getDeclaredMethods();


        for (String[] method: PUBLIC_METHODS) {
            String name = method[NAME_INDEX];
            found = false;
            for (Executable executable : methods) {
                String execName = executable.getName();
                if (execName.equals(name)) {
                    found = true;
                    mod = executable.getModifiers();
                    if (Modifier.isPrivate(mod))
                        System.out.printf("%s_is_private_while_should_be_public%n", name);
                    returnType = String.valueOf(executable.getAnnotatedReturnType().getType().getTypeName());
                    if (!returnType.equals(method[RETURN_TYPE_INDEX])) {
                        System.out.printf("%s_wrong_return_type%n", name);
                        System.out.printf("expected:%s, got:%s\n", method[RETURN_TYPE_INDEX], returnType);
                    }
                    parameterTypes = Arrays.toString(executable.getParameters());
                    if (!parameterTypes.equals(method[PARAMETER_TYPES_INDEX])) {
                        System.out.printf("%s_wrong_parameter_types%n", name);
                        System.out.printf("expected:%s, got:%s\n", method[PARAMETER_TYPES_INDEX], parameterTypes);
                    }
                }
            }
            if (!found) {
                System.out.printf("not_found_the_method_%s%n", name);
            }
        }
    }
}
