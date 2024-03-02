package ascii_output;

public class OutputFactory {
    private final String DEFAULTFONT = "Courier New";
    private final String DEFAULTHTMLFILENAME = "out.html";
    
    public AsciiOutput getOutputMethod(String OutputMethod){
        switch (OutputMethod) {
            case "console":
                return new ConsoleAsciiOutput();
            case "html":
            return new HtmlAsciiOutput(DEFAULTHTMLFILENAME, DEFAULTFONT);
            default:
                return null;
        }
    }
}
