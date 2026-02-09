import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private Room currentRoom;
    private int hp;
    private int basicAttackPower;
    private List<Item> inventory;
    private boolean hasTalkedMuhafiz = false;

    public Player(Room currentRoom, int hp) {
        this.currentRoom = currentRoom;
        this.hp = hp;
        this.basicAttackPower = 5;
        this.inventory = new ArrayList<>();
    }

    //oyuncu hayatta mi
    public boolean isAlive() {
        return hp > 0;
    }
    public void look() {
        currentRoom.showDetail();//suanda bulundugu odanin detaylarini goster
    }

    //NPC ile konusma
    public void talk(String npcName) {
        NPC npc = currentRoom.findNpc(npcName);
        if (npc == null) {
            System.out.println("Burada böyle biri yok.");
            return;
        }
        //NPC muhafiz ise
        if(npc.getName().equalsIgnoreCase("muhafiz") && !hasTalkedMuhafiz){
            System.out.println(npc.getName() + ": Depoda dikkatli ol, yüksek sesler var!");
            System.out.println("\tBu kılıcım. Al! Lazım olacaktır.");
            hasTalkedMuhafiz = true;
        } else {
            System.out.println(npc.getName() + ": " + npc.getDiyalog());
        }
    }

    //odalar arasi gecis
    public void go(String direction) {
        try {
            if(currentRoom.getExit(direction)==null){
                throw new Exception("O yöne gidemezsiniz!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        Room newRoom = currentRoom.getExit(direction);
        if (newRoom.getName().equalsIgnoreCase("garden")) {
            System.out.println("Anahtar lazım!");
            if (inventory.stream().anyMatch(i -> i.getType() == Item.Type.KEY)) {
                System.out.println("Anahtar kullan!");
            }else {
                System.out.println("Anahtar bul!");
            }
            return;
        }
        if (newRoom.getName().equalsIgnoreCase("warehouse")) {
            Scanner s=new Scanner(System.in);
            String password;
            System.out.println("Kapı şifreli, giremezsin!");
            System.out.println("Şifreyi çöz ve tekrar dene.");
            password=s.nextLine();
            if(password.equals("1995")){
                System.out.println("Şifre doğru, depoya girdin!");
            }else{
                System.out.println("Şifre yanlış, giremezsin!");
                return;
            }
        }
        if (newRoom != null) {
            currentRoom = newRoom;
            System.out.println("\n" + newRoom.getName() + " odasına geçildi.");
            look();
        }
    }

    //item alma
    public void takeItem(String itemName) {
        Item item = currentRoom.findItem(itemName);
        NPC npc = currentRoom.findNpc("Dev Fare");
        if (item != null) {
            if(item.getName().equalsIgnoreCase("kilic") && !hasTalkedMuhafiz){
                System.out.println("Muhafizdan kılıcı almadan önce izin almalısın!");
                return;
            }
            if(item.getName().equalsIgnoreCase("anahtar") && npc!=null){
                hp-=npc.getDamage();
                System.out.println("Dev Fare saldırıyor! Canın: " + hp);
                return;
            }
            inventory.add(item);
            currentRoom.removeItem(item);
            System.out.println(item.getName() + " alındı.");
            if (item.getType() == Item.Type.KITAP) {
                System.out.println("Tozlu bir kitap... Lazım olacak gibi.");
            }
        } else {
            System.out.println("Burada öyle bir eşya yok.");
        }
    }

    //envanteri goster
    public void inventoryShow() {
        System.out.println("----- Durum -----");
        System.out.println("Can: " + hp + " | Saldırı Gücü: " + totalAttackPower());
        System.out.println("\n----- Çanta -----");
        if (inventory.isEmpty())
            System.out.println("(Boş)");
        for (Item i : inventory) {
            System.out.println("-> " + i.getName() + " (" + i.getAciklama() + ")");
        }
    }

    //toplam saldiri gucu
    private int totalAttackPower() {
        int power = basicAttackPower;
        for (Item i : inventory) {
            if (i.getType() == Item.Type.SILAH) {
                power += i.getValue();
            }
        }
        return power;
    }

    //item kullanma
    public void useItem(String nameItem) {
        Item selectedItem = null;
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(nameItem)) {
                selectedItem = i;
                break;
            }
        }

        if (selectedItem == null) {
            System.out.println("Çantada bu eşya yok.");
            return;
        }

        if (selectedItem.getType() == Item.Type.IKSIR) {
            hp += selectedItem.getValue();
            if (hp > 100)
                hp = 100;
            inventory.remove(selectedItem);
            System.out.println("İksir içildi. Canın arttırıldı: " + hp);
        } else if (selectedItem.getType() == Item.Type.SILAH) {
            System.out.println("Bu bir silah. Saldırdığında otomatik kullanılır.");
        }else if (selectedItem.getType() == Item.Type.KEY) {
            if (currentRoom.getExit("west") != null &&
                currentRoom.getExit("west").getName().equalsIgnoreCase("garden")) {
                System.out.println("Anahtar kullanıldı. Bahçeye geçiş açıldı!");
                currentRoom = currentRoom.getExit("west");
                currentRoom.showDetail();
            } else {
                System.out.println("Bu bir anahtar. Cıkış kapısında kullanabilirsin.");
                System.out.println("Burada anahtar kullanılamaz.");
            }
        } else if (selectedItem.getType() == Item.Type.KITAP) {
            System.out.println("Deponun şifresi bende saklı. Java, orataya çıktığından beri.");
        } else {
            System.out.println("Bu eşya kullanılamaz.");
        }
    }

    //dusmana saldirma
    public void attack(String enemyName) {
        NPC enemy = currentRoom.findNpc(enemyName);
        
        if (enemy == null) {
            System.out.println("Burada böyle biri yok.");
            return;
        }

        if (!enemy.isEnemy()) {
            System.out.println(enemy.getName() + " sana saldırmıyor. Neden vuruyorsun?");
            return;
        }

        //dusmana hasar ver
        int damage = totalAttackPower();
        enemy.getTakeDamage(damage);
        System.out.println(enemy.getName() + " karakterine " + damage + " hasar verdin!");
        System.out.println("Kalan Canı: " + (enemy.isAlive() ? enemy.getHp(damage) : 0));

        //dusman olduyse odadan kaldir
        if (!enemy.isAlive()) {
            System.out.println(enemy.getName() + " öldü!");
            currentRoom.removeNpc(enemy);
        } else {
            //dusman oyuncuya saldirir
            int enemyDamage = enemy.getDamage();
            this.hp -= enemyDamage;
            System.out.println(enemy.getName() + " sana vurdu! Canın: " + hp);
        }
    }
}