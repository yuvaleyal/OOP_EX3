package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class PresumbitShell implements Testing{
    private static final int NAME_INDEX = 0;
    private static final int RETURN_TYPE_INDEX = 1;
    private static final int PARAMETER_TYPES_INDEX = 2;
    private static final String[] CONSTRUCTORS = {"ascii_art.Shell",
            "Shell", "[image.Image arg0]"};
    private static final String[] PUBLIC_METHODS = {"run", "void", "[]"};
    private static final String[] CONSTANT_FIELDS = {"MIN_PIXELS_PER_CHAR", "INITIAL_CHARS_IN_ROW", "FONT_NAME",
            "OUTPUT_FILENAME"};


    public void test(){
        verifyExecutables();
        verifyFields();
    }

    private void verifyExecutables(){
        boolean found;
        int mod;
        String returnType;
        String parameterTypes;

        Constructor<?>[] constructors = Shell.class.getDeclaredConstructors();
        Executable[] methods = Shell.class.getDeclaredMethods();

        String constructorName = CONSTRUCTORS[NAME_INDEX];
        found = false;
        for(Constructor<?> constructor: constructors){
            constructorName = constructor.getName();
            if(constructorName.equals(CONSTRUCTORS[NAME_INDEX])){
                found = true;
                mod = constructor.getModifiers();
                if(Modifier.isPrivate(mod)){
                    System.out.printf("$s_constructor_should_not_be_private", constructorName);
                }
                parameterTypes = Arrays.toString(constructor.getParameters());
                if (!parameterTypes.equals(CONSTRUCTORS[PARAMETER_TYPES_INDEX])) {
                    System.out.printf("%s_has_wrong_parameter_types%n", constructorName);
                    System.out.printf("expected:%s, got:%s\n", CONSTRUCTORS[PARAMETER_TYPES_INDEX], parameterTypes);
                }
            }
        }
        if (!found){
            System.out.printf("constructor_%s_not_found\n", constructorName);
        }

        String name = PUBLIC_METHODS[NAME_INDEX];
        found = false;
        for (Executable executable : methods) {
            String execName = executable.getName();
            if (execName.equals(name)) {
                found = true;
                mod = executable.getModifiers();
                if (Modifier.isPrivate(mod))
                    System.out.printf("%s_is_private_while_should_be_public%n", name);
                returnType = String.valueOf(executable.getAnnotatedReturnType().getType().getTypeName());
                if (!returnType.equals(PUBLIC_METHODS[RETURN_TYPE_INDEX])) {
                    System.out.printf("%s_wrong_return_type%n", name);
                    System.out.printf("expected:%s, got:%s\n", PUBLIC_METHODS[RETURN_TYPE_INDEX], returnType);
                }
                parameterTypes = Arrays.toString(executable.getParameters());
                if (!parameterTypes.equals(PUBLIC_METHODS[PARAMETER_TYPES_INDEX])) {
                    System.out.printf("%s_wrong_parameter_types%n", name);
                    System.out.printf("expected:%s, got:%s\n", PUBLIC_METHODS[PARAMETER_TYPES_INDEX], parameterTypes);
                }
            }
        }
        if (!found) {
            System.out.printf("not_found_the_method_%s%n", name);
        }





    }

    private static void verifyFields() {
        boolean found;
        int mod;

        Field[] fields = Shell.class.getDeclaredFields();

        for (String name : CONSTANT_FIELDS) {
            found = false;
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.equals(name)) {
                    found = true;
                    mod = field.getModifiers();
                    if (!Modifier.isStatic(mod))
                        System.out.printf("%s_should_be_static%n", name);
                    if (!Modifier.isFinal(mod))
                        System.out.printf("%s_should_be_final%n", name);
                    if (!Modifier.isPrivate(mod))
                        System.out.printf("%s_should_be_private%n", name);
                }
            }
            if (!found) {
                System.out.printf("%s_not_found%n", name);
            }
        }
    }
}
