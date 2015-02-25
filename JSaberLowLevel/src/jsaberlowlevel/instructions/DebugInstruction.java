package jsaberlowlevel.instructions;

public class DebugInstruction implements Instruction {
    private final String message;

    public DebugInstruction(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Debug instruction: " + message);
        
    }
    
}