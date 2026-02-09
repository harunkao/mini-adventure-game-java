import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String name;
    private String explanation;
    private Map<String, Room> exits; //yon-oda baglantisi
    private List<Item> items;
    private List<NPC> npcs;

    public Room(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.npcs = new ArrayList<>();
    }

    //odaya cikis ekleme
    public void addExit(String direction, Room room) {
        exits.put(direction, room);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    //item ekleme
    public void addItem(Item item) {
        items.add(item);
    }
    //item silme
    public void removeItem(Item item) {
        items.remove(item);
    }
    
    public Item findItem(String name) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }

    //NPC yonetimi
    public void addNpc(NPC npc) {
        npcs.add(npc);
    }
    public void removeNpc(NPC npc) {
        npcs.remove(npc);
    }
    
    public NPC findNpc(String name) {
        for (NPC n : npcs) {
            if (n.getName().equalsIgnoreCase(name))
                return n;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    //oda detaylarini goster
    public void showDetail() {
        System.out.println("\n----- " + name + " -----");
        System.out.println(explanation);
        
        if(!name.equalsIgnoreCase("garden")){
            System.out.print("Çıkışlar: ");
        }
        for (String e : exits.keySet())
            System.out.print(e + ", ");
        System.out.println();

        if (!items.isEmpty()) {
            System.out.print("Yerdeki Eşyalar | ");
            for (Item i : items)
                System.out.println(i.getName() + " --> (" + i.getAciklama() + ")");
            }
        
        if (!npcs.isEmpty()) {
            System.out.print("Karakterler | ");
            for (NPC n : npcs) {
                System.out.println(n.getName() + (n.isEnemy() ? " (Düşman)" : " (Dost)"));
            }
        }
    }
}