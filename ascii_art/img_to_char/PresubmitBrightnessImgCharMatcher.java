package ascii_art.img_to_char;

import ascii_art.Testing;
import image.Image;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class PresubmitBrightnessImgCharMatcher implements Testing {

    private static final String[] PUBLIC_METHODS = {"chooseChars", "char[][]",
            "[int arg0, java.lang.Character[] arg1]"};
    private static final String[] PRIVATE_METHODS = {"convertImageToAscii", "char[][]", "[]"};
    private static final String[] CONSTRUCTORS = {"ascii_art.img_to_char.BrightnessImgCharMatcher",
            "BrightnessImgCharMatcher", "[image.Image arg0, java.lang.String arg1]"};
    private static final String[] CONSTANT_FIELDS = {"cache"};
    private static final int NAME_INDEX = 0;
    private static final int RETURN_TYPE_INDEX = 1;
    private static final int PARAMETER_TYPES_INDEX = 2;


    public void test(){
        verifyExecutables();
        verifyFields();
        askedCheck();
    }

    private static void verifyFields() {
        boolean found;
        int mod;

        Field[] fields = BrightnessImgCharMatcher.class.getDeclaredFields();

        for (String name : CONSTANT_FIELDS) {
            found = false;
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.equals(name)) {
                    found = true;
                    mod = field.getModifiers();
                    if (Modifier.isStatic(mod))
                        System.out.printf("%s_should_not_be_static%n", name);
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

    private static void askedCheck(){
        Image img = Image.fromFile("/cs/course/current/oop/public_tests/ex4_2/board.jpeg");
        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Ariel");
        Character[] charset = {'m', 'o'};
        char[][] chars = charMatcher.chooseChars(2, charset);
        char[][] res = {{'m', 'o'}, {'o', 'm'}};
        if(chars.equals(res)){
            System.out.println("board.jpeg_with_2_chars_in_row_and_[m,o]_chars_doesn't_return_the_wanted_results");
        }
    }

    private static void verifyExecutables() {
        boolean found;
        int mod;
        String returnType;
        String parameterTypes;

        Constructor<?>[] constructors = BrightnessImgCharMatcher.class.getDeclaredConstructors();
        Executable[] methods = BrightnessImgCharMatcher.class.getDeclaredMethods();

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

        found = false;
        String n = PRIVATE_METHODS[NAME_INDEX];
        found = false;
        for (Executable executable : methods) {
            String execName = executable.getName();
            if (execName.equals(n)) {
                found = true;
                mod = executable.getModifiers();
                if (!Modifier.isPrivate(mod))
                    System.out.printf("%s_is_not_private%n", name);
                returnType = String.valueOf(executable.getAnnotatedReturnType().getType().getTypeName());
                if (!returnType.equals(PRIVATE_METHODS[RETURN_TYPE_INDEX])) {
                    System.out.printf("%s_wrong_return_type%n", name);
                    System.out.printf("expected:%s, got:%s\n", PRIVATE_METHODS[RETURN_TYPE_INDEX], returnType);
                }
            }
        }
        if (!found) {
            System.out.printf("not_found_the_method_%s%n", name);
        }

        String constructorName = CONSTRUCTORS[NAME_INDEX];
        found = false;
        for(Constructor<?> constructor: constructors){
            constructorName = constructor.getName();
            if(constructorName.equals(CONSTRUCTORS[NAME_INDEX])){
                found = true;
                mod = constructor.getModifiers();
                if(Modifier.isPrivate(mod)){
                    System.out.printf("$s_constructor_is_private", constructorName);
                }
            }
        }
        if (!found){
            System.out.printf("constructor_%s_not_found\n", constructorName);
        }
    }
}
