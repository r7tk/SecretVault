


/*@Author Ömer Faruk BARAN*/

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Süsleme sembolleri
        String bombSymbol = "\uD83D\uDCA3";
        String gameSymbol = "\uD83C\uDFAE";


        // SÜSLEME AŞAMASINDA KULLANMAK İSTEDİĞİM RENK SKALASI.

        String RESET = "\u001B[0m";
        String SIYAH = "\u001B[30m";
        String KIRMIZI = "\u001B[31m";
        String YESIL = "\u001B[32m";
        String SARI = "\u001B[33m";
        String MAVI = "\u001B[34m";
        String MOR = "\u001B[35m";
        String CYAN = "\u001B[36m";
        String BEYAZ = "\u001B[37m";


        // YAZIYI KALIN YAZDIRMAK İÇİN KULLANILACAK KOD CHAT-GPT SAĞOLSUN .

        String BOLD = "\u001B[1m";

        // Oyunun nasıl oynanacağı hakkında oyuncuya sunulan bilgiler
        System.out.println(SARI+BOLD+"#######################################################################################################################"+RESET);
        System.out.println(BOLD+CYAN+"\n"+
                "'  ### ###   ## ##    ##  ##     ##    ###  ##           ## ## ##  ## ##    ##  ##   ##       ## ##   ##   ##     ##'\n" +
                "'  #######  ##   ##    ####      ##    #### ##              ##    ##   ##   ##  ##   ##      ##   ##  ##          ##'\n" +
                "'  ## # ##  ##   ##     ##       ##    #######              ##    ##   ##   #####    ##      ##   ##   #####      ##'\n" +
                "'  ##   ##  #######     ##       ##    ## ####              ##    #######   ## ##    ##      #######       ##     ##'\n" +
                "'  ##   ##  ##   ##     ##       ##    ##  ###              ##    ##   ##   ## ##    ##  ##  ##   ##  ##   ##     ##'\n" +
                "'  ### ###  ##   ##    ####    ######  ##   ##             ####   ##   ##  #### ##  #######  ##   ##   #####    ######'\n" +
                "\n"+RESET);
        System.out.println(SARI+BOLD+"#######################################################################################################################"+RESET+"\n");
        System.out.println(SARI+"OYUNUN AMACI PATİKA'NIZA DÖŞENMİŞ MAYINLARA BASMADAN OYUNU BİTİREBİLMEKTİR =)" + "\n" +
                "NOT : UMARIM KURSUDA BAŞARILI ŞEKİLDE DE BİTİREBİLİRİM =P"+"\n"+
                "OYUNA BAŞLAYABİLMEK İÇİN SATIR VE SÜTUNLARI 0 DAN BÜYÜK OLACAK ŞEKİLDE , SATIR ve SÜTUNLARIN +\n"+
                "ÇARPIMININ TOPLAMI 4'ÜN KATLARI OLARAK ŞEKİLDE GİRMENİZ GEREKMEKTEDİR ."+"\n"+RESET);
        System.out.println(SARI+BOLD+"#######################################################################################################################"+RESET);
        System.out.println("\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02");
        Scanner scanner = new Scanner(System.in);

        int rows = 0;
        int columns = 0;

        // HARİTANIN BAŞLAYABİLMESİ İÇİN 0 DAN BÜYÜK OLMA ŞARTINA İLAVETEN ÇARPIMLARININDA 4'ÜN KATI OLMA KURALINI BURADA EKLEDİM .

        while (rows <= 0 || columns <= 0 || rows * columns < 4) {
            System.out.print(BOLD+MAVI+"SATIR SAYISINI GİRİNİZ : "+RESET);
            rows = scanner.nextInt();
            System.out.print(BOLD+MAVI+"SÜTUN SAYISINI GİRİNİZ : "+RESET);
            columns = scanner.nextInt();

            if (rows <= 0 || columns <= 0) {
                System.out.println(BOLD+KIRMIZI+"SATIR VE SÜTUN SAYISI 0'DAN BÜYÜK OLMAK ZORUNDADIR. ");
                continue;
                //return ;
            } else if (rows * columns < 4) {
                System.out.println(BOLD+KIRMIZI+"SATIR VE SÜTUN SAYISININ ÇARPIMI 4'ÜN KATLARI OLMAK ZORUNDADIR. ");
               continue;
                //return ;
            }
            {


                Minesweeper minesweeper = new Minesweeper(rows, columns);  // OLUŞTURULAN MİNESWEEPER NESNEMİZ.


                // OYUNUN TAHTASININ OLUŞTURULMASI ve MAYINLARIN EKLENMESİ
                minesweeper.createBoard();
                minesweeper.addMines();

                // OLUŞTURULAN MAYIN TARLASI
                minesweeper.showMinefield();

                // OYUNUN BAŞMLAMASI İÇİN GEREKEN CLASS .startgame
                minesweeper.startGame();

                scanner.close();
            }
        }
    }
}