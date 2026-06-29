# 📈 Live Crypto Trading Bot Simulation (Java)

A console-based Java application that connects to the live Binance REST API to fetch real-time cryptocurrency data and executes simulated trading logic based on dynamic market conditions.

## 🚀 Key Features & Engineering Concepts
* **Live API Integration:** Utilizes Java's modern `HttpClient` to send HTTP GET requests to the external Binance API, fetching real-time market data instantly without third-party libraries.
* **Manual JSON Parsing:** Extracts specific data points (e.g., BTC price) from complex JSON response payloads using precise String manipulation (`indexOf`, `substring`), converting raw text into actionable `double` values.
* **Algorithmic Decision Making:** Implements a localized wallet simulation (`account`, `btcAccount`) and dynamic condition checks. It automatically evaluates the live price against a target threshold (e.g., `< $60,000`) to trigger interactive buy orders.
* **Robust Control Flow:** Strict adherence to safe nested `if-else` architectures for transaction validation, avoiding abrupt program terminations and ensuring safe execution of mathematical logic.

## 🛠️ Technologies
* **Language:** Java (JDK 11+)
* **Networking:** `java.net.http.HttpClient`, `HttpRequest`, `HttpResponse`
* **Core Logic:** String manipulation, primitive data casting, interactive CLI (`Scanner`)

## 🏃‍♂️ How to Run
1. Clone this repository:
   ```bash
   git clone [https://github.com/Magruferolsarikaya/java-live-crypto-tracker.git](https://github.com/Magruferolsarikaya/java-live-crypto-tracker.git)
