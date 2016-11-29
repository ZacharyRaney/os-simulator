public class Memory {
    public static int size = 256000;
    private int available;

    public Memory() {
        available = size;
    }

    public boolean addProgram(int size) {
        if(size <= available) {
            available = available - size;
            return true;
        } else return false;
    }

    public void removeProgram(int size) {
        available += size;
    }
}
