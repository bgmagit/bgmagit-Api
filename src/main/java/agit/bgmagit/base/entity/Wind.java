package agit.bgmagit.base.entity;

public enum Wind {
    EAST("동장"),
    SOUTH("남장"),
    WEST("서장"),
    NORTH("북장");
    
    private final String korean;
    
    Wind(String korean) {
        this.korean = korean;
    }
    
    public String toKorean() {
        return korean;
    }
}
