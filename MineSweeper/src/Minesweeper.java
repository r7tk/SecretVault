import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Minesweeper {

    String RESET = "\u001B[0m";
    String SIYAH = "\u001B[30m";
    String KIRMIZI = "\u001B[31m";
    String YESIL = "\u001B[32m";
    String SARI = "\u001B[33m";
    String MAVI = "\u001B[34m";
    String MOR = "\u001B[35m";
    String CYAN = "\u001B[36m";
    String BEYAZ = "\u001B[37m";
    String BOLD = "\u001B[1m";
    // Class Özelliklerimiz

    String[][] gameBoard; // Oyun Alanı.
    int rows, columns;   // Oyun Alanının satır(rows) ve sütun(columns) miktarı.
    int mineCount; // Oyun alanı üzerindeki mayınların miktarı.
    int revealedMinesCount; // Ortaya çıkarılan mayın sayısı.

    boolean[][] openedCellsInBoard; // Açılan hücreleri tutan tahtamız.
    boolean isGameOver;


    //----------------------------------------------------------------------------------------------//

    // Constructor Methodumuz

    public Minesweeper(int rows, int columns) {  // Oyun Alanımızı ve Mayın Miktarını başlatan kurucu methodumuz.
        this.rows = rows;
        this.columns = columns;
        this.gameBoard = new String[rows][columns];   // Oyun Alanımız.
        this.mineCount = (rows * columns) / 4;       // Oyun alanının büyüklüğünün 1/4'ü kadar olan mayın sayısı.
        this.revealedMinesCount = 0;  // Ortaya çıkartılan mayın sayısı.
        this.openedCellsInBoard = new boolean[rows][columns]; // Açılan hücrelerin takip edilmesi.
        this.isGameOver = false; // Oyunun sona erme kontrolü

    }

    // Oyun Alanını oluşturan Method

    public void createBoard() {
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b < columns; b++) {
                gameBoard[a][b] = " "; // Boş Hücrelerin Oluşturulması
                openedCellsInBoard[a][b] = false; // Başlangıç hücrelerin kapalı olarak gelmesi.
            }
        }
    }

    // Mayınların Tahtaya Rastgele Yerleştirilmesi (DEĞERLENDİRME FORMU > TEMEL FONKSİYONLAR > MADDE 4)

    public void addMines() {
        Random random = new Random();  // Mayınları yerleştirmek için kullanacağımız Random objemiz.
        int addedMines = 0;

        while (addedMines < mineCount) {  // Mayınların yerleştirilmesi.
            int row = random.nextInt(rows);
            int col = random.nextInt(columns);

            // Mayınların Farklı Hücrelere Yerleştirilmesi
            if (!gameBoard[row][col].equals("*")) {
                gameBoard[row][col] = "*";
                addedMines++;
            }
        }
    }

    // Oyun Alanının Görüntülenmesi

    public void showGameBoard() {
        String unicodeString = "\uD83D\uDCA3"; // Bomba Sembolü :)

        System.out.println(SARI+BOLD+"####################################"+RESET);
        System.out.println(unicodeString + "          MAYIN TARLASI          " + unicodeString);
        System.out.println(SARI+BOLD+"####################################"+RESET);
        System.out.print("   ");

        for (int c = 0; c < columns; c++) {  // Sütun Numaraları
            System.out.printf("%4d ", c);
        }
        System.out.println();

        for (int d = 0; d < rows; d++) {  // Satır Numaraları
            System.out.printf("%2d| ", d);

            for (int e = 0; e < columns; e++) {
                if (openedCellsInBoard[d][e]) {
                    int mineCount = findAdjMines(d,e);
                    String colourNumb = "" ;
                    switch (mineCount){
                        case 1 :
                            colourNumb = MAVI;
                            break;
                        case 2 :
                            colourNumb = SARI;
                            break;
                        case 3 :
                            colourNumb = KIRMIZI;
                            break;
                        case 4 :
                            colourNumb = MOR;
                            break;
                        case 5:
                            colourNumb = SIYAH;
                            break;
                        default:
                            colourNumb = BEYAZ;
                            break;
                    }
                    System.out.printf("  " +BOLD+colourNumb+(mineCount == 0 ? "%s" : mineCount )+RESET+ "  ", gameBoard[d][e]); // HÜCRENİN ETRAFINDA KAÇ MAYIN VAR İSE KONTROL EDER MAYIN YOKSA İSE İLK GİRİLEN
                } else {                                                                                  // HÜCREYİ 0 OLARAK TANIMLAYIP ETRAFINI BOŞALTIR. MAYIN YAKININA DENK GELEN YERLERDE
                    System.out.print("  -  "); // Hücrenin kapalı olduğunda görüntüsü                     // İSE ETRAFINDA KAÇ MAYIN VARSA HÜCRE İÇİNE YAZDIRIR  ...
                }
            }

            System.out.println();
        }
    }

    // OYUNU BAŞLATMA METODU.

    public void startGame() {
        Scanner getDataFromUser = new Scanner(System.in); // Scanner objemiz

        while (!isGameOver) {     // Oyun bitene kadar devam eden döngümüz.
            showGameBoard();

            String rowSign = "\u2192";
            String colSign = "\u2193";


            System.out.print(YESIL+BOLD+"SATIR SAYISI GİRİNİZ  " + rowSign + " : "+RESET);
            int row = getDataFromUser.nextInt();
            System.out.print(YESIL+BOLD+"SÜTUN SAYISI GİRİNİZ  " + colSign + " : "+RESET);
            int col = getDataFromUser.nextInt();


            // KULLANICIDAN ALINAN DEĞERLERİN GEÇERSİZ OLMASI DURUMUNDA UYARI VERİP DÖNGÜNÜN DEVAM ETTİRİLMESİ
            if (row < 0 || row >= rows || col < 0 || col >= columns) {
                System.out.println("YANLIŞ DEĞER GİRDİNİZ LÜTFEN TEKRAR DENEYİNİZ .");
                continue;
            }

            // Kullanıcı daha önce açtığı bir bir hücreyi tekrar açmaya çalışıyorsa uyarı mesajı verilip döngünün devam ettirilmesi
            // KULLANICI DAHA ÖNCE AÇTIĞI HÜCREYİ TEKRAR AÇMAYA ÇALIŞIRSA UYARI VERİP DÖNGÜNÜN DEVAM ETTİRİLMESİ.
            if (openedCellsInBoard[row][col]) {
                System.out.println("BU HÜCRE DAHA ÖNCE AÇILDI TEKRAR DENEYİNİZ !!!");
                continue;
            }

            openedCellsInBoard[row][col] = true; // SEÇİLEN HÜCRENİN AÇIK OLARAK SEÇİLMESİ.

            if (gameBoard[row][col].equals("*")) {


                showGameBoard();  // Oyun tahtasının gösterilmesi
                Boom();


                // Oyun sonlanmadan önce kullanıcıya tekrar oynamak isteyip istemediğinin sorulması.
                String playAgain;
                Scanner getData = new Scanner(System.in);

                System.out.print("OYUNU TEKRAR OYNAMAK İÇİN (E) YAZIN SONLANDIRMAK İÇİN KLAVYEDEN HERHANGİ BİR TUŞA BASIP ENTERE BASINIZ . . .");
                playAgain = getData.nextLine();

                if (playAgain.equalsIgnoreCase("E")) {  // Oyuncunun tekrar oynamak istemesi durumu.
                    createBoard();
                    addMines();
                    showMinefield();
                    startGame();
                    getData.close();
                } else {
                    String sunSymbol = "\u2600";   // Oyuncunun oyunu sonlandırmak istemesi durumu.
                    System.out.println("MAYIN TARLASINI OYNADIĞINIZ İÇİN TEŞEKKÜRLER");
                    isGameOver = true;
                }
            } else {
                String dangerSign = "\u26A0";
                int minePerimeter = findAdjMines(row, col);
                System.out.println(SARI+BOLD+"####################################"+RESET);
                System.out.println(dangerSign + "         ETRAFTAKİ MAYINLAR " + dangerSign + " : " +BOLD+KIRMIZI+ minePerimeter+RESET);

                if (minePerimeter == 0) {  // Etrafında mayın olmaması durumunda boş olan hücrelerin açılması koşulu.(DEĞERLENDİRME FORMU > TEMEL FONKSİYONLAR > MADDE 8)
                    openEmptyCells(row, col);
                    gameBoard[row][col] = "\u001B[1m\u001B[31m+\u001B[0m"; // Sıfır ile işaretle
                }

                // Oyunun Kazanılması Durumunun Kontrolü
                if (isGameWon()) {
                    showGameBoard();
                    System.out.println("TEBRİKLER OYUNU BİR ŞEKİLDE KAZANDINIZ . . ."); // OYUNUN KAZANILMASI HALİNDE MESAJ)
                    isGameOver = true;

                }
            }
        }

        getDataFromUser.close();
    }

    // BU KISIMI GERÇEKTEN ANLAMADIM YARDIM ALDIM . ANLAMAK İÇİN DAHA SONRA TEKRAR YAPACAĞIM
    // ANLAMAMAKTAN KASITIM NASIL YAPILDIĞI YOKSA İÇ İÇE DÖNGÜLERİN ETRAFINI TARAYIP YAKININDAKİ OLAN MAYINLARI
    // HÜCRE İÇERİSİNE YAZDIRDIĞINI ANLADIM AHH HALA DÖNGÜLERİ TAM OTURTAMADIM :)
    public int findAdjMines(int row, int col) {  // Seçilen hücre etrafındaki mayın sayısını saydırtma...
        int mCount = 0 ; // Çevredeki mayın sayısını tutan değişken.

        for (int i = -1; i <= 1; i++) {  // Açılan Hücrenin yukarıdan aşağıya doğru tarayan döngü
            for (int j = -1; j <= 1; j++) {  // Açılan Hücrenin soldan sağa doğru tarayan döngü
                int mRow = row + i;  // Etraftaki hücrelerin satır koordinatları
                int mCol = col + j;  // Etraftaki hücrelerin sütun koordinatları

                if (mRow >= 0 && mRow < rows && mCol >= 0 && mCol < columns) { // Koordinatların oyun alanı içinde olması durumunda devam edilmesi
                    if (gameBoard[mRow][mCol].equals("*")) {  // Etrafta taranan hücrelerde mayın bulunma durumunda verilen mesajdaki mayın sayısını 1 arttırma.
                        mCount++;
                    }
                }
            }
        }

        return mCount; // ETRAFTA BULUNAN MAYIN SAYISININ DÖNDÜRÜLMESİ
    }

    public void openEmptyCells(int row, int col) {
        // SEÇİLEN HÜCRENİN ETRAFINI TARAYAN İÇ İÇE DÖNGÜMÜZ HALA OTURTAMADIM AMA ÇALIŞMAYA VE ANLAMAYA DEVAM
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int mRow = row + i;
                int mCol = col + j;

                if (mRow >= 0 && mRow < rows && mCol >= 0 && mCol < columns) {  // TARANAN ALANIN OYUN BOARDNIN İÇİNDE OLMASININ KONTROLÜ
                    if (!openedCellsInBoard[mRow][mCol]) {
                        openedCellsInBoard[mRow][mCol] = true;  // TARANAN HÜCREDEKİ ALAN DAHA ÖNCE AÇILMADI İSE AÇILMASI
                        int isMinesNear = findAdjMines(mRow, mCol); // AÇILAN HÜCRENİN ETRAFINDAKİ MAYINLARIN TARANMASI

                        if (isMinesNear == 0 ) {  // EĞER BU HÜCRENİN ETRAFINDA MAYIN YOKSA ETRAFININ TARANMASI
                            openEmptyCells(mRow, mCol);
                        }
                    }
                }
            }
        }
    }

