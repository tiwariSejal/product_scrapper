import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class ProductScraper {
    public static void main(String[] args) throws IOException {
        ArrayList<String[]> products = new ArrayList<String[]>();
        
        
        String url = "https://www.staples.com/Computer-office-desks/cat_CL210795/663ea?icid=BTS:2020:STUDYSPACE:DESKS";
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("div.product-item");
        for (Element element : elements) {
            String name = element.select("div.product-item__info__title").text();
            String price = element.select("span.price--amount").text();
            String itemNumber = element.attr("data-sku");
            String modelNumber = element.attr("data-model-number");
            String category = doc.title();
            String description = element.select("div.product-item__description").text();
            String[] product = { name, price, itemNumber, modelNumber, category, description };
            products.add(product);
            if (products.size() == 10) {
                break;
            }
        }
        
        // Writing the product details to a CSV file
        String[] headers = { "Product Name", "Product Price", "Item Number", "Model Number", "Product Category", "Product Description" };
        CSVFormat format = CSVFormat.DEFAULT.withHeader(headers);
        CSVPrinter printer = new CSVPrinter(new FileWriter("product_details.csv"), format);
        for (String[] product : products) {
            printer.printRecord(product);
        }
        printer.close();
        
        System.out.println("Product details saved to product_details.csv");
    }

}



