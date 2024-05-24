
      import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

        public class Bombomodev1 {
            public static final int TAHTA_BOYUTU = 10;

            public static class Tahta {
                char[][] tahta = new char[TAHTA_BOYUTU][TAHTA_BOYUTU];

                // Tahta sınıfının yapıcı methodu
                public Tahta() {
                    try (BufferedReader br = new BufferedReader(new FileReader("/Users/furkanakkamis/IdeaProjects/bombomodev1/src/harita.txt"))) {
                        for (int satir = 0; satir < TAHTA_BOYUTU; ++satir) {
                            String satirOku = br.readLine();
                            if (satirOku != null) {
                                // Harita dosyasındaki satırı tahtaya yerleştir
                                for (int sutun = 0; sutun < Math.min(satirOku.length(), TAHTA_BOYUTU); ++sutun) {
                                    tahta[satir][sutun] = satirOku.charAt(sutun);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Tahtayı ekrana yazdıran method
                public void tahtayiYazdir() {
                    for (char[] satir : tahta) {
                        for (char eleman : satir) {
                            System.out.print(eleman + " ");
                        }
                        System.out.println();
                    }
                }

                // Koordinatların tahta sınırları içinde olup olmadığını kontrol eden method
                public boolean sinirlarIcindemi(int satir, int sutun) {
                    return satir >= 0 && satir < TAHTA_BOYUTU && sutun >= 0 && sutun < TAHTA_BOYUTU;
                }

                // Verilen koordinattan başlayarak belirli bir rengi 'x' ile değiştiren recursive method
                public void sekilDegistir(int x, int y, char hedefRenk, char yerineGecenRenk) {
                    if (!sinirlarIcindemi(x, y) || tahta[x][y] != hedefRenk) {
                        return;
                    }

                    tahta[x][y] = yerineGecenRenk;

                    int[] dx = {1, -1, 0, 0};
                    int[] dy = {0, 0, 1, -1};

                    for (int k = 0; k < 4; ++k) {
                        int nx = x + dx[k];
                        int ny = y + dy[k];

                        if (sinirlarIcindemi(nx, ny) && tahta[nx][ny] == hedefRenk) {
                            sekilDegistir(nx, ny, hedefRenk, 'x');
                        }
                    }
                }

                // Belirli bir grubu 'x' ile değiştiren method
                public void grupBulVeDegistir(int satir, int sutun, char hedefRenk) {
                    if (!sinirlarIcindemi(satir, sutun) || tahta[satir][sutun] != hedefRenk) {
                        return;
                    }

                    for (int i = -1; i <= 1; i += 2) {
                        int nx = satir + i;
                        int ny = sutun;
                        if (sinirlarIcindemi(nx, ny) && tahta[nx][ny] == hedefRenk) {
                            sekilDegistir(nx, ny, hedefRenk, 'x');
                        }

                        nx = satir;
                        ny = sutun + i;
                        if (sinirlarIcindemi(nx, ny) && tahta[nx][ny] == hedefRenk) {
                            sekilDegistir(nx, ny, hedefRenk, 'x');
                        }
                    }
                }
            }

            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                Tahta tahta = new Tahta();

                while (true) {
                    int satir = 0;
                    int sutun = 0;
                    System.out.println("OYUN TAHTASI:");
                    tahta.tahtayiYazdir();

                    System.out.print("Koordinatları giriniz (Satır Sütun)(Arada bir boşluk bırakarak giriniz.): ");
                    String koordinatlar = scanner.nextLine();

                    String[] koordinatDizi = koordinatlar.split(" ");

                    if (koordinatDizi.length == 2) {
                        try {
                            satir = Integer.parseInt(koordinatDizi[0]);
                            sutun = Integer.parseInt(koordinatDizi[1]);

                            // Koordinatları kullan
                            System.out.println("Satır: " + satir);
                            System.out.println("Sütun: " + sutun);
                        } catch (NumberFormatException e) {
                            System.out.println("Hatali giriş! Lütfen sayisal değerleri boşluk bırakarak girin.");
                        }
                    } else {
                        System.out.println("Hatali giriş! Lütfen iki değeri boşluk birakarak girin.");
                    }

                    if (satir == 0 && sutun == 0) {
                        System.out.println("Oyun sonlandirildi. İyi günler:))");
                        break;
                    }

                    --satir;
                    --sutun;

                    if (!tahta.sinirlarIcindemi(satir, sutun)) {
                        System.out.println("Geçersiz koordinatlar!! Lütfen satir ve sütun değerlerini 1-10 arasinda girin.");
                        continue;
                    }

                    char secilenRenk = tahta.tahta[satir][sutun];

                    tahta.grupBulVeDegistir(satir, sutun, secilenRenk);
                    System.out.println("Belirtilen renk ve kurallara uygun grup 'x' ile değiştirildi.");
                }
                scanner.close();
            }
        }

