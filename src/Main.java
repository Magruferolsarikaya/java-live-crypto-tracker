//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        CryptoService service = new CryptoService();
        WalletService wallet = new WalletService();
        double[] savedBalances = wallet.readHistoryFromFile();
        double account = savedBalances[0];
        double btcAccount = savedBalances[1];
        String liveData = service.getLiveBitcoinPrice();
        double purePrice = service.parsePrice(liveData);
        System.out.printf("BTC PRİCE: $%.2f%n", purePrice);
        if (purePrice < 60000) {
            System.out.printf("The price has dropped; a buy order is being placed!");
            System.out.printf("you have " + account + "dolar money.Do you want to buy BTC with your whole money?\n1-Yes\n2-No\nAnswer:");
            int answer = scan.nextInt();
            if (answer == 1) {
                double btc = account / purePrice;
                btcAccount += btc;
                account = 0;
                wallet.saveTransactionToFile("BUY", btc, purePrice,account);
            } else if (answer == 2) {
                System.out.printf("You can buy max:" + account / purePrice + "\nEnter amount to how much btc you want to buy");
                double amount = scan.nextDouble();
                if (amount < account / purePrice) {
                    btcAccount += amount;
                    account -= amount * purePrice;
                    wallet.saveTransactionToFile("BUY", amount, purePrice,account);
                } else {
                    System.out.println("System: Insufficient balance!");
                }

            } else {
                System.out.println("System: Price is too high right now. Waiting for a dip.");


            }
            System.out.println("Current BTC Balance: " + btcAccount);
        }
    }
}
class CryptoService{
    public String getLiveBitcoinPrice(){
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT")).GET().build();
        try{
            HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (Exception e){
            return "Error"+e.getMessage();
        }
    }
    public double parsePrice(String jsonResponse){
        String searchingword="\"price\":\"";
        int startingIndex=jsonResponse.indexOf(searchingword)+searchingword.length();
        int finishingIndex=jsonResponse.indexOf("\"",startingIndex);
        String onlyNumbers= jsonResponse.substring(startingIndex,finishingIndex);
        return Double.parseDouble(onlyNumbers);
    }
}
class WalletService{
    public void saveTransactionToFile(String type,double amount,double price,double account){
        boolean success=false;
        try{
            BufferedWriter writer =new BufferedWriter(new FileWriter("trades.txt",true));
            String line=type+";"+amount+";"+price+";"+account;
            writer.write(line);
            writer.newLine();
            writer.close();
            success=true;
        }catch (IOException e){
            System.out.println("Error!problem was occur when file was written- "+e.getMessage());


        }
        if(success){
            System.out.println("System: Process saved permanent disk correctly.");
        }else{
            System.out.println("System:Saved process was failed but program is still working.");
        }

    }
    // void yerine double[] (Dizi) döndürüyoruz
    public double[] readHistoryFromFile() {
        boolean fileExists = true;
        double currentUsd = 1000.0; // Varsayılan başlangıç parası
        double currentBtc = 0.0;    // Varsayılan başlangıç BTC'si

        try {
            BufferedReader reader = new BufferedReader(new FileReader("trades.txt"));
            System.out.println("\n--- PROCESS HISTORY ---");
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                String[] parts = line.split(";");

                if (parts.length == 4) {
                    double islemMiktari = Double.parseDouble(parts[1]);
                    double kalanBakiye = Double.parseDouble(parts[3]);

                    currentBtc += islemMiktari;
                    currentUsd = kalanBakiye;
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            fileExists = false;
            System.out.println("System: There is no process history or file couldn't read. Starting with $1000.");
        }

        if (fileExists) {
            System.out.println("--- HISTORY END ---\n");
        }
        return new double[]{currentUsd, currentBtc};
    }
}