package pl.eizodev.app.stock.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.eizodev.app.stock.model.StockCompanyDetails;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StockDataDTO {

    private final String ticker;

    private final String companyFullName;

    private final String companyOriginCountry;

    private final String stockExchange;

    private final String companyIndustry;

    private final String ipoDate;

    private final BigDecimal marketCapitalization;

    private final double shareOutstanding;

    private final StockQuoteDTO stockQuote;

    private final String lastUpdateDate;

    public static StockDataDTO createFrom(final StockCompanyDetails stockCompanyDetails) {
        final StockQuoteDTO stockQuoteDTO = StockQuoteDTO.createFrom(stockCompanyDetails.getStockQuote());

        return new StockDataDTO(stockCompanyDetails.getTicker(), stockCompanyDetails.getCompanyFullName(), stockCompanyDetails.getCompanyOriginCountry(),
            stockCompanyDetails.getStockExchange(), stockCompanyDetails.getCompanyIndustry(), stockCompanyDetails.getIpoDate(),
            stockCompanyDetails.getMarketCapitalization(), stockCompanyDetails.getShareOutstanding(), stockQuoteDTO,
            stockCompanyDetails.getStockQuote().getLastUpdateDate().toString());
    }
}