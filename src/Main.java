//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int account =1000;
        double btcAccount=0;
        Scanner scan=new Scanner(System.in);
        CryptoService service=new CryptoService();
String liveData=service.getLiveBicoinPrice();
double purePrice=service.parsePrice(liveData);
        System.out.printf("BTC PRİCE: $%.2f%n",purePrice);
        if(purePrice<60000){
            System.out.printf("The price has dropped; a buy order is being placed!");
            System.out.printf("you have "+account+"dolar money.Do you want to buy BTC with your whole money?\n1-Yes\n2-No\nAnswer:");
            int answer= scan.nextInt();
            if(answer==1){
               double btc = account/purePrice;
               btcAccount+=btc;
               account=0;

            }else if (answer==2) {
                System.out.printf("You can buy max:"+account/purePrice+"\nEnter amount to how much btc you want to buy");
                double amount=scan.nextDouble();
                if(amount <account/purePrice){
                    btcAccount+=amount;
                    account-=amount*purePrice;
                }

            }


        }

    }
}
class CryptoService{
    public String getLiveBicoinPrice(){
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