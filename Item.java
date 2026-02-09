public class Item {
    public enum Type {
        SILAH, IKSIR, KITAP, KEY
    }

    private String name;
    private String explanation;
    private Type type;
    private int value; //Silahsa hasar gücü, İksirse can verme miktari, Kitap ve anahtarsa bos deger

    public Item(String name, String explanation, Type type, int value) {
        this.name = name;
        this.explanation = explanation;
        this.type = type;
        this.value = value;
    }

    //Getter metotlari (verilere erisim)
    public String getName() {
        return name;
    }
    public String getAciklama() {
        return explanation;
    }
    public Type getType() {
        return type;
    }
    public int getValue() {
        return value;
    }
    public boolean isKey() {
        return type == Type.KEY;
    }
}