package pl.eizodev.app.stockstats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import pl.eizodev.app.entities.Stock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Getter
@Setter
@NoArgsConstructor
public class StockFactory {

    public List<Stock> getAllStocksFromGivenIndex(String index) {
        String connectionLink = null;

        switch (index) {
            case "WIG20":
                connectionLink = "https://stooq.pl/t/?i=532";
                break;
            case "WIG40":
                connectionLink = "https://stooq.pl/t/?i=533";
                break;
            case "WIG80":
                connectionLink = "https://stooq.pl/t/?i=588";
                break;
        }
//            connectionLink = "https://stooq.pl/t/?i=606";
//        }

        List<Stock> stocks = new ArrayList<>();

        try {
            final Document doc = Jsoup.connect(connectionLink).get();
            String[] body = doc.body().html().split("\n");

            for (int i = 0; i < body.length; i++) {
                if (Pattern.matches(" {22}<tr id=\"r_[0-9]{1,2}\">", body[i])) {
                    String ticker = getTickerFromWeb(body[++i]);
                    String name = getNameFromWeb(body[++i]);
                    BigDecimal price = getPriceFromWeb(body[++i]);
                    String changePerc = getPercentageChangeFromWeb(body[++i]);
                    BigDecimal priceChange = getPriceChangeFromWeb(body[++i]);
                    String volume = getVolumeFromWeb(body[++i]);
                    String date = getUpdateDateFromWeb(body[++i]);
                    stocks.add(new Stock(index, ticker, name, price, changePerc, volume));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public Optional<Stock> getByTicker(List<Stock> stocks, String ticker) {
        return stocks.stream()
                .filter(o -> o.getTicker().equals(ticker))
                .findFirst();
    }

    private static final Pattern GET_TICKER_FROM_WEB_PATTERN = Pattern.compile(" {23}<td id=\"f13\" width=\"1%\"><b><a href=\"q/[?]s=[a-z0-9]{3}\">(.+?)</a></b></td>", Pattern.DOTALL);
    private static final Pattern GET_NAME_FROM_WEB_PATTERN = Pattern.compile(" {23}<td id=\"f10\" align=\"left\">(.+?)</td>", Pattern.DOTALL);
    private static final Pattern GET_PRICE_FROM_WEB_PATTERN = Pattern.compile(" {23}<td id=\"f13\"><b><span id=\"aq_[a-z0-9]{3}_c[0-4]\">(.+?)</span></b></td>", Pattern.DOTALL);
    private static final Pattern GET_PERCENTAGE_CHANGE_FROM_WEB_PATTERN = Pattern.compile(" {23}<td id=\"f13\" nowrap><b><span id=\"aq_[a-z0-9]{3}_m1\"><span id=\"c[0-4]\">(.+?)</span></span></b></td>", Pattern.DOTALL);
    private static final Pattern GET_PRICE_CHANGE_FROM_WEB_PATTERN = Pattern.compile(" {23}<td id=\"f13\" nowrap><span id=\"aq_[a-z0-9]{3}_m2\"><span id=\"c[0-4]\">(.+?)</span></span></td>", Pattern.DOTALL);
    private static final Pattern GET_VOLUME_FROM_WEB_PATTERN = Pattern.compile(" {23}<td id=\"f13\"><span id=\"aq_[a-z0-9]{3}_v2\">(.+?)</span></td>", Pattern.DOTALL);
    private static final Pattern GET_UPDATE_DATE_FROM_WEB_PATTERN = Pattern.compile(" {23}<td id=\"f13\" nowrap><span id=\"aq_[a-z0-9]{3}_t2\">(.+?)</span></td>", Pattern.DOTALL);

    private static String getTickerFromWeb(String body) {
        Matcher matcher = GET_TICKER_FROM_WEB_PATTERN.matcher(body);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    private static String getNameFromWeb(String body) {
        Matcher matcher = GET_NAME_FROM_WEB_PATTERN.matcher(body);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    private static BigDecimal getPriceFromWeb(String body) {
        Matcher matcher = GET_PRICE_FROM_WEB_PATTERN.matcher(body);

        if (matcher.find()) {
            return new BigDecimal(matcher.group(1));
        } else {
            return BigDecimal.ZERO;
        }
    }

    private static String getPercentageChangeFromWeb(String body) {
        Matcher matcher = GET_PERCENTAGE_CHANGE_FROM_WEB_PATTERN.matcher(body);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    private static BigDecimal getPriceChangeFromWeb(String body) {
        Matcher matcher = GET_PRICE_CHANGE_FROM_WEB_PATTERN.matcher(body);

        if (matcher.find()) {
            return new BigDecimal(matcher.group(1));
        } else {
            return BigDecimal.ZERO;
        }
    }

    private static String getVolumeFromWeb(String body) {
        Matcher matcher = GET_VOLUME_FROM_WEB_PATTERN.matcher(body);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    private static String getUpdateDateFromWeb(String body) {
        Matcher matcher = GET_UPDATE_DATE_FROM_WEB_PATTERN.matcher(body);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}