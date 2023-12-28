# MİNESWEEP OYUNU PROJESİ

Java Console 2-D arayüzü ile oynayabilen Mayın Tarlası oyunu.

## Ekran Görüntüleri
Oyunun başlama Ekranı Aşağıdaki gibidir.
## Başlama Ekranı 
![startGameSrn](https://github.com/r7tk/SecretVault/assets/6598877/88f4af0f-93f7-4f75-9ea6-d10f8737b74a)

## ScreenShot -1  (bkz : Kod Bloğu : 65 )
  Bu kısımda Kullanıcı eğer yanlış değerler girerse ekrana uyarı çıktısı yazdırıp kullanıcıyı tekrardan
loop içine alarak doğru değerleri atamasını beklemekteyiz . Kullanıcıyı gireceği değeri Min Değer ataması 
ise sütun ve satır sayısının çarpımının 4 olacak şekilde ayarlandı.


![0error](https://github.com/r7tk/SecretVault/assets/6598877/2a821fe5-a7f9-44a4-abad-75aa43f11c9e)

## ScreenShot -2  (bkz : Kod Bloğu : 273 )

  Bu Kısımda Kullanıcıya girdiği değer sonrası oluşturulan harita ile beraber rastgele yerleştirilmiş mayınları 
harita kısmında gösteriyoruz . Her zaman Haritada yerleştirilen Mayın miktarı 4 te 1 'i kadardır.

![revealed](https://github.com/r7tk/SecretVault/assets/6598877/751cfe4f-d256-41a9-9efa-f5761242bb20)

## ScreenShot -3 (bkz : Kod Bloğu : 92 )

  Açtığımız Kısımlarde Etrafında Mayın varsa açılan yerin içine kaç mayın varsa Değeri giriyor 
Örnek ( Satır 12 Sütun 1 ) Eğer etrafında büyük bir boşluk varsa tarama sonucunda mayınlara denk gelene kadar açıyor.
Açılan kısım boşluk ile karışmaması için Kırmızı + ile işaretlendi . 
  Switch-Case ile beraber Mayın Sayılarını Renklendirerek Farkındalık kazandırıldı.
  
![mineField](https://github.com/r7tk/SecretVault/assets/6598877/e7aab9ed-2bdb-469c-ba4e-72d95bf41a79)

## ScreenShot -4 (bkz : Kod Bloğu : 253 )
  Boş olan hücrelerin tamamı seçilince eşitlik sağlanıyor. Çıktı olarak Kazandınız 

![WinFinish](https://github.com/r7tk/SecretVault/assets/6598877/97335bfb-5e9b-46af-9fb8-95170e78524f)

## ScreenShot -5 (bkz : Kod Bloğu : 162 )

            # !!! UYARI YÜKSEK SES UYARI !!! 

  Eğer Hücre satır ve sütun eşit ise (*) oyunu kaybetmiş oluyorsunuz . Ascii sanatı ile yapılmış
BOOM çıktısı ve pixeboom.wav ses dosyası çıkıyor .

![LossFinish](https://github.com/r7tk/SecretVault/assets/6598877/217443c9-f09d-428b-a59c-db3958712ac4)
