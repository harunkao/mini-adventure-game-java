public class NPC {
    private String name;
    private String dialogue;
    private int hp;
    private boolean isEnemy;
    private int damage;

    public NPC(String name, String dialogue, int hp, boolean isEnemy, int damage) {
        this.name = name;
        this.dialogue = dialogue;
        this.hp = hp;
        this.isEnemy = isEnemy;
        this.damage = damage;
    }

    public boolean getFindNpc(String name) {
        return this.name.equalsIgnoreCase(name);
    }
    
    public int getHp(int amount) {
        return this.hp;
    }
    public void getTakeDamage(int amount) {
        this.hp -= amount;
    }
    public boolean isAlive() {
        return hp > 0;
    }
    public String getName() {
        return name;
    }
    public String getDiyalog() {
        return dialogue;
    }
    public boolean isEnemy() {
        return isEnemy;
    }
    public int getDamage() {
        return damage;
    }
}