// if m

    boolean isGameWon() {
        int closedCells = 0;

        // OYUN TAHTASINDAKİ HÜCRELERİN TARANMASI VE AÇILMAMIŞ HÜCRELERİN SAYILMASI

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!openedCellsInBoard[i][j]) {
                    closedCells++;
                }
            }
        }

        return closedCells == mineCount;  // HÜCRE SAYISI VE MAYIN SAYISI KAPATILAN HÜCRELERE EŞİTLENİNCE OYUNUN KAZANILMASI
    }


    // MAYINLARIN OYUN TAHTASININ OLUŞTURULMASINDAN SONRA EKLENEN MAYINLARI GÖSTERİYOR.
    // HIZLI KONTROL İÇİN EKLENDİ.

    public void showMinefield() {
        System.out.println(SARI+BOLD+"####################################"+RESET);
        System.out.println("  AÇIK HARİTALI MAYIN TARLASI       ");
        System.out.println(SARI+BOLD+"####################################"+RESET);

        // Sütun numaralarını göster
        System.out.print("   ");
        for (int j = 0; j < columns; j++) {
            System.out.printf("%4d ", j);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            // Satır numarasını göster
            System.out.printf("%2d| ", i);

            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j].equals("*")) {
                    System.out.print("  *  "); // MAYINLI HÜCRE ++ BURAYA BOMBA EKLEMEYİ ÇOK İSTERDİM UNİCODE İLE
                } else {
                    System.out.print("  -  "); // BOŞ HÜCRE BÖYLE .
                }
            }
            System.out.println();
        }



        }

    public void Boom() {
        String patlamaEfekti ="\n"+
                "   * .  * .  * . *\n" +
                        " * . * . * . * . * .\n" +
                        "   * . BOOM! .  * .\n" +
                        " * . * . * . * . * .\n" +
                        "   * .  * .  * . * "+"\n";

        System.out.println(patlamaEfekti);

        // PATLAMA EFEKTİ İLE BERABER SES DOSYASINI ÇAĞIRIYORUZ KAYBEDİLİNCE
        // BU KISMI COPY PASTE İLE ALDIM, EKLEDİM VE DOSYAYI MP4 TEN WAV'A ÇEVİRDİM VE EKLEDİM.
        try {
            String soundFilePath = "pixelboom.wav";
            playSound(soundFilePath);


            Thread.sleep(500); // BU KODU EKLEYENEYE KADAR SES GELMİYORDU. 0,5 SANİYE BEKLEME OLARAK AYARLADIM
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void playSound(String soundFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File soundFile = new File(soundFilePath);

        if (!soundFile.exists()) {
            System.err.println("Ses dosyası bulunamadı: " + soundFilePath);
            return;
        }

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
}
