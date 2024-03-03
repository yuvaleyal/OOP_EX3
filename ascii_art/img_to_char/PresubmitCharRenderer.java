package ascii_art.img_to_char;

import ascii_art.Testing;

import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/*
This test verifies that the CharRenderer class has all requested fields with the appropriate modifiers
and all requested non-private methods with the appropriate signatures.
 */
public class PresubmitCharRenderer implements Testing {

    private static final String[] PUBLIC_METHODS = {"printBoolArr", "void", "[boolean[][] arg0]"};
    private static final int NAME_INDEX = 0;
    private static final int RETURN_TYPE_INDEX = 1;
    private static final int PARAMETER_TYPES_INDEX = 2;


    public void test(){
//        verifyFields();
        verifyExecutables();
    }

    private static void verifyExecutables() {
        boolean found;
        int mod;
        String returnType;
        String pType;

        Executable[] methods = CharRenderer.class.getDeclaredMethods();

        String name = PUBLIC_METHODS[NAME_INDEX];
        found = false;
        for (Executable executable : methods) {
            String execName = executable.getName();
            if (execName.equals(name)) {
                found = true;
                mod = executable.getModifiers();
                if (Modifier.isPrivate(mod))
                    System.out.printf("%s_is_private_while_suppose_to_be_public%n", name);
                returnType = String.valueOf(executable.getAnnotatedReturnType().getType().getTypeName());
                if (!returnType.equals(PUBLIC_METHODS[RETURN_TYPE_INDEX])) {
                    System.out.printf("%s_wrong_return_type%n", name);
                    System.out.printf("expected:%s, got:%s\n", PUBLIC_METHODS[RETURN_TYPE_INDEX], returnType);
                }
                pType = Arrays.toString(executable.getParameters());
                if (!pType.equals(PUBLIC_METHODS[PARAMETER_TYPES_INDEX])) {
                    System.out.printf("%s_wrong_parameter_types%n", name);
                    System.out.printf("expected:%s, got:%s\n", PUBLIC_METHODS[PARAMETER_TYPES_INDEX], pType);
                }
            }
        }
        if (!found) {
            System.out.printf("not_found_the_method_%s%n", name);
        }
    }

}
