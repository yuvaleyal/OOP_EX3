package image;

import ascii_art.Testing;
import image.Image;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class PresubmitImage implements Testing {

    private static final String[][] PUBLIC_METHODS = {{"getWidth", "int", "[]"},
							{"getHeight", "int", "[]"},
							{"getPixel", "java.awt.Color", "[int arg0, int arg1]"}};
//    private static final String[] PRIVATE_METHODS = {};
    private static final String[] CONSTRUCTORS = {"image.FileImage",
            "FileImage", "[java.lang.String arg1]"};
    private static final String[] CONSTANT_FIELDS = {"DEFAULT_COLOR"};
    private static final int NAME_INDEX = 0;
    private static final int RETURN_TYPE_INDEX = 1;
    private static final int PARAMETER_TYPES_INDEX = 2;


    public void test(){
        verifyExecutables();
        verifyFields();
    }

    private static void verifyFields() {
        boolean found;
        int mod;

        Field[] fields = FileImage.class.getDeclaredFields();

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

    private static void verifyExecutables() {
        boolean found;
        int mod;
        String returnType;
        String parameterTypes;

        Constructor<?>[] constructors = FileImage.class.getDeclaredConstructors();
        Executable[] methods = FileImage.class.getDeclaredMethods();

	for (String[] methodDetails : PUBLIC_METHODS) {
		String name = methodDetails[NAME_INDEX];
        	found = false;
	        for (Executable executable : methods) {
        	    String execName = executable.getName();
	            if (execName.equals(name)) {
        	        found = true;
	                mod = executable.getModifiers();
        	        if (Modifier.isPrivate(mod))
                	    System.out.printf("%s_is_private_while_suppose_to_be_public%n", name);
	                returnType = String.valueOf(executable.getAnnotatedReturnType().getType().getTypeName());
        	        if (!returnType.equals(methodDetails[RETURN_TYPE_INDEX])) {
                	    System.out.printf("%s_wrong_return_type%n", name);
	                    System.out.printf("expected:%s, got:%s\n", methodDetails[RETURN_TYPE_INDEX], returnType);
        	        }
                	parameterTypes = Arrays.toString(executable.getParameters());
	                if (!parameterTypes.equals(methodDetails[PARAMETER_TYPES_INDEX])) {
        	            System.out.printf("%s_wrong_parameter_types%n", name);
                	    System.out.printf("expected:%s, got:%s\n", methodDetails[PARAMETER_TYPES_INDEX], parameterTypes);
	                }
        	    }
        	}
	        if (!found) {
        	    System.out.printf("not_found_the_method_%s%n", name);
        	}
	}
//        found = false;
//       String n = PRIVATE_METHODS[NAME_INDEX];
//        found = false;
//        for (Executable executable : methods) {
//            String execName = executable.getName();
//            if (execName.equals(n)) {
//                found = true;
//                mod = executable.getModifiers();
//                if (!Modifier.isPrivate(mod))
//                    System.out.printf("%s_is_not_private%n", name);
//                returnType = String.valueOf(executable.getAnnotatedReturnType().getType().getTypeName());
//                if (!returnType.equals(PRIVATE_METHODS[RETURN_TYPE_INDEX])) {
//                    System.out.printf("%s_wrong_return_type%n", name);
//                    System.out.printf("expected:%s, got:%s\n", PRIVATE_METHODS[RETURN_TYPE_INDEX], returnType);
//                }
//            }
//        }
//        if (!found) {
//            System.out.printf("not_found_the_method_%s%n", name);
//        }

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
