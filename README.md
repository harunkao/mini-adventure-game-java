# Mini Macera Oyunu (Mini Adventure Game) 🛡️

Java kullanılarak geliştirilen bu proje, klasik metin tabanlı (text-based) macera oyunlarının mantığını nesne yönelimli programlama ile simüle eder.

## 🕹️ Oyun Mekanikleri
- **Oda Sistemi:** `HashMap` yapısı kullanılarak odalar arası yön bağımlı geçişler (`go [direction]`) sağlanmıştır.
- **Envanter ve Etkileşim:** Oyuncu yerden eşya alabilir (`take`), kullanabilir (`use`) ve NPC'ler ile diyaloğa girebilir (`talk`).
- **Savaş ve Durum Yönetimi:** Düşman NPC'lere karşı saldırı gücü ve can (HP) yönetimini içeren temel bir combat sistemi bulunur.
- **Bulmaca Öğeleri:** Depoya girmek için şifre çözme ("1995") ve çıkış için anahtar bulma gibi mantıksal engeller içerir.

## 🛠️ Teknik Detaylar
- **Mimari:** Composition (Oda-Eşya ilişkisi) ve Association (Oyuncu-Oda ilişkisi) prensipleri uygulanmıştır.
- **Veri Yapıları:** Dinamik nesne yönetimi için `ArrayList` ve hızlı erişim için `Map/HashMap` kullanılmıştır.

## 🚀 Komutlar
`look`, `go`, `take`, `use`, `talk`, `attack`, `inv`, `durum`, `quit`
