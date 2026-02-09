import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //odalari olustur
        Room garden = new Room("Garden", "Ferah bir bahçe, kuş sesleri duyuluyor.\nTebrikler! Başardın!");
        Room corridor = new Room("Corridor", "Meşalelerle aydınlanan taş bir koridor.");
        Room laboratory = new Room("Laboratory", "Eski deney aletleri ve iksirlerle dolu bir yer.");
        Room lounge = new Room("Lounge", "Eski koltukların olduğu dinlenme alanı.");
        Room library = new Room("Library", "Tozlu kitap rafları ve eski parşömenler.");
        Room warehouse = new Room("Warehouse", "Karanlık ve rutubetli, fare sesleri geliyor.");

        //odalari bagla
        corridor.addExit("west", garden);
        corridor.addExit("south", laboratory);
        laboratory.addExit("north", corridor);
        corridor.addExit("east", warehouse);
        warehouse.addExit("west", corridor);
        corridor.addExit("north", lounge);
        lounge.addExit("south", corridor);
        lounge.addExit("east", library);
        library.addExit("west", lounge);

        //item ekle
        laboratory.addItem(new Item("Can İksiri", "Kırmızı şifalı sıvı.", Item.Type.IKSIR, 50));
        lounge.addItem(new Item("Kilic", "Güçlü bir savaş silahı.", Item.Type.SILAH, 25));
        library.addItem(new Item("Kitap", "Gizemli bir bulmaca içeriyor.", Item.Type.KITAP, 0));
        warehouse.addItem(new Item("Anahtar", "Çıkış biletin.", Item.Type.KEY, 0));

        //NPC ekle
        lounge.addNpc(new NPC("Muhafiz", "Güzel gidiyorsun. Devam et!", 100, false,0));//dost
        warehouse.addNpc(new NPC("Dev Fare", "Hıssss!", 75, true, 35));//düşman

        //oyuncu olustur ve oyunu baslat
        Player player = new Player(corridor, 100);
        Scanner scanner = new Scanner(System.in);
        boolean period = true;

        System.out.println("\n\n<===  MİNİ MACERA OYUNU  ===>");
        System.out.println("Komutlar: help, look, talk [name], go [direction], take [item], use [item], attack [name], inv, durum, canta, quit");
        player.look();

        //oyun komut girdisi ve dongusu
        while (period && player.isAlive()) {
            System.out.print("\n>>> ");
            String line = scanner.nextLine().trim();
            String[] pieces = line.split(" ", 2);
            String command = pieces[0].toLowerCase();
            String argument = pieces.length > 1 ? pieces[1].toLowerCase() : "";

            switch (command) {
                case "help":
                    System.out.println("Komutlar: help, look, talk [name], go [direction], take [item], use [item], attack [name], inv, durum, canta, quit");
                    break;
                case "look": player.look();
                    break;
                case "talk": player.talk(argument);
                    break;
                case "go": player.go(argument);
                    break;
                case "take": player.takeItem(argument);
                    break;
                case "use": player.useItem(argument);
                    break;
                case "attack": player.attack(argument);
                    break;
                case "canta":
                case "durum":
                case "inv": player.inventoryShow();
                    break;
                case "quit":
                    period = false;
                    System.out.println("Çıkış yapılıyor...\nTeşekkürler!");
                    break;
                default: System.out.println("Geçersiz komut. Yardım için 'help' yazın.");
                    break;
            }
        }

        //oyuncu ölünce oyun biter
        if (!player.isAlive()) {
            System.out.println("ÖLDÜNÜZ! OYUN BİTTİ.");
        }
    }
}